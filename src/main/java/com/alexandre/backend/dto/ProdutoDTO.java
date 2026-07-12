package com.alexandre.backend.dto;

import com.alexandre.backend.model.Produto;
import com.alexandre.backend.model.StatusProduto;
import java.math.BigDecimal;

// DTO (Data Transfer Object):
// Aprendi no curso que nao e uma boa pratica expor a Entidade do banco direto na API.
// O DTO serve para receber apenas os dados que o usuario precisa enviar e responder
// apenas o que a interface precisa ver.
public class ProdutoDTO {

    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private Integer quantidadeEstoque;
    private StatusProduto status;

    public ProdutoDTO() {}

    public ProdutoDTO(Produto p) {
        this.id = p.getId();
        this.nome = p.getNome();
        this.descricao = p.getDescricao();
        this.preco = p.getPreco();
        this.quantidadeEstoque = p.getQuantidadeEstoque();
        this.status = p.getStatus();
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public BigDecimal getPreco() { return preco; }
    public void setPreco(BigDecimal preco) { this.preco = preco; }

    public Integer getQuantidadeEstoque() { return quantidadeEstoque; }
    public void setQuantidadeEstoque(Integer quantidadeEstoque) { this.quantidadeEstoque = quantidadeEstoque; }

    public StatusProduto getStatus() { return status; }
    public void setStatus(StatusProduto status) { this.status = status; }
}
