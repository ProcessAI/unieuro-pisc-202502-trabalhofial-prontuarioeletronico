# Medicamentos Connection

Este projeto é uma aplicação simples em Java que simula operações CRUD (Create, Read, Update, Delete) para gerenciar medicamentos.

## Estrutura do Projeto

### 1. **Model**: `Medicamento`

A classe `Medicamento` representa o modelo de dados utilizado na aplicação. Cada instância desta classe corresponde a um registro na tabela `medicamento` do banco de dados.

#### Atributos:

- `id` (Long): Identificador único do medicamento.
- `nome` (String): Nome do medicamento.
- `bula` (String): Informações da bula do medicamento.
- `tipo` (String): Tipo do medicamento (representado por um caractere).
- `tarja` (String): Tarja do medicamento (representado por um caractere).
- `principioAtivo` (String): Princípio ativo do medicamento.
- `observacao` (String): Observações adicionais sobre o medicamento.

### 2. **Repository**: `MedicamentoRepository`

A classe `MedicamentoRepository` é responsável por realizar as operações de banco de dados. Ela utiliza uma lista em memória para armazenar os dados dos medicamentos.

#### Métodos:

- `findAll()`: Retorna todos os medicamentos armazenados.
- `findById(Long id)`: Busca um medicamento pelo seu ID.
- `save(Medicamento medicamento)`: Salva ou atualiza um medicamento na lista.
- `deleteById(Long id)`: Remove um medicamento pelo seu ID.

### 3. **Controller**: `MedicamentoController`

A classe `MedicamentoController` gerencia as operações de entrada e saída, interagindo com o repositório para realizar as operações CRUD.

#### Métodos:

- `listarTodos()`: Retorna todos os medicamentos.
- `buscarPorId(Long id)`: Busca um medicamento pelo ID.
- `criar(Medicamento medicamento)`: Cria um novo medicamento.
- `atualizar(Long id, Medicamento medicamento)`: Atualiza um medicamento existente.
- `deletar(Long id)`: Remove um medicamento pelo ID.

### 4. **View**: `view_medicamento.json`

A view foi criada para representar um exemplo de estrutura de dados de um medicamento em formato JSON.

#### Exemplo de Conteúdo:

```json
{
  "medicamento_id": 1,
  "medicamento_nome": "Paracetamol",
  "medicamento_bula": "Analgésico e antitérmico.",
  "medicamento_tipo": "A",
  "medicamento_tarja": "V",
  "medicamento_principioativo": "Paracetamol",
  "medicamento_observacao": "Uso adulto e pediátrico."
}
```

## Como Executar

1. Certifique-se de que você possui o Java instalado.
2. Compile o projeto utilizando o comando:
   ```bash
   javac -d bin src/**/*.java
   ```
3. Execute a aplicação conforme necessário, instanciando o controlador e chamando os métodos diretamente no código.
