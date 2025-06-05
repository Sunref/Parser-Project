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
public class TesteCorrect303 {
    
    @Test
    public void testeCorrect303() throws IOException, FileNotFoundException {
        
        testar( "TesteCorrect_303",
                "../examples/Correct/Arrays/Correct_303.cprl",
                "../examples/Correct/Arrays/Correct_303-Projeto07-Results.txt" );
        
    }
    
}
