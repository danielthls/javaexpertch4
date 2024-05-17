package com.devsuperior.movieflix.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.movieflix.dto.MovieCardDTO;
import com.devsuperior.movieflix.dto.MovieDetailsDTO;
import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.entities.Review;
import com.devsuperior.movieflix.projections.MovieProjection;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.repositories.ReviewRepository;
import com.devsuperior.movieflix.repositories.UserRepository;
import com.devsuperior.movieflix.services.exceptions.ResourceNotFoundException;

@Service
public class MovieService {

	@Autowired
	private MovieRepository repository;
	
	@Autowired
	private ReviewRepository reviewRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Transactional(readOnly = true)
	public Page<MovieCardDTO> findAllPaged(String strGenreId, Pageable pageable) {
		Long genreId = null;
		if (!"0".equals(strGenreId)) {
			genreId = Long.parseLong(strGenreId);
		}
		Page<MovieProjection> page = repository.searchMovies(genreId, pageable);
		
		List<MovieCardDTO> list = page.map(m -> new MovieCardDTO(m)).toList();
		
		Page<MovieCardDTO> dto = new PageImpl<>(list, page.getPageable(), page.getTotalElements());
		
		return dto;
	}
	
	@Transactional(readOnly = true)
	public MovieDetailsDTO findById(Long id) {
		Optional<Movie> obj = repository.searchMovieWithReview(id);
		Movie entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		Set<Review> reviews = reviewRepository.findByMovieId(id);
		return new MovieDetailsDTO(entity, reviews);		
	}
	
	@Transactional
	public MovieDetailsDTO insertReview(ReviewDTO dto) {
		Review entity = new Review();
		entity.setMovie(repository.getReferenceById(dto.getMovieId()));
		entity.setText(dto.getText());
		entity.setUser(userRepository.getReferenceById(dto.getUserId()));
		entity = reviewRepository.save(entity);
		return findById(dto.getMovieId());
	}
}
