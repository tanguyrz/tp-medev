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
     * Constructeur par défaut, pour avoir la case empty
     */
    public Cell() {
        this.content = null;
    }

    /**
     * Constructeur de copie.
     * @param color la couleur du pion dans la case, forcément une couleur
     */
    public Cell(Color color) {
        this.content = color;
    }

    /**
     * Retourne le contenu de la cellule, donc null ou une couleur
     * @return le contenu de la cellule, donc null ou une couleur
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
  
