package org.example.gelahsystem.Service;

import lombok.AllArgsConstructor;
import org.example.gelahsystem.Model.Client;
import org.example.gelahsystem.Model.Gelah;
import org.example.gelahsystem.Model.Review;
import org.example.gelahsystem.Repository.ClientRepository;
import org.example.gelahsystem.Repository.GelahRepository;
import org.example.gelahsystem.Repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final GelahRepository gelahRepository;
    private final ClientRepository clientRepository;

    public List<Review> getReviews(){
        return reviewRepository.findAll();
    }

    public Integer addReview(Review review){
        Gelah checkGelah = gelahRepository.findGelahById(review.getGelahId());
        Client checkClient = clientRepository.findClientById(review.getClientId());
        if (checkClient == null){
            return 1;
        }
        if (checkGelah == null){
            return 2;
        }
        if (review.getRating() > 5){
            return 3;
        }
        review.setCreatedAt(LocalDate.now());
        reviewRepository.save(review);
        return 0;
    }

    public Integer updateReview(Integer id , Review review){
        Gelah checkGelah = gelahRepository.findGelahById(review.getGelahId());
        Review oldReview = reviewRepository.findReviewById(id);
        Client checkClient = clientRepository.findClientById(review.getClientId());
        if (oldReview == null){
            return 1;
        }
        if (checkClient == null){
            return 2;
        }
        if (checkGelah == null){
            return 3;
        }
        if (review.getRating() > 5){
            return 4;
        }
        oldReview.setComment(review.getComment());
        oldReview.setGelahId(review.getGelahId());
        oldReview.setRating(review.getRating());
        oldReview.setClientId(review.getClientId());
        reviewRepository.save(oldReview);
        return 0;
    }

    public Boolean deleteReview(Integer id){
        Review deleted = reviewRepository.findReviewById(id);
        if (deleted == null){
            return false;
        }
        reviewRepository.delete(deleted);
        return true;
    }
}
