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
public class TesteCorrect101 {
    
    @Test
    public void testeCorrect101() throws IOException, FileNotFoundException {
        
        testar( "TesteCorrect_101",
                "../examples/Correct/CPRL0/Correct_101.cprl",
                "../examples/Correct/CPRL0/Correct_101-Projeto06-Results.txt" );
        
    }
    
}
