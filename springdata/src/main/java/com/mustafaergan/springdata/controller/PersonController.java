package com.mustafaergan.springdata.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mustafaergan.springdata.entity.Person;
import com.mustafaergan.springdata.filter.PersonFilter;
import com.mustafaergan.springdata.service.interfaces.PersonService;

@RestController
@RequestMapping("/person/")
public class PersonController {
	
	@Autowired
	PersonService personService;
	
	
    @RequestMapping(value = "/getEager", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String getEager(HttpServletRequest request, HttpServletResponse response) {
        String key = ServletRequestUtils.getStringParameter(request, "key", null);
        Person person = personService.getByKeyEager(key);
        Gson gson = new GsonBuilder().setDateFormat("dd.MM.yyyy HH:mm:ss").create();
        return gson.toJson(person);
    }
    
    
    @RequestMapping(value = "/getFilterData", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String getEager(HttpServletRequest request, HttpServletResponse response, PersonFilter personFilter) {
        List<Person> person = personService.getByKeyFilter(personFilter);
        Gson gson = new GsonBuilder().setDateFormat("dd.MM.yyyy HH:mm:ss").create();
        return gson.toJson(person);
    }
	
    @RequestMapping(value = "/get", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String getValue(HttpServletRequest request, HttpServletResponse response) {
        String key = ServletRequestUtils.getStringParameter(request, "key", null);
        Person person = personService.getByKey(key);
        Gson gson = new GsonBuilder().setDateFormat("dd.MM.yyyy HH:mm:ss")
                .addSerializationExclusionStrategy(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        return f.getName().contains("departmantSet") || f.getName().contains("customerSet");
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> aClass) {
                        return false;
                    }
                })
                .create();
        return gson.toJson(person);
    }

}
