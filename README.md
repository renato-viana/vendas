# Sistemas Comerciais Java EE com CDI, JPA e PrimeFaces

## Tópicos Abordados:

1. Formulários e Ajax com PrimeFaces
2. Prototipação com formulários
3. Exibindo painéis e dados com PrimeFaces
4. Prototipação com painéis e dados
5. Menus, mensagens e diálogos com PrimeFaces
6. Prototipação com menus e diálogos
7. Injeção de dependências com CDI
8. Tratamento de exceções e logging
9. Persistência de dados com JPA 2 e Hibernate
10. Validação do modelo com Bean Validation
11. Integrando páginas, serviços e repositórios
12. Página mestre-detalhe: cadastro de pedidos
13. Envio de e-mail com JavaMail e CDI
14. Segurança da aplicação com Spring Security
15. Gráficos com PrimeFaces
16. Relatórios com JasperReports e iReport
17. Fazendo deploy na nuvem da Amazon AWS
18. Apêndice: Migrando para Java EE 7
19. Apêndice: Mais recursos do PrimeFaces
20. PrimeFaces 6, Spring Security 4, Hibernate 5 e Log4j 2

## Comandos Maven

> Para gerar o arquivo vendas.war, que será implantado no servidor Apache Tomcat, execute o seguinte comando:

- mvn clean package

## Comandos Docker Compose

1. `docker compose up -d`

   - **Descrição:** Inicia os contêineres definidos no arquivo `docker-compose.yml` em modo "detached" (em segundo plano).
   - **Utilização:** Use esse comando para iniciar os contêineres do ambiente em background.

2. `docker compose down`
   - **Descrição:** Encerra e remove todos os contêineres, redes e volumes criados pelo arquivo `docker-compose.yml`.
   - **Utilização:** Utilize esse comando para desligar e limpar completamente o ambiente.

## Comando Docker para MySQL

`docker run -it --name mysql-client --network mysql8-network --rm mysql:8.0.33 mysql -h mysql-server -uroot -p`

- **Descrição:** Cria e executa um novo contêiner temporário com a imagem do MySQL (versão 8.0.33).
- **Utilização:** Esse comando é utilizado para acessar o cliente do MySQL interativamente no contêiner temporário. Permite interagir com o servidor MySQL especificado através dos parâmetros:
  - `-h mysql-server`: Indica o hostname do servidor MySQL que o cliente irá acessar.
  - `-uroot`: Especifica o usuário "root" para autenticação.
  - `-p`: Solicita a senha do usuário "root" para autenticação.
- **Nota:** Certifique-se de que o contêiner do servidor MySQL (chamado `mysql-server`) esteja em execução e que a rede `mysql8-network` esteja corretamente configurada para permitir a conexão entre os contêineres.

## Informações importantes

> [3 maneiras de fazer com que o JPA e o Hibernate criem tabelas em um banco de dados](https://www.theserverside.com/video/3-ways-to-have-JPA-and-Hibernate-create-tables-in-a-database)

> Você pode usar a opção "provided" para indicar que o driver será fornecido pelo ambiente de execução. Certifique-se de que o servidor Tomcat esteja configurado corretamente e que o driver do MySQL esteja presente nas bibliotecas do servidor. Você pode fazer o download do driver JDBC do MySQL [Connector/J 8.0.33](https://dev.mysql.com/downloads/connector/j/) e copiá-lo para a pasta lib do Tomcat.

> **Nota:** A propriedade javax.persistence.sql-load-script-source está disponível a partir do JPA 2.1 e pode não ser suportada em versões mais antigas do Hibernate ou JPA.
> Se você estiver usando o JPA 2.1, a sintaxe para inserção de várias linhas não é suportada. Em vez disso, você pode usar declarações INSERT separadas para cada linha.

> **Nota:** Há um problema com o repositório da dependência [simple-email](https://github.com/codylerum/simple-email), onde o download do JAR não é feito mesmo após passar a autenticação do GitHub.

### Instalação Local da Biblioteca Simple-Email

#### Para utilizar a biblioteca Simple-Email em seu projeto Maven, siga os passos abaixo:

> Clone o repositório da biblioteca Simple-Email para o seu sistema local usando o comando:

- git clone https://github.com/codylerum/simple-email.git

> Acesse a pasta do repositório clonado com o comando:

- cd simple-email

> Instale a biblioteca no seu repositório local do Maven, ignorando a execução dos testes, que estão falhando:

- mvn install -DskipTests=true

> Após a instalação bem-sucedida, atualize a dependência no seu arquivo pom.xml para a versão 2.11, que é a versão atual do projeto clonado.

### Dados de Teste para o Login

- Administrador: joao@example.com "senha123"
- Vendedor: maria@example.com "senha456"
- Auxiliar: ana@example.com "senha789"
