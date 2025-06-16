package com.revtech.database_postgre_Hibernate.services;


import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.revtech.database_postgre_Hibernate.domain.entities.AuthorEntity;

@Service
public interface AuthorService {
	
	public AuthorEntity save(AuthorEntity authorEntity);

	public List<AuthorEntity> listAuthors();

	public Optional<AuthorEntity> findone(Long id);

	public boolean isExists(Long id);

	public AuthorEntity partialUpdate(Long id,AuthorEntity authorEntity);

	public void delete(Long id);

}
