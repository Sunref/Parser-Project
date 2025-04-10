package edu.citadel.cprl;

import edu.citadel.compiler.ErrorHandler;
import edu.citadel.compiler.InternalCompilerException;
import edu.citadel.compiler.ParserException;
import edu.citadel.compiler.Position;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 *
 * Projeto Parser por Fernanda Martins e Gabriel Maia
 *
 * Projeto desenvolvido por Prof. Dr. David Buzatto
 *
 *
 */

/**
 * Essa classe implementa um analisador sintático descendente recursivo para
 * a linguagem de programação CPRL.
 *
 * Versão corrente.
 *
 * Detalhes e sugestão de roteiro para implementação:
 *
 * Nos diretórios examples\Incorrect\CPRL0, examples\Incorrect\Subprograms
 * e examples\Incorrect\Arrays existem diversos arquivos de teste que contém
 * o código fonte de programas com alguma incorretude do ponto de vista léxico,
 * sintático ou semântico.
 *
 * Para que o seu parser esteja correto, é necessário que a saída do mesmo seja
 * idêntica aos arquivos de resultados correspondentes aos arquivos de teste.
 *
 * Como estamos na fase de implementação do analisador sintático, os erros
 * léxicos e sintáticos deverão ser capturados e os problemas semânticos serão
 * ignorados. Haverá testes em que o código está correto do ponto de vista
 * léxico e sintático, mas não do ponto de vista semântico, o que será ignorado.
 *
 * Nesta classe serão seguidos os padrões apresentados nos slides da aula e você
 * deve seguir tais regras para a implementação. Não há necessidade de saber
 * neste momento quais são os conjuntos First e Follow.
 *
 * Implemente a classe seguindo os arquivos de teste, ordenadamente. Ou seja,
 * faça com que o parser analise corretamente o primeiro arquivo de teste, depois
 * o seguindo até o último (quinquagésimo nono).
 *
 * Para testar a corretude do parser, utilize primeiramente a classe TestParser,
 * contida no pacote de código fonte test.cprl. Esta classe será executada
 * automaticamente ao se rodar o projeto no NetBeans. Compare a saída gerada com
 * o arquivo de resultado correspondente. Quando notar que está correta, execute
 * o teste de unidade correspondente, contido no pacote de testes test.cprl.
 * Caso o teste passe, siga para a próxima implementação. Vários dos testes
 * passarão as vezes sem a necessidade de implementar algo, visto que o que será
 * testado pode já ter sido resolvido nas implementações prévias caso elas
 * tenham sido feito corretamente.
 *
 * Perceba que existem três grandes tipos de teste, os testes das estruturas das
 * linguagem (diretório CPRL0), os testes de subprogramas (diretório Subprograms)
 * e os testes dos tipos de arrays (diretório Arrays). Para cada um desses três
 * tipos, há uma classe de teste em lote, que executará o teste de todos os
 * arquivos de código de uma vez. Só passe para a implementação do analisador
 * para reconhecer a sintaxe de subprogramas quando o teste em lotes da sintaxe
 * da CPRL passar. De forma alguma não pule etapas!
 *
 * A implementação do parser poderá ser, para alguns de vocês, um grande desafio
 * tanto intelectual, tanto de técnicas de programação. Não se desesperem. Ao
 * notarem que o "fio da meada" foi perdido, desfaça o que foi feito e comece
 * de novo do ponto em que as coisas estavam funcionando.
 *
 * Vejam e revejam a implementação de exemplo do parser que será feita na
 * videoaula. Não mexam nos arquivos de teste!
 *
 * Boa sorte!
 *
 * Prof. Dr. David Buzatto
 *
 */
public class Parser {

    private Scanner scanner;
    private IdTable idTable;

    /**
     * Constrói um analisador sintático (parser) com um scanner especificado.
     */
    public Parser(Scanner scanner) {
        this.scanner = scanner;
        this.idTable = new IdTable();
    }

