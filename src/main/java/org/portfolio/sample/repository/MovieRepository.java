package org.portfolio.sample.repository;

import org.portfolio.sample.entity.Movie;
import org.portfolio.sample.repository.support.SupportMovieRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long>, SupportMovieRepository {

}
