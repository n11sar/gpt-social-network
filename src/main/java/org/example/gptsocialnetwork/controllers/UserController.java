package org.example.gptsocialnetwork.controllers;

import org.example.gptsocialnetwork.services.UserService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/follow/{userId}")
    public String followUser(@PathVariable Long userId, @RequestParam Long followerId) {
        userService.followUser(userId, followerId);
        return "Followed successfully";
    }

    @PostMapping("/unfollow/{userId}")
    public String unfollowUser(@PathVariable Long userId, @RequestParam Long followerId) {
        userService.unfollowUser(userId, followerId);
        return "Unfollowed successfully";
    }
}
