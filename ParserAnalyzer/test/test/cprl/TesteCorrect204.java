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
public class TesteCorrect204 {
    
    @Test
    public void testeCorrect204() throws IOException, FileNotFoundException {
        
        testar( "TesteCorrect_204",
                "../examples/Correct/Subprograms/Correct_204.cprl",
                "../examples/Correct/Subprograms/Correct_204-Projeto07-Results.txt" );
        
    }
    
}
