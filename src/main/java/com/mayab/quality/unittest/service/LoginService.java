package com.mayab.quality.unittest.service;

import com.mayab.quality.unittest.dao.IDAOUser;
import com.mayab.quality.unittest.model.User;

public class LoginService {
	IDAOUser dao;
	
	public LoginService(IDAOUser d) {
		this.dao = d;
	}
	public boolean login(String email, String pass) {
		User u = dao.findbyUserName(email);
		if (u != null) {
			if(u.getPassword() == pass) {
				u.setLogged(true);
				return true;
			}
		}
		return false;
	}
}
