package org.example.gptsocialnetwork.repositories;

import org.example.gptsocialnetwork.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
