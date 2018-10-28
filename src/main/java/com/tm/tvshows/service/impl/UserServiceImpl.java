package com.tm.tvshows.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tm.tvshows.entity.User;
import com.tm.tvshows.repository.UserRepository;
import com.tm.tvshows.service.api.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	@Override
	public User getUserById(Integer id) {
		Optional<User> optionaluser = userRepository.findById(id);
		if (optionaluser.isPresent()) {
			return optionaluser.get();
		}
		return null;
	}

}
