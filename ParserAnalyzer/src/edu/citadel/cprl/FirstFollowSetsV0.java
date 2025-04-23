/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.citadel.cprl;

import java.util.HashMap;
import java.util.Map;

/**
 * Centraliza os conjuntos First e Follow para os não-terminais da gramática
 * da linguagem CPRL.
 * 
 * Conjuntos Follow:
 *  
 *    Follow(program)           = { EOF }
 *    Follow(declarativePart)   = { begin }
 *    Follow(initialDecls)      = { procedure, function, begin }
 *    Follow(initialDecl)       = { const, type, var, procedure, function, 
 *                                  begin
 *                                }
 *    Follow(constDecl)         = Follow(initialDecl)
 *    Follow(arrayTypeDecl)     = Follow(initialDecl)
 *    Follow(varDecl)           = Follow(initialDecl)
 *    Follow(literal)           = { 
 *                                  ;, =, ], identifier, ,, (, ), then, loop, 
 *                                  and, or, !=, <, <=, >, >=, +, -, *, /, mod, 
 *                                  not
 *                                }
 *    Follow(booleanLiteral)    = Follow(literal)
 *    Follow(identifiers)       = { : }
 *    Follow(typeName)          = { ;, ,, is, ) }
 *    Follow(subprogramDecls)   = { begin }
 *    Follow(subprogramDecl)    = { procedure, function, begin }
 *    Follow(procedureDecl)     = Follow(subprogramDecl)
 *    Follow(functionDecl)      = Follow(subprogramDecl)
 *    Follow(formalParameters)  = { is, return }
 *    Follow(parameterDecl)     = { ,, ) }
 *    Follow(statementPart)     = { dot, identifier }
 *    Follow(statements)        = { end, else, elsif }
 *    Follow(statement)         = { 
 *                                  identifier, return, end, if, else, elsif, 
 *                                  while, loop, exit, read, write, writeln 
 *                                }
 *    Follow(assignmentStmt)    = Follow(statement)
 *    Follow(ifStmt)            = Follow(statement)
 *    Follow(loopStmt)          = Follow(statement)
 *    Follow(exitStmt)          = Follow(statement)
 *    Follow(readStmt)          = Follow(statement)
 *    Follow(writeStmt)         = Follow(statement)
 *    Follow(writelnStmt)       = Follow(statement)
 *    Follow(procedureCallStmt) = Follow(statement)
 *    Follow(returnStmt)        = Follow(statement)
 *    Follow(variable)          = { 
 *                                  :=, ;, intLiteral, charLiteral, 
 *                                  stringLiteral, true, false, =, ], 
 *                                  identifier, ,, (, ), then, loop, and, or, 
 *                                  !=, <, <=, >, >=, +, -, *, /, mod, not 
 *                                }
 *    Follow(expressions)       = { ;, ) }
 *    Follow(actualParameters)  = { 
 *                                  ;, intLiteral, charLiteral, stringLiteral, 
 *                                  true, false, =, ], identifier, ,, (, ), 
 *                                  then, loop, and, or, !=, <, <=, >, >=, +, -, 
 *                                  *, /, mod, not 
 *                                }
 *    Follow(expression)        = { ;, ], ,, ), then, loop }
 *    Follow(logicalOp)         = { 
 *                                  intLiteral, charLiteral, stringLiteral, 
 *                                  true, false, identifier, (, +, -, not 
 *                                }
 *    Follow(relation)          = { 
 *                                  ;, intLiteral, charLiteral, stringLiteral, 
 *                                  true, false, ], identifier, ,, (, ), then, 
 *                                  loop, and, or, +, -, not 
 *                                }
 *    Follow(relationalOp)      = { 
 *                                  intLiteral, charLiteral, stringLiteral, 
 *                                  true, false, identifier, (, +, -, not 
 *                                }
 *    Follow(simpleExpr)        = { 
 *                                  ;, intLiteral, charLiteral, stringLiteral, 
 *                                  true, false, =, ], identifier, ,, (, ), 
 *                                  then, loop, and, or, !=, <, <=, >, >=, +, -, 
 *                                  not
 *                                }
 *    Follow(term)              = Follow(simpleExpr)
 *    Follow(addingOp)          = { 
 *                                  intLiteral, charLiteral, stringLiteral, 
 *                                  true, false, identifier, (, not 
 *                                }
 *    Follow(multiplyingOp)     = Follow(addingOp)
 *    Follow(factor)            = { 
 *                                  ;, true, false, =, ], ,, ), then, loop, and, 
 *                                  or, !=, <, <=, >, >=, +, -, *, /, mod, not
 *                                }
 *    Follow(constValue)        = Follow(factor)
 *    Follow(namedValue)        = Follow(factor)
 *    Follow(functionCall)      = Follow(factor)
 *    Follow(booleanExpr)       = { ;, then, loop }
 *    Follow(intConstValue)     = { ] }
 *    Follow(constId)           = { 
 *                                  :=, ;, intLiteral, charLiteral, 
 *                                  stringLiteral, true, false, =, ], 
 *                                  identifier, ,, (, ), then, loop, and, or, 
 *                                  !=, <, <=, >, >=, +, -, *, /, mod, not
 *                                }
 *    Follow(varId)             = { 
 *                                  :=, ;, intLiteral, charLiteral, 
 *                                  stringLiteral, true, false, =, [, ], 
 *                                  identifier, ,, (, ), then, loop, and, or, 
 *                                  !=, <, <=, >, >=, +, -, *, /, mod, not
 *                                }
 *    Follow(paramId)           = { 
 *                                  :=, ;, intLiteral, charLiteral, 
 *                                  stringLiteral, true, false, =, [, ], :, 
 *                                  identifier, ,, (, ), then, loop, and, or, 
 *                                  !=, <, <=, >, >=, +, -, *, /, mod, not
 *                                }
 *    Follow(typeId)            = { ;, =, ,, is, ) }
 *    Follow(procId)            = { ;, is }
 *    Follow(funcId)            = { 
 *                                  ;, intLiteral, charLiteral, stringLiteral, 
 *                                  true, false, =, ], identifier, ,, return, (, 
 *                                  ), then, loop, and, or, !=, <, <=, >, >=, +, 
 *                                  -, *, /, mod, not
 *                                }
 * 
 * @author Prof. Dr. David Buzatto
 */
