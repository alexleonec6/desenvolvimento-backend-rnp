package com.alexandre.backend.service;

import com.alexandre.backend.dto.ProdutoDTO;
import com.alexandre.backend.model.Produto;
import com.alexandre.backend.model.StatusProduto;
import com.alexandre.backend.repository.ProdutoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

// Modulo 2 e 3 do curso:
// A camada @Service e onde ficam as regras de negocio da aplicacao.
// O Controller so recebe a requisicao e chama o Service para processar.
@Service
public class ProdutoService {

    private final ProdutoRepository repository;

    // Injecao de dependencia via construtor (boa pratica recomendada no curso)
    public ProdutoService(ProdutoRepository repository) {
        this.repository = repository;
    }

    public List<ProdutoDTO> listarTodos() {
        return repository.findAll().stream()
                .map(ProdutoDTO::new)
                .collect(Collectors.toList());
    }

    public ProdutoDTO buscarPorId(Long id) {
        Produto produto = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto nao encontrado com o ID: " + id));
        return new ProdutoDTO(produto);
    }

    public List<ProdutoDTO> listarPorStatus(StatusProduto status) {
        return repository.findByStatus(status).stream()
                .map(ProdutoDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProdutoDTO criar(ProdutoDTO dto) {
        // Regra de negocio: validar preco maior que zero
        if (dto.getPreco() == null || dto.getPreco().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O preco do produto deve ser maior que zero!");
        }

        // Regra de negocio: validar quantidade no estoque
        if (dto.getQuantidadeEstoque() == null || dto.getQuantidadeEstoque() < 0) {
            throw new IllegalArgumentException("A quantidade em estoque nao pode ser negativa!");
        }

        Produto produto = new Produto(dto.getNome(), dto.getDescricao(), dto.getPreco(), dto.getQuantidadeEstoque());
        Produto salvo = repository.save(produto);
        return new ProdutoDTO(salvo);
    }

    @Transactional
    public ProdutoDTO atualizar(Long id, ProdutoDTO dto) {
        Produto existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto nao encontrado com o ID: " + id));

        existente.setNome(dto.getNome());
        existente.setDescricao(dto.getDescricao());
        existente.setPreco(dto.getPreco());
        existente.setQuantidadeEstoque(dto.getQuantidadeEstoque());

        Produto atualizado = repository.save(existente);
        return new ProdutoDTO(atualizado);
    }

    @Transactional
    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Produto nao encontrado para exclusao com o ID: " + id);
        }
        repository.deleteById(id);
    }
}