    /**
     * Analisa a regra gramatical abaixo:
     *
     * program = declarativePart statementPart "." .
     */
    public void parseProgram() throws IOException {
        try {
            parseDeclarativePart();
            parseStatementPart();
            match(Symbol.dot);
            match(Symbol.EOF);
        } catch (ParserException e) {
            ErrorHandler.getInstance().reportError(e);
            exit();
        }
    }

    /**
     * Analisa a regra gramatical abaixo:
     *
     * declarativePart = initialDecls subprogramDecls .
     */
    public void parseDeclarativePart() throws IOException {
        parseInitialDecls();
        parseSubprogramDecls();
    }

    /**
     * Analisa a regra gramatical abaixo:
     *
     * initialDecls = ( initialDecl )* .
     */
    public void parseInitialDecls() throws IOException {
        while (scanner.getSymbol().isInitialDeclStarter()) {
            parseInitialDecl();
        }
    }

    /**
     * Analisa a regra gramatical abaixo:
     *
     * initialDecl = constDecl | arrayTypeDecl | varDecl .
     */
    public void parseInitialDecl() throws IOException {
        /* Atenção!
         *
         * Deve-se lançar um internalError se o símbolo não for constRW, typeRW
         * nem varRW. Use a mensagem "Invalid initial decl.".
         */

        //Implementação por Fernanda
        if (scanner.getSymbol() == Symbol.constRW) {
            parseConstDecl();
        } else if (scanner.getSymbol() == Symbol.varRW) {
            parseVarDecl();
        } else if (scanner.getSymbol() == Symbol.typeRW) {
            parseArrayTypeDecl();
        } else {
            throw new InternalError("Invalid initial decl.");
        }
    }

    /**
     * Analisa a regra gramatical abaixo:
     *
     * constDecl = "const" constId ":=" literal ";" .
     */
    public void parseConstDecl() throws IOException {
        //Implementação por Fernanda
        try {
            match(Symbol.constRW);
            match(Symbol.identifier);
            match(Symbol.assign);
            parseLiteral();
            match(Symbol.semicolon);
        } catch (ParserException e) {
            ErrorHandler.getInstance().reportError(e);
            exit();
        }
    }

    /**
     * Analisa as regras gramaticais abaixo:
     *
     *        literal = intLiteral | charLiteral | stringLiteral | booleanLiteral .
     * booleanLiteral = "true" | "false" .
     */
    public void parseLiteral() throws IOException {
        try {
            if (scanner.getSymbol().isLiteral()) {
                matchCurrentSymbol();
            } else {
                throw error("Invalid literal expression.");
            }
        } catch (ParserException e) {
            ErrorHandler.getInstance().reportError(e);
            exit();
        }
    }

    /**
     * Analisa a regra gramatical abaixo:
     *
     * varDecl = "var" identifiers ":" typeName ";" .
     */
    public void parseVarDecl() throws IOException {
        try {
            match(Symbol.varRW);
            List<Token> identifiers = parseIdentifiers();
            match(Symbol.colon);
            parseTypeName();
            match(Symbol.semicolon);

            for (Token identifier : identifiers) {
                idTable.add(identifier, IdType.variableId);
            }
        } catch (ParserException e) {
            ErrorHandler.getInstance().reportError(e);
            exit();
        }
    }

    /**
     * Analisa a regra gramatical abaixo:
     *
     * identifiers = identifier ( "," identifier )* .
     *
     * @return uma lista de tokens do tipo identificador. Retorna uma lista
     * vazia caso a análise falhe.
     */
    public List<Token> parseIdentifiers() throws IOException {
        try {
            List<Token> identifiers = new ArrayList<>();
            Token idToken = scanner.getToken();
            match(Symbol.identifier);
            identifiers.add(idToken);

            while (scanner.getSymbol() == Symbol.comma) {
                matchCurrentSymbol();
                idToken = scanner.getToken();
                match(Symbol.identifier);
                identifiers.add(idToken);
            }

            return identifiers;
        } catch (ParserException e) {
            ErrorHandler.getInstance().reportError(e);
            exit();
            return Collections.emptyList(); // nunca deve chegar aqui...
        }
    }

