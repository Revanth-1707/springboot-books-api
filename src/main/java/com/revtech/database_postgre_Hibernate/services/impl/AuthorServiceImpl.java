package com.revtech.database_postgre_Hibernate.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


import org.springframework.stereotype.Service;

import com.revtech.database_postgre_Hibernate.domain.entities.AuthorEntity;
import com.revtech.database_postgre_Hibernate.repositories.AuthorRepository;
import com.revtech.database_postgre_Hibernate.services.AuthorService;


@Service
public class AuthorServiceImpl implements AuthorService{

	private AuthorRepository authorRepository;
	
	public AuthorServiceImpl(AuthorRepository authorRepository) {
		this.authorRepository = authorRepository;
	}
	
	@Override
	public AuthorEntity save(AuthorEntity authorEntity) {
		return authorRepository.save(authorEntity);
	}

	@Override
	public List<AuthorEntity> listAuthors() {
		return StreamSupport.stream(authorRepository
				.findAll()
				.spliterator(),
				false)
			.collect(Collectors.toList());
	}

	@Override
	public Optional<AuthorEntity> findone(Long id) {
		return authorRepository.findById(id);
	}

	@Override
	public boolean isExists(Long id) {
		return authorRepository.existsById(id);
	}

	@Override
	public AuthorEntity partialUpdate(Long id, AuthorEntity authorEntity) {
		authorEntity.setId(id);
		return authorRepository.findById(id).map(existingAuthor -> {
			Optional.ofNullable(authorEntity.getName()).ifPresent(existingAuthor::setName);
			Optional.ofNullable(authorEntity.getAge()).ifPresent(existingAuthor::setAge);
			return authorRepository.save(existingAuthor);
		}).orElseThrow(() -> new RuntimeException("Author does not exist"));
	}
	

	@Override
	public void delete(Long id) {
		authorRepository.deleteById(id);
	}
}
