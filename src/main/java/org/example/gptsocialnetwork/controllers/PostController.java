package org.example.gptsocialnetwork.controllers;

import org.example.gptsocialnetwork.dto.PostDTO;
import org.example.gptsocialnetwork.dto.UserDTO;
import org.example.gptsocialnetwork.entities.Post;
import org.example.gptsocialnetwork.entities.User;
import org.example.gptsocialnetwork.services.PostService;
import org.example.gptsocialnetwork.services.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;
    private final UserService userService;

    public PostController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @PostMapping
    public PostDTO createPost(@RequestBody PostDTO postDTO) {
        Post post = convertToEntity(postDTO);
        Post createdPost = postService.createPost(post);
        return convertToDto(createdPost);
    }

    @GetMapping("/{postId}")
    public PostDTO getPost(@PathVariable Long postId) {
        Post post = postService.getPost(postId);
        return convertToDto(post);
    }

    @PostMapping("/{postId}/like")
    public String likePost(@PathVariable Long postId, @RequestParam Long userId) {
        postService.likePost(postId, userId);
        return "Liked post successfully";
    }

    private Post convertToEntity(PostDTO postDTO) {
        Post post = new Post();
        post.setTitle(postDTO.getTitle());
        post.setBody(postDTO.getBody());

        if (postDTO.getAuthor() != null && postDTO.getAuthor().getId() != null) {
            User author = userService.findUserById(postDTO.getAuthor().getId());
            post.setAuthor(author);
        }

        return post;
    }

    private PostDTO convertToDto(Post post) {
        PostDTO postDTO = new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setTitle(post.getTitle());
        postDTO.setBody(post.getBody());

        if (post.getAuthor() != null) {
            UserDTO authorDTO = new UserDTO();
            authorDTO.setId(post.getAuthor().getId());
            authorDTO.setUsername(post.getAuthor().getUsername());
            postDTO.setAuthor(authorDTO);
        }

        return postDTO;
    }
}