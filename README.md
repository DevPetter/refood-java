# ReFood API - Sistema de Gestão de Doações de Alimentos

---

## Descrição

O **ReFood API** é uma evolução do projeto original **ReFood-Python**, desenvolvido como um sistema acadêmico para conectar estabelecimentos comerciais (doadores) a ONGs, facilitando a doação de alimentos próximos ao vencimento ou sobras, com o objetivo de combater o desperdício de comida e promover a sustentabilidade.
Essa versão em **Java** com **Spring Boot** transforma o sistema terminal-based em uma **API REST** completa, robusta e escalável. Ela mantém a integração com MySQL, adiciona validações, tratamento de erros (ex: 404 para IDs inexistentes), cascade deletes, e respostas personalizadas. 

---

## Tecnologias principais:

- Linguagem: Java 17
- Framework: Spring Boot 3.5.7
- Banco de Dados: MySQL (com JPA/Hibernate)
- Outros: Lombok (para boilerplate), Bean Validation, Enums com conversores

---

## Estrutura de pastas

refood-api/</br>
│</br>
├── src/</br>
│   ├── main/</br>
│   │   ├── java/com/refood/api/</br>
│   │   │   ├── config/           # Configurações globais (ex: ExceptionHandler)</br>
│   │   │   ├── controller/       # Controladores REST (endpoints)</br>
│   │   │   ├── dto/              # Data Transfer Objects (requests/responses)</br>
│   │   │   ├── entity/           # Entidades JPA (Doador, Ong, Doacao, Produto)</br>
│   │   │   ├── enums/            # Enums (Perecivel, TipoEstabelecimento)</br>
│   │   │   ├── exception/        # Exceções personalizadas</br>
│   │   │   ├── repository/       # Repositórios JPA</br>
│   │   │   ├── service/          # Lógica de negócio</br>
│   │   │   └── ReFoodApplication.java  # Classe principal</br>
│   │   │</br>
│   │   └── resources/</br>
│   │       ├── application.properties  # Configurações do banco e app</br>
│   │
│   └── test/                         # Testes unitários e de integração(Não utilizados até o momento)</br>
│
├── .gitignore
├── pom.xml                           # Dependências Maven</br>
├── README.md                         # Este arquivo</br>

---

## Endopoints da API

A API possui endpoints para gerenciar Doadores, ONGs e Doações. Todos os endpoints usam JSON para requests/responses.

### 1.Endpoints de Doadores:

- ### POST /doadores
- Cria um novo doador.
- JSON de entrada:

```bash
{
  "nome": "Padaria do Zé",
  "cnpj": "12.345.678/0001-99",
  "tipoEstabelecimento": "COMERCIO",
  "rua": "Av. Paulista",
  "numero": "1000",
  "bairro": "Bela Vista"
}
```
- Resposta:200 OK com o doador criado (inclui ID gerado).

---

- ### GET /doadores</br>
- Lista todos os doadores.</br>
- Sem corpo.</br>
- Resposta:200 OK com array de doadores.</br>

---

- ### GET /doadores/{id}</br>
- Busca doador por ID.</br>
- Sem corpo.</br>
- Resposta:200 OK com doador; 404 Not Found se ID não existir:</br>
  
```bash
{ "message": "Doador não encontrado com ID: 999" }
```

---

- ### PUT /doadores/{id}</br>
- Atualiza todos os campos do doador.</br>
- JSON de entrada (mesmo formato do POST):</br>

```bash
{
  "nome": "Padaria Nova",
  "cnpj": "12.345.678/0001-99",
  "tipoEstabelecimento": "RESTAURANTE",
  "rua": "Rua XV",
  "numero": "500",
  "bairro": "Centro"
}
```

- Resposta:200 OK com doador atualizado; 404 se ID não existir.</br>

---

- ### DELETE /doadores/{id}</br>
- Deleta doador (cascade deleta doações e produtos).</br>
- Sem corpo.</br>
- Resposta:200 OK com:</br>

```bash
{
  "message": "Doador deletado com sucesso. 3 doações e 8 produtos relacionados foram removidos.",
  "doacoesRemovidas": 3,
  "produtosRemovidos": 8
}
```

---

### 2.Endpoints de Ongs:

