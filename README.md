# 📚 Biblioteca Digital

> Sistema de gestão de biblioteca construído com microserviços.  
> Projecto Lab Alternativo – Dia 1 do Microservices + DevOps + Cloud Academy 2026.

---

## 🎓 Informações do Aluno

| Campo | Valor |
|------|------|
| Nome | Pedro Daniel Pereira Santos |
| Turma | Microservices Academy 2026 |
| Lab | Lab Alternativo – Dia 1 (Tarde) |
| Data de entrega | 10/03/2026 |
| Email | pedro.pereira.santos04@gmail.com |

---

## 🏗 Arquitectura

```
Browser ───▶ [ web-ui (Flask) ]
              Python 3.11
                 :5000
                 │
                 │ HTTP REST
                 ▼
           [ book-service ]
           Spring Boot 3.x
                :8080
                 │
                 ▼
        ┌─────────────────────┐
        │                     │
   [ PostgreSQL ]        [ MongoDB ]
      :5432               :27017
     (catálogo)          (avaliações)
```

---

| Serviço | Tecnologia | Porta | Responsabilidade |
|--------|------------|------|------------------|
| web-ui | Python Flask | 5000 | Interface web (HTML/CSS) |
| book-service | Spring Boot 3.x | 8080 | API REST – catálogo de livros |
| postgresql | PostgreSQL 15 | 5432 | Dados estruturados (livros) |
| mongodb | MongoDB | 27017 | Avaliações dos leitores |

---

# ⚡ Execução Rápida

## Pré-requisitos

- Docker Desktop instalado e em execução  
- Docker Compose (incluído no Docker Desktop)

---

## Clonar e iniciar

```bash
git clone git@github.com:SEU_USERNAME/biblioteca-digital.git
cd biblioteca-digital
docker compose up --build
```

💡 **Primeira execução:** 3–5 minutos  
(Maven descarrega dependências, imagens Docker são construídas)

---

## Verificar que está tudo a correr

```bash
docker compose ps
```

Todos os **4 serviços** devem ter Status `Up`.

---

## Abrir a interface web

```
http://localhost:5000
```

⚠️ **macOS:** a porta 5000 pode estar ocupada pelo AirPlay.  
Alterar em `docker-compose.yml`:

```
'5001:5000'
```

Depois aceder:

```
http://localhost:5001
```

---

# 🧪 Testar a API REST (book-service)

### Listar todos os livros

```bash
curl http://localhost:8080/api/books | python -m json.tool
```

---

### Pesquisar livro

```bash
curl "http://localhost:8080/api/books/search?query=dune"
```

---

### Adicionar novo livro

```bash
curl -X POST http://localhost:8080/api/books \
-H "Content-Type: application/json" \
-d '{"title":"O Senhor dos Aneis","author":"Tolkien","year":1954,"genre":"Fantasia"}'
```

---

### Adicionar avaliação

```bash
curl -X POST http://localhost:8080/api/books/1/reviews \
-H "Content-Type: application/json" \
-d '{"reviewer":"Ana","rating":5,"comment":"Obra-prima absoluta!"}'
```

---

### Ver avaliações de um livro

```bash
curl http://localhost:8080/api/books/1/reviews
```

---

# 🌐 Funcionalidades da Interface Web

| Funcionalidade | URL |
|---|---|
| Catálogo completo | http://localhost:5000/ |
| Pesquisar livros | http://localhost:5000/?q=dune |
| Detalhe + avaliações | http://localhost:5000/book/1 |
| Adicionar livro | http://localhost:5000/add |

---

# 🧹 Parar e Limpar

```bash
docker compose down
docker compose down -v
```

- `down` → remove containers  
- `down -v` → remove containers + bases de dados

---

# 📁 Estrutura do Projecto

```
biblioteca-digital/
│
├─ book-service/
│  ├─ src/main/java/pt/openup/bookservice/
│  │  ├─ controller/BookController.java
│  │  ├─ model/Book.java
│  │  ├─ model/Review.java
│  │  ├─ repository/BookRepository.java
│  │  ├─ repository/ReviewRepository.java
│  │  └─ service/DataSeeder.java
│  │
│  ├─ src/main/resources/application.yml
│  ├─ Dockerfile
│  └─ pom.xml
│
├─ web-ui/
│  ├─ app.py
│  ├─ requirements.txt
│  ├─ Dockerfile
│  └─ templates/
│     ├─ base.html
│     ├─ index.html
│     ├─ book.html
│     └─ add_book.html
│
├─ docker-compose.yml
├─ .gitignore
└─ README.md
```

---

# 🔧 Troubleshooting

### Serviço não arranca

```bash
docker compose restart book-service
```

---

### Porta 5000 em uso

Alterar no `docker-compose.yml`:

```
'5001:5000'
```

---

### Ver logs

```bash
docker compose logs -f book-service
docker compose logs -f web-ui
``` REST
