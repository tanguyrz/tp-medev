package com.mycompany.tp.medev;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests unitaires pour la classe Color.
 */
public class ColorTest {
    
    @Test
    public void testDefaultConstructor() {
        // Le constructeur par défaut choisit aléatoirement true ou false.
        // On ne peut pas prédire le résultat, on teste juste que isWhite() n'est pas null.
        Color c = new Color();
        assertNotNull("isWhite() ne doit pas être null même avec le constructeur par défaut", c.isWhite());
    }
    
    @Test
    public void testConstructorWithBoolean() {
        Color cWhite = new Color(true);
        assertTrue("La couleur doit être blanche (true)", cWhite.isWhite());
        
        Color cBlack = new Color(false);
        assertFalse("La couleur doit être noire (false)", cBlack.isWhite());
    }
    
    @Test
    public void testCopyConstructor() {
        Color original = new Color(true);
        Color copy = new Color(original);
        assertEquals("La couleur copiée doit avoir la même valeur isWhite()", 
                     original.isWhite(), copy.isWhite());
    }
    
    @Test
    public void testSetWhite() {
        Color c = new Color(false);
        assertFalse(c.isWhite());
        
        c.setWhite(true);
        assertTrue("Après setWhite(true), la couleur doit être blanche", c.isWhite());
    }
}