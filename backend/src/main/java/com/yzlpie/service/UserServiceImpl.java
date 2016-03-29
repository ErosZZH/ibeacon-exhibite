package com.yzlpie.service;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yzlpie.common.EncryptUtil;
import com.yzlpie.jpa.domain.User;
import com.yzlpie.jpa.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepo;

	@Override
	public String getTokenFromDeviceToken(String deviceToken) {

		String userId = userRepo.getTokenFromDeviceToken(deviceToken);
		return EncryptUtil.encryptUserId(userId);
	}

	@Override
	public String registerUser(String deviceToken) {
		int count = userRepo.deviceTokenCount(deviceToken);
		if(count == 0) {
			User user = new User();
			user.setUserId(UUID.randomUUID().toString());
			user.setStatus(0);
			user.setCreateTime(new Date());
			user.setDeviceToken(deviceToken);
			userRepo.saveAndFlush(user);
			return EncryptUtil.encryptUserId(user.getUserId());
		} else {
			return getTokenFromDeviceToken(deviceToken);
		}
	}

	@Override
	public void updateUser(String oldToken, String newToken) {
		userRepo.updateUser(oldToken, newToken);
	}

}
