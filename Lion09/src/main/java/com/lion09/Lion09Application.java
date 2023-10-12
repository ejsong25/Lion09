package com.lion09;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import com.lion09.Lion09Application;

@SpringBootApplication
@MapperScan("com.lion09.mypage")
@MapperScan("com.lion09.pay")
@MapperScan("com.lion09.board")
@MapperScan("com.lion09.qaboard")
public class Lion09Application {

	@Autowired
	ApplicationContext applicationContext; // context 앞뒤에 뭐가 붙던 프로젝트 전체에 해당

	public static void main(String[] args) {
		SpringApplication.run(Lion09Application.class, args);
	}
	
	@Bean
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception{
		
		SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(dataSource);
		
		/*
		Resource[] res = new PathMatchingResourcePatternResolver()
				.getResources("classpath:mybatis/mapper/*.xml");
		sessionFactory.setMapperLocations(res);
		*/
		
		sessionFactory.setMapperLocations(applicationContext
				.getResources("classpath:mybatis/mapper/*.xml")); // 이게 좀 더 고급
		
		return sessionFactory.getObject();
	}


}

