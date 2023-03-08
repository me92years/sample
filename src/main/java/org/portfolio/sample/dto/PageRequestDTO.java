package org.portfolio.sample.dto;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PageRequestDTO {
	
	private int page = 1;
	private int size = 10;
	private String type = "";
	private String keyword = "";
	
	public Pageable getPageable(Sort sort) {
		return PageRequest.of(page-1, size, sort);
	}
	
}
