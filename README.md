# Books API

- Java
- Spring Boot
- Spring Data JPA
- MySQL
- Flyway
- JUnit 5
- Mockito
- Swagger
- Docker

---

## Diagrama

![Docker Solution](/images/docker-solution-resized.jpg "Docker Solution")

---

## Requerimentos

**1 - Instale o Docker em sua máquina**

<https://www.docker.com>

---

## Passo a passo de como usar

**1 - Clone o repositório**

```bash
git clone https://github.com/gustavorsmedina/books-api.git
```

**2 - Abra o terminal na pasta raiz do projeto e execute o seguinte comando:**

```bash
docker compose up -d
```

- A primeira vez que você usar o comando `docker compose up -d`, todas as imagens necessárias para a aplicação serão baixadas para rodar em sua máquina. Em poucos minutos, a aplicação estará em funcionamento.
- Você pode verificar se ambos os containers já estão em execução usando o comando `docker ps`.

---

## Observações

- A aplicação estará disponível no endereço: <http://localhost:8080>
- A documentação estará disponível no endereço: <http://localhost:8080/swagger-ui/index.html>
- Todas as requisições estão preparadas no arquivo .JSON localizado no diretório raiz do projeto.

---

## Endpoints

### Authors

| Method | Url                            | Descrição                | Request Body            |
| ------ | ------------------------------ | ------------------------ | ----------------------- |
| POST   | /v1/authors                    | Crie um novo autor.      | [JSON](#criarautor)     |
| GET    | /v1/authors/{id}               | Busque um autor por id.  |                         |
| GET    | /v1/authors                    | Busque todos autores.    |                         |
| GET    | /v1/authors/search&name={name} | Busque autores pelo nome.|                         |
| PUT    | /v1/authors/{id}               | Atualize um autor por id.| [JSON](#atualizarautor) |
| DELETE | /v1/authors/{id}               | Apaga um autor por id.   |                         |

### Books

| Method | Url                          | Description                             | Request Body            |
| ------ | ---------------------------- | --------------------------------------- | ----------------------- |
| POST   | /v1/books                    | Crie um novo livro.                     | [JSON](#criarlivro)     |
| GET    | /v1/books/{id}               | Busque um livro por id.                 |                         |
| GET    | /v1/books                    | Busque todos os livros.                 |                         |
| GET    | /v1/books/search&name={name} | Busque livros pelo nome.                |                         |
| GET    | /v1/books/genre/{genre}      | Filtre livros pelo genêro.              |                         |
| PUT    | /v1/books/{id}               | Atualize um livro por id.               | [JSON](#atualizarlivro) |
| DELETE | /v1/books/{id}               | Apaga um livro por id.                  |                         |

---

## Requisições

##### <a id="criarlivro">/v1/books - Cria um livro (Precisa de um autor).</a>

```json
{
  "name": "Some name",
  "pages": 100,
  "genre": "COMEDY",
  "synopsis": "Some synopsis",
  "publicationDate": "2024-01-01",
  "isbn": "123-456",
  "authorId": 1
}
```

##### <a id="atualizarlivro">/v1/books - Atualize um livro por id (Todos os campos são opcionais).</a>

```json
{
  "name": "Some name",
  "pages": 100,
  "genre": "FANTASY",
  "synopsis": "Some synopsis",
  "publicationDate": "2024-01-01",
  "isbn": "123-456",
  "authorId": 1
}
```

##### <a id="criarautor">/v1/books - Crie um novo autor.</a>

```json
{
  "name": "Some name",
  "pages": 100,
  "genre": "COMEDY",
  "synopsis": "Some synopsis",
  "publicationDate": "2024-01-01",
  "isbn": "123-456",
  "authorId": 1
}
```

##### <a id="atualizarautor">/v1/books - Atualize um autor por id.</a>

```json
{
  "name": "Some name",
  "pages": 100,
  "genre": "FANTASY",
  "synopsis": "Some synopsis",
  "publicationDate": "2024-01-01",
  "isbn": "123-456",
  "authorId": 1
}
```
