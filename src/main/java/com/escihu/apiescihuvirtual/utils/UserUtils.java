package com.escihu.apiescihuvirtual.utils;

import com.escihu.apiescihuvirtual.persistence.Entity.User;
import com.escihu.apiescihuvirtual.persistence.Repository.UserRepository;

import java.security.SecureRandom;
import java.util.Optional;

public class UserUtils {


    public static String generateEmail(String nombre, String apellidoPaterno, UserRepository userRepository) {
        String correo = "";
        String[] splitName = nombre.split(" ");
        correo += splitName[0].toLowerCase() + "." + apellidoPaterno.toLowerCase()  + "@escihupuebla.edu.mx";
        Optional<User> existEmailUser = userRepository.findByEmail(correo);
        int counter = 1;
        while(existEmailUser.isPresent()) {
            correo = splitName[0].toLowerCase() + "." + apellidoPaterno.toLowerCase() + counter + "@escihupuebla.edu.mx";
            counter++;
            existEmailUser = userRepository.findByEmail(correo);
        }

        return correo;
    }

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_=+";
    private static final int PASSWORD_LENGTH = 12; // Longitud deseada de la contraseña

    public static String generateRandomPassword() {
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(PASSWORD_LENGTH);

        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            password.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }

        return password.toString();

    }
}
