package com.toklahBackend.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.toklahBackend.model.ImageInfo;

public interface UserImageDao extends JpaRepository< ImageInfo, String>{

}
