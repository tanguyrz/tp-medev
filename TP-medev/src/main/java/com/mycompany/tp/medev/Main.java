package com.mycompany.tp.medev;

import java.util.List;
import java.util.Scanner;

/**
 * Classe Main pour lancer une partie d'Othello en console.
 */
public class Main {

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== Jeu d'Othello en console ===");

        Joueur playerBlack = new Joueur("Joueur Noir", Color.BLACK);
        Joueur playerWhite = new Joueur("Joueur Blanc", Color.WHITE);
        Board board = new Board(playerBlack, playerWhite);

        while (!board.isGameOver()) {
            board.printBoard();
            Joueur current = board.getCurrentPlayer();
            System.out.println("C'est le tour de " + current.getName() 
                    + " (" + (current.getColor() == Color.BLACK ? "Noir" : "Blanc") + ")");

            List<Move> validMoves = board.getValidMoves(current);
            if (validMoves.isEmpty()) {
                System.out.println("Aucun coup possible, vous passez votre tour.");
                board.switchPlayer();
                continue;
            }

            System.out.print("Entrez votre coup (ex: d3) ou 'q' pour quitter : ");
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("q")) {
                System.out.println("Partie interrompue par le joueur.");
                break;
            }

            Move chosenMove = parseMove(input, current);
            if (chosenMove == null || !board.isValidMove(chosenMove)) {
                System.out.println("Coup invalide, réessayez.");
                continue;
            }

            board.placeMove(chosenMove);
            board.switchPlayer();
        }

        // Fin de partie
        board.printBoard();
        int blackCount = board.countPions(Color.BLACK);
        int whiteCount = board.countPions(Color.WHITE);
        System.out.println("Partie terminée !");
        System.out.println("Noirs : " + blackCount + " | Blancs : " + whiteCount);

        if (blackCount > whiteCount) {
            System.out.println("Le joueur Noir gagne !");
        } else if (whiteCount > blackCount) {
            System.out.println("Le joueur Blanc gagne !");
        } else {
            System.out.println("Égalité !");
        }
    }

    /**
     * Convertit un coup sous forme "d3" en indices [row, col].
     *
     * @param input  entrée de l'utilisateur
     * @param player joueur courant
     * @return un Move si la conversion est possible, null sinon
     */
    private static Move parseMove(String input, Joueur player) {
        if (input.length() != 2) {
            return null;
        }
        // On suppose que la première lettre correspond à la colonne (a-h), et le chiffre à la ligne (1-8).
        char colChar = input.charAt(0);
        char rowChar = input.charAt(1);

        int col = colChar - 'a'; // 'a' => 0, 'b' => 1, etc.
        int row = (rowChar - '1'); // '1' => 0, '2' => 1, etc.

        if (col < 0 || col > 7 || row < 0 || row > 7) {
            return null;
        }

        return new Move(row, col, player);
    }
}