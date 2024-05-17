package com.devsuperior.movieflix.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.movieflix.dto.MovieDetailsDTO;
import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.entities.Review;
import com.devsuperior.movieflix.entities.User;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.repositories.ReviewRepository;

@Service
public class ReviewService {

	@Autowired
	private MovieService movieService;
	
	@Autowired
	private AuthService authService;

	@Autowired
	private MovieRepository movieRepository;
	
	@Autowired
	private ReviewRepository reviewRepository;
		
	
	@Transactional
	public ReviewDTO insertReview(ReviewDTO dto) {
		Review entity = new Review();
		User user = authService.authenticated();
		entity.setMovie(movieRepository.getReferenceById(dto.getMovieId()));
		entity.setText(dto.getText());
		entity.setUser(user);
		entity = reviewRepository.save(entity);
		return new ReviewDTO(entity);
	}
}
