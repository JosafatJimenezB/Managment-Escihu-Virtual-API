package com.escihu.apiescihuvirtual.Controller;

import com.escihu.apiescihuvirtual.Dto.Users.ChangePasswordRequest;
import com.escihu.apiescihuvirtual.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * Controlador de usuarios
 *
 * <p>
 * Este controlador contiene los métodos para cambiar la contraseña de un usuario.
 * </p>
 *
 */
@Tag(name = "Controlador de usuarios")
@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Cambia la contraseña del usuario autenticado.
     *
     * <p>
     * Este método permite al usuario autenticado cambiar su contraseña.
     * </p>
     *
     * @param request {@link ChangePasswordRequest} la solicitud de cambio de contraseña del usuario
     * @param connectedUser {@link Principal} el usuario conectado
     *
     */
    @Operation(summary = "Cambia la contraseña del usuario conectado",
    description = "Cambia la contraseña del usuario conectado usando su usuario para autenticarse")
    @PatchMapping
    public ResponseEntity<?> changePassword(
            @RequestBody ChangePasswordRequest request,
            Principal connectedUser
    ) {
        userService.changePassword(request, connectedUser);
        return ResponseEntity.ok("Password changed");
    }

    /**
     * Envía un correo para cambiar la contraseña.
     *
     * <p>
     * Este método permite enviar un correo para cambiar la contraseña del usuario sin tener que autenticarse.
     * </p>
     *
     * @param email un string con el correo del usuario
     *
     */
    @Operation(summary = "Envia un correo para cambiar la contraseña",
    description = "Envia un correo para cambiar la contraseña del usuario sin tener que autenticarse")
    @PutMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(
            @RequestParam String email
    ) {
        userService.forgotPassword(email);
        return ResponseEntity.ok("Email sent");
    }

    /**
     * Cambia la contraseña del usuario.
     *
     * <p>
     * Este método permite al usuario cambiar su contraseña.
     * </p>
     *
     * @param email un string con el correo del usuario
     * @param password un string la nueva contraseña
     *
     */
    @Operation(summary = "Cambia la contraseña del usuario",
    description = "Cambia la contraseña del usuario usando su correo para autenticarse")
    @PutMapping("/set-password")
    public ResponseEntity<String> setPassword(
            @RequestParam String email,
            @RequestHeader String password
    ) {
        userService.setPassword(email, password);
        return ResponseEntity.ok("Password changed");
    }

}