- ### POST /ongs
- Cria uma nova ONG.
- JSON de entrada:

```bash
{
  "nome": "ONG Esperança",
  "cnpj": "98.765.432/0001-88",
  "rua": "Rua das Flores",
  "numero": "300",
  "bairro": "Jardim Europa",
  "anoFundacao": 2015,
  "numPessoasAjudadas": 5000
}
```
- Resposta:200 OK com ONG criada.

---

- ### GET /ongs
- Lista todas as ONGs.
- Sem corpo.
- Resposta:200 OK com array de ONGs.

---

- ### GET /ongs/{id}
- Busca ONG por ID.
- Sem corpo.
- Resposta:200 OK com ONG; 404 Not Found se ID não existir.

---

- ### PUT /ongs/{id}
- Atualiza todos os campos da ONG.
- JSON de entrada (mesmo formato do POST):

```bash
{
  "nome": "ONG Esperança",
  "cnpj": "98.765.432/0001-88",
  "rua": "Rua das Flores",
  "numero": "300",
  "bairro": "Jardim Europa",
  "anoFundacao": 2015,
  "numPessoasAjudadas": 5000
}
```
- ### Resposta:200 OK com ONG atualizada.

---

- ### DELETE /ongs/{id}
- Deleta ONG (cascade).
- Sem corpo.
- Resposta:200 OK com mensagem similar ao doador.

---

## 3.Endpoint de Doações:

- ### POST /doacoes
- Cria uma doação com produtos.
- JSON de entrada:

```bash
{
  "idDoador": 1,
  "idOng": 1,
  "produtos": [
    {
      "nome": "Arroz 5kg",
      "validade": "2026-06-01",
      "quantidade": 10,
      "perecivel": "NAO"
    },
    {
      "nome": "Leite Integral",
      "validade": "2025-12-15",
      "quantidade": 20,
      "perecivel": "SIM"
    }
  ]
}
```

- Resposta: 200 OK com:

```bash
{
  "idDoacao": 1,
  "nomeDoador": "Padaria do Zé",
  "nomeOng": "ONG Esperança",
  "dataHoraDoacao": "15/11/2025 19:46:00",
  "itensDoados": [
    { "nome": "Arroz 5kg", "quantidade": 10, "validade": "2026-06-01", "perecivel": "Não" },
    { "nome": "Leite Integral", "quantidade": 20, "validade": "2025-12-15", "perecivel": "Sim" }
  ]
}
```

---

- ### GET /doacoes
- Lista todas as doações.
- Sem corpo.
- Resposta:200 OK com array no mesmo formato do POST.

---

- ### GET /doacoes
- Busca doação por ID.
- Sem corpo.
- Resposta:200 OK com objeto no mesmo formato do POST; 404 se não existir.

---

- ### DELETE /doacoes/{id}
- Deleta doação (cascade deleta produtos).
- Sem corpo.
- Resposta:200 OK com:

```bash
{
  "message": "Doação deletada com sucesso. 5 produtos relacionados foram removidos.",
  "produtosRemovidos": 5
}
```

---

## Como clonar e rodar a API:

### Pré-requisitos

- Java 17+ (JDK instalado)
- Maven 3.X
- MySQL 8.0+ (instalado e rodando)
- Git
- Postman ou Insomnia

### Passos

```bash
# 1. Clonar o repositório
git clone https://github.com/DevPetter/refood-java.git
cd refood-java

# 2. Criar banco de dados no MySQL
mysql -u root -p
CREATE DATABASE refood CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
EXIT;

# 3. Configurar application.properties
# Edite: src/main/resources/application.properties
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha

# 4. Build e rodar
mvn clean install
mvn spring-boot:run
```

- A API estará rodando em: **http://localhost:8080**

- As tabelas são criadas automaticamente (spring.jpa.hibernate.ddl-auto=update).

- Teste os endpoints via Postman ou Insomnia

---

## Observações

- Todos os campos são obrigatórios em POST/PUT.
- Enums aceitam: tipoEstabelecimento: COMERCIO | RESTAURANTE
- perecivel: SIM | NAO → retorna "Sim" | "Não"
- Deletar doador/ONG/doação remove dependentes em cascata.

---
