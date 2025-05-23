package com.ssafy.home.user.service;

import com.ssafy.home.user.dto.User;

public interface UserService {
	
	int insert(User user);
	
	User login(String username, String password);

	void update(User user);

	void delete(int userId);

	void updateProfile(String username, byte[] profile);
	
	User select(String username);
    
}
