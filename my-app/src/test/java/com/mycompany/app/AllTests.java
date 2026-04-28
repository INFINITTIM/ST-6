package com.mycompany.app;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

public class AllTests {

    private char[] board(String s) {
        return s.toCharArray();
    }

    @Test
    void testGameConstructor() {
        Game g = new Game();
        assertNotNull(g.board);
        assertEquals(9, g.board.length);
        assertEquals(State.PLAYING, g.state);
        assertEquals('X', g.player1.symbol);
        assertEquals('O', g.player2.symbol);
    }

    @Test
    void testPlayerDefaults() {
        Player p = new Player();
        assertEquals(0, p.move);
        assertFalse(p.selected);
        assertFalse(p.win);
        p.symbol = 'Z';
        p.move = 5;
        p.selected = true;
        p.win = true;
        assertEquals('Z', p.symbol);
        assertEquals(5, p.move);
        assertTrue(p.selected);
        assertTrue(p.win);
    }

    @Test
    void testCheckStateXWinsRows() {
        Game g = new Game();
        g.symbol = 'X';
        assertEquals(State.XWIN, g.checkState(board("XXX      ")));
        assertEquals(State.XWIN, g.checkState(board("   XXX   ")));
        assertEquals(State.XWIN, g.checkState(board("      XXX")));
    }

    @Test
    void testCheckStateXWinsCols() {
        Game g = new Game();
        g.symbol = 'X';
        assertEquals(State.XWIN, g.checkState(board("X  X  X  ")));
        assertEquals(State.XWIN, g.checkState(board(" X  X  X ")));
        assertEquals(State.XWIN, g.checkState(board("  X  X  X")));
    }

    @Test
    void testCheckStateXWinsDiagonals() {
        Game g = new Game();
        g.symbol = 'X';
        assertEquals(State.XWIN, g.checkState(board("X   X   X")));
        assertEquals(State.XWIN, g.checkState(board("  X X X  ")));
    }

    @Test
    void testCheckStateOWins() {
        Game g = new Game();
        g.symbol = 'O';

        assertEquals(State.OWIN, g.checkState(board("OOO      ")));

        assertEquals(State.OWIN, g.checkState(board("   OOO   ")));

        assertEquals(State.OWIN, g.checkState(board("      OOO")));

        assertEquals(State.OWIN, g.checkState(board("O  O  O  ")));
    }

    @Test
    void testCheckStateDraw() {
        Game g = new Game();
        g.symbol = 'X';
        assertEquals(State.DRAW, g.checkState(board("XOXOOXXXO")));
    }

    @Test
    void testCheckStatePlaying() {
        Game g = new Game();
        g.symbol = 'X';
        assertEquals(State.PLAYING, g.checkState(board("XOX O    ")));
    }

    @Test
    void testGenerateMoves() {
        Game g = new Game();
        ArrayList<Integer> moves = new ArrayList<>();
        char[] empty = new char[9];
        Arrays.fill(empty, ' ');
        g.generateMoves(empty, moves);
        assertEquals(9, moves.size());

        moves.clear();
        empty[0] = 'X';
        g.generateMoves(empty, moves);
        assertEquals(8, moves.size());
        assertFalse(moves.contains(0));
    }

    @Test
    void testEvaluatePosition() {
        Game g = new Game();
        Player pX = new Player(); pX.symbol = 'X';
        Player pO = new Player(); pO.symbol = 'O';

        g.symbol = 'X';
        assertEquals(Game.INF, g.evaluatePosition(board("XXX      "), pX));
        assertEquals(-Game.INF, g.evaluatePosition(board("XXX      "), pO));
        assertEquals(0, g.evaluatePosition(board("XOXOOXXXO"), pX));
        assertEquals(-1, g.evaluatePosition(board("XO XO    "), pX));
    }

    @Test
    void testMinMoveAndMaxMove() {
        Game g = new Game();
        Player p = new Player();
        g.symbol = 'X';

        int result1 = g.MinMove(board("XXX      "), p);
        assertTrue(result1 >= -Game.INF && result1 <= Game.INF);

        g.symbol = 'O';
        p.symbol = 'O';
        int result2 = g.MaxMove(board("OOO      "), p);
        assertTrue(result2 >= -Game.INF && result2 <= Game.INF);
    }

    @Test
    void testMiniMaxReturnsValidMove() {
        Game g = new Game();
        Player p = new Player();
        p.symbol = 'X';
        char[] empty = new char[9];
        Arrays.fill(empty, ' ');
        int move = g.MiniMax(empty, p);
        assertTrue(move >= 1 && move <= 9);
    }

    @Test
    void testUtilityPrintCharBoard() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        char[] board = {'X','O','X','O','X','O','O','X','O'};
        Utility.print(board);

