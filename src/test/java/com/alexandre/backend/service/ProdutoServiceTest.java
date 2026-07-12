package com.alexandre.backend.service;

import com.alexandre.backend.dto.ProdutoDTO;
import com.alexandre.backend.model.Produto;
import com.alexandre.backend.model.StatusProduto;
import com.alexandre.backend.repository.ProdutoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

// Modulo 4 do curso:
// Testes unitarios com JUnit 5 e Mockito.
// Aqui a gente "moka" (simula) o ProdutoRepository com @Mock, assim o teste
// roda super rapido sem precisar se conectar a nenhum banco de dados de verdade.
@ExtendWith(MockitoExtension.class)
public class ProdutoServiceTest {

    @Mock
    private ProdutoRepository repository;

    @InjectMocks
    private ProdutoService service;

    @Test
    @DisplayName("Deve salvar um produto com sucesso quando os dados estiverem corretos")
    void deveSalvarProdutoComSucesso() {
        ProdutoDTO novoDto = new ProdutoDTO();
        novoDto.setNome("Notebook Dell");
        novoDto.setDescricao("Notebook para desenvolvimento");
        novoDto.setPreco(new BigDecimal("4500.00"));
        novoDto.setQuantidadeEstoque(10);

        Produto produtoMockSalvo = new Produto("Notebook Dell", "Notebook para desenvolvimento", new BigDecimal("4500.00"), 10);
        produtoMockSalvo.setId(1L);

        // Ensinando o Mockito como responder
        Mockito.when(repository.save(any(Produto.class))).thenReturn(produtoMockSalvo);

        // Executando a funcao
        ProdutoDTO resultado = service.criar(novoDto);

        // Verificacoes (Assertions)
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Notebook Dell", resultado.getNome());
        assertEquals(StatusProduto.ATIVO, resultado.getStatus());
        Mockito.verify(repository, Mockito.times(1)).save(any(Produto.class));
    }

    @Test
    @DisplayName("Deve lancar excecao quando o preco do produto for menor ou igual a zero")
    void deveLancarErroQuandoPrecoForInvalido() {
        ProdutoDTO dtoInvalido = new ProdutoDTO();
        dtoInvalido.setNome("Cabo USB");
        dtoInvalido.setPreco(new BigDecimal("0.00")); // Preco invalido
        dtoInvalido.setQuantidadeEstoque(5);

        // Verifica se o Service barrou a criacao e jogou a excecao correta
        IllegalArgumentException erro = assertThrows(IllegalArgumentException.class, () -> {
            service.criar(dtoInvalido);
        });

        assertEquals("O preco do produto deve ser maior que zero!", erro.getMessage());
        Mockito.verify(repository, Mockito.never()).save(any(Produto.class));
    }

    @Test
    @DisplayName("Deve buscar um produto por ID existente")
    void deveBuscarProdutoPorIdExistente() {
        Produto produtoExistente = new Produto("Teclado Mecanico", "Switch Blue", new BigDecimal("350.00"), 8);
        produtoExistente.setId(10L);

        Mockito.when(repository.findById(10L)).thenReturn(Optional.of(produtoExistente));

        ProdutoDTO dto = service.buscarPorId(10L);

        assertNotNull(dto);
        assertEquals(10L, dto.getId());
        assertEquals("Teclado Mecanico", dto.getNome());
    }
}
