package org.portfolio.sample.service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.portfolio.sample.dto.MovieDTO;
import org.portfolio.sample.dto.MovieReviewDTO;
import org.portfolio.sample.entity.Movie;
import org.portfolio.sample.entity.MovieDetail;
import org.portfolio.sample.entity.MovieReview;
import org.portfolio.sample.repository.MovieDetailRepository;
import org.portfolio.sample.repository.MovieRepository;
import org.portfolio.sample.repository.MovieReviewRepository;
import org.portfolio.sample.service.interfaces.MovieService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

	private final MovieRepository movieRepository;
	private final MovieDetailRepository movieDetailRepository;
	private final MovieReviewRepository movieReviewRepository;
	
	@Override
	@Transactional
	public Long addMovie(MovieDTO movieDTO) {
		Movie movie = toMovie(movieDTO);
		movie = movieRepository.save(movie);
		MovieDetail movieDetail = toDetail(movie.getNo(), movieDTO);
		return movieDetailRepository.save(movieDetail).getRef();
	}
	
	@Override
	public Long addReview(MovieReviewDTO reviewDTO) {
		MovieReview review = toReview(reviewDTO);
		return movieReviewRepository.save(review).getNo();
	}

	@Override
	public List<MovieDTO> getMovies(Pageable pageable) {
		Page<Movie> result = movieRepository.findAll(pageable);
		return result.stream().map(en -> en.toDTO()).collect(Collectors.toList());
	}

	@Override
	public List<MovieDTO> getDetail(Long no) {
		Function<Object[], MovieDTO> fn = en -> toMovieWithDetail((Movie) en[0], (MovieDetail) en[1]);
		Page<Object[]> result = movieRepository.getDetail(no);
		return result.stream().map(fn).collect(Collectors.toList());
	}

	@Override
	public List<MovieReviewDTO> getReviews(Long no) {
		Page<MovieReview> result = movieRepository.getReviews(no);
		return result.stream().map(mr -> mr.toDTO()).collect(Collectors.toList());
	}

	@Override
	public boolean delReview(Long no) {
		try {
			movieReviewRepository.deleteById(no);
			return true;
		} catch (Exception e) {
			return false;	
		}
	}

	@Override
	@Transactional
	public boolean modReview(MovieReviewDTO reviewDTO) {
		try {
			MovieReview movieReview = movieReviewRepository.getByNo(reviewDTO.getNo()).map(m -> m.modify(reviewDTO)).get();
			movieReviewRepository.save(movieReview);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
}
