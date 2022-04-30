package com.tests.security.Services;

import com.tests.security.Beans.request.LoginRequest;
import com.tests.security.Beans.response.UserResponse;
import com.tests.security.Entities.RefreshTokenEntity;
import com.tests.security.Models.UserDetailsImpl;
import com.tests.security.access.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoginService {

    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @Autowired
    RefreshService refreshService;
    private final AuthenticationManager authenticationManager;
    public ResponseEntity<?> login(LoginRequest loginRequest){
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            String jwtToken = jwtTokenProvider.createAuthToken(userDetails);
            List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
            RefreshTokenEntity refreshToken = refreshService.createRefreshToken(userDetails.getId());
            return ResponseEntity.ok(new UserResponse(jwtToken, refreshToken.getToken(), userDetails.getId(),userDetails.getUsername(),userDetails.getEmail(),roles));
        }catch (AuthenticationException e){
            log.error(e.getLocalizedMessage());
        }
        return ResponseEntity.badRequest().body("Неверные данные");
    }
}
