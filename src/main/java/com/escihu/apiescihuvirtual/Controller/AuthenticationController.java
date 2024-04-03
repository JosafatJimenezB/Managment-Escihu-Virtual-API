package com.escihu.apiescihuvirtual.Controller;

import com.escihu.apiescihuvirtual.Dto.LoginRequestDTO;
import com.escihu.apiescihuvirtual.Dto.LoginResponse;
import com.escihu.apiescihuvirtual.Dto.RegistrationDTO;
import com.escihu.apiescihuvirtual.exceptions.UsernameNotFoundException;
import com.escihu.apiescihuvirtual.service.AuthenticationService;
import com.escihu.apiescihuvirtual.service.TokenService;
import com.escihu.apiescihuvirtual.utils.CookieUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final TokenService tokenService;

    public AuthenticationController(AuthenticationService authenticationService, TokenService tokenService) {
        this.authenticationService = authenticationService;
        this.tokenService = tokenService;
    }

    @PostMapping("/register")
    // TODO: Exception en caso del correo sea el mismo
    public ResponseEntity<String> registerUser(@Valid @RequestBody RegistrationDTO registrationDTO) {
        try {
            authenticationService.registerUser(registrationDTO.username(), registrationDTO.email(), registrationDTO.password(), registrationDTO.userAsigned());
            return ResponseEntity.ok("User registered successfully");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(HttpServletResponse res, @Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        try {
            LoginResponse user = authenticationService.loginUser(loginRequestDTO.username(), loginRequestDTO.password());

   //         CookieUtil.create(res, "Authorization", user.token(), false, -1, "localhost");
  //          res.addHeader("Authorization", "Bearer " + user.token());

            Cookie cookie = new Cookie("token", user.token());

            cookie.setMaxAge(60);
            cookie.setHttpOnly(true);
            cookie.setSecure(true);

            res.addCookie(cookie);

            res.addHeader("Authorization", "Bearer " + user.token());

            return ResponseEntity.ok(user);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
