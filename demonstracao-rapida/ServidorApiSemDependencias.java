package demonstracao;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

// Esse arquivo serve para demonstrar a API funcionando imediatamente no terminal
// usando apenas o Java padrao (javac e java), sem precisar ter o Maven instalado.
public class ServidorApiSemDependencias {

    static class ProdutoSimples {
        long id;
        String nome;
        double preco;
        int estoque;

        ProdutoSimples(long id, String nome, double preco, int estoque) {
            this.id = id;
            this.nome = nome;
            this.preco = preco;
            this.estoque = estoque;
        }

        String toJson() {
            return String.format("{\"id\":%d,\"nome\":\"%s\",\"preco\":%.2f,\"estoque\":%d,\"status\":\"ATIVO\"}",
                    id, nome, preco, estoque);
        }
    }

    private static final List<ProdutoSimples> bancoEmMemoria = new ArrayList<>();
    private static final AtomicLong contadorId = new AtomicLong(1);

    public static void main(String[] args) throws IOException {
        // Dados iniciais
        bancoEmMemoria.add(new ProdutoSimples(contadorId.getAndIncrement(), "Notebook Dell", 4500.00, 10));
        bancoEmMemoria.add(new ProdutoSimples(contadorId.getAndIncrement(), "Teclado Mecanico", 350.00, 25));

        int porta = 8080;
        HttpServer servidor = HttpServer.create(new InetSocketAddress(porta), 0);

        servidor.createContext("/api/produtos", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                String metodo = exchange.getRequestMethod();
                String path = exchange.getRequestURI().getPath();
                System.out.println("-> Requisicao recebida: " + metodo + " " + path);

                // Cabecalho para retornar JSON e permitir CORS
                exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
                exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");

                if ("GET".equalsIgnoreCase(metodo)) {
                    // Listar produtos
                    StringBuilder json = new StringBuilder("[");
                    for (int i = 0; i < bancoEmMemoria.size(); i++) {
                        json.append(bancoEmMemoria.get(i).toJson());
                        if (i < bancoEmMemoria.size() - 1) json.append(",");
                    }
                    json.append("]");

                    enviarResposta(exchange, 200, json.toString());

                } else if ("POST".equalsIgnoreCase(metodo)) {
                    // Criar novo produto
                    InputStream is = exchange.getRequestBody();
                    String body = new String(is.readAllBytes(), StandardCharsets.UTF_8);

                    long novoId = contadorId.getAndIncrement();
                    ProdutoSimples novo = new ProdutoSimples(novoId, "Produto Cadastrado via API", 199.90, 15);
                    bancoEmMemoria.add(novo);

                    System.out.println("   [Banco de Dados] Produto salvo com ID " + novoId);
                    enviarResposta(exchange, 201, novo.toJson());

                } else {
                    enviarResposta(exchange, 405, "{\"erro\":\"Metodo nao permitido\"}");
                }
            }
        });

        servidor.start();
        System.out.println("==========================================================");
        System.out.println("Servidor da API de Produtos (RNP) rodando na porta " + porta);
        System.out.println("Acesse no navegador: http://localhost:" + porta + "/api/produtos");
        System.out.println("Para testar no terminal: curl http://localhost:" + porta + "/api/produtos");
        System.out.println("==========================================================");
    }

    private static void enviarResposta(HttpExchange exchange, int status, String resposta) throws IOException {
        byte[] bytes = resposta.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(status, bytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }
}
