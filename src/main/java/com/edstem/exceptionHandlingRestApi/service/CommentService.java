package com.edstem.exceptionHandlingRestApi.service;

import com.edstem.exceptionHandlingRestApi.exception.ResourceNotFoundException;
import com.edstem.exceptionHandlingRestApi.model.Comment;
import com.edstem.exceptionHandlingRestApi.model.Post;
import com.edstem.exceptionHandlingRestApi.repository.CommentRepository;
import com.edstem.exceptionHandlingRestApi.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

	private final CommentRepository commentRepository;
	private final PostRepository postRepository;

	public CommentService(CommentRepository commentRepository, PostRepository postRepository) {
		this.commentRepository = commentRepository;
		this.postRepository = postRepository;
	}

	public Comment createComment(Long postId, Comment comment) {
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post not found with id " + postId));
		comment.setPost(post);
		return commentRepository.save(comment);
	}

	public List<Comment> getCommentsByPostId(Long postId) {
		return commentRepository.findByPostId(postId);
	}

	public Comment updateComment(Long id, Comment comment) {
		Comment existing = commentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Comment not found with id " + id));
		existing.setAuthor(comment.getAuthor());
		existing.setContent(comment.getContent());
		return commentRepository.save(existing);
	}

	public void deleteComment(Long id) {
		Comment comment = commentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Comment not found with id " + id));
		commentRepository.delete(comment);
	}
}
