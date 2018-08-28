package com.example.demo.appid;

import org.springframework.vault.authentication.AppIdUserIdMechanism;

public class CustomUserIdMechanism implements AppIdUserIdMechanism {

	@Override
	public String createUserId() {
		return "my-static-userid";
	}

}
