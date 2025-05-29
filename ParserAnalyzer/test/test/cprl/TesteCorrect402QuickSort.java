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
public class TesteCorrect402QuickSort {
    
    @Test
    public void testeCorrect402QuickSort() throws IOException, FileNotFoundException {
        
        testar( "TesteCorrect_402QuickSort",
                "../examples/Correct/ArraysAndProcedures/QuickSort.cprl",
                "../examples/Correct/ArraysAndProcedures/QuickSort-Projeto07-Results.txt" );
        
    }
    
}
