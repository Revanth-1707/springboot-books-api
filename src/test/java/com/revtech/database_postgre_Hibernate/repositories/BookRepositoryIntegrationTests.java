package com.revtech.database_postgre_Hibernate.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.revtech.database_postgre_Hibernate.TestDataUtil;
import com.revtech.database_postgre_Hibernate.domain.entities.AuthorEntity;
import com.revtech.database_postgre_Hibernate.domain.entities.BookEntity;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookRepositoryIntegrationTests {
	
	private BookRepository underTest;
	
	private AuthorRepository authorRepository;
	

	@Autowired
	public BookRepositoryIntegrationTests(BookRepository bookRepository, AuthorRepository authorRepository) {
		this.underTest = bookRepository;
		this.authorRepository=authorRepository;
	}
	
	@Test
	public void testThatBookCanBeCreateAndRecalled() {
		AuthorEntity authour = TestDataUtil.createTestAuthor();
		authorRepository.save(authour);
		BookEntity book = TestDataUtil.createTestBookEntity(authour);
		underTest.save(book);
		Optional<BookEntity> result = underTest.findById(book.getIsbn());
		assertThat(result).isPresent();
		assertThat(result.get()).isEqualTo(book);
	}
	
	@Test
	public void testThatMultipleBooksCanBeCreateAndRecalled() {
		AuthorEntity authourA = TestDataUtil.createTestAuthorA();
		AuthorEntity authourB = TestDataUtil.createTestAuthorB();
		authorRepository.save(authourA);
		authorRepository.save(authourB);
		
		BookEntity bookA = TestDataUtil.createTestBookA(authourA);
		BookEntity bookB = TestDataUtil.createTestBookB(authourB);
		BookEntity bookC = TestDataUtil.createTestBookC(authourB);
		underTest.save(bookA);
		underTest.save(bookB);
		underTest.save(bookC);
		
		Iterable<BookEntity> result = underTest.findAll();
		assertThat(result).hasSize(3).containsExactly(bookA,bookB,bookC);
	}
	
	@Test
	public void testThatBookCanBeUpdated() {
		AuthorEntity authour = TestDataUtil.createTestAuthor();
		authorRepository.save(authour);
		BookEntity book = TestDataUtil.createTestBookEntity(authour);
		underTest.save(book);
		
		book.setTitle("updated");
		underTest.save(book);
		
		Optional<BookEntity> result = underTest.findById(book.getIsbn());
		assertThat(result).isPresent();
		assertThat(result.get()).isEqualTo(book);
	}
	
	@Test
	public void testThatBookCanBeDeleted() {
		AuthorEntity authour = TestDataUtil.createTestAuthor();
		
		BookEntity book = TestDataUtil.createTestBookEntity(authour);
		underTest.save(book);
		
		underTest.deleteById(book.getIsbn());
		
		Optional<BookEntity> result = underTest.findById(book.getIsbn());
		assertThat(result).isEmpty();
	}

}
