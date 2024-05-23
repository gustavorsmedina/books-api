# Books API 📖

#### ☕ Tecnologias utilizadas:

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

## Diagrama 🗺️

![Docker Solution](/images/docker-solution-resized.jpg "Docker Solution")

---

## ⚙️ Passo a passo

#### 1 - Instale o [Docker](https://www.docker.com) em sua máquina. 

#### 2 - Abra o terminal na pasta raiz do projeto e execute o seguinte comando:

`docker compose up`

- A primeira vez que você usar o comando `docker compose up -d`, todas as imagens necessárias para a aplicação serão baixadas para rodar em sua máquina. Em poucos minutos, a aplicação estará em funcionamento.
- Você pode verificar se ambos os containers já estão em execução usando o comando `docker ps`.

#### 3 - Após terminar a configuração, inicie a aplicação e ela estará disponível em:

- A aplicação estará disponível no endereço: <http://localhost:8080>
- A documentação estará disponível no endereço: <http://localhost:8080/swagger-ui/index.html>
- Todas as requisições estão preparadas no arquivo .JSON localizado no diretório raiz do projeto.

---

## 📨 Requisições

### Autores

| Método | Url                            | Descrição                 | Corpo da requisição     |
| ------ | ------------------------------ | ------------------------- | ----------------------- |
| POST   | /v1/authors                    | Crie um novo autor.       | [JSON](#criarautor)     |
| GET    | /v1/authors/{id}               | Busque um autor por id.   |                         |
| GET    | /v1/authors                    | Busque todos autores.     |                         |
| GET    | /v1/authors/search&name={name} | Busque autores pelo nome. |                         |
| PUT    | /v1/authors/{id}               | Atualize um autor por id. | [JSON](#atualizarautor) |
| DELETE | /v1/authors/{id}               | Apague um autor por id.   |                         |

### Livros

| Método | Url                          | Description                             | Corpo da requisição     |
| ------ | ---------------------------- | --------------------------------------- | ----------------------- |
| POST   | /v1/books                    | Crie um novo livro.                     | [JSON](#criarlivro)     |
| GET    | /v1/books/{id}               | Busque um livro por id.                 |                         |
| GET    | /v1/books                    | Busque todos os livros.                 |                         |
| GET    | /v1/books/search&name={name} | Busque livros pelo nome.                |                         |
| GET    | /v1/books/genre/{genre}      | Filtre livros pelo genêro.              |                         |
| PUT    | /v1/books/{id}               | Atualize um livro por id.               | [JSON](#atualizarlivro) |
| DELETE | /v1/books/{id}               | Apague um livro por id.                 |                         |

---

## 📄 Corpo das requisições

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
