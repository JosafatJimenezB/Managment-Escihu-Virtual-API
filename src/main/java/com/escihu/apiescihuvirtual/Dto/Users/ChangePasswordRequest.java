package com.escihu.apiescihuvirtual.Dto.Users;

import lombok.Getter;
import lombok.Setter;
/**
 * ChangePasswordRequest es una clase DTO que representa la solicitud de cambio de contraseña de un usuario.
 * Esta clase contiene los campos {@code currentPassword} , {@code newPassword} y  {@code confirmPassword}.
 * Se utiliza para transferir datos entre el controlador y el servicio.
 * <p>Ejemplo de uso:</p>
 * <pre>
 *     ChangePasswordRequest request = new ChangePasswordRequest();
 *     request.setCurrentPassword("contraseñaActual");
 *     request.setNewPassword("nuevaContraseña");
 *     request.setConfirmPassword("confirmarNuevaContraseña");
 * </pre>
 */
@Getter
@Setter
public class ChangePasswordRequest {
    /**
     * Campo de la contraseña actual
     */
    private String currentPassword;
    /**
     * Campo de la nueva contraseña
     */
    private String newPassword;
    /**
     * Campo de la confirmación de la nueva contraseña
     */
    private String confirmPassword;
}
