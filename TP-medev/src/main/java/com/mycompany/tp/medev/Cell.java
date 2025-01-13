/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tp.medev;

/**
 * @author tanguy
 */

/**
 * Représente une cellule du plateau.
 */
public class Cell {
    
    private Color content;

    /**
     * Constructeur par défaut
     */
    public Cell() {
        this.content = new Color();
    }

    /**
     * Constructeur de copie.
     * @param color la couleur du pion dans la case 
     */
    public Cell(Color color) {
        this.content = color;
    }

    /**
     * Retourne la couleur de la cellule.
     * @return la couleur de la cellule
     */
    public Color getContent() {
        return content;
    }

    /**
     * Constructeur
     * @param content nouvelle couleur 
     */
    public void setContent(Color content) {
        this.content = content;
    }
}
  
