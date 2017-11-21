package com.mustafaergan.springdata.dao;

import com.mustafaergan.springdata.entity.Person;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PersonDao extends JpaRepository<Person, String>, JpaSpecificationExecutor<Person>{

    @EntityGraph(value = "Person.allJoins", type=EntityGraphType.LOAD)
    public Person findByPersonId(String id);
    
    //@EntityGraph haricinde fetch ilede yapılabilir
    @Query("SELECT t FROM Person t JOIN FETCH t.departmantSet l JOIN FETCH t.customerSet c WHERE t.personId = (:id)")
    public Person findByPersonIdWithFetch(@Param("id") String id);
  
	
}
