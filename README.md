# Books API

REST API for book management with Java, Spring, Maven, JPA, Hibernate, MySQL, Flyway, JUnit 5, Mockito, Swagger, Docker and AWS.

---

## Docker Solution

![Docker Solution](/images/docker-solution-resized.jpg "Docker Solution")

## AWS Solution

![AWS Solution](/images/aws-solution-resized.jpg "AWS Solution")

#### AWS solution video: <https://youtu.be/7Q_ibI6qOSU>

---

## Requirements

**1 - Install Docker on your machine**

<https://www.docker.com>

---

## Step by step

**1 - Clone the repository**

```bash
git clone https://github.com/gustavorsmedina/books-api.git
```

**2 - Open the terminal in the project root folder and execute the following command:**

```bash
docker compose up -d
```

- The first time you use the command `docker compose up -d`, you will download all necessary images for the application to run on your machine. In a few minutes, the application will be up and running.
- You can check if both containers are already running using the command `docker ps`.

---

## Observations

- The application will be available at the address: <http://localhost:8080>
- The documentation will be available at the address: <http://localhost:8080/swagger-ui/index.html>
- All requests are prepared in the .JSON file located in the root directory of the project.

---

## Endpoints

### Authors

| Method | Url                            | Description          | Request Body          |
| ------ | ------------------------------ | -------------------- | --------------------- |
| POST   | /v1/authors                    | Create a new author. | [JSON](#createauthor) |
| GET    | /v1/authors/{id}               | Get author by id.    |                       |
| GET    | /v1/authors                    | Get all authors.     |                       |
| GET    | /v1/authors/search&name={name} | Get books by name.   |                       |
| PUT    | /v1/authors/{id}               | Update author by id. | [JSON](#updateauthor) |
| DELETE | /v1/authors/{id}               | Delete author by id. |                       |

### Books

| Method | Url                          | Description                             | Request Body        |
| ------ | ---------------------------- | --------------------------------------- | ------------------- |
| POST   | /v1/books                    | Create a new book.                      | [JSON](#createbook) |
| GET    | /v1/books/{id}               | Get book by id                          |                     |
| GET    | /v1/books                    | Get all books.                          |                     |
| GET    | /v1/books/search&name={name} | Get books by name.                      |                     |
| GET    | /v1/books/genre/{genre}      | Filter books by genre.                  |                     |
| PUT    | /v1/books/{id}               | Update book by id.                      | [JSON](#updatebook) |
| DELETE | /v1/books/{id}               | Check if email is available to register |                     |

---

## Request Body

##### <a id="createbook">/v1/books - Create a new book (Needs an author).</a>

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

##### <a id="updatebook">/v1/books - Update author by id (All fields are optional).</a>

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

##### <a id="createauthor">/v1/books - Create a new author.</a>

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

##### <a id="updateauthor">/v1/books - Update author by id.</a>

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
