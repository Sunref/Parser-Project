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
public class TesteCorrect221Reverse {
    
    @Test
    public void testeCorrect221Reverse() throws IOException, FileNotFoundException {
        
        testar( "TesteCorrect_221Reverse",
                "../examples/Correct/Subprograms/Reverse.cprl",
                "../examples/Correct/Subprograms/Reverse-Projeto07-Results.txt" );
        
    }
    
}
