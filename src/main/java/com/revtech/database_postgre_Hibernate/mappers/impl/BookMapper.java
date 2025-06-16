package com.revtech.database_postgre_Hibernate.mappers.impl;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.revtech.database_postgre_Hibernate.domain.dto.BookDto;
import com.revtech.database_postgre_Hibernate.domain.entities.BookEntity;
import com.revtech.database_postgre_Hibernate.mappers.Mapper;

@Component
public class BookMapper implements Mapper<BookEntity, BookDto>{
	
	private ModelMapper modelMapper;
	
	
	public BookMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}
	
	@Override
	public BookDto mapTo(BookEntity book) {
		return modelMapper.map(book, BookDto.class);
	}

	@Override
	public BookEntity mapFrom(BookDto bookdto) {
		return modelMapper.map(bookdto, BookEntity.class);
	}
	
	

}
