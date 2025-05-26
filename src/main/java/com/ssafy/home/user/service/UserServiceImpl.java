package com.ssafy.home.user.service;
import org.springframework.stereotype.Service;

import com.ssafy.home.common.exception.CustomException;
import com.ssafy.home.common.exception.ErrorCode;
import com.ssafy.home.user.dao.HouseDao;
import com.ssafy.home.user.dao.UserDao;
import com.ssafy.home.user.dto.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

	private final UserDao userDao;
	private final HouseDao houseDao;
	
	@Override
	public int insert(User user) {
		String[] residences = user.getResidence().split(" ");
		String aptSeq = houseDao.selectAptSeq(residences);
		user.setAptSeq(aptSeq);
		return userDao.insert(user);
	}

	@Override
	public User login(String username, String password) {
		User user = userDao.select(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        } else {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }
	}

	@Override
	public void update(User user) {
		userDao.update(user);
	}

	@Override
	public void delete(int userId) {
		userDao.delete(userId);
	}

	@Override
	public void updateProfile(String username, byte[] profile) {
		userDao.updateProfile(username, profile);
	}

	@Override
	public User select(String username) {
		return userDao.select(username);
	}

}
