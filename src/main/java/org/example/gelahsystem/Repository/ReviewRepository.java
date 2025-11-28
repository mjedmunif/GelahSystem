package org.example.gelahsystem.Repository;

import org.example.gelahsystem.Model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Integer> {

    Review findReviewById(Integer id);
}
