package com.mycompany.tp.medev;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests unitaires pour la classe Move.
 */
public class MoveTest {
    
    @Test
    public void testConstructorAndGetters() {
        Color c = new Color(true);
        Joueur player = new Joueur("TestPlayer", c);
        Move move = new Move(3, 5, player);
        
        assertEquals("La ligne doit être 3", 3, move.getRow());
        assertEquals("La colonne doit être 5", 5, move.getCol());
        assertEquals("Le joueur doit être TestPlayer", player, move.getPlayer());
    }
    
    @Test
    public void testSetters() {
        Move move = new Move(0, 0, new Joueur());
        
        move.setRow(7);
        move.setCol(2);
        Joueur j = new Joueur("Player2", new Color(false));
        move.setPlayer(j);
        
        assertEquals("La ligne doit être 7", 7, move.getRow());
        assertEquals("La colonne doit être 2", 2, move.getCol());
        assertEquals("Le joueur doit être Player2", j, move.getPlayer());
    }
    
    @Test
    public void testPosition() {
        Joueur j = new Joueur("ABC", new Color(true));
        Move move = new Move(4, 6, j);
        
        String expected = "Move[4, 6] by ABC";
        assertEquals("position() doit renvoyer Move[4, 6] by ABC", expected, move.position());
    }
}