package com.codecool.nopainnogain.repositories;

import com.codecool.nopainnogain.auth.NPNGAdmin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<NPNGAdmin, Long> {

    NPNGAdmin findByUsername(String username);

}
