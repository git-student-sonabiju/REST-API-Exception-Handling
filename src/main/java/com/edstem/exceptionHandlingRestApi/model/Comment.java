package com.edstem.exceptionHandlingRestApi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Author is required")
	private String author;

	@NotBlank(message = "Content is required")
	private String content;

	@ManyToOne
	@JoinColumn(name = "post_id")
	private Post post;
}
