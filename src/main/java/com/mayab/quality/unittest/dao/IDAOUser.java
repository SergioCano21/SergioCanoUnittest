package com.mayab.quality.unittest.dao;

import java.util.List;

import com.mayab.quality.unittest.model.User;

public interface IDAOUser {
	User findbyUserName(String name);
	User findUserByEmail(String email);
	int save(User user);
	List<User> findAll();
	User findById(int id);
	boolean deleteById(int id);
	User updateUser(User userOld);
}
