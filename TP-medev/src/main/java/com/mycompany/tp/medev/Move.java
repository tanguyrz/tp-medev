package com.mycompany.tp.medev;

/**
 *
 * @author tanguy
 */
public class Move {

/**
 * Représente un coup jouable dans le jeu Othello.
 */

    private int row;        // index de ligne (1-8)
    private int col;        // lettre de colonne (a-h)
    private Joueur player;  // joueur qui joue ce coup

    /**
     * Constructeur
     * @param row    int indice de la ligne (entre 0 et 7)
     * @param col    String lettre de colonne (a-h)
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

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void setPlayer(Joueur player) {
        this.player = player;
    }
    
    public Joueur getPlayer() {
        return player;
    }

    public String position() {
        return "Move[" + row + ", " + col + "] by " + player.getName();
    }
}

