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
public class TesteEmLote06IncorretosCPRL0 {
    
    @Test
    public void testeEmLote06IncorretosCPRL0() throws IOException, FileNotFoundException {
        
        int[] intervalos = {  
            57, 85
        };
        
        TestUtils.testeEmLote( intervalos, true );
        
    }
    
}
