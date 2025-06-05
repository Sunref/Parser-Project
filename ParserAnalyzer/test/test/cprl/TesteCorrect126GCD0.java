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
public class TesteCorrect126GCD0 {
    
    @Test
    public void testeCorrect126GCD0() throws IOException, FileNotFoundException {
        
        testar( "TesteCorrect_126GCD0",
                "../examples/Correct/CPRL0/GCD0.cprl",
                "../examples/Correct/CPRL0/GCD0-Projeto06-Results.txt" );
        
    }
    
}
