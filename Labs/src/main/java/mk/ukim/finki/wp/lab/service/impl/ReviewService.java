package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.Review;
import mk.ukim.finki.wp.lab.repository.jpa.ReviewRepository;
import mk.ukim.finki.wp.lab.service.ReviewServiceInterface;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService implements ReviewServiceInterface
{
    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository)
    {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public void saveReview(Review review)
    {
        this.reviewRepository.save(review);
    }

    @Override
    public List<Review> getReviewsForBook(Long id)
    {
        return this.reviewRepository.findAll().stream()
                .filter(r -> r.getBook().getId().equals(id))
                .sorted(Comparator.comparing(Review::getTimestamp))
                .collect(Collectors.toList());
    }

    @Override
    public List<Review> getReviewsWithinTimeIntervalForBook(Long id, LocalDateTime from, LocalDateTime to)
    {
        return this.reviewRepository.findByTimestampBetween(from,to)
                .stream().filter(r -> r.getBook().getId().equals(id))
                .sorted(Comparator.comparing(Review::getTimestamp))
                .collect(Collectors.toList());
    }
}
