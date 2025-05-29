package edu.citadel.cprl.ast;

import edu.citadel.compiler.CodeGenException;
import edu.citadel.compiler.ConstraintException;
import edu.citadel.compiler.ErrorHandler;
import edu.citadel.cprl.Symbol;
import edu.citadel.cprl.Token;
import edu.citadel.cprl.Type;

/**
 * The abstract syntax tree node for a multiplying expression. A multiplying
 * expression is a binary expression where the operator is a multiplying
 * operator such as "*", "/", or "mod". A simple example would be "5*x".
 */
public class MultiplyingExpr extends BinaryExpr {

    /**
     * Construct a multiplying expression with the operator ("*", "/", or "mod")
     * and the two operands.
     */
    public MultiplyingExpr( Expression leftOperand, Token operator, Expression rightOperand ) {
        
        super( leftOperand, operator, rightOperand );
        setType( Type.Integer );
        
        assert operator.getSymbol().isMultiplyingOperator() :
                "Operator is not a multiplying operator.";
        
    }
    
    @Override
    public void checkConstraints() {
        
        // Regra de Tipo: ambos os operandos devem ser do tipo Integer.
        
        // Regra Variada: o resultado tem que ser do tipo Integer.
        
        try {
            
            Expression leftOperand = getLeftOperand();
            Expression rightOperand = getRightOperand();

            leftOperand.checkConstraints();
            rightOperand.checkConstraints();

            // somente inteiros
            if ( leftOperand.getType() != Type.Integer ) {
                String errorMsg = "Left operand for expression should have type Integer.";
                throw error( leftOperand.getPosition(), errorMsg );
            }

            if ( rightOperand.getType() != Type.Integer ) {
                String errorMsg = "Right operand for expression should have type Integer.";
                throw error( rightOperand.getPosition(), errorMsg );
            }
            
        } catch ( ConstraintException e ) {
            ErrorHandler.getInstance().reportError( e );
        }
        
    }

    @Override
    public void emit() throws CodeGenException {
        
        // <editor-fold defaultstate="collapsed" desc="Implementação">
                    
        // sua implementação aqui

        // </editor-fold>
        
    }
    
}
