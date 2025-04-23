package edu.citadel.cprl;

import edu.citadel.compiler.Position;
import edu.citadel.compiler.ParserException;
import edu.citadel.compiler.InternalCompilerException;
import edu.citadel.compiler.ErrorHandler;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.io.IOException;
import static edu.citadel.cprl.FirstFollowSets.*;

/**
 * This class uses recursive descent to perform syntax analysis of the CPRL
 * source language.
 */
public class Parser {

    private Scanner scanner;
    private IdTable idTable;

    /**
     * Constrói um analisador sintático (parser) com um scanner especificado.
     */
    public Parser( Scanner scanner ) {
        
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
            match( Symbol.dot );
            match( Symbol.EOF );
            
        } catch ( ParserException e ) {
            ErrorHandler.getInstance().reportError( e );
            recover( FOLLOW_SETS.get( "program" ) );
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
        
        while ( scanner.getSymbol().isInitialDeclStarter() ) {
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
                    
        if ( scanner.getSymbol() == Symbol.constRW ) {
            parseConstDecl();
        } else if ( scanner.getSymbol() == Symbol.typeRW ) {
            parseArrayTypeDecl();
        } else if ( scanner.getSymbol() == Symbol.varRW ) {
            parseVarDecl();
        } else {
            throw internalError( "Invalid initial decl." );
        }
        
    }

    /**
     * Analisa a regra gramatical abaixo:
     * 
     * constDecl = "const" constId ":=" literal ";" .
     */
    public void parseConstDecl() throws IOException {
                 
        try {
            
            match( Symbol.constRW );
            
            if ( scanner.getSymbol() == Symbol.identifier ) {
                idTable.add( scanner.getToken(), IdType.constantId );
                match( Symbol.identifier );
            }
            
            match( Symbol.assign );
            
            if ( scanner.getSymbol().isLiteral() ) {
                matchCurrentSymbol();
            } else {
                throw error( "Invalid literal expression." );
            }
            
            match( Symbol.semicolon );
            
        } catch ( ParserException e ) {
            ErrorHandler.getInstance().reportError( e );
            recover( FOLLOW_SETS.get( "constDecl" ) );
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
            
            if ( scanner.getSymbol().isLiteral() ) {
                matchCurrentSymbol();
            } else {
                throw error( "Invalid literal expression." );
            }
            
        } catch ( ParserException e ) {
            ErrorHandler.getInstance().reportError( e );
            recover( FOLLOW_SETS.get( "literal" ) );
        }
        
    }

    /**
     * Analisa a regra gramatical abaixo:
     * 
     * varDecl = "var" identifiers ":" typeName ";" .
     */
    public void parseVarDecl() throws IOException {
        
        try {
            
            match( Symbol.varRW );
            List<Token> identifiers = parseIdentifiers();
            match( Symbol.colon );
            parseTypeName();
            match( Symbol.semicolon );

            for ( Token identifier : identifiers ) {
                idTable.add( identifier, IdType.variableId );
            }
            
        } catch ( ParserException e ) {
            ErrorHandler.getInstance().reportError( e );
            recover( FOLLOW_SETS.get( "varDecl" ) );
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
            match( Symbol.identifier );
            identifiers.add( idToken );

            while ( scanner.getSymbol() == Symbol.comma ) {
                matchCurrentSymbol();
                idToken = scanner.getToken();
                match( Symbol.identifier );
                identifiers.add( idToken );
            }

            return identifiers;
            
        } catch ( ParserException e ) {
            ErrorHandler.getInstance().reportError( e );
            recover( FOLLOW_SETS.get( "identifiers" ) );
            return Collections.emptyList();
        }
        
    }

    /**
     * Analisa a regra gramatical abaixo:
     * 
     * arrayTypeDecl = "type" typeId "=" "array" "[" intConstValue "]" "of" typeName ";" .
     */
    public void parseArrayTypeDecl() throws IOException {
        
        try {
            
            match( Symbol.typeRW );
            
            Token typeId = scanner.getToken();
            match( Symbol.identifier );
            idTable.add( typeId, IdType.arrayTypeId );
            
            match( Symbol.equals );
            match( Symbol.arrayRW );
            match( Symbol.leftBracket );
            
            if ( scanner.getSymbol() == Symbol.intLiteral ) {
                matchCurrentSymbol();
            } else {
                throw error( "Invalid constant." );
            }
            
            match( Symbol.rightBracket );
            match( Symbol.ofRW );
            parseTypeName();
            match( Symbol.semicolon );
            
        } catch ( ParserException e ) {
            ErrorHandler.getInstance().reportError( e );
            recover( FOLLOW_SETS.get( "arrayTypeDecl" ) );
        }
        
    }

    /**
     * Analisa a regra gramatical abaixo:
     * 
     * typeName = "Integer" | "Boolean" | "Char" | typeId .
     */
    public void parseTypeName() throws IOException {
        
        try {
            
            if ( scanner.getSymbol() == Symbol.IntegerRW ) {
                matchCurrentSymbol();
            } else if ( scanner.getSymbol() == Symbol.BooleanRW ) {
                matchCurrentSymbol();
            } else if ( scanner.getSymbol() == Symbol.CharRW ) {
                matchCurrentSymbol();
            } else if ( scanner.getSymbol() == Symbol.identifier ) {
                
                Token typeId = scanner.getToken();
                matchCurrentSymbol();
                IdType idType = idTable.get( typeId );

                if ( idType != null ) {
                    if ( idType != IdType.arrayTypeId ) {
                        throw error( typeId.getPosition(), "Identifier \"" +
                                     typeId + "\" is not a valid type name." );
                    }
                } else {
                    throw error( typeId.getPosition(), "Identifier \"" +
                                 typeId + "\" has not been declared." );
                }
                
            } else {
                throw error( "Invalid type name." );
            }
            
        } catch ( ParserException e ) {
            ErrorHandler.getInstance().reportError( e );
            recover( FOLLOW_SETS.get( "typeName" ) );
        }
        
    }

    /**
     * Analisa a regra gramatical abaixo:
     * 
     * subprogramDecls = ( subprogramDecl )* .
     */
    public void parseSubprogramDecls() throws IOException {
        
        while ( scanner.getSymbol().isSubprogramDeclStarter() ) {
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
                    
        if ( scanner.getSymbol() == Symbol.procedureRW ) {
            parseProcedureDecl();
        } else if ( scanner.getSymbol() == Symbol.functionRW ) {
            parseFunctionDecl();
        } else {
            throw internalError( "Invalid subprogram decl." );
        }
        
    }

    /**
     * Analisa a regra gramatical abaixo:
     * 
     * procedureDecl = "procedure" procId ( formalParameters )? "is" initialDecls statementPart procId ";" .
     */
    public void parseProcedureDecl() throws IOException {
        
        try {
            
            match( Symbol.procedureRW );
            Token procId = scanner.getToken();
            match( Symbol.identifier );
            idTable.add( procId, IdType.procedureId );
            idTable.openScope();

            if ( scanner.getSymbol() == Symbol.leftParen ) {
                parseFormalParameters();
            }

            match( Symbol.isRW );
            parseInitialDecls();
            parseStatementPart();
            idTable.closeScope();

            Token procId2 = scanner.getToken();
            match( Symbol.identifier );

            if ( !procId.getText().equals( procId2.getText() ) ) {
                throw error( procId2.getPosition(), "Procedure name mismatch." );
            }

            match( Symbol.semicolon );
            
        } catch ( ParserException e ) {
            ErrorHandler.getInstance().reportError( e );
            recover( FOLLOW_SETS.get( "procedureDecl" ) );
        }
        
    }

    /**
     * Analisa a regra gramatical abaixo:
     * 
     * functionDecl = "function" funcId ( formalParameters )? "return" typeName "is" initialDecls statementPart funcId ";" .
     */
    public void parseFunctionDecl() throws IOException {
        
        try {
            
            match( Symbol.functionRW );
            Token funcId = scanner.getToken();
            match( Symbol.identifier );
            idTable.add( funcId, IdType.functionId );
            idTable.openScope();
            
            if ( scanner.getSymbol() == Symbol.leftParen ) {
                parseFormalParameters();
            }
            
            match( Symbol.returnRW );
            
            parseTypeName();
            
            match( Symbol.isRW );
            parseInitialDecls();
            parseStatementPart();
            idTable.closeScope();

            Token procId2 = scanner.getToken();
            match( Symbol.identifier );

            if ( !funcId.getText().equals( procId2.getText() ) ) {
                throw error( procId2.getPosition(), "Function name mismatch." );
            }

            match( Symbol.semicolon );
            
        } catch ( ParserException e ) {
            ErrorHandler.getInstance().reportError( e );
            recover( FOLLOW_SETS.get( "functionDecl" ) );
        }
        
    }

    /**
     * Analisa a regra gramatical abaixo:
     * 
     * formalParameters = "(" parameterDecl ( "," parameterDecl )* ")" .
     */
    public void parseFormalParameters() throws IOException {
        
        try {
            
            match( Symbol.leftParen );
            
            parseParameterDecl();
            
            while ( scanner.getSymbol() == Symbol.comma ) {
                matchCurrentSymbol();
                parseParameterDecl();
            }
            
            match( Symbol.rightParen );
            
        } catch ( ParserException e ) {
            ErrorHandler.getInstance().reportError( e );
            recover( FOLLOW_SETS.get( "formalParameters" ) );
        }
        
    }

    /**
     * Analisa a regra gramatical abaixo:
     * 
     * parameterDecl = ( "var" )? paramId ":" typeName .
     */
    public void parseParameterDecl() throws IOException {
        
        try {
            
            if ( scanner.getSymbol() == Symbol.varRW ) {
                matchCurrentSymbol();
            }
            
            Token paramId = scanner.getToken();
            match( Symbol.identifier );
            idTable.add( paramId, IdType.variableId );
            
            match( Symbol.colon );
            
            parseTypeName();
            
        } catch ( ParserException e ) {
            ErrorHandler.getInstance().reportError( e );
            recover( FOLLOW_SETS.get( "parameterDecl" ) );
        }
        
    }

    /**
     * Analisa a regra gramatical abaixo:
     * 
     * statementPart = "begin" statements "end" .
     */
    public void parseStatementPart() throws IOException {
        
        try {
            match( Symbol.beginRW );
            parseStatements();
            match( Symbol.endRW );
        } catch ( ParserException e ) {
            ErrorHandler.getInstance().reportError( e );
            recover( FOLLOW_SETS.get( "statementPart" ) );
        }
        
    }

    /**
     * Analisa a regra gramatical abaixo:
     * 
     * statements = ( statement )* .
     */
    public void parseStatements() throws IOException {
        
        while ( scanner.getSymbol().isStmtStarter() ) {
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
        
        /**
         * A recuperação de erros para o método parseStatement() requere atenção
         * especial quando o símbolo é um identificador, visto que um
         * identificador pode não somente iniciar uma instrução, mas também pode
         * aparecer em qualquer lugar desse tipo de construção.
         * 
         * Considere, por exemplo, uma instrução de atribuição ou uma instrução
         * de chamada de procedimento. Se avançarmos a um identificador, podemos
         * estar no meio de uma instrução ao invés de estar no início da próxima
         * instrução.
         * 
         * Dado que a maioria dos erros relacionados aos identificadores
         * estão relacionados à declarar ou referenciar um identificador de
         * forma incorreta, assumiremos que esse é o caso e avançaremos ao
         * próximo ponto e vírgula antes de implementar a recuperação de erros.
         * 
         * O objetivo é que avançando ao próximo ponto e vírgula, com sorte,
         * moveremos o scanner até o fim da instrução que contém o erro.
         */
        
        try {
            
            if ( scanner.getSymbol() == Symbol.exitRW ) {
                parseExitStmt();
            } else if ( scanner.getSymbol() == Symbol.identifier ) {
                
                IdType idType = idTable.get( scanner.getToken() );
                
                if ( idType == null ) {
                    String errorMsg = "Identifier \"" + scanner.getToken() + 
                                      "\" has not been declared.";
                    throw error( scanner.getToken().getPosition(), errorMsg );
                } else if ( idType == IdType.variableId ) {
                    parseAssignmentStmt();
                } else if ( idType == IdType.procedureId ) {
                    parseProcedureCallStmt();
                } else if ( idType == IdType.constantId ) {
                    String errorMsg = "Identifier \"" + scanner.getToken() + 
                                      "\" cannot start a statement.";
                    throw error( scanner.getToken().getPosition(), errorMsg );
                }

            } else if ( scanner.getSymbol() == Symbol.ifRW ) {
                parseIfStmt();
            } else if ( scanner.getSymbol() == Symbol.loopRW ) {
                parseLoopStmt();
            } else if ( scanner.getSymbol() == Symbol.whileRW ) {
                parseLoopStmt();
            } else if ( scanner.getSymbol() == Symbol.readRW ) {
                parseReadStmt();
            } else if ( scanner.getSymbol() == Symbol.writeRW ) {
                parseWriteStmt();
            } else if ( scanner.getSymbol() == Symbol.writelnRW ) {
                parseWritelnStmt();
            } else if ( scanner.getSymbol() == Symbol.returnRW ) {
                parseReturnStmt();
            } else {
                throw internalError( "Invalid statement." );
            }
        
        } catch ( ParserException e ) {
            ErrorHandler.getInstance().reportError( e );
            scanner.advanceTo( Symbol.semicolon );
            recover( FOLLOW_SETS.get( "statement" ) );
        }

    }

    /**
     * Analisa a regra gramatical abaixo:
     * 
     * assignmentStmt = variable ":=" expression ";" .
     */
    public void parseAssignmentStmt() throws IOException {
        
        try {
            
            parseVariable();
            
            try {
                
                match( Symbol.assign );
                
            } catch ( ParserException e ) {
                
                if ( scanner.getSymbol() == Symbol.equals ) {
                    ErrorHandler.getInstance().reportError( e );
                    matchCurrentSymbol();  // tratar "=" como ":="
                                           // nesse contexto
                } else {
                    throw e;
                }
                
            }
            
            parseExpression();
            match( Symbol.semicolon );
            
        } catch ( ParserException e ) {
            ErrorHandler.getInstance().reportError( e );
            recover( FOLLOW_SETS.get( "assignmentStmt" ) );
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
        
        try {
            
            match( Symbol.ifRW );
            parseExpression();
            match( Symbol.thenRW );
            
            parseStatements();
            
            while ( scanner.getSymbol() == Symbol.elsifRW ) {
                match( Symbol.elsifRW );
                parseExpression();
                match( Symbol.thenRW );
                parseStatements();
            }
            
            if ( scanner.getSymbol() == Symbol.elseRW ) {
                match( Symbol.elseRW );
                parseStatements();
            }
        
            match( Symbol.endRW );
            match( Symbol.ifRW );
            match( Symbol.semicolon );
            
        } catch ( ParserException e ) {
            ErrorHandler.getInstance().reportError( e );
            recover( FOLLOW_SETS.get( "ifStmt" ) );
        }
        
    }

    /**
     * Analisa a regra gramatical abaixo:
     * 
     * loopStmt = ( "while" booleanExpr )? "loop" statements "end" "loop" ";" .
     */
    public void parseLoopStmt() throws IOException {
        
        try {
            
            if ( scanner.getSymbol() == Symbol.whileRW ) {
                matchCurrentSymbol();
                parseExpression();
            }

            match( Symbol.loopRW );
            parseStatements();

            match( Symbol.endRW );
            match( Symbol.loopRW );
            match( Symbol.semicolon );
        
        } catch ( ParserException e ) {
            ErrorHandler.getInstance().reportError( e );
            recover( FOLLOW_SETS.get( "loopStmt" ) );
        }
        
    }

    /**
     * Analisa a regra gramatical abaixo:
     * 
     * exitStmt = "exit" ( "when" booleanExpr )? ";" .
     */
    public void parseExitStmt() throws IOException {
        
        try {
            
            match( Symbol.exitRW );
            
            if ( scanner.getSymbol() == Symbol.whenRW ) {
                matchCurrentSymbol();
                parseExpression();
            }
            
            match( Symbol.semicolon );
            
        } catch ( ParserException e ) {
            ErrorHandler.getInstance().reportError( e );
            recover( FOLLOW_SETS.get( "exitStmt" ) );
        }
        
    }

    /**
     * Analisa a regra gramatical abaixo:
     * 
     * readStmt = "read" variable ";" .
     */
    public void parseReadStmt() throws IOException {
        
        try {
            
            match( Symbol.readRW );
            parseVariableExpr();
            match( Symbol.semicolon );
            
        } catch ( ParserException e ) {
            ErrorHandler.getInstance().reportError( e );
            recover( FOLLOW_SETS.get( "readStmt" ) );
        }
        
    }

    /**
     * Analisa a regra gramatical abaixo:
     * 
     * writeStmt = "write" expressions ";" .
     */
    public void parseWriteStmt() throws IOException {
        
        try {
            
            match( Symbol.writeRW );
            parseExpressions();
            match( Symbol.semicolon );
            
        } catch ( ParserException e ) {
            ErrorHandler.getInstance().reportError( e );
            recover( FOLLOW_SETS.get( "writeStmt" ) );
        }
        
    }

    /**
     * Analisa a regra gramatical abaixo:
     * 
     * expressions = expression ( "," expression )* .
     */
    public void parseExpressions() throws IOException {
        
        parseExpression();
        
        while ( scanner.getSymbol() == Symbol.comma ) {
            matchCurrentSymbol();
            parseExpression();
        }
        
    }

    /**
     * Analisa a regra gramatical abaixo:
     * 
     * writelnStmt = "writeln" ( expressions )? ";" .
     */
    public void parseWritelnStmt() throws IOException {
        
        try {
            
            match( Symbol.writelnRW );

            if ( scanner.getSymbol().isExprStarter() ) {
                parseExpressions();
            }
            
            match( Symbol.semicolon );
            
        } catch ( ParserException e ) {
            ErrorHandler.getInstance().reportError( e );
            recover( FOLLOW_SETS.get( "writelnStmt" ) );
        }
        
    }

    /**
     * Analisa a regra gramatical abaixo:
     * 
     * procedureCallStmt = procId ( actualParameters )? ";" .
     */
    public void parseProcedureCallStmt() throws IOException {
        
        try {
            
            match( Symbol.identifier );
            
            if ( scanner.getSymbol().isExprStarter() ) {
                parseActualParameters();
            }
            
            match( Symbol.semicolon );
            
        } catch ( ParserException e ) {
            ErrorHandler.getInstance().reportError( e );
            recover( FOLLOW_SETS.get( "procedureCallStmt" ) );
        }
        
    }

    /**
     * Analisa a regra gramatical abaixo:
     * 
     * actualParameters = "(" expressions ")" .
     */
    public void parseActualParameters() throws IOException {
        
        try {
            
            match( Symbol.leftParen );
            parseExpressions();
            match( Symbol.rightParen );
            
        } catch ( ParserException e ) {
            ErrorHandler.getInstance().reportError( e );
            recover( FOLLOW_SETS.get( "actualParameters" ) );
        }
        
    }

    /**
     * Analisa a regra gramatical abaixo:
     * 
     * returnStmt = "return" ( expression )? ";" .
     */
    public void parseReturnStmt() throws IOException {
        
        try {
            
            match( Symbol.returnRW );
            
            if ( scanner.getSymbol().isExprStarter() ) {
                parseExpression();
            }
            
            match( Symbol.semicolon );
            
        } catch ( ParserException e ) {
            ErrorHandler.getInstance().reportError( e );
            recover( FOLLOW_SETS.get( "returnStmt" ) );
        }
        
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
        match( Symbol.identifier );
        IdType idType = idTable.get( idToken );
        
        if ( idType == null ) {
            
            String errorMsg = "Identifier \"" + idToken + 
                              "\" has not been declared.";
            throw error( idToken.getPosition(), errorMsg );
            
        } else if ( idType != IdType.variableId ) {
            
            String errorMsg = "Identifier \"" + idToken + 
                              "\" is not a variable.";
            throw error( idToken.getPosition(), errorMsg );
            
        }

        while ( scanner.getSymbol() == Symbol.leftBracket ) {
            matchCurrentSymbol();
            parseExpression();
            match( Symbol.rightBracket );
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
        } catch ( ParserException e ) {
            ErrorHandler.getInstance().reportError( e );
            recover( FOLLOW_SETS.get( "variable" ) );
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
        
        while ( scanner.getSymbol().isLogicalOperator() ) {
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
        
        parseSimpleExpr();
        
        if ( scanner.getSymbol().isRelationalOperator() ) {
            matchCurrentSymbol();
            parseSimpleExpr();
        }
        
    }

    /**
     * Analisa as regras gramaticais abaixo:
     * 
     * simpleExpr = ( addingOp )? term ( addingOp term )* .
     *   addingOp = "+" | "-" .
     */
    public void parseSimpleExpr() throws IOException {
        
        if ( scanner.getSymbol().isAddingOperator() ) {
            matchCurrentSymbol();
        }
        
        parseTerm();
        
        while ( scanner.getSymbol().isAddingOperator() ) {
            matchCurrentSymbol();
            parseTerm();
        }
        
    }

    /**
     * Analisa as regras gramaticais abaixo:
     * 
     *          term = factor ( multiplyingOp factor )* .
     * multiplyingOp = "*" | "/" | "mod" .
     */
    public void parseTerm() throws IOException {
        
        parseFactor();
        
        while ( scanner.getSymbol().isMultiplyingOperator() ) {
            matchCurrentSymbol();
            parseFactor();
        }
        
    }

    /**
     * Analisa a regra gramatical abaixo:
     * 
     * factor = "not" factor | constValue | namedValue | functionCall
     *        | "(" expression ")" .
     */
    public void parseFactor() throws IOException {
        
        try {
            
            if ( scanner.getSymbol() == Symbol.notRW ) {
                
                matchCurrentSymbol();
                parseFactor();
                
            } else if ( scanner.getSymbol().isLiteral() ) {
                
                // lida com literais de constantes separadamente dos
                // identificadores de constantes
                parseConstValue();
                
            } else if ( scanner.getSymbol() == Symbol.identifier ) {
                
                // lida com os identificadores baseando-se se eles foram
                // declarados como variáveis, constantes ou funções.
                Token idToken = scanner.getToken();
                IdType idType = idTable.get( idToken );

                if ( idType != null ) {
                    if ( idType == IdType.constantId ) {
                        parseConstValue();
                    } else if ( idType == IdType.variableId ) {
                        parseNamedValue();
                    } else if ( idType == IdType.functionId ) {
                        parseFunctionCall();
                    } else {
                        throw error( "Identifier \"" + scanner.getToken() +
                                     "\" is not valid as an expression." );
                    }
                } else {
                    throw error( "Identifier \"" + scanner.getToken() +
                                 "\" has not been declared." );
                }
                
            } else if ( scanner.getSymbol() == Symbol.leftParen ) {
                matchCurrentSymbol();
                parseExpression();
                match( Symbol.rightParen );
            } else {
                throw error( "Invalid expression." );
            }
            
        } catch ( ParserException e ) {
            ErrorHandler.getInstance().reportError( e );
            recover( FOLLOW_SETS.get( "factor" ) );
        }
        
    }

    /**
     * Analisa a regra gramatical abaixo:
     * 
     * constValue = literal | constId .
     */
    public void parseConstValue() throws IOException {
        
        try {
            
            if ( scanner.getSymbol().isLiteral() ) {
                parseLiteral();
            } else if ( scanner.getSymbol() == Symbol.identifier ) {
                matchCurrentSymbol();
            } else {
                throw error( "Invalid constant." );
            }
            
        } catch ( ParserException e ) {
            ErrorHandler.getInstance().reportError( e );
            recover( FOLLOW_SETS.get( "constValue" ) );
        }
        
    }

    /**
     * Analisa a regra gramatical abaixo:
     * 
     * namedValue = variable .
     */
    public void parseNamedValue() throws IOException {
        
        try {
            parseVariableExpr();
        } catch ( ParserException e ) {
            ErrorHandler.getInstance().reportError( e );
            recover( FOLLOW_SETS.get( "namedValue" ) );
        }
        
    }

    /**
     * Analisa a regra gramatical abaixo:
     * 
     * functionCall = funcId ( actualParameters )? .
     */
    public void parseFunctionCall() throws IOException {
        
        try {
            
            match( Symbol.identifier );
            
            if ( scanner.getSymbol().isExprStarter() ) {
                parseActualParameters();
            }
            
        } catch ( ParserException e ) {
            ErrorHandler.getInstance().reportError( e );
            recover( FOLLOW_SETS.get( "functionCall" ) );
        }
        
    }

    
    
    /***************************************************************************
     *                     Métodos utilitários de análise                      *
     **************************************************************************/
    
    /**
     * Verifica se o símbolo atual do scanner é o símbolo esperado. Se for,
     * avança o scanner. Caso contrário, lança uma ParserException.
     */
    private void match( Symbol expectedSymbol ) throws IOException, ParserException {
        
        if ( scanner.getSymbol() == expectedSymbol ) {
            scanner.advance();
        } else {
            String errorMsg = "Expecting \"" + expectedSymbol + 
                              "\" but found \"" + scanner.getToken() + 
                              "\" instead.";
            throw error( errorMsg );
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
     * Avança o scanner até que o símbolo atual seja um dos símbolos 
     * especificados no array de símbolos seguidores (follow).
     */
    private void recover( Symbol[] followers ) throws IOException {
        scanner.advanceTo( followers );
    }

    /**
     * Cria uma ParserException com a mensagem especificada e a posição corrente
     * do scanner.
     */
    private ParserException error( String errMsg ) {
        return new ParserException( scanner.getPosition(), errMsg );
    }

    /**
     * Cria uma ParserException especificando a posição atual do scanner e a
     * mensagem do erro.
     */
    private ParserException error( Position errPos, String errMsg ) {
        return new ParserException( errPos, errMsg );
    }

    /**
     * Cria uma InternalCompilerException especificando a posição atual do
     * scanner e a mensagem do erro.
     */
    private InternalCompilerException internalError( String message ) {
        return new InternalCompilerException( scanner.getPosition(), message );
    }
    
}
