package com.toklahBackend.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class ChangePassword {

	private int userId;
	private int adminId;
	private String oldPassword;
	private String newPassword;
	
}
