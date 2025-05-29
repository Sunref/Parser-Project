package edu.citadel.cprl.ast;

import edu.citadel.compiler.CodeGenException;
import edu.citadel.compiler.ConstraintException;
import edu.citadel.compiler.ErrorHandler;
import edu.citadel.cprl.Symbol;
import edu.citadel.cprl.Token;
import edu.citadel.cprl.Type;

/**
 * The abstract syntax tree node for a relational expression. A relational
 * expression is a binary expression where the operator is a relational operator
 * such as "&lt;=" or "&gt;". A simple example would be "x &lt; 5".
 */
public class RelationalExpr extends BinaryExpr {

    // labels used during code generation
    private String L1;   // label at start of right operand
    private String L2;   // label at end of the relational expression

    /**
     * Construct a relational expression with the operator ("=", "&lt;=", etc.)
     * and the two operands.
     */
    public RelationalExpr( Expression leftOperand, Token operator, Expression rightOperand ) {
        
        super( leftOperand, operator, rightOperand );
        setType( Type.Boolean );
        
        assert operator.getSymbol().isRelationalOperator() :
                "Operator is not a relational operator.";

        L1 = getNewLabel();
        L2 = getNewLabel();
        
    }

    public String getL1() {
        return L1;
    }

    public String getL2() {
        return L2;
    }
    
    @Override
    public void checkConstraints() {
        
        // Regra de Tipo: ambos os operandos devem ter o mesmo tipo.
        
        // Regra de Tipo: apenas os tipos escalares, Integer, Char ou Boolean, 
        // são permitidos como operandos. Na CPRL, não é permitido que ambos os 
        // operandos sejam arrays ou literais de String.
        
        // Regra Variada: o resultado tem que ser do tipo Boolean.
        
        try {
            
            Expression leftOperand = getLeftOperand();
            Expression rightOperand = getRightOperand();

            leftOperand.checkConstraints();
            rightOperand.checkConstraints();

            if ( leftOperand.getType() != rightOperand.getType() ) {
                String errorMsg = "Type mismatch for left and right operands of a relational expression.";
                throw error( getPosition(), errorMsg );
            } else {
                
                if ( leftOperand.getType() != Type.Integer &&
                     leftOperand.getType() != Type.Char &&
                     leftOperand.getType() != Type.Boolean ) {  // ou !leftOperand.getType().isScalar()
                    String errorMsg = "Type of operands not permitted for a relational expression.";
                    throw error( getPosition(), errorMsg );
                }
                
            }
            
        } catch ( ConstraintException e ) {
            ErrorHandler.getInstance().reportError( e );
        }

    }

    @Override
    public void emit() throws CodeGenException {
        
        emitBranch( false, L1 );

        // emit true
        emit( "LDCB " + TRUE );

        // jump over code to emit false
        emit( "BR " + L2 );

        // L1:
        emitLabel( L1 );

        // emit false
        emit( "LDCB " + FALSE );

        // L2:
        emitLabel( L2 );
        
    }

    @Override
    public void emitBranch( boolean condition, String label ) throws CodeGenException {
        
        Token operator = getOperator();

        emitOperands();
        emit( "CMP" );

        Symbol operatorSym = operator.getSymbol();

        if ( operatorSym == Symbol.equals ) {
            emit( condition ? "BZ " + label : "BNZ " + label );
        } else if ( operatorSym == Symbol.notEqual ) {
            emit( condition ? "BNZ " + label : "BZ " + label );
        } else if ( operatorSym == Symbol.lessThan ) {
            emit( condition ? "BL " + label : "BGE " + label );
        } else if ( operatorSym == Symbol.lessOrEqual ) {
            emit( condition ? "BLE " + label : "BG " + label );
        } else if ( operatorSym == Symbol.greaterThan ) {
            emit( condition ? "BG " + label : "BLE " + label );
        } else if ( operatorSym == Symbol.greaterOrEqual ) {
            emit( condition ? "BGE " + label : "BL " + label );
        } else {
            throw new CodeGenException( operator.getPosition(), "Invalid relational operator." );
        }
        
    }

    private void emitOperands() throws CodeGenException {
        
        Expression leftOperand = getLeftOperand();
        Expression rightOperand = getRightOperand();

        // Relational operators compare integers only, so we need to make sure
        // that we have enough bytes on the stack.  Pad with zero bytes.
        for ( int n = 1; n <= ( Type.Integer.getSize() - leftOperand.getType().getSize() ); ++n ) {
            emit( "LDCB 0" );
        }

        leftOperand.emit();

        for ( int n = 1; n <= ( Type.Integer.getSize() - rightOperand.getType().getSize() ); ++n ) {
            emit( "LDCB 0" );
        }

        rightOperand.emit();
        
    }
    
}
