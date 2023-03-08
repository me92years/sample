package org.portfolio.sample.repository;

import java.util.Optional;

import org.portfolio.sample.entity.MovieReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MovieReviewRepository extends JpaRepository<MovieReview, Long>{

	@Query("SELECT m from MovieReview m WHERE m.no = :no")
	Optional<MovieReview> getByNo(@Param("no") Long no);	
	
}
