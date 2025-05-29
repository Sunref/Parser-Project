package edu.citadel.cprl.ast;

import edu.citadel.compiler.CodeGenException;
import edu.citadel.compiler.ConstraintException;
import edu.citadel.compiler.ErrorHandler;
import edu.citadel.cprl.Type;

/**
 * The abstract syntax tree node for an exit statement.
 */
public class ExitStmt extends Statement {

    private Expression whenExpr;
    private LoopStmt loopStmt;

    /**
     * Construct an exit statement with its optional "when" expression (which
     * should be null if there is no "when" expression) and a reference to the
     * enclosing loop statement.
     */
    public ExitStmt( Expression whenExpr, LoopStmt loopStmt ) {
        this.whenExpr = whenExpr;
        this.loopStmt = loopStmt;
    }

    public Expression getWhenExpr() {
        return whenExpr;
    }

    public LoopStmt getLoopStmt() {
        return loopStmt;
    }
    
    @Override
    public void checkConstraints() {
        
        // Regra de Tipo: se uma expressão when existir, o seu tipo deve ser
        // Boolean.
        
        // Regra Variada: a instrução exit deve estar aninhada dentro de uma
        // instrução de laço, o que é tratado pelo parser usando LoopContext.
                    
        try {
            
            if ( whenExpr != null ) {
                if ( whenExpr.getType() != Type.Boolean ) {
                    String errorMsg = "The \"when\" expression should have type Boolean.";
                    throw error( whenExpr.getPosition(), errorMsg );
                } else {
                    whenExpr.checkConstraints();
                }
            }
            
        } catch ( ConstraintException e ) {
            ErrorHandler.getInstance().reportError( e );
        }
        
    }

    @Override
    public void emit() throws CodeGenException {
        
        String exitLabel = loopStmt.getExitLabel();

        if ( whenExpr != null ) {
            whenExpr.emitBranch( true, exitLabel );
        } else {
            emit( "BR " + exitLabel );
        }
        
    }
    
}
