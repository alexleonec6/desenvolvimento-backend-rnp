# Estudos - Curso de Desenvolvimento Backend (RNP / ESR)

Anotacoes, comprovante de conclusao e um mini-projeto em Java com Spring Boot que fiz para praticar o que aprendi no curso de Desenvolvimento Backend da RNP (Escola Superior de Redes), com o professor Kleber de Aguiar.

---

## Comprovante de Conclusao e Certificado

Terminei todas as etapas do curso no Moodle da RNP, com 100% de progresso, nota maxima na atividade SCORM e a pesquisa de satisfacao respondida.

![Comprovante de Conclusao](comprovante-conclusao-rnp.png)

- Status no Moodle - 100% concluido
- Link para pegar o certificado - https://esr.rnp.br/minha-conta/meus-certificados/
(O sistema da RNP avisa que o certificado oficial fica disponivel no portal da ESR em ate 7 dias uteis apos a aprovacao).

---

## O que aprendi no curso

### Modulo 1 - Como funciona a Web e Java
- Protocolo HTTP - como o navegador conversa com o servidor mandando pedidos (requests) e recebendo respostas (responses)
- Verbos HTTP:
  - GET - pega dados sem mudar nada no banco
  - POST - salva um registro novo
  - PUT - altera um registro que ja existe
  - DELETE - apaga um registro
- Codigos de resposta (Status Codes) - 200 quando deu certo, 201 quando criou algo novo, 404 quando nao achou, e 400 quando o dado enviado esta errado
- MVC - separar o codigo em partes (dados, regras e rotas) para manter a organizacao

### Modulo 2 - Criando a API com Spring Boot
- Spring Boot - facilita criar aplicacoes Java sem precisar configurar tudo do zero
- Anotacoes que usei:
  - @SpringBootApplication - inicia o projeto
  - @RestController - avisa que a classe responde chamadas web e devolve JSON
  - @RequestMapping - define o prefixo da rota (ex: /api/produtos)
  - @GetMapping, @PostMapping, @PutMapping, @DeleteMapping - liga cada metodo ao seu verbo HTTP correspondente
  - @RequestBody - le os dados enviados no corpo da requisicao
  - @PathVariable - pega o ID que vem direto na URL (ex: /api/produtos/2)
- DTO (Data Transfer Object) - classe simples usada para trafegar dados com seguranca sem expor a tabela do banco

### Modulo 3 - Salvando dados no banco com Spring Data JPA
- JPA e Hibernate - faz a ligacao entre as classes do Java e as tabelas do banco, sem precisar escrever SQL manual
- Anotacoes de banco:
  - @Entity - avisa que a classe vira uma tabela
  - @Id e @GeneratedValue - cria o ID automatico que aumenta sozinho (1, 2, 3...)
- JpaRepository - interface que entrega pronto os metodos para salvar, buscar por ID, listar todos e deletar
- H2 Database - banco em memoria rapido para testar sem precisar instalar MySQL ou PostgreSQL

### Modulo 4 - Testes automatizados
- Testes unitarios - testam partes isoladas do codigo com velocidade
- JUnit 5 e Mockito - ferramentas usadas para criar e rodar os testes
- Mocks (@Mock e @InjectMocks) - simulam o banco para testar as regras de validacao sem precisar de conexao real

---

## Estrutura do projeto

```
src/
├── main/
│   ├── java/com/alexandre/backend/
│   │   ├── BackendApplication.java       # Arquivo principal do Spring Boot
│   │   ├── controller/
│   │   │   └── ProdutoController.java    # Rotas da API (GET, POST, PUT, DELETE)
│   │   ├── dto/
│   │   │   └── ProdutoDTO.java           # Dados que entram e saem da API
│   │   ├── model/
│   │   │   ├── Produto.java              # Tabela de produtos mapeada pelo JPA
│   │   │   └── StatusProduto.java        # Status do produto (ATIVO, EM_FALTA, etc)
│   │   ├── repository/
│   │   │   └── ProdutoRepository.java    # Interface de comunicacao com o banco
│   │   └── service/
│   │       └── ProdutoService.java       # Validacoes e regras de negocio
│   └── resources/
│       └── application.properties        # Configuracao da porta e banco H2
└── test/
    └── java/com/alexandre/backend/service/
        └── ProdutoServiceTest.java       # Testes com JUnit 5 e Mockito
```

---

## Como rodar

### Opcao 1 - Usando o Maven (Spring Boot)
Se voce usa IntelliJ, VS Code ou tem o Maven no terminal:
```bash
# Rodar os testes
mvn test

# Subir a API
mvn spring-boot:run
```
A API fica disponivel em: `http://localhost:8080/api/produtos`  
O painel do banco de dados H2 fica em: `http://localhost:8080/h2-console`

### Opcao 2 - Teste rapido em Java puro (sem precisar de Maven)
Dentro da pasta `demonstracao-rapida/` tem um arquivo simples que compila e roda direto com o Java instalado na sua maquina:
```bash
cd demonstracao-rapida
javac ServidorApiSemDependencias.java
java demonstracao.ServidorApiSemDependencias
```
Depois e so abrir no navegador: `http://localhost:8080/api/produtos`
