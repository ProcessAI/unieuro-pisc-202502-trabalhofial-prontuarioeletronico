# Projeto Prontuário – Java + PostgreSQL

## 1. Criar o Banco de Dados

### 1.1. Abrir o pgAdmin 4

1. Abra o programa **pgAdmin 4**.
2. Na parte esquerda da tela (painel **Object Explorer**), clique duas vezes em **Servers**.
3. Clique duas vezes em **PostgreSQL 17** (ou a versão que aparecer aí).
4. Se pedir senha, digite a senha do seu usuário do PostgreSQL.

---

### 1.2. Criar o banco de dados `prontuario`

1. No painel da esquerda (**Object Explorer**), clique na setinha ao lado de **Databases** para abrir a lista.
2. Clique com o **botão direito** do mouse em **Databases**.
3. Clique em **Create > Database...**.
4. Vai abrir uma janelinha:
   - No campo **Database**, escreva exatamente:
     ```text
     prontuario
     ```
5. Clique em **Save** (ou **OK**).  
6. Depois disso, na lista da esquerda, você verá o banco **prontuario** criado embaixo de **Databases**.

---

### 1.3. Importar o arquivo `prontuario.sql` (jogar o documento dentro do banco)

Agora vamos “jogar” o conteúdo do arquivo `prontuario.sql` para dentro do banco `prontuario`.

1. No painel da esquerda, em **Databases**, localize o banco:
   - **Databases > prontuario**
2. Clique com o **botão direito** em **prontuario**.
3. Clique na opção **Query Tool** (no print é exatamente esse menu onde aparecem coisas como *Create*, *Refresh*, *Backup*, e também **Query Tool**).
4. Vai abrir uma aba nova no meio da tela, com um espaço grande em branco (é a janela de consultas SQL) e uma barra cheia de ícones em cima (play, disquete, pasta, etc.).

Agora vamos carregar o arquivo `.sql`:

5. Na barra de ícones da Query Tool, clique no ícone de **pasta** (é o botão de **Open File**, para abrir um arquivo).
6. Vai abrir uma janela do sistema para você escolher o arquivo.
7. Navegue até a pasta onde está salvo o arquivo:
   ```text
   prontuario.sql


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
