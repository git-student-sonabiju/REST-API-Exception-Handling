package com.edstem.exceptionHandlingRestApi.service;

import com.edstem.exceptionHandlingRestApi.exception.AuthenticationException;
import com.edstem.exceptionHandlingRestApi.exception.ResourceNotFoundException;
import com.edstem.exceptionHandlingRestApi.model.Post;
import com.edstem.exceptionHandlingRestApi.repository.PostRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

	private final PostRepository postRepository;

	public PostService(PostRepository postRepository) {
		this.postRepository = postRepository;
	}

	public Post createPost(Post post) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		boolean isAdmin = authentication.getAuthorities().stream()
				.anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));

		if (!isAdmin) {
			throw new AuthenticationException("User is not authorized to perform this action. ADMIN role required.");
		}

		return postRepository.save(post);
	}

	public List<Post> getAllPosts() {
		return postRepository.findAll();
	}

	public Post getPostById(Long id) {
		return postRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Post not found with id " + id));
	}

	public Post updatePost(Long id, Post post) {
		Post existingPost = getPostById(id);
		existingPost.setTitle(post.getTitle());
		existingPost.setContent(post.getContent());
		return postRepository.save(existingPost);
	}

	public void deletePost(Long id) {
		Post existingPost = getPostById(id);
		postRepository.delete(existingPost);
	}
}
