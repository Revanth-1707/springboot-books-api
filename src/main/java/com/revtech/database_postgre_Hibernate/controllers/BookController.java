package com.revtech.database_postgre_Hibernate.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.revtech.database_postgre_Hibernate.domain.dto.BookDto;
import com.revtech.database_postgre_Hibernate.domain.entities.BookEntity;
import com.revtech.database_postgre_Hibernate.mappers.Mapper;
import com.revtech.database_postgre_Hibernate.services.BookService;

@RestController
public class BookController {
	
	private Mapper<BookEntity, BookDto> bookMapper;
	private BookService bookService;
	
	public BookController(Mapper<BookEntity, BookDto> bookMapper, BookService bookService) {
		this.bookMapper = bookMapper;
		this.bookService = bookService;
	}
	
	@PutMapping("/books/{isbn}")
	public ResponseEntity<BookDto> createUpdateBook(@PathVariable("isbn") String isbn, @RequestBody BookDto bookDto){
		BookEntity bookEntity = bookMapper.mapFrom(bookDto);
		boolean bookExists = bookService.isExists(isbn);
		BookEntity savedBookEntity =  bookService.createUpdateBook(isbn, bookEntity);
		BookDto savedBookDto = bookMapper.mapTo(savedBookEntity);
		if(!bookExists) {
			return new ResponseEntity<>(savedBookDto,HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(savedBookDto,HttpStatus.OK);
		}
	}
	
	@GetMapping("/books")
	public Page<BookDto> listBooks(Pageable pageable){
		Page<BookEntity> books = bookService.findAll(pageable);
		return books.map(bookMapper::mapTo);
	}
	
	@GetMapping("/books/{isbn}")
	public ResponseEntity<BookDto> getBook(@PathVariable("isbn") String isbn) throws Exception{
		Optional<BookEntity> foundBookEnitiy = bookService.findOne(isbn);
		return foundBookEnitiy.map(bookEntity -> {
			BookDto bookDto = bookMapper.mapTo(bookEntity);
			return new ResponseEntity<>(bookDto,HttpStatus.OK);
		}).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}
	
	
	@PatchMapping("/books/{isbn}")
	public ResponseEntity<BookDto> partialUpdate(@PathVariable("isbn") String isbn, @RequestBody BookDto bookDto){
		BookEntity bookEntity = bookMapper.mapFrom(bookDto);
		boolean bookExists = bookService.isExists(isbn);
		if(!bookExists) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		BookEntity updatedBookEntity = bookService.partialUpdate(isbn,bookEntity);
		return new ResponseEntity<>(bookMapper.mapTo(updatedBookEntity),HttpStatus.OK);
	}
	
	
	@DeleteMapping("/books/{isbn}")
	public ResponseEntity deleteBook(@PathVariable("isbn") String isbn) {
		bookService.delete(isbn);
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}
}
