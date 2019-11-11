package com.toklahBackend.dao;

import org.springframework.data.repository.CrudRepository;

import com.toklahBackend.model.Complaint;

public interface ComplaintDao extends CrudRepository<Complaint, Integer> {

}
