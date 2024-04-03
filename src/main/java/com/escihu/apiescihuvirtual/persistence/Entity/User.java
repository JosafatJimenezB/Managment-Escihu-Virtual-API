package com.escihu.apiescihuvirtual.persistence.Entity;

import com.escihu.apiescihuvirtual.persistence.Entity.Student.Student;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "users")
@Schema(description = "Entity representing a user in the system.")
@Getter
@NoArgsConstructor
public class User implements UserDetails {

    /**
     * The id of the user.
     * This is a primary key in the database.
     */
    @Column(name = "user_id")
    @Schema(description = "Unique identifier of the user.")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    /**
     * The username of the user.
     * This field is unique and is mapped to the "username" column in the database.
     */
    @Column(unique = true)
    @Schema(description = "Username of the user.")
    private String username;

    /**
     * The email of the user.
     * This field is unique and is mapped to the "email" column in the database.
     */
    @Column(unique = true)
    @Schema(description = "Email of the user.")
    private String email;

    /**
     * The password of the user.
     * This field is mapped to the "password" column in the database.
     */
    @Schema(description = "Password of the user.")
    private String password;

    @Schema(description = "User asigned to one account")
    @Column(name = "user_asigned", unique = true)
    @NotNull()
    private Long userAsigned;

    /**
     * The roles (authorities) of the user.
     * This field is mapped to the "user_role_function" table in the database.
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_role_function",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
    @Schema(description = "Roles (authorities) of the user.")
    private Set<Role> authorities;

    public User(String username, String email, String password, Long userAsigned, Set<Role> authorities) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.userAsigned = userAsigned;
        this.authorities = authorities;
    }


    public User(Long userId, String username, String email, String password, Long userAsigned, Set<Role> authorities) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.userAsigned = userAsigned;
        this.authorities = authorities;
    }



    /**
     * Returns the authorities granted to the user.
     * @return a collection of granted authorities.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }


    /**
     * Checks if the user's account has not expired.
     * @return true if the account is valid (non-expired), false otherwise.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Checks if the user is not locked.
     * @return true if the user is not locked, false otherwise.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Checks if the user's credentials (password) have not expired.
     * @return true if the credentials are valid (non-expired), false otherwise.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Checks if the user is enabled.
     * @return true if the user is enabled, false otherwise.
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}