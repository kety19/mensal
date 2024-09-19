package com.atletica.mensal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.atletica.mensal.Controller.AtleticaController;
import com.atletica.mensal.Entities.AtleticaEntity;
import com.atletica.mensal.Service.AtleticaService;

@SpringBootTest
public class AtleticaControllerTest {

	@Mock
	private AtleticaService atleticaService;

	@InjectMocks
	private AtleticaController atleticaController;

	private MockMvc mockMvc;

	public AtleticaControllerTest() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(atleticaController).build();
	}

	@Test
	void testListarTodasAtleticas() throws Exception {
		AtleticaEntity atletica = new AtleticaEntity();
		atletica.setNome("Atlética XYZ");

		when(atleticaService.listarTodasAtleticas()).thenReturn(List.of(atletica));

		mockMvc.perform(get("/atleticas")).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].nome").value("Atlética XYZ"));
	}

	@Test
	void testCriarAtletica() throws Exception {
		AtleticaEntity atletica = new AtleticaEntity();
		atletica.setNome("Atlética ABC");

		when(atleticaService.salvarAtletica(any(AtleticaEntity.class))).thenReturn(atletica);

		mockMvc.perform(post("/atleticas/criar").contentType(MediaType.APPLICATION_JSON)
				.content("{\"nome\":\"Atlética ABC\", \"universidade\":\"Universidade XYZ\"}"))
				.andExpect(status().isOk()).andExpect(jsonPath("$.nome").value("Atlética ABC"));
	}

	@Test
	void testDeletarAtletica() throws Exception {
		AtleticaEntity atletica = new AtleticaEntity();
		atletica.setId(1L);

		doNothing().when(atleticaService).deletarAtletica(1L);

		mockMvc.perform(delete("/atleticas/deletar/1")).andExpect(status().isOk());

		verify(atleticaService, times(1)).deletarAtletica(1L);
	}
}
