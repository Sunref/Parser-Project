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
public class TesteCorrect217Inc {
    
    @Test
    public void testeCorrect217Inc() throws IOException, FileNotFoundException {
        
        testar( "TesteCorrect_217Inc",
                "../examples/Correct/Subprograms/Inc.cprl",
                "../examples/Correct/Subprograms/Inc-Projeto07-Results.txt" );
        
    }
    
}
