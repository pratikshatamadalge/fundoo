package com.bridgelabz.fundoo;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.Date;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.bridgelabz.fundoo.model.User;

import com.bridgelabz.fundoo.repository.IUserRepository;
import com.bridgelabz.fundoo.service.UserServiceImpl;
import com.bridgelabz.fundoo.utility.TokenUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

	private MockMvc mockmvc;

	@InjectMocks
	UserServiceImpl userServiceImpl;

	@Mock
	IUserRepository userRepository;

	@Mock
	TokenUtil tokenUtil;

	User user = new User("pratiksha123", "12345678", "pratikshatamadalge21@gmail.com", null, null, null, null, null,
			null);

	@Before
	public void Setup() throws Exception {
		mockmvc = MockMvcBuilders.standaloneSetup(userServiceImpl).build();
	}

	@Test
	public void registerTest() throws Exception {
		user.setEmailId("pratikshatamadalge21@gmail.com");
		user.setPassword("12345678");
		user.setMobileNo(null);
		user.setUserName("pratiksha123");
		user.setUpdatedDate(new Date());
		user.setUpdatedDate(new Date());

		Optional<User> already = Optional.of(user);
		
		when(userRepository.findByEmailId(user.getEmailId()));
		
		
	}
}
