package com.escihu.apiescihuvirtual.Controller;

import com.escihu.apiescihuvirtual.persistence.Entity.Role;
import com.escihu.apiescihuvirtual.persistence.Entity.User;
import com.escihu.apiescihuvirtual.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/{username}/role")
    public ResponseEntity<String> changeRole(@PathVariable String username, @RequestBody Role role) {
        Optional<User> existUser = userService.findUserByUsername(username);
        if (existUser.isPresent()) {
            User user = existUser.get();
            userService.saveUser(user,role);
            return ResponseEntity.ok("Role changed");
        }
        return ResponseEntity.ok("Role changed");
    }
}
