package edu.citadel.cprl.ast;

import edu.citadel.compiler.CodeGenException;
import edu.citadel.compiler.ConstraintException;
import edu.citadel.compiler.ErrorHandler;
import edu.citadel.compiler.Position;

/**
 * The abstract syntax tree node for a return statement.
 */
public class ReturnStmt extends Statement {

    private SubprogramDecl subprogramDecl;
    private Expression returnExpr;          // may be null
    
    // position of the return token (needed for error reporting)
    private Position returnPosition;

    /**
     * Construct a return statement with a reference to the enclosing subprogram
     * and the expression for the value being returned, which may be null.
     */
    public ReturnStmt( SubprogramDecl subprogramDecl, Expression returnExpr, Position returnPosition ) {
        this.subprogramDecl = subprogramDecl;
        this.returnExpr = returnExpr;
        this.returnPosition = returnPosition;
    }

    public SubprogramDecl getSubprogramDecl() {
        return subprogramDecl;
    }

    public Expression getReturnExpr() {
        return returnExpr;
    }

    public Position getReturnPosition() {
        return returnPosition;
    }
    
    @Override
    public void checkConstraints() {
        
        assert subprogramDecl != null : "Return statement must be nested within a subprogram.";

        // Regra de Tipo: se a instrução retorna o valor de uma função, então o 
        // tipo da expressão que será retornada deve ser do mesmo tipo do 
        // retorno da função.
        
        // Regra Variada: se a instrução return retorna um valor, então ela 
        // deve estar aninhada à declaração de uma função. 
        
        // Regra Variada: se a instrução return está aninhada a uma função, 
        // então ela deve retornar um valor.
        
        // Regra Variada: a instrução return deve estar aninhada a um 
        // subprograma, o que é tratado pelo parser usando SubprogramContext.
        
        // <editor-fold defaultstate="collapsed" desc="Implementação">
                    
        // sua implementação aqui

        // </editor-fold>
        
    }

    @Override
    public void emit() throws CodeGenException {
        // ...
    }
    
}
