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
public class TesteCorrect104 {
    
    @Test
    public void testeCorrect104() throws IOException, FileNotFoundException {
        
        testar( "TesteCorrect_104",
                "../examples/Correct/CPRL0/Correct_104.cprl",
                "../examples/Correct/CPRL0/Correct_104-Projeto06-Results.txt" );
        
    }
    
}
