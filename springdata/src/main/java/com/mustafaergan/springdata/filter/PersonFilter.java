/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mustafaergan.springdata.filter;

import lombok.Data;
import org.springframework.data.jpa.domain.Specifications;

import com.mustafaergan.springdata.entity.Person;

/**
 *
 * @author mustafa.ergan
 */
public @Data class PersonFilter extends Filter<Person>{
    
	private String key;
    private String name;

    public PersonFilter() {
    }
    
    public PersonFilter(SearchCriteria criteria) {
        super(criteria);
    }
    
    public Specifications<Person> createFilter(){
        PersonFilter spec1 = new PersonFilter(new SearchCriteria("value", ":", this.name));
        PersonFilter spec2 = new PersonFilter(new SearchCriteria("key", ":", this.key));
        return Specifications.where(spec1).and(spec2);
    }
    
}
