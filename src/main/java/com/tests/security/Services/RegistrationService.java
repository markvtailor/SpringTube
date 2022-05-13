package com.tests.security.Services;


import com.tests.security.Beans.response.MessageResponse;
import com.tests.security.Beans.request.RegistrationRequest;
import com.tests.security.Entities.RoleEntity;
import com.tests.security.Entities.UserEntity;
import com.tests.security.Entities.UserRole;
import com.tests.security.Repositories.RoleRepository;
import com.tests.security.Repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class RegistrationService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    public ResponseEntity<?> register(RegistrationRequest registrationRequest){

        if(userRepository.findByEmail(registrationRequest.getEmail()).isPresent()){
            return ResponseEntity.badRequest().body(new MessageResponse("Email занят"));
        }
        if(userRepository.findByUsername(registrationRequest.getUsername()).isPresent()){
            return ResponseEntity.badRequest().body(new MessageResponse("Username занят"));
        }
        UserEntity user = new UserEntity(
                registrationRequest.getUsername(),
                registrationRequest.getEmail(),
                passwordEncoder.encode(registrationRequest.getPassword())
        );
        Set<String> strRoles = registrationRequest.getRole();
        Set<RoleEntity> roles = new HashSet<>();
        if(strRoles == null){
            RoleEntity userRole = roleRepository.findByName(UserRole.ROLE_USER).orElseThrow(() -> new RuntimeException("Такой роли нет"));
            roles.add(userRole);
        }else {
            strRoles.forEach( role -> {switch (role){
                case "admin":
                    RoleEntity adminRole = roleRepository.findByName(UserRole.ROLE_ADMIN).orElseThrow(() -> new RuntimeException("Такой роли нет"));
                    roles.add(adminRole);
                    break;
                default:
                    RoleEntity userRole = roleRepository.findByName(UserRole.ROLE_USER)
                            .orElseThrow(() -> new RuntimeException("Такой роли нет"));
                    roles.add(userRole);
            }
            });
        }
        user.setRoles(roles);
        userRepository.save(user);
        return ResponseEntity.ok("Регистрация успешна");
    }
}
