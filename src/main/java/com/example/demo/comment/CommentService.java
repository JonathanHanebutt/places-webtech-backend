package com.example.demo.comment;

import com.example.demo.PlaceEntry;
import com.example.demo.PlaceEntryRepository;
import com.example.demo.auth.AppUser;
import com.example.demo.auth.AppUserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PlaceEntryRepository placeEntryRepository;
    private final AppUserRepository appUserRepository;

    public CommentService(CommentRepository commentRepository,
                          PlaceEntryRepository placeEntryRepository,
                          AppUserRepository appUserRepository) {
        this.commentRepository = commentRepository;
        this.placeEntryRepository = placeEntryRepository;
        this.appUserRepository = appUserRepository;
    }

    public CommentDTO createComment(Long placeId, String content, String username) {
        PlaceEntry place = placeEntryRepository.findById(placeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Place not found"));

        AppUser user = appUserRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        Comment comment = new Comment(content, place, user);
        Comment savedComment = commentRepository.save(comment);

        return new CommentDTO(savedComment);
    }

    public List<CommentDTO> getCommentsForPlace(Long placeId) {
        if (!placeEntryRepository.existsById(placeId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Place not found");
        }

        return commentRepository.findByPlaceIdOrderByCreatedAtDesc(placeId)
                .stream()
                .map(CommentDTO::new)
                .collect(Collectors.toList());
    }

    public void deleteComment(Long commentId, String username) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found"));

        // Only the author can delete their own comment
        if (!comment.getUser().getUsername().equals(username)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can only delete your own comments");
        }

        commentRepository.delete(comment);
    }

    public CommentDTO updateComment(Long commentId, String content, String username) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found"));

        // Only the author can update their own comment
        if (!comment.getUser().getUsername().equals(username)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can only update your own comments");
        }

        comment.setContent(content);
        Comment updatedComment = commentRepository.save(comment);

        return new CommentDTO(updatedComment);
    }
}

