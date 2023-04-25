package book.demo.java.dto;

import book.demo.java.entity.book.editorial.CommenterType;

import java.time.LocalDateTime;

public record CommentDTO(String content, CommenterType commenterType, String username, LocalDateTime timestamp) {

}
