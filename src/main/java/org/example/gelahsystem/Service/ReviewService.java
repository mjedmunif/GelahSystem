package org.example.gelahsystem.Service;

import lombok.AllArgsConstructor;
import org.example.gelahsystem.API.APIExecption;
import org.example.gelahsystem.Model.Client;
import org.example.gelahsystem.Model.Gelah;
import org.example.gelahsystem.Model.OrderGelah;
import org.example.gelahsystem.Model.Review;
import org.example.gelahsystem.Repository.ClientRepository;
import org.example.gelahsystem.Repository.GelahRepository;
import org.example.gelahsystem.Repository.OrderGelahRepository;
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
    private final OrderGelahRepository orderGelahRepository;

    public List<Review> getReviews(){
        return reviewRepository.findAll();
    }

    public void addReview(Review review){
        Gelah checkGelah = gelahRepository.findGelahById(review.getGelahId());
        Client checkClient = clientRepository.findClientById(review.getClientId());
        OrderGelah orderGelah = orderGelahRepository.findOrderGelahByClientIdAndGelahId(review.getClientId(),review.getGelahId());
        Review hasReview = reviewRepository.findReviewByClientIdAndGelahId(review.getClientId() , review.getGelahId());
        if (checkClient == null){
            throw new APIExecption("Client not found");
        }
        if (checkGelah == null){
            throw new APIExecption("Gelah not found");
        }
        if (review.getRating() > 5 || review.getRating() < 1){
            throw new APIExecption("rating must be between 1 to 5 ");
        }
        if (orderGelah == null){
            throw new APIExecption("order not found");
        }
        if (hasReview != null){
            throw new APIExecption("you has no review");
        }
        review.setCreatedAt(LocalDate.now());
        reviewRepository.save(review);

        Double avg = reviewRepository.getAverageRating(review.getGelahId());
        Gelah gelah = gelahRepository.findGelahById(review.getGelahId());
        gelah.setRating(avg);
        gelahRepository.save(gelah);
    }

    public void updateReview(Integer id , Review review){
        Gelah checkGelah = gelahRepository.findGelahById(review.getGelahId());
        Review oldReview = reviewRepository.findReviewById(id);
        Client checkClient = clientRepository.findClientById(review.getClientId());
        OrderGelah orderGelah = orderGelahRepository.findOrderGelahByClientIdAndGelahId(review.getClientId(),review.getGelahId());
        if (oldReview == null){
            throw new APIExecption("review not found");
        }
        if (checkClient == null){
            throw new APIExecption("client not found");
        }
        if (checkGelah == null){
            throw new APIExecption("Gelah not found");
        }
        if (review.getRating() > 5 || review.getRating() < 1) {
            throw new APIExecption("rating must be between 1 to 5");
        }
        if (orderGelah == null){
            throw new APIExecption("order not found");
        }
        oldReview.setComment(review.getComment());
        oldReview.setGelahId(review.getGelahId());
        oldReview.setRating(review.getRating());
        oldReview.setClientId(review.getClientId());
        reviewRepository.save(oldReview);

        Double avg = reviewRepository.getAverageRating(review.getGelahId());
        Gelah gelah = gelahRepository.findGelahById(review.getGelahId());
        gelah.setRating(avg);
        gelahRepository.save(gelah);
    }

    public void deleteReview(Integer id){
        Review deleted = reviewRepository.findReviewById(id);
        if (deleted == null){
            throw new APIExecption("review not found");
        }
        reviewRepository.delete(deleted);
        Double avg = reviewRepository.getAverageRating(deleted.getGelahId());
        Gelah gelah = gelahRepository.findGelahById(deleted.getGelahId());
        gelah.setRating(avg);
        gelahRepository.save(gelah);
    }

}