    /**
     * Analisa a regra gramatical abaixo:
     *
     * arrayTypeDecl = "type" typeId "=" "array" "[" intConstValue "]" "of" typeName ";" .
     */
    public void parseArrayTypeDecl() throws IOException {
        //Implementação por Fernanda
        try {
            match(Symbol.typeRW);
            match(Symbol.equals);
            match(Symbol.arrayRW);
            match(Symbol.leftBracket);
            match(Symbol.IntegerRW);
            match(Symbol.rightBracket);
            match(Symbol.ofRW);
            match(Symbol.intLiteral);
            match(Symbol.semicolon);
        } catch (ParserException e) {
            ErrorHandler.getInstance().reportError(e);
            exit();
        }
    }

    /**
     * Analisa a regra gramatical abaixo:
     *
     * typeName = "Integer" | "Boolean" | "Char" | typeId .
     */
    public void parseTypeName() throws IOException {
        try {
            if (scanner.getSymbol() == Symbol.IntegerRW) {
                matchCurrentSymbol();
            } else if (scanner.getSymbol() == Symbol.BooleanRW) {
                matchCurrentSymbol();
            } else if (scanner.getSymbol() == Symbol.CharRW) {
                matchCurrentSymbol();
            } else if (scanner.getSymbol() == Symbol.identifier) {
                Token typeId = scanner.getToken();
                matchCurrentSymbol();
                IdType idType = idTable.get(typeId);

                if (idType != null) {
                    if (idType != IdType.arrayTypeId) {
                        throw error(
                            typeId.getPosition(),
                            "Identifier \"" +
                            typeId +
                            "\" is not a valid type name."
                        );
                    }
                } else {
                    throw error(
                        typeId.getPosition(),
                        "Identifier \"" + typeId + "\" has not been declared."
                    );
                }
            } else {
                throw error("Invalid type name.");
            }
        } catch (ParserException e) {
            ErrorHandler.getInstance().reportError(e);
            exit();
        }
    }

    /**
     * Analisa a regra gramatical abaixo:
     *
     * subprogramDecls = ( subprogramDecl )* .
     */
    public void parseSubprogramDecls() throws IOException {
        //Implementação por Fernanda
        while (scanner.getSymbol().isSubprogramDeclStarter()) {
            parseSubprogramDecl();
        }
    }

    /**
     * Analisa a regra gramatical abaixo:
     *
     * subprogramDecl = procedureDecl | functionDecl .
     */
    public void parseSubprogramDecl() throws IOException {
        /* Atenção!
         *
         * Deve-se lançar um internalError se o símbolo não for procedureRW nem
         * functionRW. Use a mensagem "Invalid subprogram decl.".
         */

        //Implementação por Fernanda
        if (scanner.getSymbol() == Symbol.procedureRW) {
            parseProcedureDecl();
        } else if (scanner.getSymbol() == Symbol.functionRW) {
            parseProcedureDecl();
        } else {
            throw new InternalError("Invalid subprogram decl.");
        }
    }

    /**
     * Analisa a regra gramatical abaixo:
     *
     * procedureDecl = "procedure" procId ( formalParameters )? "is" initialDecls statementPart procId ";" .
     */
    public void parseProcedureDecl() throws IOException {
        try {
            match(Symbol.procedureRW);
            Token procId = scanner.getToken();
            match(Symbol.identifier);
            idTable.add(procId, IdType.procedureId);
            idTable.openScope();

            if (scanner.getSymbol() == Symbol.leftParen) {
                parseFormalParameters();
            }

            match(Symbol.isRW);
            parseInitialDecls();
            parseStatementPart();
            idTable.closeScope();

            Token procId2 = scanner.getToken();
            match(Symbol.identifier);

            if (!procId.getText().equals(procId2.getText())) {
                throw error(procId2.getPosition(), "Procedure name mismatch.");
            }

            match(Symbol.semicolon);
        } catch (ParserException e) {
            ErrorHandler.getInstance().reportError(e);
            exit();
        }
    }

    /**
     * Analisa a regra gramatical abaixo:
     *
     * functionDecl = "function" funcId ( formalParameters )? "return" typeName "is" initialDecls statementPart funcId ";" .
     */
    public void parseFunctionDecl() throws IOException {
        // <editor-fold defaultstate="collapsed" desc="Implementação">

        // sua implementação aqui

        // </editor-fold>

    }

