package com.revtech.database_postgre_Hibernate.controllers;



import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.revtech.database_postgre_Hibernate.TestDataUtil;
import com.revtech.database_postgre_Hibernate.domain.dto.AuthorDto;
import com.revtech.database_postgre_Hibernate.domain.entities.AuthorEntity;
import com.revtech.database_postgre_Hibernate.services.AuthorService;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class AuthorControllerIntegrationTests {
	
	private AuthorService authorService;
	
	private  MockMvc mockMvc;
	
	private ObjectMapper objectMapper;
	
	@Autowired
	public AuthorControllerIntegrationTests(MockMvc mockMvc, AuthorService authorService) {
		this.mockMvc = mockMvc;
		this.objectMapper = new ObjectMapper();
		this.authorService = authorService;
	}
	

	//Creating the book
	@Test
	public void testThatCreateAuthorSuccessfullyReturnsHttp201Created() throws Exception {
		AuthorEntity testAuthor = TestDataUtil.createTestAuthor();
		testAuthor.setId(null);
		String authorJson = objectMapper.writeValueAsString(testAuthor);
		
		mockMvc.perform(
				MockMvcRequestBuilders.post("/authors")
				.contentType(MediaType.APPLICATION_JSON)
				.content(authorJson)
		).andExpect(
				MockMvcResultMatchers.status().isCreated()
		);
	}
	@Test
	public void testThatCreateAuthorSuccessfullyReturnsSavedAuthor() throws Exception{
		AuthorEntity testAuthor = TestDataUtil.createTestAuthor();
		testAuthor.setId(null);
		String authorJson = objectMapper.writeValueAsString(testAuthor);
		
		mockMvc.perform(
				MockMvcRequestBuilders.post("/authors")
				.contentType(MediaType.APPLICATION_JSON)
				.content(authorJson)
		).andExpect(
				MockMvcResultMatchers.jsonPath("$.id").isNumber() 
		).andExpect(
				MockMvcResultMatchers.jsonPath("$.name").value("abcdef")
		).andExpect(
				MockMvcResultMatchers.jsonPath("$.age").value("72"));
	}
	
	
	
	//Getting the list of authors
	@Test
	public void testThatListAuthorsReturnHttpStatus200() throws Exception{
		mockMvc.perform(
				MockMvcRequestBuilders.get("/authors")
				.contentType(MediaType.APPLICATION_JSON)
				).andExpect(MockMvcResultMatchers.status().isOk());
	}
	@Test
	public void testThatListAuthorsReturnListOfAuthors() throws Exception{
		AuthorEntity testAuthorEntity = TestDataUtil.createTestAuthor();
		authorService.save(testAuthorEntity);
		mockMvc.perform(   
				MockMvcRequestBuilders.get("/authors")
				.contentType(MediaType.APPLICATION_JSON)
		).andExpect(
				MockMvcResultMatchers.jsonPath("$[0].id").isNumber()
		).andExpect(
				MockMvcResultMatchers.jsonPath("$[0].name").value("abcdef")
		).andExpect(
				MockMvcResultMatchers.jsonPath("$[0].age").value(72));
	}
	
	
	
	//Getting one author
	@Test
	public void testThatGetAuthorReturnsHttpStatus200WhenAuthorExist() throws Exception{
		AuthorEntity testAuthorEntity = TestDataUtil.createTestAuthor();
		authorService.save(testAuthorEntity);
		
		mockMvc.perform(
				MockMvcRequestBuilders.get("/authors/1")
				.contentType(MediaType.APPLICATION_JSON)
		).andExpect(MockMvcResultMatchers.status().isOk());
	}
	@Test
	public void testThatGetAuthorReturnsAuthorWhenAuthorExist() throws Exception{
		AuthorEntity testAuthorEntity = TestDataUtil.createTestAuthor();
		authorService.save(testAuthorEntity);
		mockMvc.perform(
				MockMvcRequestBuilders.get("/authors/"+testAuthorEntity.getId())
				.contentType(MediaType.APPLICATION_JSON)
		).andExpect(
				MockMvcResultMatchers.jsonPath("$.id").value(1)
		).andExpect(
				MockMvcResultMatchers.jsonPath("$.name").value("abcdef")
		).andExpect(
				MockMvcResultMatchers.jsonPath("$.age").value(72)
		);
	}
	
	
	//Updating the author
	@Test
	public void testThatFullUpdateAuthorReturnsHttpStatus200WhenAuthorExists() throws Exception{
		AuthorEntity testAuthorEntity = TestDataUtil.createTestAuthor();
		AuthorEntity savedAuthor = authorService.save(testAuthorEntity);
		
		AuthorDto authorDto = TestDataUtil.createTestAuthorDto();
		String authorDtoJson = objectMapper.writeValueAsString(authorDto);
		
		mockMvc.perform(
				MockMvcRequestBuilders.put("/authors/"+savedAuthor.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(authorDtoJson)
		).andExpect(MockMvcResultMatchers.status().isOk());
	}
	@Test
	public void testThatFullUpdateUpdatesExistingAuthor() throws Exception {
		AuthorEntity authorEntity = TestDataUtil.createTestAuthor();
		AuthorEntity savedAuthorEntity = authorService.save(authorEntity);
		
		AuthorEntity authorDto = TestDataUtil.createTestAuthorA();
		authorDto.setId(savedAuthorEntity.getId());
		String authorDtoUpdateJson = objectMapper.writeValueAsString(authorDto);
		
		mockMvc.perform(
				MockMvcRequestBuilders.put("/authors/"+savedAuthorEntity.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(authorDtoUpdateJson)
		).andExpect(
				MockMvcResultMatchers.jsonPath("$.id").value(savedAuthorEntity.getId())
		).andExpect(
				MockMvcResultMatchers.jsonPath("$.name").value(authorDto.getName())
		).andExpect(
				MockMvcResultMatchers.jsonPath("$.age").value(authorDto.getAge())
		);
	}
	
	
	//Partial update
	@Test
	public void testThatPartialUpdateExistingAuthorReturnsHttpStatus200ok() throws Exception {
		AuthorEntity authorEntity = TestDataUtil.createTestAuthor();
		AuthorEntity savedAuthorEntity =  authorService.save(authorEntity);
		
		AuthorDto authorDto = TestDataUtil.createTestAuthorDto();
		authorDto.setName("updated");
		String jsonAuthorDto = objectMapper.writeValueAsString(authorDto);
		
		mockMvc.perform(
				MockMvcRequestBuilders.patch("/authors/"+savedAuthorEntity.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonAuthorDto)
		).andExpect(
				MockMvcResultMatchers.status().isOk());
	}
	@Test
	public void testThatPatialUpdateExistingAuthhorReturnUpdateAuthor() throws Exception {
		AuthorEntity authorEntity = TestDataUtil.createTestAuthor();
		AuthorEntity savedAuthorEntity = authorService.save(authorEntity);
		
		AuthorDto authorDto = TestDataUtil.createTestAuthorDto();
		authorDto.setAge(82);
		authorDto.setName("abcd");
		String jsonAuthorDto = objectMapper.writeValueAsString(authorDto);
		
		mockMvc.perform(
				MockMvcRequestBuilders.patch("/authors/"+savedAuthorEntity.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonAuthorDto)
		).andExpect(
				MockMvcResultMatchers.jsonPath("$.id").value(authorEntity.getId())
		).andExpect(
				MockMvcResultMatchers.jsonPath("$.name").value("abcd")
		).andExpect(
				MockMvcResultMatchers.jsonPath("$.age").value(82)
		);
	}
	
	
	//Delete
	@Test
	public void testThatDeleteAuthorReturnHttpStatus2024ForExistingAuthor() throws Exception{
		AuthorEntity authorEntity = TestDataUtil.createTestAuthor();
		AuthorEntity savedAuthorEntity = authorService.save(authorEntity);
		
		mockMvc.perform(
				MockMvcRequestBuilders.delete("/authors/"+savedAuthorEntity.getId())
				.contentType(MediaType.APPLICATION_JSON)
		).andExpect(MockMvcResultMatchers.status().isNoContent());
	}
}
