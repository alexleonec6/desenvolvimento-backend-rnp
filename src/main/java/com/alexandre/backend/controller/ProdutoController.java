package com.alexandre.backend.controller;

import com.alexandre.backend.dto.ProdutoDTO;
import com.alexandre.backend.model.StatusProduto;
import com.alexandre.backend.service.ProdutoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Modulo 2 do curso:
// @RestController avisa ao Spring que essa classe responde requisicoes HTTP
// e converte os objetos Java automaticamente para JSON na resposta.
@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    private final ProdutoService service;

    public ProdutoController(ProdutoService service) {
        this.service = service;
    }

    // 1. GET /api/produtos -> Lista todos os produtos
    @GetMapping
    public ResponseEntity<List<ProdutoDTO>> listarTodos(@RequestParam(required = false) StatusProduto status) {
        if (status != null) {
            return ResponseEntity.ok(service.listarPorStatus(status));
        }
        return ResponseEntity.ok(service.listarTodos());
    }

    // 2. GET /api/produtos/{id} -> Busca um produto pelo ID na URL
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    // 3. POST /api/produtos -> Cria um novo produto (HTTP 201 Created)
    // @RequestBody le o JSON que o cliente enviou no corpo da requisicao
    @PostMapping
    public ResponseEntity<ProdutoDTO> criar(@RequestBody ProdutoDTO dto) {
        ProdutoDTO criado = service.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    // 4. PUT /api/produtos/{id} -> Atualiza os dados de um produto existente
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoDTO> atualizar(@PathVariable Long id, @RequestBody ProdutoDTO dto) {
        ProdutoDTO atualizado = service.atualizar(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    // 5. DELETE /api/produtos/{id} -> Exclui um produto (HTTP 204 No Content)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
