package com.mycompany.tp.medev;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests unitaires pour la classe Joueur.
 */
public class JoueurTest {
    
    @Test
    public void testDefaultConstructor() {
        Joueur j = new Joueur();
        // Nom = "Jack", color aléatoire (voir Color())
        assertNotNull("Le nom ne doit pas être null", j.getName());
        assertNotNull("La couleur ne doit pas être null", j.getColor());
    }
    
    @Test
    public void testConstructorWithColor() {
        Color c = new Color(true);
        Joueur j = new Joueur(c);
        assertEquals("Le nom par défaut est Jack", "Jack", j.getName());
        // Couleur doit être la même
        assertEquals("La couleur doit être celle passée au constructeur", c, j.getColor());
    }
    
    @Test
    public void testConstructorWithNameAndColor() {
        Color c = new Color(false);
        Joueur j = new Joueur("Alice", c);
        assertEquals("Le nom doit être Alice", "Alice", j.getName());
        assertEquals("La couleur doit être noire (false)", c, j.getColor());
    }
    
    @Test
    public void testCopyConstructor() {
        Joueur original = new Joueur("Bob", new Color(true));
        Joueur copy = new Joueur(original);
        assertEquals("Le nom du joueur copié doit être identique", 
                     original.getName(), copy.getName());
        assertEquals("La couleur doit être identique", 
                     original.getColor(), copy.getColor());
    }
    
    @Test
    public void testSetName() {
        Joueur j = new Joueur();
        j.setName("Paul");
        assertEquals("Le nom doit être Paul", "Paul", j.getName());
    }
    
    @Test
    public void testSetColor() {
        Joueur j = new Joueur();
        Color newColor = new Color(false);
        j.setColor(newColor);
        assertEquals("La couleur doit être celle qu'on a set", newColor, j.getColor());
    }
}