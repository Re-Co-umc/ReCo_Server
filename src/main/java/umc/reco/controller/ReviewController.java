package umc.reco.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import umc.reco.dto.request.ReviewRequestDto;
import umc.reco.exception.ExceptionResponse;
import umc.reco.exception.NotQualifiedDtoException;
import umc.reco.exception.TargetNotFoundException;
import umc.reco.service.ReviewService;

@RestController
@RequestMapping("/review")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> addReview(@PathVariable Long id, @RequestBody ReviewRequestDto requestDto) {
        try {
            return ResponseEntity.ok(reviewService.createReview(id, requestDto));
        } catch (TargetNotFoundException | NotQualifiedDtoException e) {
            return errorMessage(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editReview(@PathVariable Long id, @RequestBody ReviewRequestDto requestDto) {
        try {
            return ResponseEntity.ok(reviewService.editReview(id, requestDto));
        } catch (NotQualifiedDtoException | TargetNotFoundException | IllegalStateException e) {
            return errorMessage(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable Long id) {
        try {
            reviewService.deleteReview(id);
            return ResponseEntity.ok("삭제 완료");
        } catch (TargetNotFoundException | IllegalStateException e) {
            return errorMessage(e.getMessage());
        }
    }

    private static ResponseEntity<ExceptionResponse> errorMessage(String message) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(message));
    }
}
