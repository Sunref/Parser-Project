package edu.citadel.cprl.ast;

import edu.citadel.compiler.ConstraintException;
import edu.citadel.compiler.ErrorHandler;
import edu.citadel.cprl.Symbol;
import edu.citadel.cprl.Token;
import edu.citadel.cprl.Type;

/**
 * The abstract syntax tree node for a constant declaration.
 */
public class ConstDecl extends InitialDecl {

    private Token literal;

    /**
     * Construct a ConstDecl with its identifier, type, and literal.
     */
    public ConstDecl( Token identifier, Type constType, Token literal ) {
        super( identifier, constType );
        this.literal = literal;
    }

    public Token getLiteral() {
        return literal;
    }
    
    @Override
    public void checkConstraints() {
        
        // Regra Variada: se o valor do literal for do tipo Integer, então é 
        // necessário que o mesmo possa ser convertido em um inteiro na CPRL 
        // Virtual Machine. Em outras palavras, verificar se Integer.parseInt() 
        // não falha. Se a verificação falhar para uma declaração de constante, 
        // então configure o valor do literal como um valor válido para um 
        // Integer de modo a prevenir mensagens de erros adicionais toda vez 
        // que uma declaração da constante for utilizada.
                    
        try {
            
            if ( literal.getSymbol() == Symbol.intLiteral ) {
                try {
                    Integer.parseInt( literal.getText() );
                } catch ( NumberFormatException e1 ) {
                    String errorMsg = "The number \"" + literal.getText()
                            + "\" cannot be converted to an integer in CPRL.";
                    literal.setText( "0" );
                    throw error( literal.getPosition(), errorMsg );
                }
            }
            
        } catch ( ConstraintException e ) {
            ErrorHandler.getInstance().reportError( e );
        }
        
    }
    
}
