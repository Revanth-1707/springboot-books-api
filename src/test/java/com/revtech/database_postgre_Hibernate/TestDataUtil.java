package com.revtech.database_postgre_Hibernate;

import com.revtech.database_postgre_Hibernate.domain.dto.AuthorDto;
import com.revtech.database_postgre_Hibernate.domain.dto.BookDto;
import com.revtech.database_postgre_Hibernate.domain.entities.AuthorEntity;
import com.revtech.database_postgre_Hibernate.domain.entities.BookEntity;

public final class TestDataUtil {
	
	private TestDataUtil() {
		
	}
	
	public static AuthorEntity createTestAuthor() {
		return AuthorEntity.builder()
				.name("abcdef")
				.age(72)
				.build();
	}
	
	public static AuthorDto createTestAuthorDto() {
		return AuthorDto.builder()
				.name("abcdef")
				.age(72)
				.build();
	}

	public static AuthorEntity createTestAuthorA() {
		return AuthorEntity.builder()
				.name("defgh")
				.age(75)
				.build();
	}
	
	public static AuthorEntity createTestAuthorB() {
		return AuthorEntity.builder()
				.name("defgh")
				.age(75)
				.build();
	}
	
	
	//Books
	public static BookEntity createTestBookEntity(final AuthorEntity authour) {
		return BookEntity.builder()
				.isbn("123-456-7-8")
				.title("abc")
				.author(authour)
				.build();
	}
	
	public static BookDto createTestBookDto(final AuthorDto authour) {
		return BookDto.builder()
				.isbn("123-456-7-8")
				.title("abc")
				.author(authour)
				.build();
	}
	
	public static BookEntity createTestBookA(final AuthorEntity authour) {
		return BookEntity.builder()
				.isbn("123-456-8-8")
				.title("def")
				.author(authour)
				.build();
	}
	
	public static BookEntity createTestBookB(final AuthorEntity authour) {
		return BookEntity.builder()
				.isbn("123-456-9-8")
				.title("fgh")
				.author(authour)
				.build();
	}
	
	public static BookEntity createTestBookC(final AuthorEntity authour) {
		return BookEntity.builder()
				.isbn("123-456-10-8")
				.title("fgh2")
				.author(authour)
				.build();
	}
}
