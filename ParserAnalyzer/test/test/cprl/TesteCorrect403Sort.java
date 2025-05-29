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
public class TesteCorrect403Sort {
    
    @Test
    public void testeCorrect403Sort() throws IOException, FileNotFoundException {
        
        testar( "TesteCorrect_403Sort",
                "../examples/Correct/ArraysAndProcedures/Sort.cprl",
                "../examples/Correct/ArraysAndProcedures/Sort-Projeto07-Results.txt" );
        
    }
    
}
