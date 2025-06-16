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




@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthorRepositoryIntegrationTests {
	
	private AuthorRepository underTest;
	
	@Autowired
	public AuthorRepositoryIntegrationTests(AuthorRepository underTest) {
		this.underTest = underTest;
	}
	
	@Test
	public void testThatAuthorCanBeCreatedAndRecalled() {
		
		AuthorEntity authour = TestDataUtil.createTestAuthor();
		underTest.save(authour);
		Optional<AuthorEntity> result =  underTest.findById(authour.getId());
		assertThat(result).isPresent();
	}
	
	@Test
	public void testThatMultipleAuthorsCanBeCreatedAndRecalled() {
		AuthorEntity authourA = TestDataUtil.createTestAuthorA();
		AuthorEntity authourB = TestDataUtil.createTestAuthorB();
		underTest.save(authourA);
		underTest.save(authourB);
		Iterable<AuthorEntity> authours = underTest.findAll();
		assertThat(authours).hasSize(2);
		assertThat(authours).containsExactly(authourA,authourB);
	}
	
	@Test
	public void testThatAuthorCanBeUpdated() {
		AuthorEntity authour = TestDataUtil.createTestAuthor();
		underTest.save(authour);
		authour.setName("Updated");
		underTest.save(authour);
		Optional<AuthorEntity> result = underTest.findById(authour.getId());
		assertThat(result).isPresent();
		assertThat(result.get()).isEqualTo(authour);
	}
	
	@Test
	public void testThatAuthorCanBeDeleted() {
		AuthorEntity authour = TestDataUtil.createTestAuthor();
		underTest.save(authour);
		underTest.deleteById(authour.getId());
		Optional<AuthorEntity> result= underTest.findById(authour.getId());
		assertThat(result).isEmpty();
	}
	
//	@Test
//	public void testThatGetAuthorWithAgeLessThan() {
//		AuthorEntity authour = TestDataUtil.createTestAuthor();
//		underTest.save(authour);
//		
//		AuthorEntity authourA = TestDataUtil.createTestAuthorA();
//		underTest.save(authourA);
//		
//		AuthorEntity authourB= TestDataUtil.createTestAuthorB();
//		underTest.save(authourB);
//		
//		Iterable<AuthorEntity> result = underTest.ageLessThan(75);
//		assertThat(result).containsExactly(authour);
//	}
//	
//	@Test
//	public void testThatGetAuthorWithAgeGreaterThan() {
//		AuthorEntity authour = TestDataUtil.createTestAuthor();
//		underTest.save(authour);
//		
//		AuthorEntity authourA = TestDataUtil.createTestAuthorA();
//		underTest.save(authourA);
//		
//		AuthorEntity authourB= TestDataUtil.createTestAuthorB();
//		underTest.save(authourB);
//		
//		Iterable<AuthorEntity> result = underTest.ageGreaterThan(73);
//		assertThat(result).containsExactly(authourA, authourB);
//	}
}
