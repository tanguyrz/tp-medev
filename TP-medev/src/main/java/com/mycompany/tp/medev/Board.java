package com.mycompany.tp.medev;

import java.util.ArrayList;
import java.util.List;

/**
 * Gère la logique du plateau et du jeu Othello.
 */
public class Board {

    private static final int SIZE = 8;
    private Cell[][] grid;
    
    private Joueur playerBlack;
    private Joueur playerWhite;
    private Joueur currentPlayer;
    
    /**
     * Constructeur par défaut du plateau.
     */
    public Board(){
        this.playerBlack = new Joueur();
        this.playerWhite = new Joueur();
        this.currentPlayer = playerBlack;
        grid = new Cell[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                grid[i][j] = new Cell(Color.EMPTY);
            }
        }
        initBoard();
    }
    
    /**
     * Constructeur du plateau.
     *
     * @param playerBlack joueur noir
     * @param playerWhite joueur blanc
     */
    public Board(Joueur playerBlack, Joueur playerWhite) {
        this.playerBlack = playerBlack;
        this.playerWhite = playerWhite;
        this.currentPlayer = playerBlack; // Noir commence

        grid = new Cell[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                grid[i][j] = new Cell(Color.EMPTY);
            }
        }
        initBoard();
    }
    
    /**
     * Constructeur de copie du plateau.
     *
     * @param b un plateau b
     */
    
    public Board(Board b){
        this.playerBlack = b.playerBlack;
        this.playerWhite = b.playerWhite;
        this.currentPlayer = b.playerBlack; // Noir commence
        grid = new Cell[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                grid[i][j] = new Cell(Color.EMPTY);
            }
        }
        initBoard();
    }

    /**
     * Initialise le plateau avec les 4 pions centraux.
     */
    private void initBoard() {
        // Positions de départ : e4, d5 pour Noir (en indices => d4 => (3,3), e4 => (3,4), etc.)
        // Indice 3,4 correspond au 4ème rang (row=3), 5ème colonne (col=4)...
        // On s'aligne sur la convention (0-based) :
        
        // D4 (row=3, col=3) => Blanc
        grid[3][3].setContent(Color.WHITE);
        // E4 (row=3, col=4) => Noir
        grid[3][4].setContent(Color.BLACK);
        // D5 (row=4, col=3) => Noir
        grid[4][3].setContent(Color.BLACK);
        // E5 (row=4, col=4) => Blanc
        grid[4][4].setContent(Color.WHITE);
    }

    /**
     * Affiche le plateau en console.
     */
    public void printBoard() {
        System.out.println("   a  b  c  d  e  f  g  h");
        for (int i = 0; i < SIZE; i++) {
            System.out.print((i+1) + " ");
            for (int j = 0; j < SIZE; j++) {
                Color c = grid[i][j].getContent();
                char symbol;
                switch (c) {
                    case BLACK:
                        symbol = 'N';
                        break;
                    case WHITE:
                        symbol = 'B';
                        break;
                    default:
                        symbol = '.';
                }
                System.out.print(" " + symbol + " ");
            }
            System.out.println(" " + (i+1));
        }
        System.out.println("   a  b  c  d  e  f  g  h");
    }

    /**
     * Retourne la liste de coups valides pour le joueur donné.
     *
     * @param player le joueur pour lequel on cherche les coups
     * @return la liste de coups valides
     */
    public List<Move> getValidMoves(Joueur player) {
        List<Move> moves = new ArrayList<>();
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                Move m = new Move(row, col, player);
                if (isValidMove(m)) {
                    moves.add(m);
                }
            }
        }
        return moves;
    }

    /**
     * Vérifie qu'un coup est valide (case vide + capture au moins un pion).
     */
    public boolean isValidMove(Move move) {
        int row = move.getRow();
        int col = move.getCol();
        if (!isInBounds(row, col) || grid[row][col].getContent() != Color.EMPTY) {
            return false;
        }
        // Vérifier si le coup capture au moins un pion
        if (getCapturedPions(move).isEmpty()) {
            return false;
        }
        return true;
    }

    /**
     * Joue un coup (et retourne les pions capturés).
     *
     * @param move le coup à jouer
     */
    public void placeMove(Move move) {
        List<int[]> captured = getCapturedPions(move);
        // Mettre le pion du joueur sur la case
        grid[move.getRow()][move.getCol()].setContent(move.getPlayer().getColor());
        // Retourner les pions adverses capturés
        for (int[] pos : captured) {
            grid[pos[0]][pos[1]].setContent(move.getPlayer().getColor());
        }
    }

    /**
     * Retourne la liste des positions (row,col) des pions adverses capturés si on joue le coup donné.
     *
     * @param move coup en question
     * @return liste des positions capturées
     */
    private List<int[]> getCapturedPions(Move move) {
        List<int[]> captured = new ArrayList<>();
        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                if (!(dr == 0 && dc == 0)) {
                    List<int[]> lineCaptures = checkDirection(move, dr, dc);
                    captured.addAll(lineCaptures);
                }
            }
        }
        return captured;
    }

    /**
     * Vérifie dans une direction donnée si on capture des pions adverses.
     */
    private List<int[]> checkDirection(Move move, int dr, int dc) {
        List<int[]> lineCaptures = new ArrayList<>();
        Color myColor = move.getPlayer().getColor();
        int row = move.getRow() + dr;
        int col = move.getCol() + dc;
        // Avancer tant qu'on est dans les limites et qu'on trouve des pions adverses
        while (isInBounds(row, col) && grid[row][col].getContent() != Color.EMPTY 
                && grid[row][col].getContent() != myColor) {
            lineCaptures.add(new int[] {row, col});
            row += dr;
            col += dc;
        }
        // Vérifier si on finit sur un pion de la même couleur
        if (isInBounds(row, col) && grid[row][col].getContent() == myColor) {
            // On capture la ligne
            return lineCaptures;
        } else {
            // Sinon, pas de capture
            return new ArrayList<>();
        }
    }

    /**
     * Vérifie si l'indice (row, col) est dans les limites du plateau.
     */
    private boolean isInBounds(int row, int col) {
        return (row >= 0 && row < SIZE && col >= 0 && col < SIZE);
    }

    /**
     * Retourne le joueur courant.
     *
     * @return le joueur courant
     */
    public Joueur getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Passe au joueur suivant.
     */
    public void switchPlayer() {
        if (currentPlayer == playerBlack) {
            currentPlayer = playerWhite;
        } else {
            currentPlayer = playerBlack;
        }
    }

    /**
     * Retourne le nombre de pions pour une couleur donnée.
     *
     * @param color couleur recherchée
     * @return nombre de pions
     */
    public int countPions(Color color) {
        int count = 0;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (grid[i][j].getContent() == color) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Vérifie si le plateau est plein ou si plus aucun coup n'est possible pour personne.
     *
     * @return vrai si la partie est terminée
     */
    public boolean isGameOver() {
        // Vérifier s'il reste des cases vides
        boolean hasEmpty = false;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (grid[i][j].getContent() == Color.EMPTY) {
                    hasEmpty = true;
                    break;
                }
            }
            if (hasEmpty) break;
        }
        if (!hasEmpty) {
            return true;
        }

        // Vérifier s'il y a au moins un coup valide pour un des deux joueurs
        if (!getValidMoves(playerBlack).isEmpty() || !getValidMoves(playerWhite).isEmpty()) {
            return false;
        }
        return true;
    }

    public static int getSIZE() {
        return SIZE;
    }

    public Cell[][] getGrid() {
        return grid;
    }

    public Joueur getPlayerBlack() {
        return playerBlack;
    }

    public Joueur getPlayerWhite() {
        return playerWhite;
    }

    public void setGrid(Cell[][] grid) {
        this.grid = grid;
    }

    public void setPlayerBlack(Joueur playerBlack) {
        this.playerBlack = playerBlack;
    }

    public void setPlayerWhite(Joueur playerWhite) {
        this.playerWhite = playerWhite;
    }

    public void setCurrentPlayer(Joueur currentPlayer) {
        this.currentPlayer = currentPlayer;
    }
    
    
}
