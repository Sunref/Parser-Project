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
public class TesteEmLote01CorretosCPRL0 {
    
    @Test
    public void testeEmLote01CorretosCPRL0() throws IOException, FileNotFoundException {
        
        int[] intervalos = {  
            1, 27
        };
        
        TestUtils.testeEmLote( intervalos, true );
        
    }
    
}
