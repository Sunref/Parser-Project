/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.cprl;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.junit.Test;

/**
 *
 * @author Prof. Dr. David Buzatto
 */
public class TesteEmLote05TodosCorretos {
    
    @Test
    public void testeEmLote05TodosCorretos() throws IOException, FileNotFoundException {
        
        int[] intervalos = { 
            1, 56
        };
        
        TestUtils.testeEmLote( intervalos, true );
        
    }
    
}
