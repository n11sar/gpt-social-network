package org.example.gptsocialnetwork.services;


import java.util.function.LongFunction;
import org.example.gptsocialnetwork.entities.User;
import org.example.gptsocialnetwork.exceptions.FollowerNotFoundException;
import org.example.gptsocialnetwork.exceptions.UserNotFoundException;
import org.example.gptsocialnetwork.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private static final String USER_NOT_FOUND_MESSAGE = "User with id %d not found";
    private static final String FOLLOWER_NOT_FOUND_MESSAGE = "Follower with id %d not found";

    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void followUser(Long userId, Long followerId) {
        User user = validateUserExists(userId, id -> new UserNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, id)));
        User follower = validateUserExists(followerId, id -> new FollowerNotFoundException(String.format(FOLLOWER_NOT_FOUND_MESSAGE, id)));

        user.getFollowers().add(follower);
        userRepository.save(user);
    }

    public void unfollowUser(Long userId, Long followerId) {
        User user = validateUserExists(userId, id -> new UserNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, id)));
        User follower = validateUserExists(followerId, id -> new FollowerNotFoundException(String.format(FOLLOWER_NOT_FOUND_MESSAGE, id)));

        user.getFollowers().remove(follower);
        userRepository.save(user);
    }

    private User validateUserExists(Long userId, LongFunction<RuntimeException> exceptionSupplier) {
        return userRepository.findById(userId)
            .orElseThrow(() -> exceptionSupplier.apply(userId));
    }

    public User findUserById(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException("User not found"));
    }
}
