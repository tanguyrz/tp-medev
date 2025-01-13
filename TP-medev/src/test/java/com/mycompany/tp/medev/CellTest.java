package com.mycompany.tp.medev;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests unitaires pour la classe Cell.
 */
public class CellTest {
    
    @Test
    public void testDefaultConstructor() {
        Cell cell = new Cell();
        assertNull("Par défaut, content doit être null", cell.getContent());
    }
    
    @Test
    public void testConstructorWithColor() {
        Color color = new Color(true);
        Cell cell = new Cell(color);
        assertEquals("Le contenu de la cellule doit être la couleur passée", color, cell.getContent());
    }
    
    @Test
    public void testSetContent() {
        Cell cell = new Cell();
        assertNull(cell.getContent());
        
        Color color = new Color(false);
        cell.setContent(color);
        assertEquals("La cellule doit contenir la couleur qu'on lui a mise", color, cell.getContent());
    }
}