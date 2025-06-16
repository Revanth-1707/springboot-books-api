package com.revtech.database_postgre_Hibernate.mappers;

public interface Mapper<A,B> {
	
	B mapTo(A a);
	
	A mapFrom(B b);
}
