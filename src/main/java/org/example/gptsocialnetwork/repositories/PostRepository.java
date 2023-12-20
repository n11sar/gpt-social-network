package org.example.gptsocialnetwork.repositories;

import org.example.gptsocialnetwork.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
