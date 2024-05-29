package com.escihu.apiescihuvirtual.service;

import com.escihu.apiescihuvirtual.exceptions.UsernameNotFoundException;
import com.escihu.apiescihuvirtual.persistence.Entity.ImageData;
import com.escihu.apiescihuvirtual.persistence.Entity.User;
import com.escihu.apiescihuvirtual.persistence.Repository.StorageRepository;
import com.escihu.apiescihuvirtual.persistence.Repository.UserRepository;
import com.escihu.apiescihuvirtual.utils.ImageUtils;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
public class StorageService {

    private final StorageRepository storageRepository;
    private final UserRepository userRepository;
    private final Path rootLocation;


    public StorageService(StorageRepository storageRepository, UserRepository userRepository) {
        this.storageRepository = storageRepository;
        this.userRepository = userRepository;
        this.rootLocation = Paths.get("../profile-images").toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("Could not create directory");

        }
    }

    @Transactional
    public String uploadImage(MultipartFile file, Long userId) throws IOException {


        ImageData imageData = storageRepository.save(ImageData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .data(ImageUtils.compressImage(file.getBytes()))
                .build());

        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setProfileImage(imageData);
        userRepository.save(user);
        return "Image uploaded successfully";
    }

    public byte[] downloadImage(String fileName) {
        Optional<ImageData> imageData = storageRepository.findByName(fileName);
        byte[] images = ImageUtils.decompressImage(imageData.get().getData());
        return images;
    }

    public String uploadImageToFileSystem(MultipartFile file, Long userId) throws IOException {
        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        String fileName = userId + "_" + file.getOriginalFilename();
        Path destinationFile = this.rootLocation.resolve(Paths.get(fileName)).normalize().toAbsolutePath();

        if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
            throw new IOException("Cannot store file outside current directory.");
        }

        Files.copy(file.getInputStream(), destinationFile);
        user.setProfileImageUrl(fileName);
        userRepository.save(user);
        generateFileUrl(fileName);

        return "File uploaded successfully";
    }

    public String loadFromFileSystem(Long userId)  {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return generateFileUrl(user.getProfileImageUrl());

    }

    public byte[] load(Long userId) throws IOException {
        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Path destinationFile = this.rootLocation.resolve(Paths.get(user.getProfileImageUrl()));
        System.out.println("File path: " + destinationFile);
        if(Files.exists(destinationFile))
        {

            Files.readAllBytes(destinationFile);
        } else throw new IOException("File not found");
        return null;
    }

    private String generateFileUrl(String filename) {
        return rootLocation.resolve(filename).toUri().toString();
    }


}
