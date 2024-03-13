package com.escihu.apiescihuvirtual.persistence.Repository;

import com.escihu.apiescihuvirtual.persistence.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {

    Optional<Role> findByAuthority(String authority);

    boolean existsByAuthority(String authority);
}
