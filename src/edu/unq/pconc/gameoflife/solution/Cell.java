package edu.unq.pconc.gameoflife.solution;

public class Cell {
	private int y;
	private int x;
	
	private int vecinos;

	public Cell(int col, int row) {
		super();
		x = col;
		y = row;
	}

	public int getVecinos() {
		return vecinos;
	}

	public void setVecinos(int vecinos) {
		this.vecinos = vecinos;
	}
	
}
