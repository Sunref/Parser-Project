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
public class TesteCorrect202 {
    
    @Test
    public void testeCorrect202() throws IOException, FileNotFoundException {
        
        testar( "TesteCorrect_202",
                "../examples/Correct/Subprograms/Correct_202.cprl",
                "../examples/Correct/Subprograms/Correct_202-Projeto07-Results.txt" );
        
    }
    
}
