package org.portfolio.sample.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class MovieReviewDTO {
	
	private Long no;
	
	private String text;
	
	private String reviewer;
	
	private Long ref;
	
}
