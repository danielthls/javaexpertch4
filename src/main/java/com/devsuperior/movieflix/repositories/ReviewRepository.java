package com.devsuperior.movieflix.repositories;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.movieflix.entities.Genre;
import com.devsuperior.movieflix.entities.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
	Set<Review> findByMovieId(Long id);
}
