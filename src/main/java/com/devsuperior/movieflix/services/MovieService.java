package com.devsuperior.movieflix.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.movieflix.dto.MovieCardDTO;
import com.devsuperior.movieflix.projections.MovieProjection;
import com.devsuperior.movieflix.repositories.MovieRepository;

@Service
public class MovieService {

	@Autowired
	private MovieRepository repository;
	
	@Transactional(readOnly = true)
	public Page<MovieProjection> findAllPaged(String strGenreId, Pageable pageable) {
		Long genreId = null;
		if (!"0".equals(strGenreId)) {
			genreId = Long.parseLong(strGenreId);
		}
		Page<MovieProjection> page = repository.searchMovies(genreId, pageable);
		
		//Page<MovieCardDTO> pageDto = new PageImpl<>(page, page.getPageable(), page.getTotalElements());
		
		return page;
	}
}
