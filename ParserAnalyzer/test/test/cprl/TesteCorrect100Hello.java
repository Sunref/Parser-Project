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
public class TesteCorrect100Hello {
    
    @Test
    public void testeCorrect100Hello() throws IOException, FileNotFoundException {
        
        testar( "TesteCorrect_100Hello",
                "../examples/Correct/CPRL0/Hello.cprl",
                "../examples/Correct/CPRL0/Hello-Projeto06-Results.txt" );
        
    }
    
}
