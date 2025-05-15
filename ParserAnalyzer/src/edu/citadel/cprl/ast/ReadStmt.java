package edu.citadel.cprl.ast;

import edu.citadel.compiler.CodeGenException;
import edu.citadel.compiler.ConstraintException;
import edu.citadel.compiler.ErrorHandler;
import edu.citadel.cprl.ArrayType;
import edu.citadel.cprl.Type;

/**
 * The abstract syntax tree node for a read statement.
 */
public class ReadStmt extends Statement {

    private Variable variable;

    /**
     * Construct a read statement with the specified variable for storing the
     * input.
     */
    public ReadStmt( Variable variable ) {
        this.variable = variable;
    }

    public Variable getVariable() {
        return variable;
    }
    
    @Override
    public void checkConstraints() {
        
        // Regra de Tipo: a variável deve ser do tipo Integer ou do tipo Char.
        // Dica: cuidado com variáveis de tipos de arrays.
        
        // <editor-fold defaultstate="collapsed" desc="Implementação">
                    
        // sua implementação aqui

        // </editor-fold>
        
    }

    @Override
    public void emit() throws CodeGenException {
        // ...
    }
    
}
