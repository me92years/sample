package org.portfolio.sample.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
@Table(name = "movies_details")
public class MovieDetail extends Base {

	@Id
	private Long ref;
	
	@Column(nullable = false, length = 1000)
	private String synopsis;
	
	@Column(nullable = false, length = 100)
	private String genre;
	
}
