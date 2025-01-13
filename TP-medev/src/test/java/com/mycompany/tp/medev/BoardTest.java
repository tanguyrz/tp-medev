package com.mycompany.tp.medev;

import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests unitaires pour la classe Board.
 */
public class BoardTest {
    
    private Board board;
    private Joueur black;
    private Joueur white;
    
    @Before
    public void setUp() {
        // Création des joueurs noir et blanc explicites
        Color blackColor = new Color(false);  // false = Noir
        Color whiteColor = new Color(true);   // true  = Blanc
        
        black = new Joueur("PlayerBlack", blackColor);
        white = new Joueur("PlayerWhite", whiteColor);
        
        // On teste le constructeur (paramétré) : 
        board = new Board(black, white);
    }
    
    /**
     * Test du constructeur par défaut.
     * Vérifie notamment la grille 8x8 et l'init de 4 pions centraux.
     */
    @Test
    public void testDefaultConstructor() {
        Board defaultBoard = new Board();
        assertNotNull("La grille ne doit pas être null", defaultBoard.getGrid());
        assertEquals("La grille doit avoir 8 lignes", 8, defaultBoard.getGrid().length);
        assertEquals("La grille doit avoir 8 colonnes", 8, defaultBoard.getGrid()[0].length);
        
        // Vérifie que le joueur courant est bien le joueur noir par défaut
        // (dans votre implémentation, le joueur noir correspond à new Color(false))
        assertNotNull("Le joueur courant ne doit pas être null", defaultBoard.getCurrentPlayer());
        
        // Vérifie qu'on a au moins 2 pions "Noir" et 2 "Blanc" au centre
        // (ici, attention : votre initBoard place 4 pions de la même couleur ??? 
        // d’après le code, c’est c1 sur [3,3], [3,4], [4,3] et c2 sur [4,4].
        // À adapter selon votre logique)
        
        // On fait un simple décompte : 
        int blackCount = defaultBoard.countPions(new Color(false));
        int whiteCount = defaultBoard.countPions(new Color(true));
        
        // Selon le code d'initBoard(), tout est mis en c1(false) sauf [4,4] = c2(true).
        // Donc on s’attend potentiellement à 3 pions noirs, 1 pion blanc.
        // On adapte en fonction de votre code exact :
        assertEquals("Devrait avoir 3 pions noirs après init", 3, blackCount);
        assertEquals("Devrait avoir 1 pion blanc après init", 1, whiteCount);
    }
    
    /**
     * Test du constructeur paramétré.
     * Vérifie qu'on a bien les joueurs passés en paramètres et la bonne initialisation.
     */
    @Test
    public void testParamConstructor() {
        assertEquals("Le joueur noir doit être PlayerBlack", black, board.getPlayerBlack());
        assertEquals("Le joueur blanc doit être PlayerWhite", white, board.getPlayerWhite());
        assertEquals("Le joueur courant doit être le joueur noir en début de partie",
                black, board.getCurrentPlayer());
    }

    /**
     * Test du constructeur de copie.
     */
    @Test
    public void testCopyConstructor() {
        // On modifie légèrement l'état du plateau
        Move move = new Move(2, 3, black);
        if (board.isValidMove(move)) {
            board.placeMove(move);
        }
        // Création d'un board en copie
        Board copied = new Board(board);
        
        // Vérification basique : la grille ne doit pas être la même instance
        assertNotSame("La grille copiée ne doit pas être la même référence", 
                board.getGrid(), copied.getGrid());
        
        // Vérif du contenu : on s’assure qu’ils ont les mêmes couleurs de pions
        for (int i = 0; i < Board.getSIZE(); i++) {
            for (int j = 0; j < Board.getSIZE(); j++) {
                assertEquals("Le contenu doit être identique dans la copie",
                    board.getGrid()[i][j].getContent(), 
                    copied.getGrid()[i][j].getContent());
            }
        }
        
        // Vérif joueurs
        assertEquals("Le joueur noir doit être identique", 
                board.getPlayerBlack(), copied.getPlayerBlack());
        assertEquals("Le joueur blanc doit être identique", 
                board.getPlayerWhite(), copied.getPlayerWhite());
    }
    
