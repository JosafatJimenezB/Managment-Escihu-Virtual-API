package com.escihu.apiescihuvirtual.service;


import com.escihu.apiescihuvirtual.Dto.Users.PaginatedUsersDtoResponse;
import com.escihu.apiescihuvirtual.Dto.Users.UserDtoResponse;
import com.escihu.apiescihuvirtual.persistence.Entity.Role;
import com.escihu.apiescihuvirtual.persistence.Entity.User;
import com.escihu.apiescihuvirtual.persistence.Repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * UserService is a service class that handles operations related to users.
 * It implements the UserDetailsService interface from Spring Security.
 * It provides methods for loading a user by username, finding a user by username, and saving a user.
 * It uses the UserRepository to perform these operations.
 *
 */
@Service
public class UserService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;


    /**
     * Constructs a new UserService with the specified UserRepository.
     *
     * @param userRepository the UserRepository to be used by the UserService
     */
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Loads a user by the specified username.
     * If the user does not exist, a UsernameNotFoundException is thrown.
     *
     * @param username the username of the user to load
     * @return a UserDetails object containing the user's details
     * @throws UsernameNotFoundException if the user does not exist
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("in the user details service");

        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public PaginatedUsersDtoResponse listUsersPaginated(Pageable pageable) {

        Page<User> usersPage = userRepository.findAll(pageable);

        List<UserDtoResponse> usersDto = usersPage.getContent().stream()
                .map(user -> new UserDtoResponse(
                        user.getUserId(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getUserAsigned(),
                        user.getAuthorities()
                )).collect(Collectors.toList());

        return new PaginatedUsersDtoResponse(
                usersDto,
                usersPage.getNumber(),
                usersPage.getTotalPages(),
                usersPage.getSize()
        );
    }

    /**
     * Finds a user by the specified username.
     *
     * @param username the username of the user to find
     * @return an Optional containing the user if found, or an empty Optional if not found
     */
    public Optional<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Saves a user with the specified details and role.
     * The user's role is added to a set of roles, which is then assigned to the user.
     * The user is then saved in the UserRepository.
     *
     * @param user the user to save
     * @param role the role to assign to the user
     */
    public void saveUser(User user, Role role) {
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        logger.info(String.format("Saving user with username %s and de role %s", user.getUsername(), role.getAuthority()));
        userRepository.save(new User(user.getUserId(), user.getUsername(), user.getEmail(), user.getPassword(), user.getUserAsigned(), roles));
    }

}
