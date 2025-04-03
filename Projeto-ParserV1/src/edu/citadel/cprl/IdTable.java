package edu.citadel.cprl;

import edu.citadel.compiler.ParserException;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

/**
 * Uma versão simplificada de uma tabela de identificadores, também conhecida
 * como tabela de símbolos, usada para armazenar os atributos dos
 * identificadores na linguagem de programação CPRL.
 * 
 * A classe IdTable é implementada como uma pilha de mapas,
 * onde cada mapa associa a string dos identificadores com seu IdType. A
 * pilha é implementada usando uma Lista. Quando um novo escopo é aberto,
 * um novo mapa é empilhado. A busca por uma declaração envolve pesquisar
 * dentro do nível atual, contidas no mapa no topo da pilha, e então dentro
 * de escopos mais externos, ou seja, em mapas abaixo do topo da pilha.
 */
public final class IdTable {

    private List<Map<String, IdType>> table;
    private int currentLevel;

    /**
     * Constrói uma tabela de identificadores vazia com o nível de escopo 
     * iniciando em zero.
     */
    public IdTable() {
        table = new ArrayList<Map<String, IdType>>();
        currentLevel = 0;
        table.add( currentLevel, new HashMap<>() );
    }

    /**
     * Abre um novo escopo para identificadores.
     */
    public void openScope() {
        currentLevel++;
        table.add( currentLevel, new HashMap<>() );
    }

    /**
     * Fecha o escopo mais interno.
     */
    public void closeScope() {
        table.remove( currentLevel );
        currentLevel--;
    }

    /**
     * Insere um token e seu tipo no nível de escopo atual.
     *
     * @throws ParserException se o identificador do token já estiver definido
     * dentro do escopo atual.
     */
    public void add( Token idToken, IdType idType ) throws ParserException {
        
        // assume que idToken é um token de identificador
        assert idToken.getSymbol() == Symbol.identifier :
                "IdTable.add(): The symbol for idToken is not an identifier.";

        Map<String, IdType> idMap = table.get( currentLevel );
        IdType oldDecl = idMap.put( idToken.getText(), idType );

        // verifica se o idendificador não foi definido previamente
        if ( oldDecl != null ) {
            String errorMsg = "Identifier \"" + idToken.getText() +
                              "\" is already defined in the current scope.";
            throw new ParserException( idToken.getPosition(), errorMsg );
        }
        
    }

    /**
     * Retorna o IdType associado com o texto do token do identificador.
     * Retorna null se o identificador não for encontrado. Esse método busca em
     * escopos mais externos caso necessário.
     */
    public IdType get( Token idToken ) {
        
        // assume que idToken é um token de identificador
        assert idToken.getSymbol() == Symbol.identifier :
                "IdTable.get(): The symbol for idToken is not an identifier.";

        IdType idType = null;
        int level = currentLevel;

        while ( level >= 0 && idType == null ) {
            Map<String, IdType> idMap = table.get( level );
            idType = idMap.get( idToken.getText() );
            level--;
        }

        return idType;
        
    }
    
}
