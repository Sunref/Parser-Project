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
public class TesteCorrect218Max {
    
    @Test
    public void testeCorrect218Max() throws IOException, FileNotFoundException {
        
        testar( "TesteCorrect_218Max",
                "../examples/Correct/Subprograms/Max.cprl",
                "../examples/Correct/Subprograms/Max-Projeto07-Results.txt" );
        
    }
    
}
