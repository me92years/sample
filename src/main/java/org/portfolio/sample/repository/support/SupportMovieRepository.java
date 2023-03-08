package org.portfolio.sample.repository.support;

import org.portfolio.sample.entity.MovieReview;
import org.springframework.data.domain.Page;

public interface SupportMovieRepository {
	
	Page<Object[]> getDetail(Long no);
	
	Page<MovieReview> getReviews(Long no);
	
}
