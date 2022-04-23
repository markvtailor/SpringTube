package com.spring.tests.springtube.access;

import com.google.gson.Gson;
import com.spring.tests.springtube.Beans.CurrentUser;
import com.spring.tests.springtube.Providers.JwtSettingsProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@Component
@Slf4j
@AllArgsConstructor
public class AccessFilter implements Filter  {

    private final JwtSettingsProvider jwtSettingsProvider;
    private final CurrentUserProvider currentUserProvider;
    private final Gson gson = new Gson();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        CurrentUser currentUser = null;
        String authToken = null;
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies != null){
            for (Cookie cookie:
                 cookies) {
                if(cookie.getName().equals(jwtSettingsProvider.getCookieAuthTokenName()));{
                    authToken = cookie.getValue();
                }
            }
        }
        if(authToken != null && !authToken.isEmpty()){
            currentUser = fetchRemoteUser(httpServletRequest);
        }
    }

    private CurrentUser fetchRemoteUser(HttpServletRequest httpServletRequest) {
        try {
            URL url = new URL("http://localhost:8090/user/auth/current"); //заменить на переменную
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(2000);
            connection.setReadTimeout(1000);
            connection.setRequestProperty("Cookie",httpServletRequest.getHeader("Cookie"));
            connection.setRequestProperty("Content-Type","application/json");
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null){
                content.append(inputLine);
            }
            in.close();
            connection.disconnect();

            return gson.fromJson(content.toString(),CurrentUser.class);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return new CurrentUser();
    }
}
