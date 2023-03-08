package org.portfolio.sample.service.interfaces;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import org.portfolio.sample.dto.MovieDTO;
import org.portfolio.sample.dto.MovieReviewDTO;
import org.portfolio.sample.entity.Movie;
import org.portfolio.sample.entity.MovieDetail;
import org.portfolio.sample.entity.MovieReview;
import org.springframework.data.domain.Pageable;

public interface MovieService {

	Long addMovie(MovieDTO movieDTO);
	
	Long addReview(MovieReviewDTO reviewDTO);
	
	List<MovieDTO> getMovies(Pageable pageable);
	
	List<MovieDTO> getDetail(Long no);

	List<MovieReviewDTO> getReviews(Long no);
	
	boolean delReview(Long no);
	
	boolean modReview(MovieReviewDTO reviewDTO);
	
	default Movie toMovie(MovieDTO dto) {
		return Movie.builder()
				.title(dto.getTitle())
				.posterUrl(dto.getUploadPath().toString())
				.build();
	}
	
	default MovieReview toReview(MovieReviewDTO dto) {
		return MovieReview.builder()
				.text(dto.getText())
				.reviewer(dto.getReviewer())
				.ref(dto.getRef())
				.build();
	}
	
	default MovieDetail toDetail(Long no, MovieDTO dto) {
		return MovieDetail.builder()
				.ref(no)
				.synopsis(dto.getSynopsis())
				.genre(dto.getGenre())
				.build();
	}

	default MovieDTO toMovieWithDetail(Movie movie, MovieDetail movieDetail) {
		MovieDTO dto = null;
		try {
			dto = MovieDTO.builder()
					.no(movie.getNo())
					.title(movie.getTitle())
					.posterUrl(URLEncoder.encode(movie.getPosterUrl(), "utf-8"))
					.synopsis(movieDetail.getSynopsis())
					.genre(movieDetail.getGenre())
					.build();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dto;
	}
	
}
