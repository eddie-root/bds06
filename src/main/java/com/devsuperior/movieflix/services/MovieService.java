package com.devsuperior.movieflix.services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.movieflix.dto.MovieDTO;
import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.entities.Genre;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.entities.Review;
import com.devsuperior.movieflix.repositories.GenreRepository;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.repositories.ReviewRepository;
import com.devsuperior.movieflix.services.exceptions.ResourceNotFoundException;

@Service
public class MovieService {

	@Autowired
	private MovieRepository repository;

	@Autowired
	private GenreRepository genreRepository;

	@Autowired
	private ReviewRepository reviewRepository;

	@Transactional(readOnly = true)
	@PreAuthorize("hasAnyRole('MEMBER','VISITOR')")
	public MovieDTO findById(Long id) {
		Optional<Movie> obj = repository.findById(id);
		Movie entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		return new MovieDTO(entity);
	}

	@Transactional(readOnly = true)
	@PreAuthorize("hasAnyRole('MEMBER','VISITOR')")
	public Page<MovieDTO> findAllPaged(Long genreId, Pageable pageable) {
		List<Genre> genre = (genreId == 0) ? null : Arrays.asList(genreRepository.getOne(genreId));
		Page<Movie> page = repository.findByGenre(genre, pageable);
		return page.map(x -> new MovieDTO(x));
	}

	@Transactional(readOnly = true)
	@PreAuthorize("hasAnyRole('MEMBER','VISITOR')")
	public List<ReviewDTO> findByMovieIdReview(Long movieId) {
		List<Review> reviews = reviewRepository.findByMovieIdReview(movieId);
		return reviews.stream().map(x -> new ReviewDTO(x)).collect(Collectors.toList());

	}

}
