package org.portfolio.sample.repository.support;

import java.util.List;
import java.util.stream.Collectors;

import org.portfolio.sample.entity.Movie;
import org.portfolio.sample.entity.MovieReview;
import org.portfolio.sample.entity.QMovie;
import org.portfolio.sample.entity.QMovieDetail;
import org.portfolio.sample.entity.QMovieReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPQLQuery;

public class SupportMovieRepositoryImpl extends QuerydslRepositorySupport implements SupportMovieRepository {

	public SupportMovieRepositoryImpl() {
		super(Movie.class);
	}

	@Override
	public Page<Object[]> getDetail(Long no) {
		QMovie movie = QMovie.movie;
		QMovieDetail detail = QMovieDetail.movieDetail;
		JPQLQuery<Movie> query = from(movie);
		query.leftJoin(detail).on(detail.ref.eq(movie.no));
		query.where(movie.no.eq(no));
		JPQLQuery<Tuple> result = query.select(movie, detail);
		return new PageImpl<>(result.stream().map(t -> t.toArray()).collect(Collectors.toList()));
	}

	@Override
	public Page<MovieReview> getReviews(Long no) {
		QMovie movie = QMovie.movie;
		QMovieReview review = QMovieReview.movieReview;
		JPQLQuery<MovieReview> query = from(review);
		query.leftJoin(movie).on(movie.no.eq(review.ref));
		JPQLQuery<MovieReview> selected = query.select(review);
		selected.where(review.no.gt(0L));
		List<MovieReview> reviews = selected.fetch();
		return new PageImpl<>(reviews);
	}

	
	
}
