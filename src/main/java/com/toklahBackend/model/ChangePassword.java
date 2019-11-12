package com.toklahBackend.model;

import java.sql.Date;
import java.sql.Time;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class ChangePassword {

	private int userId;
	private String oldPassword;
	private String newPassword;

	
}
