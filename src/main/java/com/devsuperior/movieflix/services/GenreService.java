package com.devsuperior.movieflix.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.movieflix.dto.GenreDTO;
import com.devsuperior.movieflix.entities.Genre;
import com.devsuperior.movieflix.repositories.GenreRepository;

@Service
public class GenreService {
	
	final GenreRepository repository;
	
	public GenreService(GenreRepository repository) {
		this.repository = repository;
	}
	
	@Transactional(readOnly = true)
	@PreAuthorize("hasAnyRole('VISITOR', 'MEMBER')")
	public List<GenreDTO> findAll() {
		 List<Genre> list = repository.findAll();
	     return list.stream().map(x -> new GenreDTO(x)).collect(Collectors.toList());
	}

}
