package com.lion09.user;

import javax.transaction.Transactional;

@Transactional
public class UserService {
	private final UserJPARepository userJPARepository;
	
	public UserService(UserJPARepository userJPARepository) {
		this.userJPARepository = userJPARepository;
	}
	
	
	
}