    /**
     * Analisa a regra gramatical abaixo:
     *
     * formalParameters = "(" parameterDecl ( "," parameterDecl )* ")" .
     */
    public void parseFormalParameters() throws IOException {
        // <editor-fold defaultstate="collapsed" desc="Implementação">

        // sua implementação aqui

        // </editor-fold>

    }

    /**
     * Analisa a regra gramatical abaixo:
     *
     * parameterDecl = ( "var" )? paramId ":" typeName .
     */
    public void parseParameterDecl() throws IOException {
        // <editor-fold defaultstate="collapsed" desc="Implementação">

        // sua implementação aqui

        // </editor-fold>

    }

    /**
     * Analisa a regra gramatical abaixo:
     *
     * statementPart = "begin" statements "end" .
     */
    public void parseStatementPart() throws IOException {
        try {
            match(Symbol.beginRW);
            parseStatements();
            match(Symbol.endRW);
        } catch (ParserException e) {
            ErrorHandler.getInstance().reportError(e);
            exit();
        }
    }

    /**
     * Analisa a regra gramatical abaixo:
     *
     * statements = ( statement )* .
     */
    public void parseStatements() throws IOException {
        //Implementação por Fernanda

        while (scanner.getSymbol().isStmtStarter()) {
            parseStatement();
        }
    }

    /**
     * Analisa a regra gramatical abaixo:
     *
     * statement = assignmentStmt | ifStmt | loopStmt | exitStmt | readStmt
     *           | writeStmt | writelnStmt | procedureCallStmt | returnStmt .
     */
    public void parseStatement() throws IOException {
        // assume que scanner.getSymbol() pode iniciar uma instrução
        assert scanner.getSymbol().isStmtStarter() : "Invalid statement.";

        /**
         * Como analisar um assignmentStmt e um procCallStmt dado que ambos
         * iniciam com um identificador?
         *
         * Dica: usar a tabela de identificadores.
         */

        //Implementação por Fernanda

        Symbol symbol = scanner.getSymbol();

        if (symbol == Symbol.identifier) {
            IdType idType = idTable.get(scanner.getToken());
            if (idType != null) {
                if (idType == IdType.variableId) {
                    parseAssignmentStmt();
                } else if (idType == IdType.procedureId) {
                    parseProcedureCallStmt();
                } else {
                    throw new InternalError("Invalid identifier type");
                }
            } else {
                throw new InternalError("Identifier not found");
            }
        } else if (symbol == Symbol.ifRW) {
            parseIfStmt();
        } else if (symbol == Symbol.loopRW || symbol == Symbol.whileRW) {
            parseLoopStmt();
        } else if (symbol == Symbol.exitRW) {
            parseExitStmt();
        }
    }

    /**
     * Analisa a regra gramatical abaixo:
     *
     * assignmentStmt = variable ":=" expression ";" .
     */
    public void parseAssignmentStmt() throws IOException {
        //Implementação por Fernanda
        try {
            parseVariable();
            match(Symbol.assign);
            parseExpression();
            match(Symbol.semicolon);
        } catch (ParserException e) {
            ErrorHandler.getInstance().reportError(e);
            exit();
        }
    }

    /**
     * Analisa a regra gramatical abaixo:
     *
     * ifStmt = "if" booleanExpr "then" statements
     *          ( "elsif" booleanExpr "then" statements )*
     *          ( "else" statements )? "end" "if" ";" .
     */
    public void parseIfStmt() throws IOException {
        //Implementação por Fernanda
        try {
            match(Symbol.ifRW);
            parseExpression();
            match(Symbol.thenRW);
            parseStatements();

            while (scanner.getSymbol() == Symbol.elsifRW) {
                matchCurrentSymbol();
                parseExpression();
                match(Symbol.thenRW);
                parseStatements();
            }

            if (scanner.getSymbol() == Symbol.elseRW) {
                matchCurrentSymbol();
                parseStatements();
            }

            match(Symbol.andRW);
            match(Symbol.ifRW);
            match(Symbol.semicolon);
        } catch (ParserException e) {
            ErrorHandler.getInstance().reportError(e);
            exit();
        }
    }

