package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.Review;
import mk.ukim.finki.wp.lab.repository.jpa.ReviewRepository;
import mk.ukim.finki.wp.lab.service.ReviewServiceInterface;
import org.springframework.stereotype.Service;

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
        List<Review> list = this.reviewRepository.findAll().stream()
                .filter(r -> r.getBook().getId().equals(id))
                .collect(Collectors.toList());
        return list;
    }
}
