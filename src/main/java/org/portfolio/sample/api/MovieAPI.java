package org.portfolio.sample.api;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import org.portfolio.sample.dto.MovieDTO;
import org.portfolio.sample.dto.MovieReviewDTO;
import org.portfolio.sample.dto.PageRequestDTO;
import org.portfolio.sample.dto.UserDTO;
import org.portfolio.sample.service.interfaces.MovieService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MovieAPI {
	
	@Value("${spring.custom.multipart.upload-path}")
	private String uploadPath;
	
	private final MovieService movieService;
	
	@GetMapping("/apio/get/movies")
	public List<MovieDTO> getMovies(PageRequestDTO pageRequestDTO) {
		return movieService.getMovies(pageRequestDTO.getPageable(Sort.by("no").ascending()));
	}
	
	@GetMapping("/apio/get/detail/{no}")
	public MovieDTO getDetail(@PathVariable("no") Long no) {
		List<MovieDTO> result = movieService.getDetail(no);
		return result.get(0);
	}
	
	@GetMapping("/apio/get/poster")
	public ResponseEntity<byte[]> getPoster(@RequestParam String posterUrl) throws IOException {
		File file = new File(posterUrl);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", Files.probeContentType(file.toPath()));
		return new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), headers, HttpStatus.OK);
	}
	
	@GetMapping("/apio/get/review/{no}")
	public List<MovieReviewDTO> getReview(@PathVariable("no") Long no) {
		return movieService.getReviews(no);
	}
	
	@PostMapping(value = "/apix/del/review", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> delReview(@RequestBody MovieReviewDTO reviewDTO, @AuthenticationPrincipal UserDTO userDTO) {
		boolean isSameUser = userDTO.getNickname().equals(reviewDTO.getReviewer());
		boolean isDelete = false;
		if (isSameUser) {
			isDelete = movieService.delReview(reviewDTO.getNo());
		}
		return new ResponseEntity<>(isDelete, (isDelete) ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PostMapping(value = "/apix/mod/review", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> modReview(@RequestBody MovieReviewDTO reviewDTO, @AuthenticationPrincipal UserDTO userDTO) {
		boolean isSameUser = userDTO.getEmail().equals(reviewDTO.getReviewer());
		boolean result = false;
		if (isSameUser) {
			result = movieService.modReview(reviewDTO);
		}
		return new ResponseEntity<>(result, (result) ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR); 
	}
	
	@PostMapping("/apix/add/review")
	public Long addReview(@RequestBody MovieReviewDTO reviewDTO) {
		return movieService.addReview(reviewDTO);
	}
	
	@PostMapping("/apixx/add/movie")
	public Long addMovie(MovieDTO movieDTO) throws IllegalStateException, IOException {
		for (MultipartFile file : movieDTO.getFiles()) {
			String originalFleName = file.getOriginalFilename();
			String posterFileName = originalFleName.substring(originalFleName.lastIndexOf("/")+1);
			movieDTO.setUploadPath(uploadToServer(posterFileName));
			file.transferTo(movieDTO.getUploadPath());
			return movieService.addMovie(movieDTO);
		}
		return 0L;
	}

	
	
	private Path uploadToServer(String posterFileName) {
		String posterName = UUID.randomUUID().toString() + "_" + posterFileName;
		String folderPath = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
		folderPath.replace("/", File.separator);
		File folderDirs = new File(uploadPath + File.separator + folderPath);
		if (!folderDirs.exists()) {
			folderDirs.mkdirs(); 
		}
		return Paths.get(uploadPath, folderPath, posterName);
	}
	
}
