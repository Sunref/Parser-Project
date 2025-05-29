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
public class TesteCorrect401 {
    
    @Test
    public void testeCorrect401() throws IOException, FileNotFoundException {
        
        testar( "TesteCorrect_401",
                "../examples/Correct/ArraysAndProcedures/Correct_401.cprl",
                "../examples/Correct/ArraysAndProcedures/Correct_401-Projeto07-Results.txt" );
        
    }
    
}
