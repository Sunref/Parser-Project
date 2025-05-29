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
public class TesteCorrect222Hanoi {
    
    @Test
    public void testeCorrect222Hanoi() throws IOException, FileNotFoundException {
        
        testar( "TesteCorrect_222Hanoi",
                "../examples/Correct/Subprograms/Hanoi.cprl",
                "../examples/Correct/Subprograms/Hanoi-Projeto07-Results.txt" );
        
    }
    
}