    /**
     * Analisa a regra gramatical abaixo:
     *
     * loopStmt = ( "while" booleanExpr )? "loop" statements "end" "loop" ";" .
     */
    public void parseLoopStmt() throws IOException {
        //Implementação por Fernanda
        try {
            if (scanner.getSymbol() == Symbol.whileRW) {
                matchCurrentSymbol();
                parseExpression();
            }

            match(Symbol.loopRW);
            parseStatements();
            match(Symbol.endRW);
            match(Symbol.loopRW);
            match(Symbol.semicolon);
        } catch (ParserException e) {
            ErrorHandler.getInstance().reportError(e);
            exit();
        }
    }

    /**
     * Analisa a regra gramatical abaixo:
     *
     * exitStmt = "exit" ( "when" booleanExpr )? ";" .
     */
    public void parseExitStmt() throws IOException {
        //Implementação por Fernanda
        try {
            match(Symbol.endRW);

            if (scanner.getSymbol() == Symbol.whenRW) {
                parseExpression();
            }

            match(Symbol.semicolon);
        } catch (ParserException e) {
            ErrorHandler.getInstance().reportError(e);
            exit();
        }
    }

    /**
     * Analisa a regra gramatical abaixo:
     *
     * readStmt = "read" variable ";" .
     */
    public void parseReadStmt() throws IOException {
        // <editor-fold defaultstate="collapsed" desc="Implementação">

        // sua implementação aqui

        // </editor-fold>

    }

    /**
     * Analisa a regra gramatical abaixo:
     *
     * writeStmt = "write" expressions ";" .
     */
    public void parseWriteStmt() throws IOException {
        // <editor-fold defaultstate="collapsed" desc="Implementação">

        // sua implementação aqui

        // </editor-fold>

    }

    /**
     * Analisa a regra gramatical abaixo:
     *
     * expressions = expression ( "," expression )* .
     */
    public void parseExpressions() throws IOException {
        // <editor-fold defaultstate="collapsed" desc="Implementação">

        // sua implementação aqui

        // </editor-fold>

    }

    /**
     * Analisa a regra gramatical abaixo:
     *
     * writelnStmt = "writeln" ( expressions )? ";" .
     */
    public void parseWritelnStmt() throws IOException {
        try {
            match(Symbol.writelnRW);

            if (scanner.getSymbol().isExprStarter()) {
                parseExpressions();
            }

            match(Symbol.semicolon);
        } catch (ParserException e) {
            ErrorHandler.getInstance().reportError(e);
            exit();
        }
    }

    /**
     * Analisa a regra gramatical abaixo:
     *
     * procedureCallStmt = procId ( actualParameters )? ";" .
     */
    public void parseProcedureCallStmt() throws IOException {
        // <editor-fold defaultstate="collapsed" desc="Implementação">

        // sua implementação aqui

        // </editor-fold>

    }

    /**
     * Analisa a regra gramatical abaixo:
     *
     * actualParameters = "(" expressions ")" .
     */
    public void parseActualParameters() throws IOException {
        // <editor-fold defaultstate="collapsed" desc="Implementação">

        // sua implementação aqui

        // </editor-fold>

    }

    /**
     * Analisa a regra gramatical abaixo:
     *
     * returnStmt = "return" ( expression )? ";" .
     */
    public void parseReturnStmt() throws IOException {
        // <editor-fold defaultstate="collapsed" desc="Implementação">

        // sua implementação aqui

        // </editor-fold>

    }

