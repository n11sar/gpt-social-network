package org.example.gptsocialnetwork;

import java.util.HashSet;
import java.util.Optional;
import org.example.gptsocialnetwork.repositories.PostRepository;
import org.example.gptsocialnetwork.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.example.gptsocialnetwork.entities.Post;
import org.example.gptsocialnetwork.entities.User;
import org.example.gptsocialnetwork.services.PostService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PostService postService;

    @Test
    void testCreatePost() {
        User author = new User();
        author.setId(1L);
        author.setUsername("author");

        Post post = new Post();
        post.setTitle("Test Post");
        post.setBody("This is a test post");
        post.setAuthor(author);

        when(postRepository.save(any(Post.class))).thenReturn(post);

        Post createdPost = postService.createPost(post);

        assertNotNull(createdPost);
        assertEquals("Test Post", createdPost.getTitle());
    }

    @Test
    void testLikePost() {
        User author = new User();
        author.setId(1L);
        author.setUsername("author");

        Post post = new Post();
        post.setId(1L);
        post.setTitle("Test Post");
        post.setBody("This is a test post");
        post.setAuthor(author);
        post.setLikes(new HashSet<>());

        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        when(userRepository.findById(1L)).thenReturn(Optional.of(author));

        postService.likePost(1L, 1L);

        assertTrue(post.getLikes().contains(author));
        verify(postRepository, times(1)).save(post);
    }
}

