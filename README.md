# BLOG API

Desenvolvida usando spring-boot e PostgreSQL.


- ##### Segurança da App usando Spring Security
Para garantir a segurança da aplicação
Username: admin
Password: secret

Credenciais usadas para gerar o token de acesso 
Rota para gerar o token: 
Method: POST
Rota: api/v1/auth
Body: 
    {
        "username":"admin",
        "password":"password"
    }

Para cada request deve ser enviado o token no cabeçalho com
Authorization: [token gerado na request anterior]

Usando o swagger, basta apenas colocar o token no campo authorize apresentado pela interface do swagger.

Foi usado o `flyway db` é uma ferramenta que se permite garantir a ordem e organização para os scripts SQL que são executados na base de dados, garantindo o controle de versão dos mesmos.

## Como executar?

- `cd app-api`
- `mvnw clean install -DskipTests`
- `java -jar target/app-api-0.0.1-SNAPSHOT.jar`

A partir deste momento, seguindo corretamente os passos terá a aplicação correndo no endereço `http://localhost:8080`

NB:
Poderá encontrar uma documentação mais interativa da API no endereço `http://localhost:8080/swagger-ui/`

## Alternativa para executar

- `cd blog-api`
- `docker build -f Dockerfile -t api/app-api .`
- `docker run -d -p 8080:8080 api/app-api`

Permite executar criando um container docker
