package edu.citadel.cprl.ast;

import edu.citadel.compiler.CodeGenException;
import edu.citadel.compiler.ConstraintException;
import edu.citadel.compiler.ErrorHandler;
import edu.citadel.cprl.Symbol;
import edu.citadel.cprl.Token;
import edu.citadel.cprl.Type;

/**
 * The abstract syntax tree node for a negation expression. A negation
 * expression is a unary expression where the operator is either "-" or "+". A
 * simple example would be "-x".
 */
public class NegationExpr extends UnaryExpr {

    /**
     * Construct a negation expression with the specified operator ("-") and
     * operand.
     */
    public NegationExpr( Token operator, Expression operand ) {
        
        super( operator, operand );
        setType( Type.Integer );
        
        assert operator.getSymbol() == Symbol.minus : "Operator is not \"-\" operator.";
        
    }
    
    @Override
    public void checkConstraints() {
        
        // Regra de Tipo: o operando tem que ser do tipo Integer.
        
        // Regra Variada: o resultado tem que ser do tipo Integer.
        
        try {

            Expression operand = getOperand();
            operand.checkConstraints();

            // os operadores unários +/- podem ser aplicados somente à
            // expressões do tipo Integer
            if ( operand.getType() != Type.Integer ) { // pode usar matchTypes também!
                String errorMsg = "Expression following \"-\" operator is not a Integer expression.";
                throw error( operand.getPosition(), errorMsg );
            }

        } catch ( ConstraintException e ) {
            ErrorHandler.getInstance().reportError( e );
        }
        
    }

    @Override
    public void emit() throws CodeGenException {
        // ...
    }
    
}
