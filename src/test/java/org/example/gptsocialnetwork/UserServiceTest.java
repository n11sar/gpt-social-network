package org.example.gptsocialnetwork;

import java.util.HashSet;
import java.util.Optional;
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
        assertTrue(user.getFollowers().contains(follower));

        userService.unfollowUser(1L, 2L);

        assertFalse(user.getFollowers().contains(follower));
        verify(userRepository, times(1)).save(user);
    }
}

