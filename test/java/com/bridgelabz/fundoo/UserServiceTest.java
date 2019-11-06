package com.bridgelabz.fundoo;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.bridgelabz.fundoo.dto.RegisterDTO;
import com.bridgelabz.fundoo.dto.UserDTO;
import com.bridgelabz.fundoo.exception.RegistrationException;
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

	@Mock
	RegisterDTO regDTO;

	@Mock
	JavaMailSender javaMailSender;

	@Mock
	ModelMapper modelmapper;

	@Mock
	PasswordEncoder passwordEncoder;

	@Mock
	UserDTO userDTO;

	User user = new User("pratiksha123", "12345678", "pratikshatamadalge21@gmail.com", null, null, null, null, null,
			null);

	@Before
	public void Setup() throws Exception {
		mockmvc = MockMvcBuilders.standaloneSetup(userServiceImpl).build();
	}

	/**
	 * to test register api
	 * 
	 * @throws Exception
	 */
	@Test
	public void registerTest() throws Exception {
		regDTO.setEmailId("pratikshatamadalge21@gmail.com");
		regDTO.setPassword("12345678");
		regDTO.setMobileNo(7875680359L);
		regDTO.setUserName("pratiksha123");
		Optional<User> already = Optional.of(user);
		when(userRepository.findByEmailId(user.getEmailId()) != null)
				.thenThrow(new RegistrationException("email is already registered"));
		when(modelmapper.map(regDTO, User.class)).thenReturn(user);
		when(userRepository.save(user)).thenReturn(user);
		assertEquals(regDTO.getEmailId(), already.get().getEmailId());
	}

	/**
	 * to test the login api
	 */
	@Test
	public void loginTest() {

		userDTO.setEmailId("pratikshatamadalge");
		userDTO.setPassword("12345678");
		Optional<User> already = Optional.of(user);
		when(userRepository.findByEmailId(user.getEmailId())).thenReturn(user);
		when(passwordEncoder.matches(userDTO.getPassword(), user.getPassword())).thenReturn(true);
		assertEquals(userDTO.getEmailId(), already.get().getEmailId());
	}

	/**
	 * to test forgot password api
	 */
	@Test
	public void forgotPassword() {
		when(userRepository.findByEmailId(user.getEmailId())).thenReturn(user);
		when(userRepository.save(user)).thenReturn(user);
		assertEquals(userDTO.getEmailId(), user.getEmailId());
	}

	/**
	 * to test reset password
	 */
	@Test
	public void resetPassword() {
		user.setPassword("87654321");
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		when(userRepository.save(user)).thenReturn(user);
		assertEquals(user.getEmailId(), user.getPassword());
	}
}
