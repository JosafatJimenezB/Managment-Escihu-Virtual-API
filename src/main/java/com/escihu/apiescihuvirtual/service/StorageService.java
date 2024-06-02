package com.escihu.apiescihuvirtual.service;

import com.escihu.apiescihuvirtual.exceptions.UsernameNotFoundException;
import com.escihu.apiescihuvirtual.persistence.Entity.User;
import com.escihu.apiescihuvirtual.persistence.Repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Servicio para almacenar imagenes en el sistema de archivos
 */
@Service
public class StorageService {

    private final UserRepository userRepository;
    private final Path rootLocation;

    /**
     * Constructor de la clase StorageService que inicializa el repositorio de usuarios y la ruta de almacenamiento
     *
     * @param userRepository el repositorio de usuarios
     * @throws RuntimeException si no se puede crear el directorio
     */
    public StorageService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.rootLocation = Paths.get("src/main/resources/static/images/profiles-images").toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("Could not create directory");

        }
    }

    /**
     * Almacena una imagen en el sistema de archivos y actualiza la URL de la imagen en la base de datos
     *
     * @param file {@link MultipartFile} el archivo a almacenar
     * @param userId el id del usuario al que pertenece la imagen
     * @throws IOException si no se puede almacenar el archivo
     */
    public void uploadImageToFileSystem(MultipartFile file, Long userId) throws IOException {
        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        String fileName = userId + "_" + file.getOriginalFilename();
        Path destinationFile = this.rootLocation.resolve(Paths.get(fileName)).normalize().toAbsolutePath();

        if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
            throw new IOException("Cannot store file outside current directory.");
        }

        Files.copy(file.getInputStream(), destinationFile);
        user.setProfileImageUrl(generateFileUrl(fileName)); // Set the URL in the user entity
        userRepository.save(user);
    }

    /**
     * Carga una imagen del sistema de archivos y regresa la URL de la imagen
     *
     * @param userId el id del usuario al que pertenece la imagen
     * @return la URL de la imagen
     */
    public String loadFromFileSystem(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return user.getProfileImageUrl();

    }

    /**
     * Carga una imagen del sistema de archivos
     *
     * @param userId el id del usuario al que pertenece la imagen
     * @return un arreglo de bytes con la imagen
     * @throws IOException si no se puede cargar la imagen
     */
    public byte[] loadImage(Long userId) throws IOException {
        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Path destinationFile = this.rootLocation.resolve(Paths.get(user.getProfileImageUrl()));
        System.out.println("File path: " + destinationFile);
        if (Files.exists(destinationFile)) {

            Files.readAllBytes(destinationFile);
        } else throw new IOException("File not found");
        return null;
    }

    /**
     * Genera la URL de un archivo
     *
     * @param filename el nombre del archivo
     * @return la URL del archivo
     */
    private String generateFileUrl(String filename) {
        return "/static/images/profiles-images/" + filename;
    }

}
