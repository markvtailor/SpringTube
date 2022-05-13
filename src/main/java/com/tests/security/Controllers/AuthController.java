package com.tests.security.Controllers;

import com.tests.security.Beans.request.LoginRequest;
import com.tests.security.Beans.request.RefreshTokenRequest;
import com.tests.security.Beans.request.RegistrationRequest;
import com.tests.security.Beans.response.MessageResponse;
import com.tests.security.Entities.UserEntity;
import com.tests.security.Repositories.UserRepository;
import com.tests.security.Services.LoginService;
import com.tests.security.Services.RefreshService;
import com.tests.security.Services.RegistrationService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/user/auth")
@Slf4j
public class AuthController {
    @Autowired
    RegistrationService registrationService;
    @Autowired
    LoginService loginService;
    @Autowired
    RefreshService refreshService;
    @Autowired
    UserRepository userRepository;


    @PostMapping("/registration")
    public ResponseEntity<?> registration(@RequestBody RegistrationRequest registrationRequest, HttpServletResponse response){
        try {
            System.out.println(registrationRequest.getEmail());
            return registrationService.register(registrationRequest);
        }catch (Exception e){
            log.error(e.getLocalizedMessage());
            return buildErrorResponse(e.getLocalizedMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response){
        try {
            return loginService.login(loginRequest);
        }catch (Exception e){
            log.error(e.getLocalizedMessage());
            return buildErrorResponse(e.getLocalizedMessage());
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@Valid @RequestBody RefreshTokenRequest request) {
        try {
            return refreshService.newAuthToken(request);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            return buildErrorResponse(e.getLocalizedMessage());
        }
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserEntity> usersList(){
            return userRepository.findAll();
    }

    @Transactional
    @PostMapping("/logout")
    public ResponseEntity<?> logout (@Valid @RequestBody RefreshTokenRequest request){
        try {
            return  refreshService.deleteToken(request);
        }catch (Exception e){
            log.error(e.getLocalizedMessage());
            return  buildErrorResponse(e.getLocalizedMessage());
        }
    }
  /*  @GetMapping("/current")
    public ResponseEntity<?> current(){
        try{
            UserEntity user = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return buildUserResponse(user);
        }catch (NullPointerException e){
            log.error(e.getLocalizedMessage());
        }
        return buildUserResponse(new UserEntity());
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse httpServletResponse){
        clearTokens(httpServletResponse);
        SecurityContextHolder.clearContext();
        return buildUserResponse(new UserEntity());
    }



private void clearTokens(HttpServletResponse httpServletResponse) {
    Cookie authCookie = new Cookie(jwtTokenProvider.getAuthCookieName(), "-");
    authCookie.setPath(jwtTokenProvider.getCookiePath());
    Cookie refreshCookie = new Cookie(jwtTokenProvider.getRefreshCookieName(), "-");
    refreshCookie.setPath(jwtTokenProvider.getCookiePath());
    httpServletResponse.addCookie(authCookie);
    httpServletResponse.addCookie(refreshCookie);
}
private ResponseEntity<?> buildUserResponse(UserEntity user){
    return ResponseEntity.ok(new UserResponse(user));
}*/

    private ResponseEntity<?> buildErrorResponse(String message){
    return ResponseEntity.ok(new MessageResponse(message));
}
}
