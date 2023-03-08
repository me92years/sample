package org.portfolio.sample.entity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.portfolio.sample.dto.MovieDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Table(name = "movies")
public class Movie {	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long no;
	
	@Column(nullable = false, unique = true)
	private String title;
	
	@Column(nullable = false)
	private String posterUrl;
	
	public MovieDTO toDTO() {
		MovieDTO dto = null;
		try {
			dto = MovieDTO.builder()
					.no(no)
					.title(title)
					.posterUrl(URLEncoder.encode(posterUrl, "utf-8"))
					.build();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return dto;
	}
	
}
