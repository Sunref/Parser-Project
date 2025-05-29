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
        
        try {
            
            if ( variable.getType() != Type.Integer && 
                 variable.getType() != Type.Char && 
                 !( variable.getType() instanceof ArrayType ) ) {
                String errorMsg = "Input supported only for integers and characters.";
                throw error( variable.getPosition(), errorMsg );
            } else {
                variable.checkConstraints();
            }
            
        } catch ( ConstraintException e ) {
            ErrorHandler.getInstance().reportError( e );
        }
        
    }

    @Override
    public void emit() throws CodeGenException {
        
        variable.emit();

        if ( variable.getType() == Type.Integer ) {
            emit( "GETINT" );
        } else {  // o tipo tem que ser Char
            emit( "GETCH" );
        }

        emitStoreInst( variable.getType() );
        
    }
    
}
