package com.devsuperior.movieflix.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.projections.MovieProjection;

public interface MovieRepository extends JpaRepository<Movie, Long> {

	@Query(nativeQuery = true, value = """
			SELECT * FROM 
			(SELECT DISTINCT tb_movie.id, tb_movie.title, tb_movie.sub_title as subtitle, tb_movie.movie_year as `year`, tb_movie.img_url as imgurl
			FROM tb_movie
			INNER JOIN tb_genre ON tb_movie.genre_id = tb_genre.id
			WHERE (:genreId IS NULL or tb_movie.genre_id = :genreId)
			ORDER BY title
			) as tb_result
			""", countQuery = """
			SELECT COUNT(*)
			FROM (
			    SELECT DISTINCT tb_movie.id, tb_movie.title, tb_movie.sub_title, tb_movie.movie_year, tb_movie.img_url
			    FROM tb_movie
			    INNER JOIN tb_genre ON tb_movie.genre_id = tb_genre.id
			    WHERE (:genreId IS NULL OR tb_movie.genre_id = :genreId)
			) AS tb_count;	
			""")
	Page<MovieProjection> searchMovies(Long genreId, Pageable pageable); 
	
	@Query("SELECT obj "
			+ "FROM Movie obj " 		
			+ "JOIN FETCH obj.reviews "
			+ "WHERE obj.id = :movieId "
			)
	Optional<Movie> searchMovieWithReview(Long movieId);
	
}

