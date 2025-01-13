package com.mycompany.tp.medev;
import java.util.Random;

/**
 *
 * @author tanguy
 */
public class Joueur {
    private String name;
    private Color color;

    /**
     * Constructeur par defaut
     */
    public Joueur(){
        
        this.name="Jack";

        Random random = new Random();
        int chance = random.nextInt(2);
        if (chance == 0) {
            this.color = "Blanche";
        } 
        else {
            this.color = "Noir";
        }
    }
    
    /**
     * Constructeur.
     *
     * @param name  nom du joueur
     * @param color couleur du joueur (BLACK ou WHITE)
     */
    public Joueur(String name, Color color) {
        this.name = name;
        this.color = color;
    }

    /**
     * Getter du nom du joueur.
     *
     * @return le nom du joueur
     */
    public String getName() {
        return name;
    }

    /**
     * Getter de la couleur du joueur.
     *
     * @return la couleur du joueur (BLACK ou WHITE)
     */
    public Color getColor() {
        return color;
    }
}

