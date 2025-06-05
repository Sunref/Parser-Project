package test.cprl;

import static test.cprl.TestUtils.executar;

/**
 * Modificação da classe de testes original, facilitando a implementação do
 * Parser por parte dos alunos.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class TestCompiler {

    public static void main( String args[] ) throws Exception {
        
        /* Atenção!
         *
         * Cada linha abaixo executa os processo de compilação completo para um
         * arquivo de código fonte. Você pode descomentar uma linha de cada vez 
         * e ir testando, comparando que é gerado com os arquivos de resultados.
         *
         * Quando achar que está tudo ok, execute o teste (no pacote de testes)
         * correspondente para a verificação de cada caso.
         *
         * Para conferir todo o processo, faça os testes em lote.
         */
        
        
        /**********************************************************
         * TESTES PROGRAMAS CORRETOS
         **********************************************************/
        
        // testes CPRL0
        //executar( "../examples/Correct/CPRL0/Hello.cprl" );           // 01
        //executar( "../examples/Correct/CPRL0/Correct_101.cprl" );     // 02
        //executar( "../examples/Correct/CPRL0/Correct_102.cprl" );     // 03
        //executar( "../examples/Correct/CPRL0/Correct_103.cprl" );     // 04
        //executar( "../examples/Correct/CPRL0/Correct_104.cprl" );     // 05
        //executar( "../examples/Correct/CPRL0/Correct_105.cprl" );     // 06
        //executar( "../examples/Correct/CPRL0/Correct_106.cprl" );     // 07
        //executar( "../examples/Correct/CPRL0/Correct_107.cprl" );     // 08
        //executar( "../examples/Correct/CPRL0/Correct_108.cprl" );     // 09
        //executar( "../examples/Correct/CPRL0/Correct_109.cprl" );     // 10
        //executar( "../examples/Correct/CPRL0/Correct_110.cprl" );     // 11
        //executar( "../examples/Correct/CPRL0/Correct_111.cprl" );     // 12
        //executar( "../examples/Correct/CPRL0/Correct_112.cprl" );     // 13
        //executar( "../examples/Correct/CPRL0/Correct_113.cprl" );     // 14
        //executar( "../examples/Correct/CPRL0/Correct_114.cprl" );     // 15
        //executar( "../examples/Correct/CPRL0/Correct_115.cprl" );     // 16
        //executar( "../examples/Correct/CPRL0/Correct_116.cprl" );     // 17
        //executar( "../examples/Correct/CPRL0/Correct_117.cprl" );     // 18
        //executar( "../examples/Correct/CPRL0/Correct_118.cprl" );     // 19
        //executar( "../examples/Correct/CPRL0/Correct_119.cprl" );     // 20
        //executar( "../examples/Correct/CPRL0/Correct_120.cprl" );     // 21
        //executar( "../examples/Correct/CPRL0/Correct_121.cprl" );     // 22
        //executar( "../examples/Correct/CPRL0/Correct_122.cprl" );     // 23
        //executar( "../examples/Correct/CPRL0/Correct_123.cprl" );     // 24
        //executar( "../examples/Correct/CPRL0/Correct_124.cprl" );     // 25
        //executar( "../examples/Correct/CPRL0/Correct_125.cprl" );     // 26
        //executar( "../examples/Correct/CPRL0/GCD0.cprl" );            // 27
        
        // testes subprogramas
        //executar( "../examples/Correct/Subprograms/Correct_201.cprl" );  // 28
        //executar( "../examples/Correct/Subprograms/Correct_202.cprl" );  // 29
        //executar( "../examples/Correct/Subprograms/Correct_203.cprl" );  // 30
        //executar( "../examples/Correct/Subprograms/Correct_204.cprl" );  // 31
        //executar( "../examples/Correct/Subprograms/Correct_205.cprl" );  // 32
        //executar( "../examples/Correct/Subprograms/Correct_206.cprl" );  // 33
        //executar( "../examples/Correct/Subprograms/Correct_207.cprl" );  // 34
        //executar( "../examples/Correct/Subprograms/Correct_208.cprl" );  // 35
        //executar( "../examples/Correct/Subprograms/Correct_209.cprl" );  // 36
        //executar( "../examples/Correct/Subprograms/Correct_210.cprl" );  // 37
        //executar( "../examples/Correct/Subprograms/Correct_211.cprl" );  // 38
        //executar( "../examples/Correct/Subprograms/Correct_212.cprl" );  // 39
        //executar( "../examples/Correct/Subprograms/Correct_213.cprl" );  // 40
        //executar( "../examples/Correct/Subprograms/Correct_214.cprl" );  // 41
        //executar( "../examples/Correct/Subprograms/Correct_215.cprl" );  // 42
        //executar( "../examples/Correct/Subprograms/GCD.cprl" );          // 43
        //executar( "../examples/Correct/Subprograms/Inc.cprl" );          // 44
        //executar( "../examples/Correct/Subprograms/Max.cprl" );          // 45
        //executar( "../examples/Correct/Subprograms/Abs.cprl" );          // 46
        //executar( "../examples/Correct/Subprograms/Composition.cprl" );  // 47
        //executar( "../examples/Correct/Subprograms/Reverse.cprl" );      // 48
        //executar( "../examples/Correct/Subprograms/Hanoi.cprl" );        // 49
        
        // testes arrays
        //executar( "../examples/Correct/Arrays/Correct_301.cprl" );  // 50
        //executar( "../examples/Correct/Arrays/Correct_302.cprl" );  // 51
        //executar( "../examples/Correct/Arrays/Correct_303.cprl" );  // 52
        //executar( "../examples/Correct/Arrays/Correct_304.cprl" );  // 53
        
        // testes arrays e subprogramas
        //executar( "../examples/Correct/ArraysAndProcedures/Correct_401.cprl" );  // 54
        //executar( "../examples/Correct/ArraysAndProcedures/Sort.cprl" );         // 55
        //executar( "../examples/Correct/ArraysAndProcedures/QuickSort.cprl" );    // 56
        
        
        
        /**********************************************************
         * TESTES PROGRAMAS INCORRETOS
         **********************************************************/
        
        // testes CPRL0
        //executar( "../examples/Incorrect/CPRL0/Incorrect_101.cprl" );  // 57
        //executar( "../examples/Incorrect/CPRL0/Incorrect_102.cprl" );  // 58
        //executar( "../examples/Incorrect/CPRL0/Incorrect_103.cprl" );  // 59
        //executar( "../examples/Incorrect/CPRL0/Incorrect_104.cprl" );  // 60
        //executar( "../examples/Incorrect/CPRL0/Incorrect_105.cprl" );  // 61
        //executar( "../examples/Incorrect/CPRL0/Incorrect_106.cprl" );  // 62
        //executar( "../examples/Incorrect/CPRL0/Incorrect_107.cprl" );  // 63
        //executar( "../examples/Incorrect/CPRL0/Incorrect_108.cprl" );  // 64
        //executar( "../examples/Incorrect/CPRL0/Incorrect_109.cprl" );  // 65
        //executar( "../examples/Incorrect/CPRL0/Incorrect_110.cprl" );  // 66
        //executar( "../examples/Incorrect/CPRL0/Incorrect_111.cprl" );  // 67
        //executar( "../examples/Incorrect/CPRL0/Incorrect_112.cprl" );  // 68
        //executar( "../examples/Incorrect/CPRL0/Incorrect_113.cprl" );  // 69
        //executar( "../examples/Incorrect/CPRL0/Incorrect_114.cprl" );  // 70
        //executar( "../examples/Incorrect/CPRL0/Incorrect_115.cprl" );  // 71
        //executar( "../examples/Incorrect/CPRL0/Incorrect_116.cprl" );  // 72
        //executar( "../examples/Incorrect/CPRL0/Incorrect_117.cprl" );  // 73
        //executar( "../examples/Incorrect/CPRL0/Incorrect_118.cprl" );  // 74
        //executar( "../examples/Incorrect/CPRL0/Incorrect_119.cprl" );  // 75
        //executar( "../examples/Incorrect/CPRL0/Incorrect_120.cprl" );  // 76
        //executar( "../examples/Incorrect/CPRL0/Incorrect_121.cprl" );  // 77
        //executar( "../examples/Incorrect/CPRL0/Incorrect_122.cprl" );  // 78
        //executar( "../examples/Incorrect/CPRL0/Incorrect_123.cprl" );  // 79
        //executar( "../examples/Incorrect/CPRL0/Incorrect_124.cprl" );  // 80
        //executar( "../examples/Incorrect/CPRL0/Incorrect_125.cprl" );  // 81
        //executar( "../examples/Incorrect/CPRL0/Incorrect_126.cprl" );  // 82
        //executar( "../examples/Incorrect/CPRL0/Incorrect_127.cprl" );  // 83
        //executar( "../examples/Incorrect/CPRL0/Incorrect_128.cprl" );  // 84
        //executar( "../examples/Incorrect/CPRL0/Incorrect_129.cprl" );  // 85
        
        // testes subprogramas
        //executar( "../examples/Incorrect/Subprograms/Incorrect_201.cprl" );  // 86
        //executar( "../examples/Incorrect/Subprograms/Incorrect_202.cprl" );  // 87
        //executar( "../examples/Incorrect/Subprograms/Incorrect_203.cprl" );  // 88
        //executar( "../examples/Incorrect/Subprograms/Incorrect_204.cprl" );  // 89
        //executar( "../examples/Incorrect/Subprograms/Incorrect_205.cprl" );  // 90
        //executar( "../examples/Incorrect/Subprograms/Incorrect_206.cprl" );  // 91
        //executar( "../examples/Incorrect/Subprograms/Incorrect_207.cprl" );  // 92
        //executar( "../examples/Incorrect/Subprograms/Incorrect_208.cprl" );  // 93
        //executar( "../examples/Incorrect/Subprograms/Incorrect_209.cprl" );  // 94
        //executar( "../examples/Incorrect/Subprograms/Incorrect_210.cprl" );  // 95
        //executar( "../examples/Incorrect/Subprograms/Incorrect_211.cprl" );  // 96
        //executar( "../examples/Incorrect/Subprograms/Incorrect_212.cprl" );  // 97
        //executar( "../examples/Incorrect/Subprograms/Incorrect_213.cprl" );  // 98
        //executar( "../examples/Incorrect/Subprograms/Incorrect_214.cprl" );  // 99
        //executar( "../examples/Incorrect/Subprograms/Incorrect_215.cprl" );  // 100
        //executar( "../examples/Incorrect/Subprograms/Incorrect_216.cprl" );  // 101
        //executar( "../examples/Incorrect/Subprograms/Incorrect_217.cprl" );  // 102
        //executar( "../examples/Incorrect/Subprograms/Incorrect_218.cprl" );  // 103
        //executar( "../examples/Incorrect/Subprograms/Incorrect_219.cprl" );  // 104
        //executar( "../examples/Incorrect/Subprograms/Incorrect_220.cprl" );  // 105
        //executar( "../examples/Incorrect/Subprograms/Incorrect_221.cprl" );  // 106
        //executar( "../examples/Incorrect/Subprograms/Incorrect_222.cprl" );  // 107
        
        // testes arrays
        //executar( "../examples/Incorrect/Arrays/Incorrect_301.cprl" );  // 108
        //executar( "../examples/Incorrect/Arrays/Incorrect_302.cprl" );  // 109
        //executar( "../examples/Incorrect/Arrays/Incorrect_303.cprl" );  // 110
        //executar( "../examples/Incorrect/Arrays/Incorrect_304.cprl" );  // 111
        //executar( "../examples/Incorrect/Arrays/Incorrect_305.cprl" );  // 112
        
    }
    
}
