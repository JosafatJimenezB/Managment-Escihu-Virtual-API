package com.escihu.apiescihuvirtual.Controller;

import com.escihu.apiescihuvirtual.Dto.Message;
import com.escihu.apiescihuvirtual.persistence.Entity.Role;
import com.escihu.apiescihuvirtual.persistence.Entity.User;
import com.escihu.apiescihuvirtual.service.user.UserServiceImpl;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    private final UserServiceImpl userServiceImpl;

    public AdminController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @PutMapping("/{username}/role")
    public ResponseEntity<String> changeRole(@PathVariable String username, @RequestBody Role role) {
        Optional<User> existUser = userServiceImpl.findUserByUsername(username);
        if (existUser.isPresent()) {
            User user = existUser.get();
            userServiceImpl.saveUser(user, role);
            return ResponseEntity.ok("Role changed");
        }
        return ResponseEntity.ok("Role changed");
    }

    @GetMapping("/list_users")
    public ResponseEntity<?> listUsers(
            @RequestParam(defaultValue = "0") int currentPage,
            @RequestParam(defaultValue = "10") int pageSize) {
        try {
            Pageable modifiedPage = PageRequest.of(currentPage, pageSize);
            return new ResponseEntity<>(userServiceImpl.listUsersPaginated(modifiedPage), HttpStatus.OK);
        } catch (DataAccessException e) {
            return new ResponseEntity<>(Message.builder()
                    .message(e.getMessage())
                    .object(null)
                    .build(), HttpStatus.BAD_REQUEST);
        }
    }
}
