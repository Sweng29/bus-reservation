package com.mantistech.busreservation.repository.user;

import com.mantistech.busreservation.model.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long>{

    Optional<Role> findByRole(String role);
}
