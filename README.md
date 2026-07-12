# Desenvolvimento Backend (RNP / ESR) - Estudos e Mini-Projeto Prático

Repositório com as minhas anotações, comprovante de conclusão e o mini-projeto prático em **Java e Spring Boot** que fiz para aplicar tudo o que aprendi no curso de **Desenvolvimento Backend** da **Escola Superior de Redes (ESR / RNP)**, ministrado pelo Prof. Kleber de Aguiar.

---

## Comprovante de Conclusão do Curso

Concluí **100% das etapas do curso** no Moodle oficial da RNP, com nota máxima no pacote interativo SCORM e a pesquisa de satisfação enviada.

![Comprovante de Conclusao 100% no Moodle](comprovante-conclusao-rnp.png)

- **Status no Moodle:** 100% Completo (Aprovado)
- **Portal de Certificados da ESR:** [https://esr.rnp.br/minha-conta/meus-certificados/](https://esr.rnp.br/minha-conta/meus-certificados/)  
  *(De acordo com o Moodle da RNP, o certificado oficial sincroniza e é emitido no portal da ESR em até 7 dias após a aprovação das atividades).*

---

## O que aprendi no curso (resumo simples em 4 módulos)

### 📘 Módulo 1: Fundamentos da Web e Java Enterprise
- **Protocolo HTTP:** Entendi como funciona a conversa entre cliente e servidor (as requisições que enviamos e as respostas que recebemos com cabeçalhos e corpo).
- **Verbos HTTP:** Quando usar cada um na prática:
  - `GET`: buscar informações sem alterar nada no servidor.
  - `POST`: cadastrar/criar um recurso novo.
  - `PUT`: atualizar os dados de algo que já existe.
  - `DELETE`: remover um registro.
- **Códigos de Status (HTTP Status Codes):** A importância de devolver os códigos certos (ex: `200 OK`, `201 Created` quando cadastrou, `404 Not Found` quando não achou, `400 Bad Request` quando o dado veio errado).
- **Arquitetura MVC (Model-View-Controller):** Separação de responsabilidades para não misturar regra de negócio com acesso ao banco e resposta da tela.

### 🚀 Módulo 2: Construindo sua primeira API REST com Spring Boot
- **O que é o Spring Boot:** Um framework que cuida de toda a configuração pesada do Java pra gente focar só na lógica da aplicação.
- **Anotações principais:**
  - `@SpringBootApplication`: ponto de partida da aplicação.
  - `@RestController`: avisa ao Spring que aquela classe vai receber requisições HTTP e devolver dados em formato JSON.
  - `@RequestMapping("/api/...")`: rota base dos endpoints.
  - `@GetMapping`, `@PostMapping`, `@PutMapping`, `@DeleteMapping`: mapeia qual método Java roda em cada verbo HTTP.
  - `@RequestBody`: lê o JSON que o usuário mandou no corpo da requisição.
  - `@PathVariable`: pega o ID que vem na própria URL (ex: `/api/produtos/5`).
- **DTOs (Data Transfer Object):** Aprendi que não devemos expor a entidade do banco de dados diretamente na API. Usamos classes DTO para controlar exatamente o que entra e o que sai.

### 💾 Módulo 3: Persistência de Dados com Spring Data JPA
- **JPA e Hibernate:** Como transformar classes normais do Java em tabelas no banco de dados sem precisar ficar escrevendo comandos SQL na mão.
- **Anotações de Banco:**
  - `@Entity`: marca a classe como uma tabela.
  - `@Id` e `@GeneratedValue(strategy = GenerationType.IDENTITY)`: cria a chave primária auto-incrementada (1, 2, 3...).
  - `@Column`: customiza as colunas (tamanho máximo, se aceita nulo ou não).
- **Spring Data JPA (`JpaRepository`):** Apenas criando uma interface que estende `JpaRepository<Produto, Long>`, o Spring já cria automaticamente os métodos `save()`, `findAll()`, `findById()` e `deleteById()`.
- **Derived Queries:** Criar consultas no banco só pelo nome do método no Java (ex: `findByStatus(StatusProduto status)`).

### 🧪 Módulo 4: Testes Automatizados em APIs Spring Boot
- **Por que testar:** Para ter certeza de que uma alteração nova não quebrou nenhuma funcionalidade antiga.
- **Testes Unitários com JUnit 5 e Mockito:** Testar a camada de serviço (`@Service`) de forma isolada e ultra rápida.
- **Mocks (`@Mock` e `@InjectMocks`):** Em vez de ligar o banco de dados de verdade no teste unitário, a gente simula o repositório com o Mockito, dizendo o que ele deve retornar e testando se as regras de validação funcionam.

---

## Estrutura do Mini-Projeto no Repositório

Montei uma API de gerenciamento de produtos e estoque aplicando essa arquitetura em camadas:

```
src/
├── main/
│   ├── java/com/alexandre/backend/
│   │   ├── BackendApplication.java       # Ponto de entrada do Spring Boot
│   │   ├── controller/
│   │   │   └── ProdutoController.java    # Endpoints REST (GET, POST, PUT, DELETE)
│   │   ├── dto/
│   │   │   └── ProdutoDTO.java           # Objeto de transferencia de dados
│   │   ├── model/
│   │   │   ├── Produto.java              # Entidade JPA mapeada para a tabela 'produtos'
│   │   │   └── StatusProduto.java        # Enum (ATIVO, EM_FALTA, DESCONTINUADO)
│   │   ├── repository/
│   │   │   └── ProdutoRepository.java    # Interface Spring Data JPA
│   │   └── service/
│   │       └── ProdutoService.java       # Regras de negocio e validacoes
│   └── resources/
│       └── application.properties        # Configuracao com banco em memoria H2
└── test/
    └── java/com/alexandre/backend/service/
        └── ProdutoServiceTest.java       # Testes unitarios com JUnit 5 e Mockito
```

---

## Como rodar o projeto

### Opção 1: Rodando com o Maven (Spring Boot Completo)
Se você tem o Maven ou usa uma IDE (IntelliJ, VS Code, Eclipse):
```bash
# Rodar os testes automatizados
mvn test

# Subir a aplicacao
mvn spring-boot:run
```
A API vai subir em `http://localhost:8080/api/produtos`.  
O console do banco H2 fica acessível em `http://localhost:8080/h2-console`.

### Opção 2: Demonstração Rápida usando só o Java puro (Sem precisar do Maven)
Na pasta `demonstracao-rapida/` deixei um servidor HTTP nativo em Java puro com os mesmos endpoints REST para testar na hora com o Java da sua máquina:
```bash
cd demonstracao-rapida
javac ServidorApiSemDependencias.java
java demonstracao.ServidorApiSemDependencias
```
Abra `http://localhost:8080/api/produtos` no seu navegador para ver o JSON retornando na hora!
