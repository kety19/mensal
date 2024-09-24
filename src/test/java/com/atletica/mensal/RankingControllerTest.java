package com.atletica.mensal;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.atletica.mensal.Controller.RankingController;
import com.atletica.mensal.Repository.RankingRepository;

public class RankingControllerTest {


	@Autowired
	RankingController rankingController;
	
	@MockBean
	RankingRepository rankingRepository;
	
	@BeforeEach
	void setup() {
		
		Mockito.when(
				rankingRepository.save(null)
				
				).thenReturn(null);
		
	}
	
	@Test
	@DisplayName("funcao q testa atualizar a pontuacao")
	void pontMethodTest() {
		
		ResponseEntity<Integer> dado = rankingController.atualizarPontuacao(1,2);
		assertEquals(HttpStatus.OK, dado.getStatusCode());
		assertEquals(25, dado.getBody());;
		
	}
}
