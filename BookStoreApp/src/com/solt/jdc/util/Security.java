package com.solt.jdc.util;

import com.solt.jdc.entity.User;

public class Security {
	private static User user;

	public static User getUser() {
		return user;
	}

	public static void setUser(User user) {
		Security.user = user;
	}
	

}
