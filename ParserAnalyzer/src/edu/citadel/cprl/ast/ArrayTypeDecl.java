package edu.citadel.cprl.ast;

import edu.citadel.compiler.ConstraintException;
import edu.citadel.compiler.ErrorHandler;
import edu.citadel.cprl.ArrayType;
import edu.citadel.cprl.Token;
import edu.citadel.cprl.Type;

/**
 * The abstract syntax tree node for an array type declaration.
 */
public class ArrayTypeDecl extends InitialDecl {

    private ConstValue numElements;

    /**
     * Construct an ArrayTypeDecl with its identifier, element type, and number
     * of elements. Note that the index type is always Integer in CPRL.
     */
    public ArrayTypeDecl( Token typeId, Type elemType, ConstValue numElements ) {
        super( typeId, new ArrayType( typeId.getText(), numElements.getLiteralIntValue(), elemType ) );
        this.numElements = numElements;
    }

    public ConstValue getNumElements() {
        return numElements;
    }
    
    @Override
    public void checkConstraints() {
        
        // Regra de Tipo: o valor da constante que especifica a quantidade de 
        // itens de um array deve ser do tipo Integer e o valor associado deve 
        // ser um número positivo.
        
        try {
            
            numElements.checkConstraints();
            
            if ( numElements.getLiteralIntValue() <= 0 ) {
                String errorMsg = "Invalid constant.";
                throw error( getPosition(), errorMsg );
            }
            
        } catch ( ConstraintException e ) {
            ErrorHandler.getInstance().reportError( e );
        }
        
    }
    
}
