package me.livanec.don.tic_tac.helper;

public class GameController {
	private char[][] board = new char[3][3];
	/* id for winner */
	private char winner;
	/* is game finished */
	private boolean isGameOver;
	/* player x or player y turn */
	private char player_turn;

	/** Constructor to initialize board */
	public GameController() {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				board[i][j] = ' ';
			}
		}
		/** not game over */
		isGameOver = false;
		/** every time player x starts */
		player_turn = 'X';
		winner = ' ';
	}

	/**
	 * ---------------------------setters & getters-----------------------------
	 */
	public char[][] getBoard() {
		return board;
	}

	public void setBoard(char[][] board) {
		this.board = board;
	}

	public char getWinner() {
		return winner;
	}

	public void setWinner(char winner) {
		this.winner = winner;
	}

	public boolean isGameOver() {
		return isGameOver;
	}

	public void setGameOver(boolean isGameOver) {
		this.isGameOver = isGameOver;
	}

	public char getPlayer_turn() {
		return player_turn;
	}

	public void setPlayer_turn(char player_turn) {
		this.player_turn = player_turn;
	}

	/**
	 * --------------------------------end--------------------------------------
	 * -
	 */

	public boolean setIfValid(int i, int j) {
		if (!isGameOver && winner == ' ') {
			if (board[i][j] == ' ') {
				System.out.println("is valid");
				board[i][j] = player_turn;

				// check if game over or winner
				isGameOver = check_gameover();
				checkWinner();
				if (!isGameOver && winner == ' ') {
					if (player_turn == 'X')
						player_turn = 'O';
					else
						player_turn = 'X';
				}
				return true;
			} else
				return false;
		} else
			return false;
	}

	public boolean check_gameover() {
		System.out.println("check gameover");
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				if (board[i][j] == ' ') {
					return false;
				}
			}
		}
		return true;
	}

	/** check winner */
	public void checkWinner() {
		System.out.println("check winner");
		// check rows
		for (int i = 0; i < board.length; i++) {
			if (board[i][0] == player_turn && board[i][1] == player_turn
					&& board[i][2] == player_turn) {
				winner = player_turn;
			}
		}
		// check columns
		for (int i = 0; i < board.length; i++) {
			if (board[0][i] == player_turn && board[1][i] == player_turn
					&& board[2][i] == player_turn) {
				winner = player_turn;
			}
		}
		// check diagonals
		if (board[0][0] == player_turn && board[1][1] == player_turn
				&& board[2][2] == player_turn) {
			winner = player_turn;
		}

		if (board[0][2] == player_turn && board[1][1] == player_turn
				&& board[2][0] == player_turn) {
			winner = player_turn;
		}
		
		
		//put score
		System.out.println(winner);

	}

}
