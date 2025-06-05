/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.cprl;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.junit.Test;
import static test.cprl.TestUtils.testar;

/**
 *
 * @author Prof. Dr. David Buzatto
 */
public class TesteCorrect220Composition {
    
    @Test
    public void testeCorrect220Composition() throws IOException, FileNotFoundException {
        
        testar( "TesteCorrect_220Composition",
                "../examples/Correct/Subprograms/Composition.cprl",
                "../examples/Correct/Subprograms/Composition-Projeto07-Results.txt" );
        
    }
    
}
