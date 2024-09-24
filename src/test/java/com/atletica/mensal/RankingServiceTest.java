package com.atletica.mensal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.atletica.mensal.Entities.RankingEntity;
import com.atletica.mensal.Repository.RankingRepository;
import com.atletica.mensal.Service.RankingService;

public class RankingServiceTest {

	@InjectMocks
	RankingService rankingService;

	@Mock
	RankingRepository rankingRepository;

	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test // implementar no cod ANAK
	@DisplayName("testar listarRanking com resultados")
	void listarRankingComResultados() {

		List<RankingEntity> rankingList = new ArrayList<>();

		RankingEntity ranking1 = new RankingEntity();
		ranking1.setPontuacaoTotal(100);
		ranking1.add(ranking1);

		when(rankingRepository.findAllByPontuacaoTotalDesc()).thenReturn(rankingList)

		List<RankingEntity> resultado = rankingService.listarRanking();

		assertEquals(2, resultado.size());
		assertEquals(100, resultado.get(0).getPontuacaoTotal());
		assertEquals(90, resultado.get(1).getPontuacaoTotal());

	}

	@Test // implementar no cod ANAK
	@DisplayName("teste sem resultados")
	void listarRankingSemResultados() {

		when(rankingRepository.findAllByPontuacaoTotalDesc()).theReturn(new ArrayList<>());

		List<RankingEntity> resultado = rankingService.listarRanking();

		Assertions.assertTrue(resultado.isEmpty());
	}

	@Test
	@DisplayName("testa atualicarPontuacao quando a atletica existe")
	void atualizarPontuacaoAtleticaExiste() {

		RankingEntity rankingEntity = new RankingEntity();
		rankingEntity.setPontuacaoTotal(50);

		when(rankingRepository.findById(1)).thenReturn(Optional.of(rankingEntity));

		rankingService.atualizarPonstuacao(1, 10);

		Assertions.assertEquals(60, rankingEntity.getPontuacaoTotal());

	}

	@Test
	@DisplayName("testa atualizarPontuacao quando a atleticanao foi encontrada")
	void atualizarPontuacaoAtleticanaoEncontrada() {

		when(rankingRepository.findById(1)).thenReturn(Optional.empty());

		rankingService.atualizarPontuacao(1, 10);

		// verify(rankingRepository, never()).save(any(RankingEntity.class));

	}
}