    /**
     * Analisa a regra gramatical abaixo:
     *
     * variable = ( varId | paramId ) ( "[" expression "]" )* .
     *
     * Esse método auxiliar provê uma lógica comum aos métodos parseVariable() e
     * parseNamedValue(). Esse método não lida com quaisquer exceções geradas
     * pelo parser (ParseException), lançando-as ao método chamador para que
     * possam ser manipuladas apropriadamente.
     *
     * @throws ParserException se a análise falhar.
     * @see #parseVariable()
     * @see #parseNamedValue()
     */
    public void parseVariableExpr() throws IOException, ParserException {
        Token idToken = scanner.getToken();
        match(Symbol.identifier);
        IdType idType = idTable.get(idToken);

        if (idType == null) {
            String errorMsg =
                "Identifier \"" + idToken + "\" has not been declared.";
            throw error(idToken.getPosition(), errorMsg);
        } else if (idType != IdType.variableId) {
            String errorMsg =
                "Identifier \"" + idToken + "\" is not a variable.";
            throw error(idToken.getPosition(), errorMsg);
        }

        while (scanner.getSymbol() == Symbol.leftBracket) {
            matchCurrentSymbol();
            parseExpression();
            match(Symbol.rightBracket);
        }
    }

    /**
     * Analisa a regra gramatical abaixo:
     *
     * variable = ( varId | paramId ) ( "[" expression "]" )* .
     */
    public void parseVariable() throws IOException {
        try {
            parseVariableExpr();
        } catch (ParserException e) {
            ErrorHandler.getInstance().reportError(e);
            exit();
        }
    }

    /**
     * Analisa as regras gramaticais abaixo:
     *
     * expression = relation ( logicalOp relation )* .
     *  logicalOp = "and" | "or" .
     */
    public void parseExpression() throws IOException {
        parseRelation();

        while (scanner.getSymbol().isLogicalOperator()) {
            matchCurrentSymbol();
            parseRelation();
        }
    }

    /**
     * Analisa as regras gramaticais abaixo:
     *
     *     relation = simpleExpr ( relationalOp simpleExpr )? .
     * relationalOp = "=" | "!=" | "<" | "<=" | ">" | ">=" .
     */
    public void parseRelation() throws IOException {
        //Implementação por Fernanda
        try {
            parseSimpleExpr();
            //preciso ver o que está errado aqui - fernandita
            if (scanner.getSymbol().isRelationalOp) {
                matchCurrentSymbol();
                parseSimpleExpr();
            }
        } catch (ParserException e) {
            ErrorHandler.getInstance().reportError(e);
            exit();
        }
    }

    /**
     * Analisa as regras gramaticais abaixo:
     *
     * simpleExpr = ( addingOp )? term ( addingOp term )* .
     *   addingOp = "+" | "-" .
     */
    public void parseSimpleExpr() throws IOException {
        // <editor-fold defaultstate="collapsed" desc="Implementação">

        // sua implementação aqui

        // </editor-fold>

    }

    /**
     * Analisa as regras gramaticais abaixo:
     *
     *          term = factor ( multiplyingOp factor )* .
     * multiplyingOp = "*" | "/" | "mod" .
     */
    public void parseTerm() throws IOException {
        // <editor-fold defaultstate="collapsed" desc="Implementação">

        // sua implementação aqui

        // </editor-fold>

    }

    /**
     * Analisa a regra gramatical abaixo:
     *
     * factor = "not" factor | constValue | namedValue | functionCall
     *        | "(" expression ")" .
     */
    public void parseFactor() throws IOException {
        try {
            if (scanner.getSymbol() == Symbol.notRW) {
                matchCurrentSymbol();
                parseFactor();
            } else if (scanner.getSymbol().isLiteral()) {
                // lida com literais de constantes separadamente dos
                // identificadores de constantes
                parseConstValue();
            } else if (scanner.getSymbol() == Symbol.identifier) {
                // lida com os identificadores baseando-se se eles foram
                // declarados como variáveis, constantes ou funções.
                Token idToken = scanner.getToken();
                IdType idType = idTable.get(idToken);

                if (idType != null) {
                    if (idType == IdType.constantId) {
                        parseConstValue();
                    } else if (idType == IdType.variableId) {
                        parseNamedValue();
                    } else if (idType == IdType.functionId) {
                        parseFunctionCall();
                    } else {
                        throw error(
                            "Identifier \"" +
                            scanner.getToken() +
                            "\" is not valid as an expression."
                        );
                    }
                } else {
                    throw error(
                        "Identifier \"" +
                        scanner.getToken() +
                        "\" has not been declared."
                    );
                }
            } else if (scanner.getSymbol() == Symbol.leftParen) {
                matchCurrentSymbol();
                parseExpression();
                match(Symbol.rightParen);
            } else {
                throw error("Invalid expression.");
            }
        } catch (ParserException e) {
            ErrorHandler.getInstance().reportError(e);
            exit();
        }
    }

