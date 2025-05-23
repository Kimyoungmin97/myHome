package com.ssafy.home.user.dao;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.home.user.dto.User;

@Mapper
public interface UserDao {
	
	int insert(User user);
	
	User select(String username);

    public int update(User user);

    public int delete(int userId);

    public int updateProfile(String username, byte[] profileImage);
    
}
