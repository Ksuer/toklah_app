package com.toklahBackend.model;

import java.sql.Date;
import java.sql.Time;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class Login {
	
	private String mobileOrEmail;
	private String password;
}