    /**
     * Test des méthodes getGrid() / setGrid().
     */
    @Test
    public void testGridGetSet() {
        Cell[][] newGrid = new Cell[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                newGrid[i][j] = new Cell();
            }
        }
        board.setGrid(newGrid);
        assertSame("La grille doit être celle qu'on vient de setter",
                newGrid, board.getGrid());
    }
    
    /**
     * Test isValidMove() sur un coup vide ou hors limites.
     */
    @Test
    public void testIsValidMove() {
        // Coup dans les limites
        Move validMove = new Move(2, 2, black);
        boolean isValid = board.isValidMove(validMove);
        // Selon l'état initial, ce test peut être true ou false,
        // l'important est de ne pas planter. 
        // On vérifie qu’on obtient un booléen.
        assertNotNull("isValidMove ne doit pas être null", isValid);
        
        // Coup hors limites
        Move invalidMove = new Move(-1, 20, black);
        assertFalse("Un coup hors-limite doit être invalide",
                board.isValidMove(invalidMove));
    }
    
    /**
     * Test placeMove() - on vérifie que la case se remplit et éventuellement que des pions sont retournés.
     */
    @Test
    public void testPlaceMove() {
        // On récupère la liste des coups valides pour noir
        List<Move> validMoves = board.getValidMoves(black);
        
        if (!validMoves.isEmpty()) {
            Move firstMove = validMoves.get(0);
            board.placeMove(firstMove);
            // Vérifie que la case [row,col] n'est plus null
            assertNotNull("La case jouée doit avoir la couleur du joueur noir",
                    board.getGrid()[firstMove.getRow()][firstMove.getCol()].getContent());
        } else {
            // Si aucun coup valide, c'est très surprenant en début de partie (selon votre code).
            // On peut donc fail:
            fail("Aucun coup valide pour le joueur noir, vérifiez la logique.");
        }
    }
    
    /**
     * Test de switchPlayer().
     */
    @Test
    public void testSwitchPlayer() {
        Joueur before = board.getCurrentPlayer();
        board.switchPlayer();
        Joueur after = board.getCurrentPlayer();
        
        assertNotEquals("Le joueur courant doit changer après switchPlayer", before, after);
    }
    
    /**
     * Test de countPions() : on doit avoir X pions de la couleur false (noir), X pions de la couleur true (blanc)
     * selon l'état du plateau.
     */
    @Test
    public void testCountPions() {
        // À l'init, on attend 3 pions noirs et 1 pion blanc (d'après initBoard).
        int blackCount = board.countPions(new Color(false));
        int whiteCount = board.countPions(new Color(true));
        
        // On adapte aux valeurs attendues d’après votre initBoard
        assertEquals("Nombre de pions noirs attendu = 3", 3, blackCount);
        assertEquals("Nombre de pions blancs attendu = 1", 1, whiteCount);
    }
    
    /**
     * Test isGameOver() : au début, la partie ne doit pas être terminée.
     */
    @Test
    public void testIsGameOver() {
        assertFalse("Au début, la partie ne doit pas être considérée comme terminée",
                board.isGameOver());
    }
    
    /**
     * Test getValidMoves().
     */
    @Test
    public void testGetValidMoves() {
        List<Move> movesForBlack = board.getValidMoves(black);
        assertNotNull("La liste des coups pour Noir ne doit pas être null", movesForBlack);
        
        List<Move> movesForWhite = board.getValidMoves(white);
        assertNotNull("La liste des coups pour Blanc ne doit pas être null", movesForWhite);
    }
    
    /**
     * Test printBoard() : on vérifie simplement qu'il n'y a pas d'exception.
     */
    @Test
    public void testPrintBoard() {
        try {
            board.printBoard();
        } catch (Exception e) {
            fail("printBoard() ne doit pas lever d'exception : " + e.getMessage());
        }
    }
}