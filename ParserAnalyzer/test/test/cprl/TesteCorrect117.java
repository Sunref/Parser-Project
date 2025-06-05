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
public class TesteCorrect117 {
    
    @Test
    public void testeCorrect117() throws IOException, FileNotFoundException {
        
        testar( "TesteCorrect_117",
                "../examples/Correct/CPRL0/Correct_117.cprl",
                "../examples/Correct/CPRL0/Correct_117-Projeto06-Results.txt" );
        
    }
    
}
