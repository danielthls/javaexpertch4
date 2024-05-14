package com.devsuperior.movieflix.projections;

public interface MovieProjection {
	Long getId();
	String getTitle();
	String getSubTitle();
	Integer getYear();
	String getImgUrl();
}
