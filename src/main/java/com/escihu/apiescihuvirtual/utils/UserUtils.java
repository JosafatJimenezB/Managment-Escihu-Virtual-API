package com.escihu.apiescihuvirtual.utils;

import com.escihu.apiescihuvirtual.persistence.Entity.User;
import com.escihu.apiescihuvirtual.persistence.Repository.UserRepository;

import java.security.SecureRandom;
import java.util.Optional;

/**
 * Clase con métodos útiles para la entidad {@link User}. Proporciona dos métodos estáticos como
 * generadores de correo electrónico y contraseñas aleatorias.
 * <ul>
 *     <li>{@link #generateEmail(String, String, UserRepository)} Genera un correo electrónico a partir del nombre y apellido paterno del usuario</li>
 *     <li>{@link #generateRandomPassword()} Genera una contraseña aleatoria</li>
 * </ul>
 *
 */
public class UserUtils {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_=+";
    private static final int PASSWORD_LENGTH = 12; // Longitud deseada de la contraseña


    /**
     * Genera un correo electrónico a partir del nombre y apellido paterno del usuario
     * en caso de que el correo ya exista, se le agrega un número al final
     *
     * @param nombre El nombre del usuario
     * @param apellidoPaterno El apellido paterno del usuario
     * @param userRepository Repositorio de usuarios
     * @return Un string con el correo electrónico generado
     */
    public static String generateEmail(String nombre, String apellidoPaterno, UserRepository userRepository) {
        String correo = "";
        correo += nombre.toLowerCase() + "." + apellidoPaterno.toLowerCase() + "@escihu.com";
        Optional<User> existEmailUser = userRepository.findByEmail(correo);
        int counter = 1;
        while (existEmailUser.isPresent()) {
            correo = nombre.toLowerCase() + "." + apellidoPaterno.toLowerCase() + counter + "@escihu.com";
            counter++;
            existEmailUser = userRepository.findByEmail(correo);
        }

        return correo;
    }

    /**
     * Genera una contraseña aleatoria
     *
     * @return un string con la contraseña aleatoria
     */
    public static String generateRandomPassword() {
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(PASSWORD_LENGTH);

        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            password.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }

        return password.toString();

    }
}
