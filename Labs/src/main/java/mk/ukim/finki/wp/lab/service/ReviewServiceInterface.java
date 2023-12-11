package mk.ukim.finki.wp.lab.service;

import mk.ukim.finki.wp.lab.model.Review;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public interface ReviewServiceInterface
{
    void saveReview(Review review);

    List<Review> getReviewsForBook(Long id);

    List<Review> getReviewsWithinTimeIntervalForBook(Long id,LocalDateTime from, LocalDateTime to);
    List<Review> findReviewsForBookWithKeyword(Long id, String keyword);
}
