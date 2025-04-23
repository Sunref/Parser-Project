# CPRL Project - Parser

![Feito com](https://img.shields.io/badge/Feito%20com-Java-EC4134?labelColor=black&style=flat-square&logo=openjdk)
![Feito com](https://img.shields.io/badge/Feito%20com-Netbeans-0751CF?labelColor=black&style=flat-square&logo=apachenetbeanside)


_[Read in English](Readme.md)_

## Sobre

Este é um projeto atribuído por <a href="https://github.com/davidbuzatto">David Buzatto</a> e desenvolvido por <a href="https://github.com/Sunref">Sunref</a> e <a href="https://github.com/gm64x">gm64x</a> para a disciplina de Construção de Compiladores.

> [!NOTE]  
> Este repositório contém uma implementação de um compilador para CPRL (Compiler PRogramming Language) com foco nos conjuntos First e Follow, que são componentes essenciais para a construção de analisadores sintáticos.

## Estrutura do Projeto

```
.
├── examples               # Programas de exemplo em CPRL para testes
│   ├── Correct           # Programas CPRL corretos
│   │   ├── Arrays
│   │   ├── ArraysAndProcedures
│   │   ├── CPRL0
│   │   └── Subprograms
│   ├── Incorrect        # Programas CPRL incorretos para teste de erros
│   └── ScannerTests     # Testes específicos para o analisador léxico
├── ParserAnalyzer    # Código-fonte principal do projeto
    ├── lib              # Dependências da biblioteca
    ├── src              # Código-fonte
        ├── edu
            ├── citadel
                ├── compiler
                └── cprl   # Implementação do compilador CPRL
```

>[!TIP]
>Para executar este projeto em editores de código ou no Netbeans IDE, é necessário abrir o workbench no diretório `./ParserAnalyzer`. Se o seu editor de código suporta `tasks.json` ou `launch.json`, este repositório já está preparado para isso.

## Funcionalidades

- Implementação dos conjuntos First e Follow para a gramática CPRL
- Scanner para análise léxica
- Parser para análise sintática
- Gerenciamento de tabela de símbolos
- Conjunto abrangente de testes com programas CPRL corretos e incorretos

## Início Rápido

### Pré-requisitos

- Kit de Desenvolvimento Java (JDK) 8 ou superior
- Apache Ant (para compilação)

### Compilando o Projeto

```bash
cd ParserAnalyzer
ant build
```

### Executando Testes

```bash
ant test
```

## Componentes Principais

- **Scanner**: Realiza a análise léxica, convertendo código-fonte em tokens
- **Parser**: Analisa a estrutura sintática do programa
- **FirstFollowSets**: Implementa o cálculo dos conjuntos First e Follow para a gramática CPRL
- **Tabela de Símbolos**: Gerencia declarações de identificadores e seus escopos

## Licença

Este projeto está disponível sob a licença MIT - veja o arquivo LICENSE para detalhes.
