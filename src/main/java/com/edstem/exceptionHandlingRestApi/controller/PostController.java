package com.edstem.exceptionHandlingRestApi.controller;

import com.edstem.exceptionHandlingRestApi.model.Post;
import com.edstem.exceptionHandlingRestApi.service.PostService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

	private final PostService postService;

	public PostController(PostService postService) {
		this.postService = postService;
	}

	// Create
	@PostMapping
	public ResponseEntity<Post> createPost(@Valid @RequestBody Post post) {
		Post savedPost = postService.createPost(post);
		return new ResponseEntity<>(savedPost, HttpStatus.CREATED);
	}

	// Get all
	@GetMapping
	public ResponseEntity<List<Post>> getAllPosts() {
		return ResponseEntity.ok(postService.getAllPosts());
	}

	// Get one
	@GetMapping("/{id}")
	public ResponseEntity<Post> getPostById(@PathVariable Long id) {
		return ResponseEntity.ok(postService.getPostById(id));
	}

	// Update
	@PutMapping("/{id}")
	public ResponseEntity<Post> updatePost(@PathVariable Long id, @Valid @RequestBody Post post) {
		Post updatedPost = postService.updatePost(id, post);
		return ResponseEntity.ok(updatedPost);
	}

	// Delete
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletePost(@PathVariable Long id) {
		postService.deletePost(id);
		return ResponseEntity.noContent().build();
	}
}