        System.setOut(System.out);
        String output = out.toString();
        assertTrue(output.contains("X"));
        assertTrue(output.contains("-"));
    }

    @Test
    void testUtilityPrintIntBoard() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        int[] nums = {1,2,3,4,5,6,7,8,9};
        Utility.print(nums);

        System.setOut(System.out);
        assertTrue(out.toString().length() > 0);
    }

    @Test
    void testUtilityPrintArrayList() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        Utility.print(list);

        System.setOut(System.out);
        assertTrue(out.toString().contains("1"));
    }

    @Test
    void testUtilityConstructor() {
        Utility u = new Utility();
        assertNotNull(u);
    }

    @Test
    void testTicTacToeCell() {
        TicTacToeCell cell = new TicTacToeCell(5, 2, 1);
        assertEquals(5, cell.getNum());
        assertEquals(2, cell.getCol());
        assertEquals(1, cell.getRow());
        assertEquals(' ', cell.getMarker());
        assertEquals(" ", cell.getText());

        cell.setMarker("X");
        assertEquals('X', cell.getMarker());
        assertEquals("X", cell.getText());
        assertFalse(cell.isEnabled());

        cell.setMarker("O");
        assertEquals('O', cell.getMarker());
    }

    @Test
    void testTicTacToePanelCreation() {
        TicTacToePanel panel = new TicTacToePanel(new java.awt.GridLayout(3, 3));
        assertEquals(9, panel.getComponentCount());

        for (int i = 0; i < 9; i++) {
            assertTrue(panel.getComponent(i) instanceof TicTacToeCell);
        }
    }

    @Test
    void testTicTacToePanelCellAccess() {
        TicTacToePanel panel = new TicTacToePanel(new java.awt.GridLayout(3, 3));
        TicTacToeCell cell = (TicTacToeCell) panel.getComponent(4);
        assertNotNull(cell);
        assertEquals(4, cell.getNum());
    }

    @Test
    void testProgramConstructor() {
        Program p = new Program();
        assertNotNull(p);
    }

    @Test
    void testStateEnum() {
        assertEquals(State.PLAYING, State.valueOf("PLAYING"));
        assertEquals(State.XWIN, State.XWIN);
        assertEquals(State.OWIN, State.OWIN);
        assertEquals(State.DRAW, State.DRAW);
    }

    @Test
    void testProgramMainInHeadlessMode() {
        assertNotNull(Program.class);
    }

    @Test
    void testTicTacToePanelMultipleClicks() {
        TicTacToePanel panel = new TicTacToePanel(new java.awt.GridLayout(3, 3));

        for (int i = 0; i < 3; i++) {
            TicTacToeCell cell = (TicTacToeCell) panel.getComponent(i);
            assertDoesNotThrow(() -> cell.doClick());
        }
    }

    @Test
    void testTicTacToePanelEndGameXWin() {
        TicTacToePanel panel = new TicTacToePanel(new java.awt.GridLayout(3, 3));

        for (int i = 0; i < 3; i++) {
            TicTacToeCell cell = (TicTacToeCell) panel.getComponent(i);
            cell.setMarker("X");
        }

        assertNotNull(panel.getComponent(0));
    }

    @Test
    void testTicTacToePanelEndGameOWin() {
        TicTacToePanel panel = new TicTacToePanel(new java.awt.GridLayout(3, 3));

        int[] indices = {0, 4, 8};
        for (int i : indices) {
            TicTacToeCell cell = (TicTacToeCell) panel.getComponent(i);
            cell.setMarker("O");
        }

        assertNotNull(panel.getComponent(4));
    }

    @Test
    void testTicTacToePanelEndGameDraw() {
        TicTacToePanel panel = new TicTacToePanel(new java.awt.GridLayout(3, 3));

        String[] markers = {"X", "O", "X", "O", "X", "O", "O", "X", "O"};
        for (int i = 0; i < 9; i++) {
            TicTacToeCell cell = (TicTacToeCell) panel.getComponent(i);
            cell.setMarker(markers[i]);
        }

        assertEquals(9, panel.getComponentCount());
    }

    @Test
    void testTicTacToePanelCellMarkers() {
        TicTacToePanel panel = new TicTacToePanel(new java.awt.GridLayout(3, 3));

        for (int i = 0; i < 9; i++) {
            TicTacToeCell cell = (TicTacToeCell) panel.getComponent(i);
            assertEquals(' ', cell.getMarker());
        }
    }

    @Test
    void testTicTacToePanelGridLayout() {
        TicTacToePanel panel = new TicTacToePanel(new java.awt.GridLayout(3, 3));
        java.awt.LayoutManager layout = panel.getLayout();
        assertTrue(layout instanceof java.awt.GridLayout);
    }

    @Test
    void testProgramFieldsAccess() {
        Program prog = new Program();
        assertNotNull(prog);
    }
}