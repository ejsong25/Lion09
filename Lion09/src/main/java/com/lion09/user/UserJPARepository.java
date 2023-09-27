package com.lion09.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJPARepository extends JpaRepository<User, String>{
	//내장 함수 : save, findAll
}
