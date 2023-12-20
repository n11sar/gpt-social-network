package org.example.gptsocialnetwork;

import java.util.HashSet;
import java.util.Optional;
import org.example.gptsocialnetwork.entities.UserFollowers;
import org.example.gptsocialnetwork.repositories.UserFollowersRepository;
import org.example.gptsocialnetwork.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.example.gptsocialnetwork.entities.User;
import org.example.gptsocialnetwork.services.UserService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserFollowersRepository userFollowersRepository;

    @InjectMocks
    private UserService userService;

    private User user;
    private User follower;

    @BeforeEach
    public void setup() {
        user = new User();
        user.setId(1L);
        user.setUsername("user1");
        user.setFollowers(new HashSet<>());

        follower = new User();
        follower.setId(2L);
        follower.setUsername("user2");

        user.getFollowers().add(follower);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.findById(2L)).thenReturn(Optional.of(follower));
    }

    @Test
    void testFollowUser() {
        userService.followUser(1L, 2L);

        assertTrue(user.getFollowers().contains(follower));
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testUnfollowUser() {
        Long userId = 1L;
        Long followerId = 2L;

        User user = new User();
        user.setId(userId);
        User follower = new User();
        follower.setId(followerId);

        UserFollowers userFollower = new UserFollowers();
        userFollower.setUser(user);
        userFollower.setFollower(follower);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.findById(followerId)).thenReturn(Optional.of(follower));
        when(userFollowersRepository.findByUserAndFollower(user, follower)).thenReturn(userFollower);

        userService.unfollowUser(userId, followerId);

        verify(userFollowersRepository, times(1)).findByUserAndFollower(user, follower);
        verify(userFollowersRepository, times(1)).delete(userFollower);
    }
}

