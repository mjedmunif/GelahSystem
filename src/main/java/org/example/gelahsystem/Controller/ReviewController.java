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
    public ResponseEntity<?> addReview(@RequestBody @Valid Review review , Errors errors){
        if (errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));
        }
        int checkStatus = reviewService.addReview(review);
        if (checkStatus == 1){
            return ResponseEntity.status(404).body(new ApiResponse("client with id '"+review.getClientId() +"' not found"));
        }
        if (checkStatus == 2){
            return ResponseEntity.status(404).body(new ApiResponse("Gelah with id '"+review.getGelahId()+"' not found"));
        }
        if (checkStatus == 3){
            return ResponseEntity.status(400).body(new ApiResponse("rating should be between 1 and 5"));
        }
        if (checkStatus == 4){
            return ResponseEntity.status(404).body(new ApiResponse("Client with this Gelah has no order"));
        }
        if (checkStatus == 5){
            return ResponseEntity.status(400).body(new ApiResponse("you have a previous review"));
        }
        return ResponseEntity.status(200).body(new ApiResponse("added review successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateReview(@PathVariable Integer id , @RequestBody @Valid Review review , Errors errors){
        if (errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));
        }
        int checkStatus = reviewService.updateReview(id, review);
        if (checkStatus == 1){
            return ResponseEntity.status(404).body(new ApiResponse("you dont has previous order"));
        }
        if (checkStatus == 2){
            return ResponseEntity.status(404).body(new ApiResponse("client with id '"+review.getClientId() +"' not found"));
        }
        if (checkStatus == 3){
            return ResponseEntity.status(404).body(new ApiResponse("Gelah with id '"+review.getGelahId()+"' not found"));
        }
        if (checkStatus == 4){
            return ResponseEntity.status(400).body(new ApiResponse("rating should be between 1 and 5"));
        }
        if (checkStatus == 5){
            return ResponseEntity.status(404).body(new ApiResponse("Client with this Gelah has no order"));
        }
        return ResponseEntity.status(200).body(new ApiResponse("updated review successfully"));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable Integer id){
        boolean checkStatus = reviewService.deleteReview(id);
        if (checkStatus){
            return ResponseEntity.status(404).body(new ApiResponse("review with id '"+ id +"' Not found"));
        }
        return ResponseEntity.status(200).body(new ApiResponse("deleted review successfully"));
    }


}
