package com.mustafaergan.springdata.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mustafaergan.springdata.dao.PersonDao;
import com.mustafaergan.springdata.entity.Person;
import com.mustafaergan.springdata.filter.PersonFilter;
import com.mustafaergan.springdata.service.interfaces.PersonService;

@Service
@Transactional
public class PersonServiceImpl implements PersonService {
	
	@Autowired
	PersonDao personDao;

	@Override
	public Person getByKey(String key) {
		return personDao.findOne(key);
	}

	@Override
	public Person getByKeyEager(String key) {
		return personDao.findByPersonId(key);
	}

	@Override
	public List<Person> getByKeyFilter(PersonFilter personFilter) {
		return personDao.findAll(personFilter.createFilter());
	}

}
