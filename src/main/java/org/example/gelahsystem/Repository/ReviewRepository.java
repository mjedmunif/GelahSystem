package org.example.gelahsystem.Repository;

import org.example.gelahsystem.Model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {

    Review findReviewById(Integer id);

    @Query("select avg(r.rating) from Review r where r.gelahId = ?1")
    Double getAverageRating(Integer gelahId);

    Review findReviewByClientIdAndGelahId(Integer clientId, Integer gelahId);

    @Query("select r.rating,r.comment from Review r where r.gelahId = ?1")
    List<Object[]> getReviewByGelahId(Integer gelahId);


}
