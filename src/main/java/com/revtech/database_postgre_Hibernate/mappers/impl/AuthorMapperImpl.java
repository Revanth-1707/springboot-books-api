
package com.revtech.database_postgre_Hibernate.mappers.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.revtech.database_postgre_Hibernate.domain.dto.AuthorDto;
import com.revtech.database_postgre_Hibernate.domain.entities.AuthorEntity;
import com.revtech.database_postgre_Hibernate.mappers.Mapper;

@Component
public class AuthorMapperImpl implements Mapper<AuthorEntity, AuthorDto>{
	
	private ModelMapper modelMapper;

	private AuthorMapperImpl(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}
	
	@Override
	public AuthorDto mapTo(AuthorEntity authorEntity) {
		return modelMapper.map(authorEntity, AuthorDto.class);
	}

	@Override
	public AuthorEntity mapFrom(AuthorDto authorDto) {
		return modelMapper.map(authorDto, AuthorEntity.class);
	}
}
