# README - Backend do Aplicativo de Controle Financeiro

Este documento contém instruções detalhadas para configurar e executar o backend do aplicativo de controle financeiro desenvolvido com Spring Boot e PostgreSQL.

## Tecnologias Utilizadas

- Java 11
- Spring Boot 2.7.0
- Spring Security com JWT
- PostgreSQL
- Docker e Docker Compose
- Maven

## Pré-requisitos

Para executar o backend, você precisa ter instalado:

- Docker e Docker Compose
- Java 11 ou superior (opcional, apenas se quiser compilar localmente)
- Maven (opcional, apenas se quiser compilar localmente)

## Configuração e Execução

### Método 1: Usando Docker (Recomendado)

Este método é o mais simples e não requer instalação de Java ou Maven.

1. **Clone o repositório ou extraia o arquivo ZIP**

2. **Navegue até a pasta raiz do projeto**
   ```
   cd caminho/para/app-financeiro
   ```

3. **Execute o Docker Compose**
   ```
   docker-compose up -d
   ```
   
   Este comando irá:
   - Construir a imagem do backend usando Maven dentro do container
   - Compilar o código-fonte
   - Criar o arquivo JAR
   - Iniciar o banco de dados PostgreSQL
   - Executar o aplicativo Spring Boot

4. **Verifique se os containers estão rodando**
   ```
   docker-compose ps
   ```

5. **Verifique os logs do backend (opcional)**
   ```
   docker-compose logs -f app-backend
   ```

6. **Acesse a API**
   - A API estará disponível em: http://localhost:8080
   - A documentação Swagger estará em: http://localhost:8080/swagger-ui.html

7. **Para parar os containers**
   ```
   docker-compose down
   ```

### Método 2: Compilação Local (Avançado)

Se preferir compilar localmente antes de usar o Docker:

1. **Navegue até a pasta do backend**
   ```
   cd caminho/para/app-financeiro/backend
   ```

2. **Compile o projeto com Maven**
   ```
   mvn clean package -DskipTests
   ```
   No Windows, use `mvn` se o Maven estiver instalado e configurado no PATH.

3. **Volte para a pasta raiz e execute o Docker Compose**
   ```
   cd ..
   docker-compose up -d
   ```

## Estrutura do Projeto

O backend segue uma arquitetura em camadas:

- **Controller**: Recebe requisições HTTP e delega para os serviços
- **Service**: Contém a lógica de negócio
- **Repository**: Interface com o banco de dados
- **Model**: Entidades JPA que representam as tabelas
- **DTO**: Objetos para transferência de dados
- **Security**: Configuração de segurança e JWT

## Endpoints da API

A API REST expõe os seguintes endpoints principais:

### Autenticação
- `POST /api/auth/register` - Registrar novo usuário
- `POST /api/auth/login` - Autenticar usuário e obter token JWT

### Usuários
- `GET /api/users/me` - Obter dados do usuário atual
- `PUT /api/users/{id}` - Atualizar dados do usuário
- `PUT /api/users/{id}/password` - Atualizar senha

### Transações
- `GET /api/transactions` - Listar todas as transações
- `GET /api/transactions/{id}` - Obter transação por ID
- `POST /api/transactions` - Criar nova transação
- `PUT /api/transactions/{id}` - Atualizar transação
- `DELETE /api/transactions/{id}` - Excluir transação

### Metas Financeiras
- `GET /api/goals` - Listar todas as metas
- `GET /api/goals/{id}` - Obter meta por ID
- `POST /api/goals` - Criar nova meta
- `PUT /api/goals/{id}` - Atualizar meta
- `DELETE /api/goals/{id}` - Excluir meta

Para detalhes completos sobre os endpoints, parâmetros e respostas, consulte a documentação Swagger disponível em http://localhost:8080/swagger-ui.html após iniciar o backend.

## Configuração do Banco de Dados

O banco de dados PostgreSQL é configurado automaticamente pelo Docker Compose. As tabelas são criadas automaticamente pelo Hibernate quando o backend é iniciado pela primeira vez.

Configurações do banco de dados:
- **Host**: localhost (ou db para acesso interno entre containers)
- **Porta**: 5432
- **Banco de dados**: app_financeiro
- **Usuário**: postgres
- **Senha**: postgres

## Solução de Problemas

### O container do backend não inicia
- Verifique os logs: `docker-compose logs app-backend`
- Certifique-se de que a porta 8080 não está sendo usada por outro aplicativo

### Erro de conexão com o banco de dados
- Verifique se o container do PostgreSQL está rodando: `docker-compose ps`
- Verifique os logs do PostgreSQL: `docker-compose logs db`

### Outros problemas
- Tente reconstruir os containers: `docker-compose down && docker-compose up -d --build`
- Verifique se o Docker tem permissões suficientes no seu sistema

## Próximos Passos

Após iniciar o backend com sucesso, você pode:
1. Testar os endpoints usando o Swagger UI
2. Configurar e executar o frontend Android (veja o README do frontend)
3. Desenvolver novos recursos ou personalizar os existentes
