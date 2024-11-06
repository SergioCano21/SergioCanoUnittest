package com.mayab.quality.unittest.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.mayab.quality.unittest.dao.IDAOUser;
import com.mayab.quality.unittest.model.User;

class UserServiceTest {
	
	private IDAOUser dao;
	private UserService service;
	private HashMap<Integer, User> db;

	@BeforeEach
	public void setUp() {
		dao = mock(IDAOUser.class);
		service = new UserService(dao);
		db = new HashMap<Integer, User>();
	}
	
	@Test
	public void create_User_Incorrect_when_Duplicated_Email() {
		User user = new User("nombre", "email@email.com", "123456789");
		
		when(dao.findUserByEmail(anyString())).thenReturn(user);
		when(dao.save(any(User.class))).thenReturn(0);
		
		User prueba = service.createUser(user.getName(), user.getEmail(), user.getPassword());
		
		assertThat(prueba, is(nullValue()));
	}
	@Test
	void create_User_Correct_when_All_Data_Valid() {
		int sizeBefore = db.size();
		
		User user = new User("nombre", "email@email.com", "123456789");
		
		when(dao.findUserByEmail(anyString())).thenReturn(null);
		when(dao.save(any(User.class))).thenAnswer(new Answer<Integer>() {
			public Integer answer(InvocationOnMock invocation) {
				User arg = (User) invocation.getArguments()[0];
				db.put(db.size()+1, arg);
				return db.size();
			}
		});
		
		User prueba = service.createUser(user.getName(), user.getEmail(), user.getPassword());
		
		assertThat(prueba, notNullValue());
		assertThat(db.size(), is(sizeBefore+1));
	}
	
	@Test
	void update_User_Correct() {
		User oldUser = new User("prueba", "prueba@email.com", "123456");
		db.put(1, oldUser);
		oldUser.setId(1);
		User newUser = new User("prueba", "prueba@email.com", "1234567");
		newUser.setId(1);
		
		when(dao.findById(anyInt())).thenReturn(oldUser);
		when(dao.updateUser(any(User.class))).thenAnswer(new Answer<User>() {
			public User answer(InvocationOnMock invocation) {
				User arg = (User) invocation.getArguments()[0];

				db.replace(arg.getId(), arg);

				return db.get(arg.getId());
			}
		});
		User result = service.updateUser(newUser);
		
		assertThat(result.getPassword(), is(newUser.getPassword()));
	}
	
	@Test
	void delete_User_Correct() {
		User user = new User("user", "email", "password");
		user.setId(1);
		
		when(dao.deleteById(anyInt())).thenReturn(true);
		boolean result = service.deleteUser(user.getId());
		
		assertThat(result, is(true));
	}
	
	@Test
	void find_User_By_Email_Correct() {
		User user = new User("user", "email", "password");
		user.setId(1);
		
		when(dao.findUserByEmail(anyString())).thenReturn(user);
		User result = service.findUserByEmail(user.getEmail());
		
		assertThat(result, is(user));
	}
	@Test
	void find_User_By_Email_Not_Found() {
		User user = new User("user", "email", "password");
		user.setId(1);
		
		when(dao.findUserByEmail(anyString())).thenReturn(null);
		User result = service.findUserByEmail(user.getEmail());

		assertNull(result);
	}
	
	@Test
	void find_All_Correct() {
		User user1 = new User("user", "email", "password");
		user1.setId(1);
		User user2 = new User("user", "email", "password");
		user2.setId(2);
		List<User> list = new ArrayList<User>();
		
		list.add(user1);
		list.add(user2);
		
		when(dao.findAll()).thenReturn(list);
		
		List<User> result = service.findAllUsers();
		
		assertThat(result, is(list));
	}
	
	
	
	
	
	/*
	@Test
	public void create_User_Incorrect_when_Password_Short() {
		User user = new User("nombre", "email@email.com", "123");
		
		when(dao.findUserByEmail(anyString())).thenReturn(user);
		when(dao.save(any(User.class))).thenReturn(0);
		
		User prueba = service.createUser(user.getName(), user.getEmail(), user.getPassword());
		
		assertThat(prueba, is(nullValue()));
	}
	@Test
	public void create_User_Incorrect_when_Password_Long() {
		User user = new User("nombre", "email@email.com", "12345678910111213141516");
		
		when(dao.findUserByEmail(anyString())).thenReturn(user);
		when(dao.save(any(User.class))).thenReturn(0);
		
		User prueba = service.createUser(user.getName(), user.getEmail(), user.getPassword());
		
		assertThat(prueba, is(nullValue()));
	}

	@Test
	void find_User_By_Id_Correct() {
		User user = new User("user", "email", "password");
		user.setId(1);
		
		when(dao.findById(anyInt())).thenReturn(user);
		User result = service.findUserById(user.getId());
		
		assertThat(result, is(user));
	}
	@Test
	void find_User_By_Id_Not_Found() {
		User user = new User("user", "email", "password");
		user.setId(1);
		
		when(dao.findById(anyInt())).thenReturn(null);
		User result = service.findUserById(user.getId());

		assertNull(result);
	}
	
	@Test
	void delete_User_Not_Found() {
		User user = new User("user", "email", "password");
		user.setId(1);
		
		when(dao.deleteById(anyInt())).thenReturn(false);
		boolean result = service.deleteUser(user.getId());

		assertThat(result, is(false));
	}
	*/
}
