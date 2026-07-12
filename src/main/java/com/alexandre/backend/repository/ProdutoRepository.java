package com.alexandre.backend.repository;

import com.alexandre.backend.model.Produto;
import com.alexandre.backend.model.StatusProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// Modulo 3 do curso:
// Quando nossa interface estende JpaRepository<Produto, Long>, o Spring Data
// cria sozinho todos os metodos basicos de banco (salvar, listar, buscar por ID, deletar).
// A gente nao precisa escrever nenhum SQL na mao!
@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    // Consulta derivada (Derived Query): o Spring le o nome do metodo e monta o SELECT sozinho:
    // SELECT * FROM produtos WHERE status = ?
    List<Produto> findByStatus(StatusProduto status);

    // SELECT * FROM produtos WHERE LOWER(nome) LIKE LOWER(CONCAT('%', ?, '%'))
    List<Produto> findByNomeContainingIgnoreCase(String termo);
}
