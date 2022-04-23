package com.spring.tests.springtube.Controllers;

import com.spring.tests.springtube.access.CurrentUserProvider;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@AllArgsConstructor
public class AccessController {

    private final CurrentUserProvider currentUserProvider;

    @GetMapping("/access")
    public ResponseEntity<?> getAccess(){
        return ResponseEntity.ok(currentUserProvider.get().isEnabled()?"Access granted":"Forbidden");
    }
}
