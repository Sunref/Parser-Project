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
public class TesteEmLote07IncorretosSubprograms {
    
    @Test
    public void testeEmLote07IncorretosSubprograms() throws IOException, FileNotFoundException {
        
        int[] intervalos = { 
            86, 107
        };
        
        TestUtils.testeEmLote( intervalos, true );
        
    }
    
}
