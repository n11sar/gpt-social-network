package org.example.gptsocialnetwork.repositories;

import org.example.gptsocialnetwork.entities.User;
import org.example.gptsocialnetwork.entities.UserFollowers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserFollowersRepository extends JpaRepository<UserFollowers, Long> {
    UserFollowers findByUserAndFollower(User user, User follower);
}
