package com.codingwithsanket.apis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;

import com.codingwithsanket.blog.repositories.UserRepo;

@SpringBootTest
public class DemoApplicationTests {

	
	@Autowired
	private UserRepo userRepo;

	@Test
	public void repoTest() {
		String className = this.userRepo.getClass().getName();
		String PackageName = this.userRepo.getClass().getPackageName();
		System.out.println(className);
		System.out.println(PackageName);
	}

}