public abstract class FirstFollowSetsV0 {
    
    public static final Map<String, Symbol[]> FIRST_SETS = new HashMap<>();
    public static final Map<String, Symbol[]> FOLLOW_SETS = new HashMap<>();
    
    static {
        
        // <editor-fold defaultstate="collapsed" desc="Conjuntos First">
        
        FIRST_SETS.put( "program", new Symbol[]{ 
                Symbol.constRW, 
                Symbol.typeRW, 
                Symbol.varRW, 
                Symbol.procedureRW, 
                Symbol.functionRW, 
                Symbol.beginRW 
        });
        
        FIRST_SETS.put( "declarativePart", new Symbol[]{ 
                Symbol.constRW, 
                Symbol.typeRW, 
                Symbol.varRW, 
                Symbol.procedureRW, 
                Symbol.functionRW
        });
        
        FIRST_SETS.put( "initialDecls", new Symbol[]{ 
                Symbol.constRW, 
                Symbol.typeRW, 
                Symbol.varRW 
        });
        
        FIRST_SETS.put( "initialDecl", new Symbol[]{ 
                Symbol.constRW, 
                Symbol.typeRW, 
                Symbol.varRW 
        });
        
        FIRST_SETS.put( "constDecl", new Symbol[]{ 
                Symbol.constRW 
        });
        
        FIRST_SETS.put( "literal", new Symbol[]{ 
                Symbol.intLiteral, 
                Symbol.charLiteral, 
                Symbol.stringLiteral, 
                Symbol.trueRW, 
                Symbol.falseRW
        });
        
        FIRST_SETS.put( "booleanLiteral", new Symbol[]{ 
                Symbol.trueRW,
                Symbol.falseRW 
        });
        
        FIRST_SETS.put( "arrayTypeDecl", new Symbol[]{ 
                Symbol.typeRW 
        });
        
        FIRST_SETS.put( "varDecl", new Symbol[]{ 
                Symbol.varRW 
        });
        
        FIRST_SETS.put( "identifiers", new Symbol[]{ 
                Symbol.identifier, 
                Symbol.comma 
        });
        
        FIRST_SETS.put( "typeName", new Symbol[]{ 
                Symbol.identifier, 
                Symbol.IntegerRW, 
                Symbol.BooleanRW, 
                Symbol.CharRW
        });
        
        FIRST_SETS.put( "subprogramDecls", new Symbol[]{ 
                Symbol.procedureRW, 
                Symbol.functionRW
        });

        FIRST_SETS.put( "subprogramDecl", new Symbol[]{ 
                Symbol.procedureRW, 
                Symbol.functionRW
        });

        FIRST_SETS.put( "procedureDecl", new Symbol[]{ 
                Symbol.procedureRW
        });

        FIRST_SETS.put( "functionDecl", new Symbol[]{ 
                Symbol.functionRW
        });

        FIRST_SETS.put( "formalParameters", new Symbol[]{ 
                Symbol.leftParen
        });

        FIRST_SETS.put( "parameterDecl", new Symbol[]{ 
                Symbol.varRW, 
                Symbol.identifier
        });

        FIRST_SETS.put( "statementPart", new Symbol[]{ 
                Symbol.beginRW
        });

        FIRST_SETS.put( "statements", new Symbol[]{ 
                Symbol.identifier, 
                Symbol.returnRW, 
                Symbol.ifRW, 
                Symbol.whileRW, 
                Symbol.loopRW, 
                Symbol.exitRW, 
                Symbol.readRW, 
                Symbol.writeRW, 
                Symbol.writelnRW
        });

        FIRST_SETS.put( "statement", new Symbol[]{ 
                Symbol.identifier, 
                Symbol.returnRW, 
                Symbol.ifRW, 
                Symbol.whileRW, 
                Symbol.loopRW, 
                Symbol.exitRW, 
                Symbol.readRW, 
                Symbol.writeRW, 
                Symbol.writelnRW
        });

        FIRST_SETS.put( "assignmentStmt", new Symbol[]{ 
                Symbol.identifier
        });

        FIRST_SETS.put( "variable", new Symbol[]{ 
                Symbol.identifier
        });

        FIRST_SETS.put( "ifStmt", new Symbol[]{ 
                Symbol.ifRW
        });

        FIRST_SETS.put( "loopStmt", new Symbol[]{ 
                Symbol.whileRW, 
                Symbol.loopRW
        });

        FIRST_SETS.put( "exitStmt", new Symbol[]{ 
                Symbol.exitRW
        });

        FIRST_SETS.put( "readStmt", new Symbol[]{ 
                Symbol.readRW
        });

        FIRST_SETS.put( "writeStmt", new Symbol[]{ 
                Symbol.writeRW
        });

        FIRST_SETS.put( "expressions", new Symbol[]{ 
                Symbol.intLiteral, 
                Symbol.charLiteral, 
                Symbol.stringLiteral, 
                Symbol.trueRW, 
                Symbol.falseRW, 
                Symbol.identifier, 
                Symbol.comma, 
                Symbol.leftParen, 
                Symbol.andRW, 
                Symbol.orRW, 
                Symbol.plus, 
                Symbol.minus, 
                Symbol.notRW
        });

        FIRST_SETS.put( "writelnStmt", new Symbol[]{ 
                Symbol.writelnRW
        });

        FIRST_SETS.put( "procedureCallStmt", new Symbol[]{ 
                Symbol.identifier
        });

        FIRST_SETS.put( "actualParameters", new Symbol[]{ 
                Symbol.leftParen
        });

        FIRST_SETS.put( "returnStmt", new Symbol[]{ 
                Symbol.returnRW
        });

        FIRST_SETS.put( "expression", new Symbol[]{ 
                Symbol.intLiteral, 
                Symbol.charLiteral, 
                Symbol.stringLiteral, 
                Symbol.trueRW, 
                Symbol.falseRW, 
                Symbol.identifier, 
                Symbol.leftParen, 
                Symbol.andRW, 
                Symbol.orRW, 
                Symbol.plus, 
                Symbol.minus, 
                Symbol.notRW
        });

        FIRST_SETS.put( "logicalOp", new Symbol[]{ 
                Symbol.andRW, 
                Symbol.orRW
        });

        FIRST_SETS.put( "relation", new Symbol[]{ 
                Symbol.intLiteral, 
                Symbol.charLiteral, 
                Symbol.stringLiteral, 
                Symbol.trueRW, 
                Symbol.falseRW, 
                Symbol.identifier, 
                Symbol.leftParen, 
                Symbol.plus, 
                Symbol.minus, 
                Symbol.notRW
        });

        FIRST_SETS.put( "relationalOp", new Symbol[]{ 
                Symbol.equals, 
                Symbol.notEqual, 
                Symbol.lessThan, 
                Symbol.lessOrEqual, 
                Symbol.greaterThan, 
                Symbol.greaterOrEqual
        });

        FIRST_SETS.put( "simpleExpr", new Symbol[]{ 
                Symbol.intLiteral, 
                Symbol.charLiteral, 
                Symbol.stringLiteral, 
                Symbol.trueRW, 
                Symbol.falseRW, 
                Symbol.identifier, 
                Symbol.leftParen, 
                Symbol.plus, 
                Symbol.minus, 
                Symbol.notRW
        });

        FIRST_SETS.put( "addingOp", new Symbol[]{ 
                Symbol.plus, 
                Symbol.minus
        });

        FIRST_SETS.put( "term", new Symbol[]{ 
                Symbol.intLiteral, 
                Symbol.charLiteral, 
                Symbol.stringLiteral, 
                Symbol.trueRW, 
                Symbol.falseRW, 
                Symbol.identifier, 
                Symbol.leftParen, 
                Symbol.notRW
        });

        FIRST_SETS.put( "multiplyingOp", new Symbol[]{ 
                Symbol.times, 
                Symbol.divide, 
                Symbol.modRW
        });

        FIRST_SETS.put( "factor", new Symbol[]{ 
                Symbol.intLiteral, 
                Symbol.charLiteral, 
                Symbol.stringLiteral, 
                Symbol.trueRW, 
                Symbol.falseRW, 
                Symbol.identifier, 
                Symbol.leftParen, 
                Symbol.notRW
        });

        FIRST_SETS.put( "constValue", new Symbol[]{ 
                Symbol.intLiteral, 
                Symbol.charLiteral, 
                Symbol.stringLiteral, 
                Symbol.trueRW, 
                Symbol.falseRW, 
                Symbol.identifier
        });

        FIRST_SETS.put( "namedValue", new Symbol[]{ 
                Symbol.identifier
        });

        FIRST_SETS.put( "functionCall", new Symbol[]{ 
                Symbol.identifier
        });

        FIRST_SETS.put( "booleanExpr", new Symbol[]{ 
                Symbol.intLiteral, 
                Symbol.charLiteral, 
                Symbol.stringLiteral, 
                Symbol.trueRW, 
                Symbol.falseRW, 
                Symbol.identifier, 
                Symbol.leftParen, 
                Symbol.andRW, 
                Symbol.orRW, 
                Symbol.plus, 
                Symbol.minus, 
                Symbol.notRW
        });

        FIRST_SETS.put( "intConstValue", new Symbol[]{ 
                Symbol.intLiteral, 
                Symbol.charLiteral, 
                Symbol.stringLiteral, 
                Symbol.trueRW, 
                Symbol.falseRW, 
                Symbol.identifier
        });

        FIRST_SETS.put( "constId", new Symbol[]{ 
                Symbol.identifier
        });

        FIRST_SETS.put( "varId", new Symbol[]{ 
                Symbol.identifier
        });

        FIRST_SETS.put( "paramId", new Symbol[]{ 
                Symbol.identifier
        });

        FIRST_SETS.put( "typeId", new Symbol[]{ 
                Symbol.identifier
        });

        FIRST_SETS.put( "procId", new Symbol[]{ 
                Symbol.identifier
        });

        FIRST_SETS.put( "funcId", new Symbol[]{ 
                Symbol.identifier
        });

        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Conjuntos Follow">
        
        FOLLOW_SETS.put( "program", new Symbol[]{ 
                Symbol.EOF 
        });

        FOLLOW_SETS.put( "declarativePart", new Symbol[]{ 
                Symbol.beginRW 
        });

        FOLLOW_SETS.put( "initialDecls", new Symbol[]{ 
                Symbol.procedureRW, 
                Symbol.functionRW, 
                Symbol.beginRW 
        });

        FOLLOW_SETS.put( "initialDecl", new Symbol[]{ 
                Symbol.constRW, 
                Symbol.typeRW, 
                Symbol.varRW, 
                Symbol.procedureRW, 
                Symbol.functionRW, 
                Symbol.beginRW 
        });

        // não precisa mexer...
        FOLLOW_SETS.put( "constDecl", FOLLOW_SETS.get( "initialDecl" ) );
        FOLLOW_SETS.put( "arrayTypeDecl", FOLLOW_SETS.get( "initialDecl" ) );
        FOLLOW_SETS.put( "varDecl", FOLLOW_SETS.get( "initialDecl" ) );
        
        FOLLOW_SETS.put( "literal", new Symbol[]{ 
        });

        // não precisa mexer...
        FOLLOW_SETS.put( "booleanLiteral", FOLLOW_SETS.get( "literal" ) );

        FOLLOW_SETS.put( "identifiers", new Symbol[]{ 
        });

        FOLLOW_SETS.put( "typeName", new Symbol[]{ 
        });

        FOLLOW_SETS.put( "subprogramDecls", new Symbol[]{ 
        });

        FOLLOW_SETS.put( "subprogramDecl", new Symbol[]{ 
        });
        
        // não precisa mexer...
        FOLLOW_SETS.put( "procedureDecl", FOLLOW_SETS.get( "subprogramDecl" ) );
        FOLLOW_SETS.put( "functionDecl", FOLLOW_SETS.get( "subprogramDecl" ) );

        FOLLOW_SETS.put( "formalParameters", new Symbol[]{ 
        });

        FOLLOW_SETS.put( "parameterDecl", new Symbol[]{ 
        });

        FOLLOW_SETS.put( "statementPart", new Symbol[]{ 
        });

        FOLLOW_SETS.put( "statements", new Symbol[]{ 
        });

        FOLLOW_SETS.put( "statement", new Symbol[]{ 
        });

        // não precisa mexer...
        FOLLOW_SETS.put( "assignmentStmt", FOLLOW_SETS.get( "statement" ) );
        FOLLOW_SETS.put( "ifStmt", FOLLOW_SETS.get( "statement" ) );
        FOLLOW_SETS.put( "loopStmt", FOLLOW_SETS.get( "statement" ) );
        FOLLOW_SETS.put( "exitStmt", FOLLOW_SETS.get( "statement" ) );
        FOLLOW_SETS.put( "readStmt", FOLLOW_SETS.get( "statement" ) );
        FOLLOW_SETS.put( "writeStmt", FOLLOW_SETS.get( "statement" ) );
        FOLLOW_SETS.put( "writelnStmt", FOLLOW_SETS.get( "statement" ) );
        FOLLOW_SETS.put( "procedureCallStmt", FOLLOW_SETS.get( "statement" ) );
        FOLLOW_SETS.put( "returnStmt", FOLLOW_SETS.get( "statement" ) );
        
        FOLLOW_SETS.put( "variable", new Symbol[]{Symbol.assign, 
        });
        
        FOLLOW_SETS.put( "expressions", new Symbol[]{ 
        });
        
        FOLLOW_SETS.put( "actualParameters", new Symbol[]{ 
        });

        FOLLOW_SETS.put( "expression", new Symbol[]{ 
        });

        FOLLOW_SETS.put( "logicalOp", new Symbol[]{ 
        });

        FOLLOW_SETS.put( "relation", new Symbol[]{ 
        });

        FOLLOW_SETS.put( "relationalOp", new Symbol[]{ 
        });

        FOLLOW_SETS.put( "simpleExpr", new Symbol[]{ 
        });

        // não precisa mexer...
        FOLLOW_SETS.put( "term", FOLLOW_SETS.get( "simpleExpr" ) );
        
        FOLLOW_SETS.put( "addingOp", new Symbol[]{ 
        });
        
        // não precisa mexer...
        FOLLOW_SETS.put( "multiplyingOp", FOLLOW_SETS.get( "addingOp" ) );

        FOLLOW_SETS.put( "factor", new Symbol[]{ 
                
        });

        // não precisa mexer...
        FOLLOW_SETS.put( "constValue", FOLLOW_SETS.get( "factor" ) );
        FOLLOW_SETS.put( "namedValue", FOLLOW_SETS.get( "factor" ) );
        FOLLOW_SETS.put( "functionCall", FOLLOW_SETS.get( "factor" ) );

        FOLLOW_SETS.put( "booleanExpr", new Symbol[]{ 
        });

        FOLLOW_SETS.put( "intConstValue", new Symbol[]{ 
        });

        FOLLOW_SETS.put( "constId", new Symbol[]{ 
        });

        FOLLOW_SETS.put( "varId", new Symbol[]{ 
        });
        
        FOLLOW_SETS.put( "paramId", new Symbol[]{ 
        });

        FOLLOW_SETS.put( "typeId", new Symbol[]{ 
        });

        FOLLOW_SETS.put( "procId", new Symbol[]{ 
        });

        FOLLOW_SETS.put( "funcId", new Symbol[]{ 
        });
        
        // </editor-fold>
        
    }
    
}
