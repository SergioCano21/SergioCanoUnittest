package com.mayab.quality.unittest.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mayab.quality.unittest.dao.IDAOUser;
import com.mayab.quality.unittest.model.User;
import com.mayab.quality.unittest.service.LoginService;

class LoginServiceTest {

	private IDAOUser dao;
	private User user;
	private LoginService service;
	private static String NAME = "Sergio";
	private static String PASS = "Sergio123";
	@BeforeEach
	void setUp(){
		dao = mock(IDAOUser.class);
		user = mock(User.class);
		
		service = new LoginService(dao);
	}
	@Test
	void whenPasswordCorrectLoginPass(){
		when(user.getPassword()).thenReturn(PASS);
		when(dao.findbyUserName(NAME)).thenReturn(user);
		boolean isLogged = service.login(NAME, PASS);
		assertThat(isLogged, is(true));
	}
	@Test
	void whenPasswordWrongLoginFail(){
		when(user.getPassword()).thenReturn("OtraContraseña123");
		when(dao.findbyUserName(NAME)).thenReturn(user);
		boolean isLogged = service.login(NAME, PASS);
		assertThat(isLogged, is(false));
	}
}
