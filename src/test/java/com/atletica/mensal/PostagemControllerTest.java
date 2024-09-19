package com.atletica.mensal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
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

import com.atletica.mensal.Controller.PostagemController;
import com.atletica.mensal.Entities.PostagemEntity;
import com.atletica.mensal.Service.PostagemService;

@SpringBootTest
public class PostagemControllerTest {

	@Mock
    private PostagemService postagemService;

    @InjectMocks
    private PostagemController postagemController;

    private MockMvc mockMvc;

    public PostagemControllerTest() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(postagemController).build();
    }

    @Test
    void testCriarPostagem() throws Exception {
        PostagemEntity post = new PostagemEntity();
        post.setConteudo("Nova Festa");

      
		when(postagemService.criarPostagem(anyLong(), any(PostagemEntity.class), anyLong())).thenReturn(post);

        mockMvc.perform(post("/posts/criar/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"conteudo\":\"Nova Festa\"}")
                .param("userId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.conteudo").value("Nova Festa"));
    }

    @Test
    public void testListarPostagensPorAtletica() throws Exception {
        PostagemEntity post1 = new PostagemEntity();
        post1.setConteudo("Festa 1");

        PostagemEntity post2 = new PostagemEntity();
        post2.setConteudo("Festa 2");

        when(postagemService.listarPostagensPorAtletica(1L)).thenReturn(List.of(post1, post2));

        mockMvc.perform(get("/postagens/atletica/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].conteudo").value("Festa 1"))
                .andExpect(jsonPath("$[1].conteudo").value("Festa 2"));
    }
}
