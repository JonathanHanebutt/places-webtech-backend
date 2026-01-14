package com.example.demo.comment;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@CrossOrigin(origins = "*")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // DTO for creating a comment
    public static class CreateCommentRequest {
        public String content;
    }

    public static class UpdateCommentRequest {
        public String content;
    }

    // POST /api/comments/place/{placeId} - Create a comment on a place
    @PostMapping("/place/{placeId}")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDTO createComment(
            @PathVariable Long placeId,
            @RequestBody CreateCommentRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        return commentService.createComment(placeId, request.content, userDetails.getUsername());
    }

    // GET /api/comments/place/{placeId} - Get all comments for a place
    @GetMapping("/place/{placeId}")
    public List<CommentDTO> getCommentsForPlace(@PathVariable Long placeId) {
        return commentService.getCommentsForPlace(placeId);
    }

    // DELETE /api/comments/{commentId} - Delete own comment
    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(
            @PathVariable Long commentId,
            @AuthenticationPrincipal UserDetails userDetails) {
        commentService.deleteComment(commentId, userDetails.getUsername());
    }

    // PUT /api/comments/{commentId} - Update own comment
    @PutMapping("/{commentId}")
    public CommentDTO updateComment(
            @PathVariable Long commentId,
            @RequestBody UpdateCommentRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        return commentService.updateComment(commentId, request.content, userDetails.getUsername());
    }
}

