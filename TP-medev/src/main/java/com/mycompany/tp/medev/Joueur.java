/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tp.medev;

/**
 *
 * @author tanguy
 */
public class Joueur {
    private String name;
    private Color color;

    /**
     * Constructeur.
     *
     * @param name  nom du joueur
     * @param color couleur du joueur (BLACK ou WHITE)
     */
    public Player(String name, Color color) {
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

