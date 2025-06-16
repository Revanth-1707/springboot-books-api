package com.revtech.database_postgre_Hibernate.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.revtech.database_postgre_Hibernate.domain.entities.BookEntity;

@Service
public interface BookService {
	
	BookEntity createUpdateBook(String isbn, BookEntity book);

	List<BookEntity> findAll();
	
	Page<BookEntity> findAll(Pageable pageable);

	Optional<BookEntity> findOne(String isbn);

	boolean isExists(String isbn);

	BookEntity partialUpdate(String isbn, BookEntity bookEntity);

	void delete(String isbn);

}
