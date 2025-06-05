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
public class TesteCorrect209 {
    
    @Test
    public void testeCorrect209() throws IOException, FileNotFoundException {
        
        testar( "TesteCorrect_209",
                "../examples/Correct/Subprograms/Correct_209.cprl",
                "../examples/Correct/Subprograms/Correct_209-Projeto07-Results.txt" );
        
    }
    
}
