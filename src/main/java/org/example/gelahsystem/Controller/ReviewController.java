package org.example.gelahsystem.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.gelahsystem.API.ApiResponse;
import org.example.gelahsystem.Model.Review;
import org.example.gelahsystem.Service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/get")
    public ResponseEntity<?> getAllReviews(){
        return ResponseEntity.status(200).body(reviewService.getReviews());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addReview(@RequestBody @Valid Review review ){
        reviewService.addReview(review);
        return ResponseEntity.status(200).body(new ApiResponse("added review successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateReview(@PathVariable Integer id , @RequestBody @Valid Review review , Errors errors){
        reviewService.updateReview(id, review);
        return ResponseEntity.status(200).body(new ApiResponse("updated review successfully"));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable Integer id){
        reviewService.deleteReview(id);
        return ResponseEntity.status(200).body(new ApiResponse("deleted review successfully"));
    }


}
