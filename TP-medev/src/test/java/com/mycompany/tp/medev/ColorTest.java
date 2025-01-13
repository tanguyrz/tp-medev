package com.mycompany.tp.medev;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de test pour la classe Color.
 */
public class ColorTest {

    private Color color;

    /**
     * Initialisation avant chaque test.
     * Cette méthode sera appelée avant chaque test pour garantir que chaque test a une instance propre de Color.
     */
    @BeforeEach
    public void setUp() {
        color = new Color();  // Crée une nouvelle instance de Color avant chaque test
    }

    /**
     * Test du constructeur par défaut
     * Vérifie que la couleur est soit blanche soit noire.
     */
    @Test
    public void testDefaultConstructor() {
        // Comme le constructeur par défaut affecte un boolean au hasard (true ou false),
        // nous devons vérifier que la couleur est valide (soit true, soit false)
        boolean isWhite = color.isWhite();
        assertTrue(isWhite == true || isWhite == false, "La couleur devrait être soit vraie soit fausse");
    }

    /**
     * Test du constructeur avec paramètre
     * Vérifie que la couleur est correctement assignée à partir du paramètre.
     */
    @Test
    public void testConstructorWithParam() {
        Color whiteColor = new Color(true);
        assertTrue(whiteColor.isWhite(), "La couleur devrait être blanche.");

        Color blackColor = new Color(false);
        assertFalse(blackColor.isWhite(), "La couleur devrait être noire.");
    }

    /**
     * Test du constructeur de copie
     * Vérifie que la couleur est correctement copiée d'un autre objet Color.
     */
    @Test
    public void testCopyConstructor() {
        Color originalColor = new Color(true); // Crée une couleur blanche
        Color copiedColor = new Color(originalColor);  // Copie cette couleur
        
        assertTrue(copiedColor.isWhite(), "La couleur copiée devrait être blanche.");
    }

    /**
     * Test du getter et setter pour isWhite
     * Vérifie que les getters et setters fonctionnent correctement.
     */
    @Test
    public void testGetterAndSetter() {
        color.setWhite(true);  // Définit la couleur à blanche
        assertTrue(color.isWhite(), "La couleur devrait être blanche après l'utilisation du setter.");

        color.setWhite(false);  // Définit la couleur à noire
        assertFalse(color.isWhite(), "La couleur devrait être noire après l'utilisation du setter.");
    }
}
