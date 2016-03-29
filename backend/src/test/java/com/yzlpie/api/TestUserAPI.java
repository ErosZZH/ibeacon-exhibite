package com.yzlpie.api;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.yzlpie.jpa.domain.User;
import com.yzlpie.jpa.repository.UserRepository;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext-all.xml")
@WebAppConfiguration
public class TestUserAPI {
	
	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext wac;
	
	@Autowired
	private UserRepository userRepo;
	
	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}
	
	@Test
	public void testGetFootPrint() throws Exception {
		mockMvc.perform(get("/api/v1/user").accept(MediaType.APPLICATION_JSON)).
		andDo(print()).
		andExpect(status().isOk());	
	}
	
	@Test
	public void testAddUser() throws Exception {
		mockMvc.perform(post("/api/v1/user/add").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).
		andDo(print()).
		andExpect(status().isOk());	
	}
	
	@Test
	public void tesUpdateUser() throws Exception {
		String requestBody = "{\"oldDeviceToken\":\"aadevicetoken\"}";
		mockMvc.perform(put("/api/v1/user/update").contentType(MediaType.APPLICATION_JSON).content(requestBody).accept(MediaType.APPLICATION_JSON)).
		andDo(print()).
		andExpect(status().isOk());	
	}
}
