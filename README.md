# Sistema de Gerenciamento de Funcionários

## Descrição
Sistema desenvolvido para gerenciamento de dados de funcionários, permitindo o cadastro, consulta, atualização e geração de relatórios. A aplicação oferece funcionalidades como cálculo de salários, identificação de aniversariantes, agrupamento por função e análises estatísticas.

## Funcionalidades Principais
- Cadastro completo de funcionários
- Cálculo de idade e tempo de serviço
- Aumento salarial com percentual configurável
- Relatórios personalizados
- Cálculo de salários em relação ao salário mínimo
- Identificação de aniversariantes por mês
- Agrupamento de funcionários por função

## Tecnologias Utilizadas
- Java 17
- JUnit 5
- Mockito
- Maven

## Requisitos do Sistema
- JDK 17 ou superior
- Maven 3.6.3 ou superior

## Instalação e Execução

### Clonando o Repositório
```bash
git clone https://github.com/seu-usuario/sistema-gerenciamento-funcionarios.git
cd sistema-gerenciamento-funcionarios
```

### Compilando o Projeto
```bash
mvn clean install
```

### Executando a Aplicação
```bash
mvn exec:java -Dexec.mainClass="com.br.industria.Main"
```

### Executando os Testes
```bash
mvn test
```

## Documentação da API

### Classe Funcionario
Representa a entidade principal do sistema.

#### Atributos:
- `nome`: Nome do funcionário
- `dataNascimento`: Data de nascimento
- `salario`: Valor do salário
- `funcao`: Cargo/função do funcionário

### FuncionarioService
Serviço que gerencia as operações relacionadas aos funcionários.

#### Métodos Principais:
- `listarTodos()`: Retorna todos os funcionários cadastrados
- `calcularIdade(Funcionario)`: Calcula a idade do funcionário
- `calcularTotalSalarios()`: Calcula o total de salários de todos os funcionários
- `calcularSalariosMinimos(Funcionario)`: Calcula quantos salários mínimos um funcionário recebe

### RelatorioService
Serviço responsável pela geração de relatórios.

#### Métodos Principais:
- `imprimirListaFuncionarios(List<Funcionario>)`: Exibe a lista de funcionários
- `imprimirFuncionariosPorFuncao(Map<String, List<Funcionario>>)`: Exibe funcionários agrupados por função
- `imprimirAniversariantes(List<Funcionario>, int...)`: Exibe aniversariantes dos meses especificados
- `imprimirTotalSalarios()`: Exibe o total de salários da empresa
