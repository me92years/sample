package org.portfolio.sample.dto;

import java.nio.file.Path;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MovieDTO {
	
	private Long no;
	
	private String title;
	
	private MultipartFile[] files;
	
	private String synopsis;
	
	private String genre;
	
	private String posterUrl;
	
	private Path uploadPath;
	
}
