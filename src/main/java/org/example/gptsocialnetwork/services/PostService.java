package org.example.gptsocialnetwork.services;

import org.example.gptsocialnetwork.entities.Post;
import org.example.gptsocialnetwork.entities.User;
import org.example.gptsocialnetwork.exceptions.PostNotFoundException;
import org.example.gptsocialnetwork.exceptions.UserNotFoundException;
import org.example.gptsocialnetwork.repositories.PostRepository;
import org.example.gptsocialnetwork.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    private static final String USER_NOT_FOUND_MESSAGE = "User with id %d not found";
    private static final String POST_NOT_FOUND_MESSAGE = "Post with id %d not found";

    private final PostRepository postRepository;
    private final UserRepository userRepository;


    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    public Post getPost(Long postId) {
        return postRepository.findById(postId).orElseThrow(
            () -> new RuntimeException("Post not found")
        );
    }

    public void likePost(Long postId, Long userId) {
        Post post = validatePostExists(postId);
        User user = validateUserExists(userId);

        post.getLikes().add(user);
        postRepository.save(post);
    }

    private User validateUserExists(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, userId)));
    }

    private Post validatePostExists(Long postId) {
        return postRepository.findById(postId)
            .orElseThrow(() -> new PostNotFoundException(String.format(POST_NOT_FOUND_MESSAGE, postId)));
    }

}
