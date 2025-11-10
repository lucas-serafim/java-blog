
# 📰 Java Blog

Um projeto de **blog** desenvolvido em **Java** com **Spring Boot**, oferecendo autenticação segura com **Spring Security** e **OAuth2**, integração com **AWS S3** para upload de imagens e persistência de dados em **MongoDB**.  

O sistema permite que usuários se registrem, criem posts com imagens, comentem e curtam tanto posts quanto comentários.

---

## 🚀 Tecnologias Utilizadas

- **Java 21**
- **Spring Boot** – Framework principal da aplicação  
- **Spring Security + OAuth2** – Autenticação e autorização  
- **AWS S3** – Armazenamento de imagens  
- **MongoDB** – Banco de dados NoSQL  
- **Lombok** – Redução de código boilerplate  
- **Docker Compose** – Subida rápida do ambiente MongoDB  
- **Maven/Gradle** – Gerenciamento de dependências  

---

## 📚 Funcionalidades

### 👤 Usuário
- **Sign Up:** Registro de novos usuários  
- **Sign In:** Login com autenticação via OAuth2

### 📝 Postagens
- Criar posts com **título**, **descrição** e **imagens**  
- Listar todos os posts existentes
- Listar todos os posts existentes de um usuário
- Curtir posts de outros usuários  
- Comentar publicações  
- Curtir comentários
- Atualizar
- Deletar

---

## ⚙️ Configuração e Execução

### 🧩 Pré-requisitos

Antes de iniciar, certifique-se de ter instalado:
- **Java 17+**
- **Docker e Docker Compose**
- **Conta AWS** com bucket S3 configurado
- **Credenciais OAuth2** (Google, GitHub, etc.)

---

### 🐳 Subindo o MongoDB com Docker Compose

O projeto já conta com um arquivo `docker-compose.yml` que facilita a inicialização do banco de dados MongoDB localmente.

Para subir o serviço, execute:

```bash
docker-compose up -d
```

Isso criará um container com o MongoDB rodando na porta padrão `27017`.

Para verificar se está funcionando:

```bash
docker ps
```

---

### 🧾 Configuração do Projeto

Edite o arquivo `application.yml` com suas credenciais e informações de ambiente:

```yaml
spring.application.name=java-blog
spring.data.mongodb.uri=mongodb://localhost:27017/java-blog

aws.bucket-name=

aws.accessKey=
aws.secretKey=

jwt.public.key=classpath:app.pub
jwt.private.key=classpath:app.key

spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB
```

---

### ▶️ Executando o Projeto

1. **Clone o repositório**
   ```bash
   git clone https://github.com/seu-usuario/java-blog.git](https://github.com/lucas-serafim/java-blog.git
   cd java-blog
   ```

2. **Inicie o MongoDB com Docker**
   ```bash
   docker-compose up -d
   ```

3. **Execute a aplicação**
   ```bash
   ./mvnw spring-boot:run
   ```
   ou
   ```bash
   ./gradlew bootRun
   ```

4. **Acesse no navegador**
   ```
   http://localhost:8080
   ```

---

## ☁️ Upload de Imagens com AWS S3

As imagens dos posts são armazenadas diretamente em um bucket **AWS S3**.  
Durante a criação de um post, a aplicação envia o arquivo para a nuvem e armazena apenas a **URL pública** e **KeyName** no banco de dados.  

Essa abordagem mantém o sistema leve e escalável.

---

## 🔒 Segurança

A autenticação é gerenciada pelo **Spring Security** e **OAuth2**, garantindo:
- Proteção de rotas autenticadas
- Tokens JWT para controle de sessão  

---

## 🧠 Boas Práticas Aplicadas

- **Arquitetura organizada e simples**: código limpo e fácil de entender  
- **DTOs** para transferência de dados entre camadas  
- **Validações customizadas** para entrada de dados  
- **Uso do Lombok** para eliminar código repetitivo  
- **Tratamento centralizado de exceções**  
- **Docker Compose** para facilitar o setup do ambiente local  

---

## 💡 Exemplos de Requisições

### Criar um novo post
```bash
POST /posts
Authorization: Bearer <TOKEN>
Content-Type: multipart/form-data

title=Meu Primeiro Post
description=Um post de exemplo
image=@/caminho/para/imagem.jpg
```

### Comentar um post
```bash
curl --location 'http://localhost:8080/posts' \
--header 'Authorization: Bearer <TOKEN>' \
--form 'title="teste"' \
--form 'text="teste"' \
--form 'images=@"/path/to/file"'
```

### Curtir um comentário
```bash
curl --location --request POST 'http://localhost:8080/commentaries/:comentaryId/posts/:postId/like' \
--header 'Authorization: Bearer <TOKEN>'
```
