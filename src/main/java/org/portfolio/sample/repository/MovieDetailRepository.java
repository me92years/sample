package org.portfolio.sample.repository;

import org.portfolio.sample.entity.MovieDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieDetailRepository extends JpaRepository<MovieDetail, Long>{

}
