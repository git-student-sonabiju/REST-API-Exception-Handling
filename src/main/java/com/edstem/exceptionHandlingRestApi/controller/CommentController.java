package com.edstem.exceptionHandlingRestApi.controller;

import com.edstem.exceptionHandlingRestApi.model.Comment;
import com.edstem.exceptionHandlingRestApi.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts/{postId}/comments")
public class CommentController {

	private final CommentService commentService;

	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}

	@PostMapping
	public ResponseEntity<Comment> createComment(@PathVariable Long postId, @Valid @RequestBody Comment comment) {
		return new ResponseEntity<>(commentService.createComment(postId, comment), HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<List<Comment>> getComments(@PathVariable Long postId) {
		return ResponseEntity.ok(commentService.getCommentsByPostId(postId));
	}

	@PutMapping("/{commentId}")
	public ResponseEntity<Comment> updateComment(@PathVariable Long commentId, @Valid @RequestBody Comment comment) {
		return ResponseEntity.ok(commentService.updateComment(commentId, comment));
	}

	@DeleteMapping("/{commentId}")
	public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
		commentService.deleteComment(commentId);
		return ResponseEntity.noContent().build();
	}
}
