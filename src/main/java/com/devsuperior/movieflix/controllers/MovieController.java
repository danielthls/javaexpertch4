package com.devsuperior.movieflix.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.movieflix.projections.MovieProjection;
import com.devsuperior.movieflix.services.MovieService;

@RestController
@RequestMapping(value = "/movies")
public class MovieController {

	@Autowired
	private MovieService service;
	
    @PreAuthorize("hasAnyRole('VISITOR', 'MEMBER')")
	@GetMapping
	public ResponseEntity<Page<MovieProjection>> findAll(
			@RequestParam(value = "genreId", defaultValue  = "0")String genreId,
			Pageable pageable
			) {
		Page<MovieProjection> list = service.findAllPaged(genreId, pageable);
		return ResponseEntity.ok(list);
	}
}
