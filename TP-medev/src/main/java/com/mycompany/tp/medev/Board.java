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
     * Initialise 3 pions "noirs" et 1 pion "blanc" au centre,
     * de manière à avoir (au moins) un coup valide pour Noir.
     */
    public Board(){
        Color w = new Color(true);   // Blanc
        Color b = new Color(false);  // Noir
        
        this.playerBlack = new Joueur(b);
        this.playerWhite = new Joueur(w);
        this.currentPlayer = playerBlack;  // Noir commence

        grid = new Cell[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                grid[i][j] = new Cell();
            }
        }
        initBoard();
    }
    
    /**
     * Constructeur du plateau (paramétré).
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
                grid[i][j] = new Cell();
            }
        }
        initBoard();
    }
    
    /**
     * Constructeur de copie du plateau.
     * Copie également le contenu de la grille.
     *
     * @param b le plateau à copier
     */
    public Board(Board b){
        this.playerBlack = new Joueur(b.playerBlack);   // copie Joueur noir
        this.playerWhite = new Joueur(b.playerWhite);   // copie Joueur blanc
        // On copie aussi le "currentPlayer" sans le forcer à playerBlack
        this.currentPlayer = (b.currentPlayer == b.playerBlack) 
                             ? this.playerBlack 
                             : this.playerWhite;
        
        grid = new Cell[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                // Copie de la Cell
                grid[i][j] = new Cell();
                Color originalColor = b.grid[i][j].getContent();
                if (originalColor != null) {
                    // Copie par valeur : new Color(originalColor)
                    grid[i][j].setContent(new Color(originalColor));
                }
            }
        }
    }

    /**
     * Initialise le plateau avec 3 pions noirs et 1 pion blanc au centre,
     * de sorte qu'il y ait (au moins) un coup possible pour Noir.
     * 
     * On va placer les pions comme suit (indices 0-based) :
     *   (3,3) = Noir
     *   (3,4) = Blanc
     *   (4,3) = Noir
     *   (4,4) = Noir
     *
     * Ainsi, si Noir place un pion en (3,5), il devrait capturer le pion blanc en (3,4).
     */
    private void initBoard() {
        Color noir  = new Color(false);
        Color blanc = new Color(true);
        
        // (3,3) = Noir
        grid[3][3].setContent(noir);
        // (3,4) = Blanc
        grid[3][4].setContent(blanc);
        // (4,3) = Noir
        grid[4][3].setContent(noir);
        // (4,4) = Noir
        grid[4][4].setContent(noir);
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
                
                if (c == null) {
                    // Case vide
                    symbol = '.';
                } else if (c.isWhite()) {
                    // Blanc
                    symbol = 'B';
                } else {
                    // Noir
                    symbol = 'N';
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
     * Vérifie qu'un coup est valide : 
     * - la case doit être vide 
     * - et le coup doit capturer au moins un pion adverse.
     * 
     * @param move le mouvement du pion
     * @return true si le coup est valide, false sinon
     */
    public boolean isValidMove(Move move) {
        int row = move.getRow();
        int col = move.getCol();
        
        if (!isInBounds(row, col)) {
            return false;
        }
        // La case doit être vide
        if (grid[row][col].getContent() != null) {
            return false;
        }
        
        // Vérifier si on capture au moins un pion
        List<int[]> captured = getCapturedPions(move);
        return !captured.isEmpty();
    }

    /**
     * Joue un coup (et retourne les pions capturés).
     *
     * @param move le coup à jouer
     */
    public void placeMove(Move move) {
        List<int[]> captured = getCapturedPions(move);
        // Mettre le pion du joueur sur la case
        Color myColor = move.getPlayer().getColor();
        grid[move.getRow()][move.getCol()].setContent(new Color(myColor)); 
        // Retourner les pions adverses capturés
        for (int[] pos : captured) {
            grid[pos[0]][pos[1]].setContent(new Color(myColor));
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
     * On avance tant qu'on voit des pions de la couleur adverse, puis si on
     * tombe sur un pion de la même couleur que le joueur, on capture tout 
     * ce qu'on a traversé.
     */
    private List<int[]> checkDirection(Move move, int dr, int dc) {
        List<int[]> lineCaptures = new ArrayList<>();
        Color myColor = move.getPlayer().getColor();
        
        int row = move.getRow() + dr;
        int col = move.getCol() + dc;
        
        // On avance tant qu'on est dans les limites, 
        // qu'il y a un pion et que c'est la couleur adverse
        while (isInBounds(row, col) 
               && grid[row][col].getContent() != null
               && grid[row][col].getContent().isWhite() != myColor.isWhite()) {
            lineCaptures.add(new int[] {row, col});
            row += dr;
            col += dc;
        }
        
        // Vérifier si on finit sur un pion de la même couleur
        if (isInBounds(row, col) 
            && grid[row][col].getContent() != null
            && grid[row][col].getContent().isWhite() == myColor.isWhite()) {
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
     * Compare par la valeur (isWhite), pour éviter les problèmes de référence.
     *
     * @param color couleur recherchée
     * @return nombre de pions
     */
    public int countPions(Color color) {
        int count = 0;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                Color cellColor = grid[i][j].getContent();
                // Comparer la "valeur" (isWhite) plutôt que la référence
                if (cellColor != null && cellColor.isWhite() == color.isWhite()) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Vérifie si le plateau est plein ou si plus aucun coup n'est possible 
     * pour personne => alors la partie est finie.
     *
     * @return vrai si la partie est terminée
     */
    public boolean isGameOver() {
        // Vérifier s'il reste des cases vides
        boolean hasEmpty = false;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (grid[i][j].getContent() == null) {
                    hasEmpty = true;
                    break;
                }
            }
            if (hasEmpty) break;
        }
        // Si plus de case vide, c'est forcément terminé
        if (!hasEmpty) {
            return true;
        }

        // Vérifier s'il y a au moins un coup valide pour Noir ou Blanc
        List<Move> movesBlack = getValidMoves(playerBlack);
        List<Move> movesWhite = getValidMoves(playerWhite);
        if (!movesBlack.isEmpty() || !movesWhite.isEmpty()) {
            return false;
        }
        // Plus de coup pour personne
        return true;
    }
    
    // --- GETTERS / SETTERS / DIVERS ---

    /**
     * @return la taille du plateau (8)
     */
    public static int getSIZE() {
        return SIZE;
    }
    
    /**
     * @return la grille (Cell[][])
     */
    public Cell[][] getGrid() {
        return grid;
    }
    
    /**
     * @return le joueur noir
     */
    public Joueur getPlayerBlack() {
        return playerBlack;
    }
    
    /**
     * @return le joueur blanc
     */
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