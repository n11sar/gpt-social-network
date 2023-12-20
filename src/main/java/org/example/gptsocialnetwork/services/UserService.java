package org.example.gptsocialnetwork.services;


import java.util.function.LongFunction;
import org.example.gptsocialnetwork.entities.User;
import org.example.gptsocialnetwork.entities.UserFollowers;
import org.example.gptsocialnetwork.exceptions.FollowerNotFoundException;
import org.example.gptsocialnetwork.exceptions.UserNotFoundException;
import org.example.gptsocialnetwork.repositories.UserFollowersRepository;
import org.example.gptsocialnetwork.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private static final String USER_NOT_FOUND_MESSAGE = "User with id %d not found";
    private static final String FOLLOWER_NOT_FOUND_MESSAGE = "Follower with id %d not found";

    private final UserRepository userRepository;
    private final UserFollowersRepository userFollowersRepository;


    public UserService(UserRepository userRepository,
        UserFollowersRepository userFollowersRepository) {
        this.userRepository = userRepository;
        this.userFollowersRepository = userFollowersRepository;
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

        UserFollowers userFollower = userFollowersRepository.findByUserAndFollower(user, follower);

        if (userFollower != null) {
            userFollowersRepository.delete(userFollower);
        } else {
            // Логирование или обработка ошибки, если соответствующий объект не найден
            throw new  UserNotFoundException("User Not found");
        }
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
