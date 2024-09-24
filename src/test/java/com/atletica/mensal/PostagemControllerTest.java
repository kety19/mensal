package com.atletica.mensal;

import static org.junit.jupiter.api.Assertions.assertEquals;

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


    public PostagemControllerTest() {
        MockitoAnnotations.openMocks(this); // Inicializa mocks
    }


    @Test
    void testCriarPostagem() throws Exception {
   
        PostagemEntity post = new PostagemEntity();
        post.setImagem("Nova Festa");

        // criar uma postagem
        when(postagemService.criarPostagem(anyLong(), any(PostagemEntity.class), anyLong())).thenReturn(post);

        // uma nova postagem
        PostagemEntity novaPostagem = new PostagemEntity();
        novaPostagem.setImagem("Nova Festa");


        PostagemEntity resultado = postagemController.criarPostagem( novaPostagem, "Atletica Ursao");

        // retorna
        assertEquals("Nova Festa", resultado.getImagem());
    }
    @Test
    public void testListarPostagensPorAtletica() throws Exception {
        // Criação das entidades de postagens com imagem
        PostagemEntity postagem1 = new PostagemEntity();
        postagem1.setImagem("Festa 1");

        PostagemEntity postagem2 = new PostagemEntity();
        postagem2.setImagem("Festa 2");

        when(postagemService.listarPostagensPorAtletica(1L)).thenReturn(List.of(postagem1, postagem2));

        List<PostagemEntity> resultado = postagemController.listarPostagensPorAtletica(1L);

        // Verifica o conteúdo da imagem nas postagens retornadas
        assertEquals(2, resultado.size());
        assertEquals("Festa 1", resultado.get(0).getImagem());
        assertEquals("Festa 2", resultado.get(1).getImagem());
    }
}
