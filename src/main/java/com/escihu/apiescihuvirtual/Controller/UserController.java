package com.escihu.apiescihuvirtual.Controller;

import com.escihu.apiescihuvirtual.Dto.Message;
import com.escihu.apiescihuvirtual.Dto.Users.ChangePasswordRequest;
import com.escihu.apiescihuvirtual.persistence.Entity.User;
import com.escihu.apiescihuvirtual.service.StorageService;
import com.escihu.apiescihuvirtual.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Controlador de usuarios
 *
 * <p>
 * Este controlador contiene los métodos para cambiar la contraseña de un usuario.
 * </p>
 */
@Tag(name = "Controlador de usuarios")
@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;
    private final StorageService storageService;
    private final Logger logger = org.slf4j.LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService, StorageService storageService) {
        this.userService = userService;
        this.storageService = storageService;
    }

    /**
     * Cambia la contraseña del usuario autenticado.
     *
     * <p>
     * Este método permite al usuario autenticado cambiar su contraseña.
     * </p>
     *
     * @param request       {@link ChangePasswordRequest} la solicitud de cambio de contraseña del usuario
     * @param connectedUser {@link Principal} el usuario conectado
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
     * @param email    un string con el correo del usuario
     * @param password un string la nueva contraseña
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

    @PostMapping("/upload/{userId}")
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file, @PathVariable Long userId) throws IOException {
        storageService.uploadImageToFileSystem(file, userId);
        return ResponseEntity.ok("File uploaded successfully:");
    }


    @GetMapping("/profile-image/{userId}")
    public ResponseEntity<byte[]> getUserProfileImage(@PathVariable Long userId) throws IOException {
        byte[] imageData = storageService.loadImage(userId);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG) // Ajusta el tipo de contenido según tu imagen
                .body(imageData);
    }


    @GetMapping("/profile-image-url/{userId}")
    public ResponseEntity<?> getUserProfileImageUrl(@PathVariable Long userId) {
        String imageUrl = storageService.loadFromFileSystem(userId);
        Map<String, String> response = new HashMap<>();
        response.put("imageUrl", imageUrl);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> findUserById(@PathVariable Long userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        logger.info("Current username {}", currentUsername);

        // Obtener los roles del usuario autenticado
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        boolean isAdmin = authorities.stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));

        // Buscar el usuario por ID
        Optional<User> optionalUser = userService.findUserById(userId);

        // Verificar si el usuario existe
        if (!optionalUser.isPresent()) {
            return new ResponseEntity<>(Message.builder()
                    .message("User not found")
                    .object(null)
                    .build(), HttpStatus.NOT_FOUND);
        }

        User user = optionalUser.get();
        String userUsername = user.getUsername(); // Suponiendo que `getUsername()` devuelve el nombre de usuario del `User`

        // Verificar si el usuario autenticado es el mismo usuario solicitado o si es un administrador
        if (!isAdmin && !userUsername.equals(currentUsername)) {
            return new ResponseEntity<>(Message.builder()
                    .message("Access denied")
                    .object(null)
                    .build(), HttpStatus.FORBIDDEN);
        }

        return ResponseEntity.ok(user);
    }


}
