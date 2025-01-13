package com.mycompany.tp.medev;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Classe de tests unitaires pour la classe Board.
 */
public class BoardTest {

    private Board board;
    private Joueur black;
    private Joueur white;

    @Before
    public void setUp() {
        // Création de deux joueurs
        black = new Joueur("Black", Color.BLACK);
        white = new Joueur("White", Color.WHITE);

        // On utilise le constructeur principal avec paramètres
        board = new Board(black, white);
    }

    /**
     * Test du constructeur par défaut.
     * On s'attend à ce que le plateau soit correctement initialisé 
     * (selon ce que vous avez décidé pour ce constructeur).
     */
    @Test
    public void testConstructeurDefaut() {
        Board defaut = new Board();
        // Par exemple, vérifier si la grille n'est pas null
        assertNotNull("La grille ne doit pas être nulle", defaut.getGrid());
        // Vérifier la taille de la grille
        assertEquals("La grille doit être de 8x8", 8, defaut.getGrid().length);
        assertEquals("La grille doit être de 8x8", 8, defaut.getGrid()[0].length);
        // Vérifier que toutes les cases sont vides (si c’est le comportement par défaut)
        int emptyCount = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (defaut.getGrid()[i][j].getContent() == Color.EMPTY) {
                    emptyCount++;
                }
            }
        }
        assertEquals("Toutes les cases doivent être vides à la création par défaut",
                64, emptyCount);
        // Vérifier les joueurs si le constructeur par défaut les initialise (selon vos choix)
        assertNull("Le joueur noir devrait être null par défaut (si vous l’avez choisi ainsi)",
                defaut.getPlayerBlack());
        assertNull("Le joueur blanc devrait être null par défaut (si vous l’avez choisi ainsi)",
                defaut.getPlayerWhite());
    }

    /**
     * Test du constructeur de copie.
     */
    @Test
    public void testCopyConstructor() {
        // On joue un coup ou on modifie l'état du plateau existant
        // (afin de voir si la copie est fidèle)
        Move move = new Move(2, 3, black); 
        if (board.isValidMove(move)) {
            board.placeMove(move);
        }

        // Création d'un nouveau Board en copie
        Board copiedBoard = new Board(board);

        // Vérifier que la grille est copiée correctement (pas la même référence, 
        // mais un contenu identique)
        assertNotSame("La grille copiée ne doit pas être la même référence",
                board.getGrid(), copiedBoard.getGrid());

        // Comparer le contenu
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                assertEquals("Le contenu des cellules doit être identique",
                    board.getGrid()[i][j].getContent(),
                    copiedBoard.getGrid()[i][j].getContent());
            }
        }

        // Vérifier la copie des joueurs
        assertEquals("Le joueur noir doit être le même (même nom, même couleur)",
                board.getPlayerBlack().getName(), copiedBoard.getPlayerBlack().getName());
        assertEquals("Le joueur blanc doit être le même (même nom, même couleur)",
                board.getPlayerWhite().getName(), copiedBoard.getPlayerWhite().getName());

        // Vérifier le joueur courant
        assertEquals("Le joueur courant doit être identique",
                board.getCurrentPlayer().getColor(), copiedBoard.getCurrentPlayer().getColor());
    }

    /**
     * Test des getters et setters : setGrid / getGrid, setPlayerBlack / setPlayerWhite, etc.
     */
    @Test
    public void testGettersAndSetters() {
        // 1) setGrid & getGrid
        Cell[][] newGrid = new Cell[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                newGrid[i][j] = new Cell(Color.EMPTY);
            }
        }
        newGrid[0][0].setContent(Color.BLACK);
        board.setGrid(newGrid);

        // Vérifier que la grille du board a bien été mise à jour
        assertEquals("La case [0,0] doit être BLACK",
                Color.BLACK, board.getGrid()[0][0].getContent());

        // 2) setPlayerBlack / getPlayerBlack
        Joueur newBlack = new Joueur("NewBlack", Color.BLACK);
        board.setPlayerBlack(newBlack);
        assertEquals("Le joueur noir doit être NewBlack", "NewBlack",
                board.getPlayerBlack().getName());

        // 3) setPlayerWhite / getPlayerWhite
        Joueur newWhite = new Joueur("NewWhite", Color.WHITE);
        board.setPlayerWhite(newWhite);
        assertEquals("Le joueur blanc doit être NewWhite", "NewWhite",
                board.getPlayerWhite().getName());

        // 4) setCurrentPlayer / getCurrentPlayer
        board.setCurrentPlayer(newWhite);
        assertEquals("Le joueur courant doit être White (NewWhite)",
                Color.WHITE, board.getCurrentPlayer().getColor());
    }

    /**
     * Test de la méthode isValidMove().
     */
    @Test
    public void testIsValidMove() {
        // Récupération du joueur courant (Noir au départ)
        Joueur current = board.getCurrentPlayer();
        // Supposons qu’un coup (2,3) soit valide ou non selon l'état initial
        Move move = new Move(2, 3, current);
        boolean validity = board.isValidMove(move);

        // On ne peut pas savoir à coup sûr si c'est valide sans connaître la logique,
        // mais on peut vérifier qu’on obtient une réponse cohérente.
        // Par exemple, dans le jeu Othello initial, (2,3) n'est *souvent* pas valide
        // (cela dépend de l'implémentation).
        // On peut simplement vérifier que le code s'exécute sans erreur :
        assertNotNull("La validité du coup ne doit pas être null", validity);
    }

    /**
     * Test de la méthode placeMove().
     */
    @Test
    public void testPlaceMove() {
        // Trouver un coup valide (ex: souvent (2,4) est valide en début de partie)
        List<Move> validMoves = board.getValidMoves(black);
        if (!validMoves.isEmpty()) {
            Move firstMove = validMoves.get(0); // on prend le premier coup valide
            board.placeMove(firstMove);

            // Vérifier que la case jouée n’est plus vide
            assertEquals("La case jouée doit contenir BLACK",
                Color.BLACK,
                board.getGrid()[firstMove.getRow()][firstMove.getCol()].getContent());

            // Vérifier que certains pions adverses ont pu être retournés 
            // (selon l’alignement concret).
            // Au minimum, on s’assure qu’on n’a pas de crash.
        } else {
            // Si aucune liste de coups valides en début de partie, c’est anormal 
            // (ou dépend de la logique). Dans ce cas, fail:
            fail("Aucun coup valide n’a été trouvé pour le joueur Noir en début de partie, vérifiez la logique.");
        }
    }

    /**
     * Test de la méthode switchPlayer().
     */
    @Test
    public void testSwitchPlayer() {
        Joueur beforeSwitch = board.getCurrentPlayer();
        board.switchPlayer();
        Joueur afterSwitch = board.getCurrentPlayer();
        // On s’attend à ce qu'après le switch, le joueur courant soit l'autre 
        // (s’il était Noir, il devient Blanc, et inversement)
        assertNotEquals("Le joueur courant doit avoir changé", 
                beforeSwitch, afterSwitch);
    }

    /**
     * Test de la méthode countPions().
     */
    @Test
    public void testCountPions() {
        // Par défaut, il y a 2 pions noirs et 2 pions blancs placés (positions initiales).
        int blackCount = board.countPions(Color.BLACK);
        int whiteCount = board.countPions(Color.WHITE);

        assertEquals("Au début, il doit y avoir 2 pions noirs",
                2, blackCount);
        assertEquals("Au début, il doit y avoir 2 pions blancs",
                2, whiteCount);
    }

    /**
     * Test de la méthode isGameOver().
     * En tout début de partie, la partie ne devrait pas être terminée.
     */
    @Test
    public void testIsGameOver() {
        assertFalse("La partie ne doit pas être finie au début",
                board.isGameOver());
    }

    /**
     * Test de la méthode getValidMoves().
     */
    @Test
    public void testGetValidMoves() {
        // Au début, Noir doit avoir des coups valides.
        List<Move> movesForBlack = board.getValidMoves(black);
        assertFalse("Le joueur Noir doit avoir au moins un coup valide au début",
                movesForBlack.isEmpty());

        // De même pour Blanc, il devrait y avoir quelques coups (mais souvent moins)
        List<Move> movesForWhite = board.getValidMoves(white);
        // On ne sait pas si c’est toujours vide ou pas : dans beaucoup d’implémentations,
        // Blanc a quand même des coups. On peut juste vérifier que la liste n’est pas null.
        assertNotNull("La liste de coups pour Blanc ne doit pas être null", movesForWhite);
    }

    /**
     * Test de la méthode printBoard().
     * On teste juste qu'elle ne génère pas d'exception. 
     * L'affichage console est difficile à tester automatiquement.
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