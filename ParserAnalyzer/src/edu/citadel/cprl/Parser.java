package edu.citadel.cprl;

import static edu.citadel.cprl.FirstFollowSets.*;

import edu.citadel.compiler.ErrorHandler;
import edu.citadel.compiler.InternalCompilerException;
import edu.citadel.compiler.ParserException;
import edu.citadel.compiler.Position;
import edu.citadel.cprl.ast.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class uses recursive descent to perform syntax analysis of the CPRL
 * source language.
 */
public class Parser {

    private Scanner scanner;
    private IdTable idTable;
    private LoopContext loopContext;
    private SubprogramContext subprogramContext;

    /**
     * Constrói um analisador sintático (parser) com um scanner especificado.
     */
    public Parser(Scanner scanner) {
        this.scanner = scanner;
        this.idTable = new IdTable();
        this.loopContext = new LoopContext();
        this.subprogramContext = new SubprogramContext();
    }

    /**
     * Analisa a regra gramatical abaixo:
     *
     * program = declarativePart statementPart "." .
     */
    public Program parseProgram() throws IOException {
        try {
            DeclarativePart declPart = parseDeclarativePart();
            StatementPart stmtPart = parseStatementPart();
            match(Symbol.dot);
            match(Symbol.EOF);
            return new Program(declPart, stmtPart);
        } catch (ParserException e) {
            ErrorHandler.getInstance().reportError(e);
            recover(FOLLOW_SETS.get("program"));
            return null;
        }
    }

    /**
     * Analisa a regra gramatical abaixo:
     *
     * declarativePart = initialDecls subprogramDecls .
     */
    public DeclarativePart parseDeclarativePart() throws IOException {
        List<InitialDecl> initialDecls = parseInitialDecls();
        List<SubprogramDecl> subprogDecls = parseSubprogramDecls();

        return new DeclarativePart(initialDecls, subprogDecls);
    }

    /**
     * Analisa a regra gramatical abaixo:
     *
     * initialDecls = ( initialDecl )* .
     */
    public List<InitialDecl> parseInitialDecls() throws IOException {
        List<InitialDecl> initialDecls = new ArrayList<>();

        while (scanner.getSymbol().isInitialDeclStarter()) {
            InitialDecl decl = parseInitialDecl();

            if (decl instanceof VarDecl) {
                VarDecl varDecl = (VarDecl) decl;
                for (SingleVarDecl singleVarDecl : varDecl.getSingleVarDecls()) {
                    initialDecls.add(singleVarDecl);
                }
            } else {
                initialDecls.add(decl);
            }
        }

        return initialDecls;
    }

    /**
     * Analisa a regra gramatical abaixo:
     *
     * initialDecl = constDecl | arrayTypeDecl | varDecl .
     */
    public InitialDecl parseInitialDecl() throws IOException {
        // Implementação:
        if (scanner.getSymbol() == Symbol.constRW) {
            return parseConstDecl();
        } else if (scanner.getSymbol() == Symbol.typeRW) {
            return parseArrayTypeDecl();
        } else if (scanner.getSymbol() == Symbol.varRW) {
            return parseVarDecl();
        } else {
            throw internalError("Invalid initial decl.");
        }
    }

    /**
     * Analisa a regra gramatical abaixo:
     *
     * constDecl = "const" constId ":=" literal ";" .
     */
    public ConstDecl parseConstDecl() throws IOException {
        // Implementação:
        try {
            match(Symbol.constRW);

            Token constId = scanner.getToken();

            match(Symbol.identifier);
            match(Symbol.assign);

            Token literal = parseLiteral();

            match(Symbol.semicolon);

            Type constType = Type.UNKNOWN;
            if (literal != null) {
                constType = Type.getTypeOf(literal.getSymbol());
            }

            ConstDecl constDecl = new ConstDecl(constId, constType, literal);
            idTable.add(constDecl);
            return constDecl;
        } catch (ParserException e) {
            ErrorHandler.getInstance().reportError(e);
            recover(FOLLOW_SETS.get("constDecl"));
            return null;
        }
    }

