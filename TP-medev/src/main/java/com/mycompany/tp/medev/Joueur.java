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
        this.color=new Color();
    }
    
    /**
     * Constructeur.
     * @param name  nom du joueur
     * @param color couleur du joueur (BLACK ou WHITE)
     */
    public Joueur(String name, Color color) {
        this.name = name;
        this.color = color;
    }
    
    /**
     * Constructeur de copie
     * @param joueur 
     */
    public Joueur(Joueur joueur){
        this.name=joueur.getName();
        this.color=joueur.getColor();
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

    public void setName(String name) {
        this.name = name;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}

