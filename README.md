# Projeto Prontuário – Java + PostgreSQL

## 1. Criar o Banco de Dados

1. Abra o PostgreSQL (pgAdmin ou terminal).
2. Crie um banco de dados com o nome:

```
prontuario
```

3. Execute o arquivo **prontuario.sql** no query tools para criar todas as tabelas e estruturas necessárias do sistema.

---

## 2. Preparar o Projeto Java

1. Extraia o arquivo:

```
prontuario.zip
```

2. Abra o projeto no **NetBeans**.

O projeto possui a seguinte estrutura principal:

```
src/
 ├── view
 ├── model
 │    └── Conexao.java
 ├── main
 └── controller
postgresql-42.7.8.jar
```

---

## 3. Adicionar o Driver do PostgreSQL

O driver JDBC necessário já está dentro da pasta do projeto:

```
postgresql-42.7.8.jar
```

Para adicionar no NetBeans:

- Clique com o botão direito em **Libraries**.
- Clique em **Add JAR/Folder**.
- Selecione o arquivo **postgresql-42.7.8.jar** que está na raiz do projeto.
- Confirme a adição.

---

## 4. Ajustar Configurações de Conexão

O arquivo de conexão fica em:

```
src/model/Conexao.java
```

Edite usuário e senha conforme o seu PostgreSQL local:

```java
private static final String USUARIO = "seu_usuario";
private static final String SENHA   = "sua_senha";
```

Certifique-se de que correspondem ao seu ambiente local.

---

## 5. Executar o Sistema

1. Acesse o diretório:

```
src/main
```

2. Localize a classe que contém o método `main`.
3. Execute o projeto pelo NetBeans usando **Run Project**.
4. O usuario é admin e a senha é 123

Se tudo estiver configurado corretamente, o sistema inicia e conecta ao banco normalmente.

---

Com o banco criado, o driver configurado e a conexão ajustada, o sistema Prontuário estará totalmente funcional.
