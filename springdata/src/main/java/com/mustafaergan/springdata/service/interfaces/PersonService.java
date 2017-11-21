package com.mustafaergan.springdata.service.interfaces;

import java.util.List;

import com.mustafaergan.springdata.entity.Person;
import com.mustafaergan.springdata.filter.PersonFilter;

public interface PersonService {

	Person getByKey(String key);

	Person getByKeyEager(String key);

	List<Person> getByKeyFilter(PersonFilter personFilter);

}
