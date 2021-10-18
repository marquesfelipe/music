package com.ftech.music.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class MusicControllerTest {
	
	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;


	@BeforeEach
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
	}

	@WithMockUser(value = "spring")
	@Test
	public void mustSearchMusics() throws Exception {
		mockMvc.perform(get("/musics")).andExpect(status().isOk());
	}

	@WithMockUser(value = "spring")
	@Test
	public void mustCreateMusic() throws Exception {

		JSONObject form = new JSONObject();
		form.put("name", "breaking the law");
		form.put("band", "Judas Priest");
		form.put("released", "1981");
		form.put("lyric", "breaking ....");
		form.put("genre", "heavy metal");

		mockMvc.perform(MockMvcRequestBuilders.post("/musics").contentType(MediaType.APPLICATION_JSON)
				.content(form.toString()).header("Content-Type", "application/json"))
				.andExpect(MockMvcResultMatchers.status().is(HttpStatus.CREATED.value()));
	}

	@WithMockUser(value = "spring")
	@Test
	public void shouldSearchMusic() throws Exception {
		mockMvc.perform(get("/musics/1")).andExpect(status().isOk());
	}
	
	@WithMockUser(value = "spring")
	@Test
	public void shouldUpdateMusic() throws Exception {

		JSONObject form = new JSONObject();
		form.put("name", "breaking the law");
		form.put("band", "Judas Priest");
		form.put("released", "1981");
		form.put("lyric", "breaking the what ....");
		form.put("genre", "heavy metal");
		

		mockMvc.perform(MockMvcRequestBuilders.put("/musics/1").contentType(MediaType.APPLICATION_JSON)
				.content(form.toString()).header("Content-Type", "application/json"))
				.andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()));
	}
	

	
		
	@WithMockUser(value = "spring")
	@Test
	public void shouldDeleteMusic() throws Exception {

		JSONObject form = new JSONObject();
		form.put("name", "Painkiller");
		form.put("band", "Judas Priest");
		form.put("released", "1991");
		form.put("lyric", "Painkiller ....");
		form.put("genre", "heavy metal");

		mockMvc.perform(MockMvcRequestBuilders.post("/musics").contentType(MediaType.APPLICATION_JSON)
				.content(form.toString()).header("Content-Type", "application/json"))
				.andExpect(MockMvcResultMatchers.status().is(HttpStatus.CREATED.value()));
		
		mockMvc.perform(MockMvcRequestBuilders.delete("/musics/2"))
				.andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()));
	}


}
