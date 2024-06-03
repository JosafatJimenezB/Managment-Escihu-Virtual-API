package com.escihu.apiescihuvirtual.service.user;

import com.escihu.apiescihuvirtual.Dto.Users.ChangePasswordRequest;
import com.escihu.apiescihuvirtual.Dto.Users.UserDtoResponse;
import com.escihu.apiescihuvirtual.persistence.Entity.Role;
import com.escihu.apiescihuvirtual.persistence.Entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.security.Principal;
import java.util.Optional;

public interface UserService {

    /**
     * Lista los usuarios paginados.
     *
     * @param pageable el objeto pageable
     * @return un objeto Page con los usuarios
     */
    Page<UserDtoResponse> listUsersPaginated(Pageable pageable);

    /**
     * Encuentra un usuario por su username.
     *
     * @param username el username del usuario a buscar
     * @return un objeto Optional con el usuario si existe o un objeto vacio si no existe
     */
    Optional<User> findUserByUsername(String username);

    /**
     * Guardar un usuario. El usuario se guarda con el rol especificado.
     * El rol del usuario se agrega a un conjunto de roles, que luego se asigna al usuario.
     * El usuario se guarda en el UserRepository.
     *
     * @param user {@link User} el usuario a guardar
     * @param role {@link Role} el rol del usuario
     */
    void saveUser(User user, Role role);


    /**
     * Cambia la contraseña de un usuario del usuario authenticado
     * Changes the role of a user with the specified username.
     *
     * @param request       {@link ChangePasswordRequest } la solicitud de cambio de contraseña
     * @param connectedUser el usuario autenticado
     */
    void changePassword(ChangePasswordRequest request, Principal connectedUser);

    /**
     * Envia un correo electronico al usuario que olvido su contraseña.
     *
     * @param email el correo electronico del usuario
     */
    void forgotPassword(String email);

    /**
     * Cambia la contraseña de un usuario.
     *
     * @param email    a string with the user email
     * @param password a string the new password
     */
    void setPassword(String email, String password);

    Optional<User> findUserById(Long userId);
}