    /**
     * Analisa a regra gramatical abaixo:
     *
     * constValue = literal | constId .
     */
    public void parseConstValue() throws IOException {
        // <editor-fold defaultstate="collapsed" desc="Implementação">

        // sua implementação aqui

        // </editor-fold>

    }

    /**
     * Analisa a regra gramatical abaixo:
     *
     * namedValue = variable .
     */
    public void parseNamedValue() throws IOException {
        try {
            parseVariableExpr();
        } catch (ParserException e) {
            ErrorHandler.getInstance().reportError(e);
            exit();
        }
    }

    /**
     * Analisa a regra gramatical abaixo:
     *
     * functionCall = funcId ( actualParameters )? .
     */
    public void parseFunctionCall() throws IOException {
        // <editor-fold defaultstate="collapsed" desc="Implementação">

        // sua implementação aqui

        // </editor-fold>

    }

    /***************************************************************************
     *                     Métodos utilitários de análise                      *
     **************************************************************************/

    /**
     * Verifica se o símbolo atual do scanner é o símbolo esperado. Se for,
     * avança o scanner. Caso contrário, lança uma ParserException.
     */
    private void match(Symbol expectedSymbol)
        throws IOException, ParserException {
        if (scanner.getSymbol() == expectedSymbol) {
            scanner.advance();
        } else {
            String errorMsg =
                "Expecting \"" +
                expectedSymbol +
                "\" but found \"" +
                scanner.getToken() +
                "\" instead.";
            throw error(errorMsg);
        }
    }

    /**
     * Avança o scanner. Esse método representa uma correspondência
     * incondicional com o símbolo atual do scanner.
     */
    private void matchCurrentSymbol() throws IOException {
        scanner.advance();
    }

    /**
     * Cria uma ParserException com a mensagem especificada e a posição corrente
     * do scanner.
     */
    private ParserException error(String errMsg) {
        return new ParserException(scanner.getPosition(), errMsg);
    }

    /**
     * Cria uma ParserException especificando a posição atual do scanner e a
     * mensagem do erro.
     */
    private ParserException error(Position errPos, String errMsg) {
        return new ParserException(errPos, errMsg);
    }

    /**
     * Cria uma InternalCompilerException especificando a posição atual do
     * scanner e a mensagem do erro.
     */
    private InternalCompilerException internalError(String message) {
        return new InternalCompilerException(scanner.getPosition(), message);
    }

    /**
     * Imprime uma mensagem de erro e termina o processo de compilação.
     *
     * Atenção!
     *
     * Da forma que a finalização foi implementada, ou seja, finalizando a JVM
     * com um erro, os testes falharão, pois não haverá tempo do JUnit testar
     * o que foi gerado antes da JVM ser finalizada.
     *
     * Verifique abaixo outras instruções.
     */
    private void exit() throws IllegalStateException {
        System.out.println("Errors detected -- parsing terminated.");

        /* Na versão final do Parser V1, a seção e código abaixo deve ser
         * descomentada, pois em caso de geração de erros de análise
         * a JVM deve ser finalizada.
         */
        /*{
            System.out.println();
            System.exit( -1 );
        }*/

        /* Na versão de implementação do parser, a seção e código abaixo deve
         * descomentada, pois em caso de geração de erros de análise para os
         * testes a JVM não pode ser finalizada (motivos acima).
         *
         * Para conhecimento, o bloco abaixo faz o scanner consumir todos os
         * tokens ser disponíveis e então lança uma exceção do tipo
         * IllegalStateException que será capturada pelo método
         * TestUtils.executar.
         */
        {
            try {
                while (scanner.getSymbol() != Symbol.EOF) {
                    scanner.advance();
                }
                throw new IllegalStateException("finished");
            } catch (IOException exc) {
                exc.printStackTrace();
            }
        }
    }
}
