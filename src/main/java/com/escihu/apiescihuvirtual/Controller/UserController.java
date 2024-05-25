package com.escihu.apiescihuvirtual.Controller;

import com.escihu.apiescihuvirtual.Dto.Users.ChangePasswordRequest;
import com.escihu.apiescihuvirtual.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    //TODO: documentar
    @PatchMapping
    public ResponseEntity<?> changePassword(
            @RequestBody ChangePasswordRequest request,
            Principal connectedUser
    ) {
        userService.changePassword(request, connectedUser);
        return ResponseEntity.ok("Password changed");
    }
    //TODO: documentar
    @PutMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(
            @RequestParam String email
    ) {
        userService.forgotPassword(email);
        return ResponseEntity.ok("Email sent");
    }

    @PutMapping("/set-password")
    public ResponseEntity<String> setPassword(
            @RequestParam String email,
            @RequestHeader String password
    ) {
        userService.setPassword(email, password);
        return ResponseEntity.ok("Password changed");
    }

}
