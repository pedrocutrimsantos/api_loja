# Loja Backend (Paulo Neto Construções)

### 🚀 Stack
- Java 21, Spring Boot 3
- PostgreSQL
- Planejamento de Migrations com Flyway
- OpenAPI/Swagger UI
- Docker Compose (Postgres + Keycloak – planejado)

---

### 🛠️ Subir infra (Postgres)
```bash
docker compose up -d
```
> Obs: Keycloak já previsto no `docker-compose.yml`, mas ainda **não configurado/validado**.

---

### ▶️ Rodar a aplicação (perfil dev)
```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```
- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI JSON: http://localhost:8080/v3/api-docs

---

### 🌐 Perfis
- `dev`: DB `localhost:5432`
- `hml`/`prod`: ajustes no `application.yml` e secrets → ainda **não aplicados**

---

### 🗄️ Banco de Dados
- Extensão `pgcrypto` para geração de UUID.
- **Tabelas criadas**:
    - `categoria`
    - `produto`
    - `deposito`
    - `posicao_estoque`

- **Restrições aplicadas**:
    - `sku` único em produto
    - Nome único em categoria e depósito
    - Chaves estrangeiras em `posicao_estoque`
    - `unique(produto_id, deposito_id)`

---

### 📦 Backend – Módulos Disponíveis
- **Categoria**: CRUD completo
- **Produto**: CRUD completo
- **Depósito**: CRUD completo
- **Posição de Estoque**: CRUD completo
- **Movimentação de Estoque**:
    - Entrada e saída de produtos
    - Atualização automática de saldos
    - Registro histórico de movimentações

---

### 📂 Estrutura Atual
```
/loja-backend
 ├── src/main/java
 │   ├── .../controller   # Endpoints REST
 │   ├── .../service      # Regras de negócio
 │   ├── .../repository   # Persistência
 │   └── .../dto          # DTOs e validações
 └── src/main/resources
     └── application.yml  # Configurações
```

---

### 🔎 Status
- ✅ CRUDs concluídos: Categoria, Produto, Depósito, Posição de Estoque
- ✅ Serviço de movimentação de estoque implementado
- ⏳ Em planejamento: Vendas, Pedidos, Caixa, Funcionários, Integrações externas (PIX/WhatsApp), autenticação com Keycloak  