    /**
     * Analisa as regras gramaticais abaixo:
     *
     * literal = intLiteral | charLiteral | stringLiteral | booleanLiteral .
     * booleanLiteral = "true" | "false" .
     */
    public Token parseLiteral() throws IOException {
        try {
            if (scanner.getSymbol().isLiteral()) {
                Token literal = scanner.getToken();
                matchCurrentSymbol();
                return literal;
            } else {
                throw error("Invalid literal expression.");
            }
        } catch (ParserException e) {
            ErrorHandler.getInstance().reportError(e);
            recover(FOLLOW_SETS.get("literal"));
            return null;
        }
    }

    /**
     * Analisa a regra gramatical abaixo:
     *
     * varDecl = "var" identifiers ":" typeName ";" .
     */
    public VarDecl parseVarDecl() throws IOException {
        try {
            match(Symbol.varRW);
            List<Token> identifiers = parseIdentifiers();
            match(Symbol.colon);
            Type typeName = parseTypeName();
            match(Symbol.semicolon);

            VarDecl varDecl = new VarDecl(
                identifiers,
                typeName,
                idTable.getScopeLevel()
            );

            for (SingleVarDecl decl : varDecl.getSingleVarDecls()) {
                idTable.add(decl);
            }

            return varDecl;
        } catch (ParserException e) {
            ErrorHandler.getInstance().reportError(e);
            recover(FOLLOW_SETS.get("varDecl"));
            return null;
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
            recover(FOLLOW_SETS.get("identifiers"));
            return Collections.emptyList();
        }
    }

    /**
     * Analisa a regra gramatical abaixo:
     *
     * arrayTypeDecl = "type" typeId "=" "array" "[" intConstValue "]" "of"
     * typeName ";" .
     */
    public ArrayTypeDecl parseArrayTypeDecl() throws IOException {
        // Implementação:
        try {
            Token arrayTypeId = null;
            Type elemType = null;
            match(Symbol.typeRW);
            arrayTypeId = scanner.getToken();
            match(Symbol.identifier);
            match(Symbol.equals);
            match(Symbol.arrayRW);
            match(Symbol.leftBracket);

            ConstValue numElem = parseConstValue();

            if (numElem == null) {
                numElem = new ConstValue(
                    new Token(Symbol.intLiteral, scanner.getPosition(), "0")
                );
            }

            match(Symbol.rightBracket);
            match(Symbol.ofRW);
            elemType = parseTypeName();
            match(Symbol.semicolon);

            ArrayTypeDecl arrayTypeDecl = new ArrayTypeDecl(
                arrayTypeId,
                elemType,
                numElem
            );
            idTable.add(arrayTypeDecl);
            return arrayTypeDecl;
        } catch (ParserException e) {
            ErrorHandler.getInstance().reportError(e);
            recover(FOLLOW_SETS.get("arrayTypeDecl"));

            return null;
        }
    }

