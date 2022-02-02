# Started
### Projeto Teste Tecnico para SoftDesign

*********************************************************************************
# Passo 1
*********************************************************************************

### Docker
O Docker deve estar instalado e rodando na sua máquina:
Instalar o Docker (Get Docker) -> https://docs.docker.com/get-docker/

*********************************************************************************
# Passo 2
*********************************************************************************

### MongoDB
Gerar um imagem docker do mongo 

NoSQLBooster
É necessário uma interface de gerenciamento de banco de dados para a realização de algumas alterações no banco. Recomendamos o NoSQLBooster.
Se preferir, pode utilizar outra ferramenta ou realizar os passos diretamente pelo terminal.

Criação da base de dados e Collection
- É necessario realizar a criação de um Data Base com o nome "teste-soft-design"
- É necessario realizar a criação de uma Collection com o nome "Pauta"

*********************************************************************************
# Passo 3
*********************************************************************************
- Realizar o clone do projeto

- O projeto pode ser executando de 2 formas:

  IDE de desenvolvimento:

  Descomentar a seguinte linha do arquivo.properties -> spring.data.mongodb.host=127.0.0.1
  
  Roda o projeto
  
  docker

  Descomentar a seguinte linha do arquivo.properties -> spring.data.mongodb.host=host.docker.internal
  
  Executar os comando abaixo

  docker build --build-arg JAR_FILE=build/libs/*.jar -t testetecnico/testesoftdesign .

  docker run -p 8090:8090 testetecnico/testesoftdesign

*********************************************************************************
# Passo 4
*********************************************************************************

### Postman
Para realizar os testes:

### Realizar o cadastro de uma Pauta
Metodo POST - http://localhost:8090/pautas

Request
```json
{
    "assunto":"Liberação de cachorros no condominio"
}
```

Response
```json
{
    "code": 200,
    "messagem": "Pauta cadastrar com sucesso!",
    "numeroPauta": 1333
}
```

*********************************************************************************
### Realizar o cadastro de uma sessão na respectiva Pauta
Metodo POST - http://localhost:8090/sessao

### Observação: para realizar o cadastro de uma sessao tem que pegar o numeroPauta gerado no cadastra da mesma.
Request
```json 
{
    "numeroPauta": //numeroPauta Gerado no cadastro
    "inicioSessao": "2022-02-01T10:15:00-03:00",
    "duracaoMinutosSessao": 10    
}
```
Response
```json 
{
    "code": 200,
    "messagem": "Sessão adicionada na pauta com sucesso!",
    "numeroPauta": 3682
}
```
*********************************************************************************
### Realizar o cadastro de um voto na respectiva Pauta
Metodo POST - http://localhost:8090/votacao

### Observação: para realizar o cadastro de um voto tem que pegar o numeroPauta gerado no cadastra da mesma.
Request
```json 
{
    "cpf": "???????????",
    "nome": "????????????",
    "numeroPauta": //numeroPauta Gerado no cadastro,
    "voto": "Sim"    
}
```
*********************************************************************************
### Realizar a listagem das Pautas
Metodo GET - http://localhost:8090/pautas



*********************************************************************************
### Segue algumas referências
Para referência adicional, considere as seguintes seções:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.6.3/gradle-plugin/reference/html/)
* [Spring Data MongoDB](https://docs.spring.io/spring-boot/docs/2.6.3/reference/htmlsingle/#boot-features-mongodb)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/2.6.3/reference/htmlsingle/#using-boot-devtools)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.6.3/reference/htmlsingle/#boot-features-developing-web-applications)

### Guias
Os guias a seguir ilustram como usar alguns recursos de forma concreta:

* [Accessing Data with MongoDB](https://spring.io/guides/gs/accessing-data-mongodb/)
* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)
