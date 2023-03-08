package org.portfolio.sample.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import org.portfolio.sample.dto.MovieReviewDTO;

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
@Table(name = "movies_reviews", indexes = {
		@Index(columnList = "ref")
})
public class MovieReview extends Base {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long no;
	
	@Column(nullable = false, length = 200)
	private String text;
	
	@Column(nullable = false)
	private String reviewer;
	
	@Column(nullable = false)
	private Long ref;
	
	public MovieReviewDTO toDTO() {
		return MovieReviewDTO.builder()
				.no(no)
				.text(text)
				.reviewer(reviewer)
				.ref(ref)
				.build();
	}
	
	public MovieReview modify(MovieReviewDTO dto) {
		this.text = dto.getText();
		return this;
	}
		
}