    /**
     * Analisa a regra gramatical abaixo:
     *
     * typeName = "Integer" | "Boolean" | "Char" | typeId .
     */
    public Type parseTypeName() throws IOException {
        Type type = Type.UNKNOWN;

        try {
            if (scanner.getSymbol() == Symbol.IntegerRW) {
                type = Type.Integer;
                matchCurrentSymbol();
            } else if (scanner.getSymbol() == Symbol.BooleanRW) {
                type = Type.Boolean;
                matchCurrentSymbol();
            } else if (scanner.getSymbol() == Symbol.CharRW) {
                type = Type.Char;
                matchCurrentSymbol();
            } else if (scanner.getSymbol() == Symbol.identifier) {
                Token typeId = scanner.getToken();
                matchCurrentSymbol();
                Declaration decl = idTable.get(typeId);

                if (decl != null) {
                    if (decl instanceof ArrayTypeDecl) {
                        type = decl.getType();
                    } else {
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
            recover(FOLLOW_SETS.get("typeName"));
        }

        return type;
    }

    /**
     * Analisa a regra gramatical abaixo:
     *
     * subprogramDecls = ( subprogramDecl )* .
     */
    public List<SubprogramDecl> parseSubprogramDecls() throws IOException {
        // Implementação:
        List<SubprogramDecl> listSubprogramDecls = new ArrayList<>();
        while (scanner.getSymbol().isSubprogramDeclStarter()) {
            listSubprogramDecls.add(parseSubprogramDecl());
        }
        return listSubprogramDecls;
    }

    /**
     * Analisa a regra gramatical abaixo:
     *
     * subprogramDecl = procedureDecl | functionDecl .
     */
    public SubprogramDecl parseSubprogramDecl() throws IOException {
        /// Implementação:
        if (scanner.getSymbol() == Symbol.procedureRW) {
            return parseProcedureDecl();
        } else if (scanner.getSymbol() == Symbol.functionRW) {
            return parseFunctionDecl();
        } else {
            return null;
        }
    }

    /**
     * Analisa a regra gramatical abaixo:
     *
     * procedureDecl = "procedure" procId ( formalParameters )? "is"
     * initialDecls statementPart procId ";" .
     */
    public ProcedureDecl parseProcedureDecl() throws IOException {
        try {
            match(Symbol.procedureRW);
            Token procId = scanner.getToken();
            match(Symbol.identifier);
            ProcedureDecl procDecl = new ProcedureDecl(procId);
            idTable.add(procDecl);
            idTable.openScope();

            if (scanner.getSymbol() == Symbol.leftParen) {
                procDecl.setFormalParams(parseFormalParameters());
            }

            match(Symbol.isRW);
            procDecl.setInitialDecls(parseInitialDecls());

            subprogramContext.beginSubprogramDecl(procDecl);
            procDecl.setStatementPart(parseStatementPart());
            subprogramContext.endSubprogramDecl();
            idTable.closeScope();

            Token procId2 = scanner.getToken();
            match(Symbol.identifier);

            if (!procId.getText().equals(procId2.getText())) {
                throw error(procId2.getPosition(), "Procedure name mismatch.");
            }

            match(Symbol.semicolon);

            return procDecl;
        } catch (ParserException e) {
            ErrorHandler.getInstance().reportError(e);
            recover(FOLLOW_SETS.get("procedureDecl"));
            return null;
        }
    }

    /**
     * Analisa a regra gramatical abaixo:
     *
     * functionDecl = "function" funcId ( formalParameters )? "return" typeName
     * "is" initialDecls statementPart funcId ";" .
     */
    public FunctionDecl parseFunctionDecl() throws IOException {
        // Implementação:
        try {
            match(Symbol.functionRW);
            Token funcId = scanner.getToken();
            match(Symbol.identifier);
            FunctionDecl funcDecl = new FunctionDecl(funcId);
            idTable.add(funcDecl);
            idTable.openScope();

            if (scanner.getSymbol() == Symbol.leftParen) {
                funcDecl.setFormalParams(parseFormalParameters());
            }

            match(Symbol.returnRW);

            funcDecl.setType(parseTypeName());

            match(Symbol.isRW);
            funcDecl.setInitialDecls(parseInitialDecls());

            subprogramContext.beginSubprogramDecl(funcDecl);

            funcDecl.setStatementPart(parseStatementPart());
            subprogramContext.endSubprogramDecl();
            idTable.closeScope();

            Token procId2 = scanner.getToken();
            match(Symbol.identifier);

            if (!funcId.getText().equals(procId2.getText())) {
                throw error(procId2.getPosition(), "Function name mismatch.");
            }

            match(Symbol.semicolon);

            return funcDecl;
        } catch (ParserException e) {
            ErrorHandler.getInstance().reportError(e);
            recover(FOLLOW_SETS.get("functionDecl"));
            return null;
        }
    }

    /**
     * Analisa a regra gramatical abaixo:
     *
     * formalParameters = "(" parameterDecl ( "," parameterDecl )* ")" .
     */
    public List<ParameterDecl> parseFormalParameters() throws IOException {
        // Implementação:
        try {
            ArrayList<ParameterDecl> parameterDecls = new ArrayList<>();

            match(Symbol.leftParen);

            parameterDecls.add(parseParameterDecl());

            while (scanner.getSymbol() == Symbol.comma) {
                matchCurrentSymbol();
                parameterDecls.add(parseParameterDecl());
            }

            match(Symbol.rightParen);
            return parameterDecls;
        } catch (ParserException e) {
            ErrorHandler.getInstance().reportError(e);
            recover(FOLLOW_SETS.get("formalParameters"));
            return null;
        }
    }

    /**
     * Analisa a regra gramatical abaixo:
     *
     * parameterDecl = ( "var" )? paramId ":" typeName .
     */
    public ParameterDecl parseParameterDecl() throws IOException {
        // Implementação:
        try {
            Token paramId = null;
            Type type = null;
            boolean isVar = false;
            if (scanner.getSymbol() == Symbol.varRW) {
                matchCurrentSymbol();
                isVar = true;
            }

            paramId = scanner.getToken();
            match(Symbol.identifier);

            match(Symbol.colon);

            type = parseTypeName();

            ParameterDecl parameterDecl = new ParameterDecl(
                paramId,
                type,
                isVar
            );
            idTable.add(parameterDecl);
            return parameterDecl;
        } catch (ParserException e) {
            ErrorHandler.getInstance().reportError(e);
            recover(FOLLOW_SETS.get("parameterDecl"));
            return null;
        }
    }

    /**
     * Analisa a regra gramatical abaixo:
     *
     * statementPart = "begin" statements "end" .
     */
    public StatementPart parseStatementPart() throws IOException {
        try {
            match(Symbol.beginRW);
            List<Statement> statements = parseStatements();
            match(Symbol.endRW);
            return new StatementPart(statements);
        } catch (ParserException e) {
            ErrorHandler.getInstance().reportError(e);
            recover(FOLLOW_SETS.get("statementPart"));
            return null;
        }
    }

    /**
     * Analisa a regra gramatical abaixo:
     *
     * statements = ( statement )* .
     */
    public List<Statement> parseStatements() throws IOException {
        // Implementação:
        List<Statement> statements = new ArrayList<>();

        while (scanner.getSymbol().isStmtStarter()) {
            Statement statement = parseStatement();
            statements.add(statement);
        }

        return statements;
    }

    /**
     * Analisa a regra gramatical abaixo:
     *
     * statement = assignmentStmt | ifStmt | loopStmt | exitStmt | readStmt |
     * writeStmt | writelnStmt | procedureCallStmt | returnStmt .
     */
    public Statement parseStatement() throws IOException {
        // Implementação:
        assert scanner.getSymbol().isStmtStarter() : "Invalid statement.";

        try {
            if (scanner.getSymbol() == Symbol.exitRW) {
                return parseExitStmt();
            } else if (scanner.getSymbol() == Symbol.identifier) {
                Declaration idType = idTable.get(scanner.getToken());

                if (idType == null) {
                    String errorMsg =
                        "Identifier \"" +
                        scanner.getToken() +
                        "\" has not been declared.";
                    throw error(scanner.getToken().getPosition(), errorMsg);
                } else if (idType instanceof NamedDecl) {
                    return parseAssignmentStmt();
                } else if (idType instanceof ProcedureDecl) {
                    return parseProcedureCallStmt();
                } else {
                    String errorMsg =
                        ("Identifier \"" +
                            scanner.getToken() +
                            "\" cannot start a statement.");
                    throw error(scanner.getToken().getPosition(), errorMsg);
                }
            } else if (scanner.getSymbol() == Symbol.ifRW) {
                return parseIfStmt();
            } else if (
                scanner.getSymbol() == Symbol.loopRW ||
                scanner.getSymbol() == Symbol.whileRW
            ) {
                return parseLoopStmt();
            } else if (scanner.getSymbol() == Symbol.readRW) {
                return parseReadStmt();
            } else if (scanner.getSymbol() == Symbol.writeRW) {
                return parseWriteStmt();
            } else if (scanner.getSymbol() == Symbol.writelnRW) {
                return parseWritelnStmt();
            } else if (scanner.getSymbol() == Symbol.returnRW) {
                return parseReturnStmt();
            } else {
                throw internalError("Invalid statement.");
            }
        } catch (ParserException e) {
            ErrorHandler.getInstance().reportError(e);
            scanner.advanceTo(Symbol.semicolon);
            recover(FOLLOW_SETS.get("statement"));
            return null;
        }
    }

    /**
     * Analisa a regra gramatical abaixo:
     *
     * assignmentStmt = variable ":=" expression ";" .
     */
    public AssignmentStmt parseAssignmentStmt() throws IOException {
        // Implementação:
        Expression expression = null;
        Position position = null;

        try {
            Variable variable = parseVariable();
            position = scanner.getPosition();
            try {
                match(Symbol.assign);
            } catch (ParserException e) {
                if (scanner.getSymbol() == Symbol.equals) {
                    ErrorHandler.getInstance().reportError(e);
                    matchCurrentSymbol();
                } else {
                    throw e;
                }
            }

            expression = parseExpression();
            match(Symbol.semicolon);

            AssignmentStmt assignmentStmt = new AssignmentStmt(
                variable,
                expression,
                position
            );

            return assignmentStmt;
        } catch (ParserException e) {
            ErrorHandler.getInstance().reportError(e);
            recover(FOLLOW_SETS.get("assignmentStmt"));
            return null;
        }
    }

    /**
     * Analisa a regra gramatical abaixo:
     *
     * ifStmt = "if" booleanExpr "then" statements ( "elsif" booleanExpr "then"
     * statements )* ( "else" statements )? "end" "if" ";" .
     */
    public IfStmt parseIfStmt() throws IOException {
        // Implementação:
        try {
            match(Symbol.ifRW);

            Expression booleanExpr = parseExpression();

            match(Symbol.thenRW);

            List<Statement> stmts = parseStatements();
            List<ElsifPart> elseIfPart = new ArrayList<>();
            List<Statement> elseStmts = new ArrayList<>();

            while (scanner.getSymbol() == Symbol.elsifRW) {
                match(Symbol.elsifRW);

                Expression elseIfExpr = parseExpression();

                match(Symbol.thenRW);
                List<Statement> elseIfStmt = parseStatements();

                elseIfPart.add(new ElsifPart(elseIfExpr, elseIfStmt));
            }

            if (scanner.getSymbol() == Symbol.elseRW) {
                match(Symbol.elseRW);
                elseStmts = parseStatements();
            }

            match(Symbol.endRW);
            match(Symbol.ifRW);
            match(Symbol.semicolon);

            return new IfStmt(booleanExpr, stmts, elseIfPart, elseStmts);
        } catch (ParserException e) {
            ErrorHandler.getInstance().reportError(e);
            recover(FOLLOW_SETS.get("ifStmt"));
            return null;
        }
    }

    /**
     * Analisa a regra gramatical abaixo:
     *
     * loopStmt = ( "while" booleanExpr )? "loop" statements "end" "loop" ";" .
     */
    public LoopStmt parseLoopStmt() throws IOException {
        // Implementação:
        try {
            LoopStmt loopStmt = new LoopStmt();
            if (scanner.getSymbol() == Symbol.whileRW) {
                matchCurrentSymbol();
                Expression whileExpr = parseExpression();
                loopStmt.setWhileExpr(whileExpr);
            }

            match(Symbol.loopRW);
            loopContext.beginLoop(loopStmt);
            loopStmt.setStatements(parseStatements());
            loopContext.endLoop();

            match(Symbol.endRW);
            match(Symbol.loopRW);
            match(Symbol.semicolon);
            return loopStmt;
        } catch (ParserException e) {
            ErrorHandler.getInstance().reportError(e);
            recover(FOLLOW_SETS.get("loopStmt"));
        }
        return null;
    }

    /**
     * Analisa a regra gramatical abaixo:
     *
     * exitStmt = "exit" ( "when" booleanExpr )? ";" .
     */
    public ExitStmt parseExitStmt() throws IOException {
        // Implementação:
        try {
            Expression booleanExpr = null;
            Position exitPosition = scanner.getPosition();

            match(Symbol.exitRW);

            if (scanner.getSymbol() == Symbol.whenRW) {
                matchCurrentSymbol();
                booleanExpr = parseExpression();
            }

            match(Symbol.semicolon);

            LoopStmt loopStmt = loopContext.getLoopStmt();
            if (loopStmt == null) {
                throw error(
                    exitPosition,
                    "Exit statement is not nested within a loop."
                );
            }

            return new ExitStmt(booleanExpr, loopStmt);
        } catch (ParserException e) {
            ErrorHandler.getInstance().reportError(e);
            recover(FOLLOW_SETS.get("exitStmt"));
            return null;
        }
    }

    /**
     * Analisa a regra gramatical abaixo:
     *
     * readStmt = "read" variable ";" .
     */
    public ReadStmt parseReadStmt() throws IOException {
        // Implementação:
        try {
            Variable variable = null;
            match(Symbol.readRW);
            variable = parseVariableExpr();
            match(Symbol.semicolon);
            ReadStmt readStmt = new ReadStmt(variable);
            return readStmt;
        } catch (ParserException e) {
            ErrorHandler.getInstance().reportError(e);
            recover(FOLLOW_SETS.get("readStmt"));
            return null;
        }
    }

    /**
     * Analisa a regra gramatical abaixo:
     *
     * writeStmt = "write" expressions ";" .
     */
    public WriteStmt parseWriteStmt() throws IOException {
        // Implementação:
        try {
            match(Symbol.writeRW);

            List<Expression> expressions = parseExpressions();

            match(Symbol.semicolon);

            return new WriteStmt(expressions);
        } catch (ParserException e) {
            ErrorHandler.getInstance().reportError(e);
            recover(FOLLOW_SETS.get("writeStmt"));
            return null;
        }
    }

    /**
     * Analisa a regra gramatical abaixo:
     *
     * expressions = expression ( "," expression )* .
     */
    public List<Expression> parseExpressions() throws IOException {
        // Implementação:
        List<Expression> expressions = new ArrayList<>();
        expressions.add(parseExpression());

        while (scanner.getSymbol() == Symbol.comma) {
            matchCurrentSymbol();
            expressions.add(parseExpression());
        }

        return expressions;
    }

    /**
     * Analisa a regra gramatical abaixo:
     *
     * writelnStmt = "writeln" ( expressions )? ";" .
     */
    public WritelnStmt parseWritelnStmt() throws IOException {
        try {
            match(Symbol.writelnRW);

            List<Expression> expressions;
            if (scanner.getSymbol().isExprStarter()) {
                expressions = parseExpressions();
            } else {
                expressions = Collections.emptyList();
            }

            match(Symbol.semicolon);

            return new WritelnStmt(expressions);
        } catch (ParserException e) {
            ErrorHandler.getInstance().reportError(e);
            recover(FOLLOW_SETS.get("writelnStmt"));
            return null;
        }
    }

    /**
     * Analisa a regra gramatical abaixo:
     *
     * procedureCallStmt = procId ( actualParameters )? ";" .
     */
    public ProcedureCallStmt parseProcedureCallStmt() throws IOException {
        // Implementação:
        try {
            Token procId = scanner.getToken();
            List<Expression> actualParams = new ArrayList<>();
            ProcedureDecl procedureDecl = null;
            match(Symbol.identifier);

            if (scanner.getSymbol().isExprStarter()) {
                actualParams = parseActualParameters();
            }

            match(Symbol.semicolon);

            procedureDecl = (ProcedureDecl) idTable.get(procId);

            ProcedureCallStmt procedureCallStmt = new ProcedureCallStmt(
                procId,
                actualParams,
                procedureDecl
            );
            return procedureCallStmt;
        } catch (ParserException e) {
            ErrorHandler.getInstance().reportError(e);
            recover(FOLLOW_SETS.get("procedureCallStmt"));
            return null;
        }
    }

    /**
     * Analisa a regra gramatical abaixo:
     *
     * actualParameters = "(" expressions ")" .
     */
    public List<Expression> parseActualParameters() throws IOException {
        // Implementação:
        try {
            List<Expression> actualParameters = new ArrayList<>();
            match(Symbol.leftParen);
            actualParameters = parseExpressions();
            match(Symbol.rightParen);
            return actualParameters;
        } catch (ParserException e) {
            ErrorHandler.getInstance().reportError(e);
            recover(FOLLOW_SETS.get("actualParameters"));
        }
        return null;
    }

    /**
     * Analisa a regra gramatical abaixo:
     *
     * returnStmt = "return" ( expression )? ";" .
     */
    public ReturnStmt parseReturnStmt() throws IOException {
        // Implementação:
        try {
            Position position = scanner.getPosition();
            Expression returnExpr = null;
            match(Symbol.returnRW);

            if (scanner.getSymbol().isExprStarter()) {
                returnExpr = parseExpression();
            }

            match(Symbol.semicolon);

            SubprogramDecl subDecl = subprogramContext.getSubprogramDecl();
            if (subDecl == null) {
                throw error(
                    position,
                    "Return statement is not nested within a subprogram."
                );
            }

            ReturnStmt returnStmt = new ReturnStmt(
                subDecl,
                returnExpr,
                position
            );
            return returnStmt;
        } catch (ParserException e) {
            ErrorHandler.getInstance().reportError(e);
            recover(FOLLOW_SETS.get("returnStmt"));
            return null;
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
    public Variable parseVariableExpr() throws IOException, ParserException {
        Token idToken = scanner.getToken();

        match(Symbol.identifier);

        Declaration decl = idTable.get(idToken);

        if (decl == null) {
            String errorMsg =
                "Identifier \"" + idToken + "\" has not been declared.";
            throw error(idToken.getPosition(), errorMsg);
        } else if (!(decl instanceof NamedDecl)) {
            String errorMsg =
                "Identifier \"" + idToken + "\" is not a variable.";
            throw error(idToken.getPosition(), errorMsg);
        }

        NamedDecl namedDecl = (NamedDecl) decl;
        List<Expression> indexExprs = new ArrayList<>();

        while (scanner.getSymbol() == Symbol.leftBracket) {
            matchCurrentSymbol();
            indexExprs.add(parseExpression());
            match(Symbol.rightBracket);
        }

        return new Variable(namedDecl, idToken.getPosition(), indexExprs);
    }

    /**
     * Analisa a regra gramatical abaixo:
     *
     * variable = ( varId | paramId ) ( "[" expression "]" )* .
     */
    public Variable parseVariable() throws IOException {
        try {
            return parseVariableExpr();
        } catch (ParserException e) {
            ErrorHandler.getInstance().reportError(e);
            recover(FOLLOW_SETS.get("variable"));
            return null;
        }
    }

    /**
     * Analisa as regras gramaticais abaixo:
     *
     * expression = relation ( logicalOp relation )* . logicalOp = "and" | "or"
     * .
     */
    public Expression parseExpression() throws IOException {
        Expression relation = parseRelation();
        Expression relation2 = null;
        Token operator = null;

        while (scanner.getSymbol().isLogicalOperator()) {
            operator = scanner.getToken();
            matchCurrentSymbol();
            relation2 = parseRelation();
            relation = new LogicalExpr(relation, operator, relation2);
        }

        return relation;
    }

    /**
     * Analisa as regras gramaticais abaixo:
     *
     * relation = simpleExpr ( relationalOp simpleExpr )? . relationalOp = "=" |
     * "!=" | "<" | "<=" | ">" | ">=" .
     */
    public Expression parseRelation() throws IOException {
        // Implementação:
        Expression LeftOp = null;
        LeftOp = parseSimpleExpr();

        if (scanner.getSymbol().isRelationalOperator()) {
            Token operator = scanner.getToken();
            matchCurrentSymbol();
            Expression rightOp = parseSimpleExpr();
            return new RelationalExpr(LeftOp, operator, rightOp);
        } else {
            return LeftOp;
        }
    }

    /**
     * Analisa as regras gramaticais abaixo:
     *
     * simpleExpr = ( addingOp )? term ( addingOp term )* . addingOp = "+" | "-"
     * .
     */
    public Expression parseSimpleExpr() throws IOException {
        // Implementação:
        Token operator = null;

        if (scanner.getSymbol().isAddingOperator()) {
            operator = scanner.getToken();
            matchCurrentSymbol();
        }

        Expression leftExpr = parseTerm();

        if (operator != null) {
            leftExpr = new NegationExpr(operator, leftExpr);
        }

        while (scanner.getSymbol().isAddingOperator()) {
            operator = scanner.getToken();
            matchCurrentSymbol();
            Expression rightExpr = parseTerm();

            leftExpr = new AddingExpr(leftExpr, operator, rightExpr);
        }

        return leftExpr;
    }

    /**
     * Analisa as regras gramaticais abaixo:
     *
     * term = factor ( multiplyingOp factor )* . multiplyingOp = "*" | "/" |
     * "mod" .
     */
    public Expression parseTerm() throws IOException {
        // Implementação:
        Expression leftFactor = parseFactor();

        Token operator = null;
        if (scanner.getSymbol().isMultiplyingOperator()) {
            while (scanner.getSymbol().isMultiplyingOperator()) {
                operator = scanner.getToken();
                matchCurrentSymbol();
                Expression rightFactor = parseFactor();

                leftFactor = new MultiplyingExpr(
                    leftFactor,
                    operator,
                    rightFactor
                );
            }

            return leftFactor;
        }

        return leftFactor;
    }

    /**
     * Analisa a regra gramatical abaixo:
     *
     * factor = "not" factor | constValue | namedValue | functionCall | "("
     * expression ")" .
     */
    public Expression parseFactor() throws IOException {
        try {
            Expression expr;

            if (scanner.getSymbol() == Symbol.notRW) {
                Token operator = scanner.getToken();
                matchCurrentSymbol();
                Expression factorExpr = parseFactor();
                expr = new NotExpr(operator, factorExpr);
            } else if (scanner.getSymbol().isLiteral()) {
                expr = parseConstValue();
            } else if (scanner.getSymbol() == Symbol.identifier) {
                Token idToken = scanner.getToken();
                Declaration decl = idTable.get(idToken);

                if (decl != null) {
                    if (decl instanceof ConstDecl) {
                        expr = parseConstValue();
                    } else if (decl instanceof NamedDecl) {
                        expr = parseNamedValue();
                    } else if (decl instanceof FunctionDecl) {
                        expr = parseFunctionCall();
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
                expr = parseExpression();
                match(Symbol.rightParen);
            } else {
                throw error("Invalid expression.");
            }

            return expr;
        } catch (ParserException e) {
            ErrorHandler.getInstance().reportError(e);
            recover(FOLLOW_SETS.get("factor"));
            return null;
        }
    }

    /**
     * Analisa a regra gramatical abaixo:
     *
     * constValue = literal | constId .
     */
    public ConstValue parseConstValue() throws IOException {
        // Implementação:
        try {
            if (scanner.getSymbol().isLiteral()) {
                return new ConstValue(parseLiteral());
            } else if (scanner.getSymbol() == Symbol.identifier) {
                Token Id = scanner.getToken();
                matchCurrentSymbol();
                return new ConstValue(Id, (ConstDecl) idTable.get(Id));
            } else {
                throw error("Invalid constant.");
            }
        } catch (ParserException e) {
            ErrorHandler.getInstance().reportError(e);
            recover(FOLLOW_SETS.get("constValue"));
            return null;
        }
    }

    /**
     * Analisa a regra gramatical abaixo:
     *
     * namedValue = variable .
     */
    public NamedValue parseNamedValue() throws IOException {
        try {
            Variable variableExpr = parseVariableExpr();
            return new NamedValue(variableExpr);
        } catch (ParserException e) {
            ErrorHandler.getInstance().reportError(e);
            recover(FOLLOW_SETS.get("namedValue"));
            return null;
        }
    }

    /**
     * Analisa a regra gramatical abaixo:
     *
     * functionCall = funcId ( actualParameters )? .
     */
    public FunctionCall parseFunctionCall() throws IOException {
        // Implementação:
        try {
            Token funcId = scanner.getToken();
            List<Expression> actualParams = new ArrayList<>();
            match(Symbol.identifier);

            if (scanner.getSymbol().isExprStarter()) {
                actualParams = parseActualParameters();
            }

            FunctionDecl functionDecl = (FunctionDecl) idTable.get(funcId);

            FunctionCall functionCall = new FunctionCall(
                funcId,
                actualParams,
                functionDecl
            );
            return functionCall;
        } catch (ParserException e) {
            ErrorHandler.getInstance().reportError(e);
            recover(FOLLOW_SETS.get("functionCall"));
            return null;
        }
    }

    /**
     * *************************************************************************
     * Métodos utilitários de análise *
     *************************************************************************
     */
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
     * Avança o scanner até que o símbolo atual seja um dos símbolos
     * especificados no array de símbolos seguidores (follow).
     */
    private void recover(Symbol[] followers) throws IOException {
        scanner.advanceTo(followers);
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
}
