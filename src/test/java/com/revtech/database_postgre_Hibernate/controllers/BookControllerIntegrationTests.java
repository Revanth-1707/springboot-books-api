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
import com.revtech.database_postgre_Hibernate.domain.dto.BookDto;
import com.revtech.database_postgre_Hibernate.domain.entities.BookEntity;
import com.revtech.database_postgre_Hibernate.services.BookService;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class BookControllerIntegrationTests {
	
	private MockMvc mockMvc;
	
	private ObjectMapper objectMapper;
	
	private BookService bookService;
	
	@Autowired
	public BookControllerIntegrationTests(MockMvc mockMvc, ObjectMapper objectMapper, BookService bookService) {
		this.mockMvc = mockMvc;
		this.objectMapper = objectMapper;
		this.bookService = bookService;
	}
	
	//Creating Book
	@Test
	public void testThatCreateBookReturnsHttpStatus201Created() throws Exception{
		BookDto bookDto= TestDataUtil.createTestBookDto(null);
		String createBookJson = objectMapper.writeValueAsString(bookDto);
		
		mockMvc.perform(
				MockMvcRequestBuilders.put("/books/"+bookDto.getIsbn())
				.contentType(MediaType.APPLICATION_JSON)
				.content(createBookJson)
		).andExpect( 
				MockMvcResultMatchers.status().isCreated()
		);
	}
	@Test
	public void testThatCreateBookReturnsCreateBook() throws Exception{
		BookDto bookDto = TestDataUtil.createTestBookDto(null);
		String createBookJson = objectMapper.writeValueAsString(bookDto);
		
		mockMvc.perform( 
				MockMvcRequestBuilders.put("/books/"+bookDto.getIsbn())
				.contentType(MediaType.APPLICATION_JSON)
				.content(createBookJson)
		).andExpect(  
				MockMvcResultMatchers.jsonPath("$.isbn").value(bookDto.getIsbn())
		).andExpect(
				MockMvcResultMatchers.jsonPath("$.title").value(bookDto.getTitle())
		);
	}
	
	
	
	
	//Getting list of books
	@Test
	public void testThatListBooksReturnsStatus200ok() throws Exception{
		mockMvc.perform(
				MockMvcRequestBuilders.get("/books")
				.contentType(MediaType.APPLICATION_JSON)
		).andExpect(
				MockMvcResultMatchers.status().isOk());
	}
	@Test
	public void testThatListBooksReturnsBook() throws Exception{
		BookEntity bookEntity = TestDataUtil.createTestBookEntity(null);
		bookService.createUpdateBook(bookEntity.getIsbn(), bookEntity);
		mockMvc.perform(
				MockMvcRequestBuilders.get("/books")
				.contentType(MediaType.APPLICATION_JSON)
		).andExpect(
				MockMvcResultMatchers.jsonPath("$.content[0].isbn").value("123-456-7-8")
		).andExpect(
				MockMvcResultMatchers.jsonPath("$.content[0].title").value("abc"));
	}
	
	
	
	
	//Getting one book
	@Test
	public void testThatGetBookReturnsBookreturnStatus200okWhenBookExist() throws Exception{
		BookEntity bookEntity = TestDataUtil.createTestBookEntity(null);
		bookService.createUpdateBook(bookEntity.getIsbn(), bookEntity);
		mockMvc.perform(
				MockMvcRequestBuilders.get("/books/"+bookEntity.getIsbn())
				.contentType(MediaType.APPLICATION_JSON)
		).andExpect(
				MockMvcResultMatchers.status().isOk());
	}
	
	
	
	
	//updating
	@Test
	public void testThatUpdateBookReturnsHttpStatus200ok() throws Exception{
		BookEntity testBookEntity = TestDataUtil.createTestBookEntity(null);
		BookEntity savedBookEntity = bookService.createUpdateBook(testBookEntity.getIsbn(), testBookEntity);
		
		BookDto testBookDto = TestDataUtil.createTestBookDto(null);
		testBookDto.setIsbn(savedBookEntity.getIsbn());
		String testBookDtoJson = objectMapper.writeValueAsString(testBookDto);

		mockMvc.perform(
				MockMvcRequestBuilders.put("/books/"+savedBookEntity.getIsbn())
				.contentType(MediaType.APPLICATION_JSON)
				.content(testBookDtoJson)
		).andExpect(
				MockMvcResultMatchers.status().isOk()
		);
	}
	@Test
	public void testThatUpdateBookReturnsUpdateBook() throws Exception{
		BookEntity testBookEntity = TestDataUtil.createTestBookEntity(null);
		BookEntity savedBookEntity = bookService.createUpdateBook(testBookEntity.getIsbn(), testBookEntity);
		
		BookDto testBookDto = TestDataUtil.createTestBookDto(null);
		testBookDto.setIsbn(savedBookEntity.getIsbn());
		testBookDto.setTitle("updated");
		String testBookDtoJson = objectMapper.writeValueAsString(testBookDto);
		
		mockMvc.perform(
				MockMvcRequestBuilders.put("/books/"+savedBookEntity.getIsbn())
				.contentType(MediaType.APPLICATION_JSON)
				.content(testBookDtoJson)
		).andExpect(
				MockMvcResultMatchers.jsonPath("$.isbn").value(testBookDto.getIsbn())
		).andExpect(
				MockMvcResultMatchers.jsonPath("$.title").value(testBookDto.getTitle())
		);	
	}
	
	
	@Test
	public void testThatPartialUpdateBookReturnsHttpStatus200ok() throws Exception {
		BookEntity bookEntity = TestDataUtil.createTestBookEntity(null);
		BookEntity savedBookEntity = bookService.createUpdateBook(bookEntity.getIsbn(), bookEntity);
		
		BookDto bookDto = TestDataUtil.createTestBookDto(null);
		bookDto.setIsbn(savedBookEntity.getIsbn());
		bookDto.setTitle("new title");
		String jsonBookDto = objectMapper.writeValueAsString(bookDto);
		mockMvc.perform(
				MockMvcRequestBuilders.patch("/books/"+savedBookEntity.getIsbn())
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonBookDto)
		).andExpect(
				MockMvcResultMatchers.status().isOk()
		);
	}
	
	@Test
	public void testThatPartialUpdateBookReturnsUpdateBook() throws Exception {
		BookEntity bookEntity = TestDataUtil.createTestBookEntity(null);
		BookEntity savedBookEntity = bookService.createUpdateBook(bookEntity.getIsbn(), bookEntity);
		
		BookDto bookDto = TestDataUtil.createTestBookDto(null);
		bookDto.setIsbn(savedBookEntity.getIsbn());
		bookDto.setTitle("new title");
		String jsonBookDto = objectMapper.writeValueAsString(bookDto);
		mockMvc.perform(
				MockMvcRequestBuilders.patch("/books/"+savedBookEntity.getIsbn())
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonBookDto)
		).andExpect(
				MockMvcResultMatchers.jsonPath("$.isbn").value(savedBookEntity.getIsbn())
		).andExpect(
				MockMvcResultMatchers.jsonPath("$.title").value("new title")
		);
	}
	
	
	//delete
	@Test
	public void testThatDeleteNonExistingBookReturnHttpStatus204NoContent() throws Exception{
		BookEntity bookEntity = TestDataUtil.createTestBookEntity(null);
		BookEntity savedBookEntity = bookService.createUpdateBook(bookEntity.getIsbn(), bookEntity);
		
		mockMvc.perform(
				MockMvcRequestBuilders.delete("/books/"+savedBookEntity.getIsbn())
				.contentType(MediaType.APPLICATION_JSON)
		).andExpect(MockMvcResultMatchers.status().isNoContent());
	}
}
