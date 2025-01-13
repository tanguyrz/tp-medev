
package com.mycompany.tp.medev;

/**
 *
 * @author tanguy
 */
public class Move {

/**
 * Représente un coup jouable dans le jeu Othello.
 */

    private int row;        // index de ligne (0-7)
    private int col;        // index de colonne (0-7)
    private Joueur player;  // joueur qui joue ce coup

    /**
     * Constructeur
     * 
     * @param row    indice de la ligne (entre 0 et 7)
     * @param col    indice de la colonne (entre 0 et 7)
     * @param player joueur qui joue ce coup
     */
    public Move(int row, int col, Joueur player) {
        this.row = row;
        this.col = col;
        this.player = player;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public Joueur getPlayer() {
        return player;
    }

    @Override
    public String toString() {
        return "Move[" + row + ", " + col + "] by " + player.getName();
    }
}

