package com.mycompany.tp.medev;
import java.util.Random;

/**
 * Représente la couleur d'un pion ou l'absence de pion.
 */
public class Color {
    
    private boolean isWhite;

    /**
    *Constructeur par défaut : initialise la couleur comme blanche avec une chance sur deux
    */   
    public Color() {
      
        Random random = new Random();
        int chance = random.nextInt(2);
        if (chance == 0) {
            this.isWhite = true;
        } 
        else {
            this.isWhite = false;
        }
    }

    /** 
    * Constructeur
    * @param isWhite : boolean renvoit true si la couleur doit être blanche
    */
    public Color(boolean isWhite) {
        this.isWhite = isWhite;
    }

    /**
     * Constructeur de copie 
     * @param color : Couleur du pion
     */ 
    public Color(Color color) {
        this.isWhite=color.isWhite();
    }

    // Getter pour isWhite
    public boolean isWhite() {
        return isWhite;
    }

    // Setter pour isWhite
    public void setWhite(boolean isWhite) {
        this.isWhite = isWhite;
    }
}
