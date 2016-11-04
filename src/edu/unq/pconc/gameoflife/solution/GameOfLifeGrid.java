package edu.unq.pconc.gameoflife.solution;

import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;

import edu.unq.pconc.gameoflife.CellGrid;

public class GameOfLifeGrid implements CellGrid{

	private Map <Dimension,Cell> liveCells;
	private Map <Dimension,Cell> nextLiveCells;
	private Map <Dimension,Cell> checkedCells;
	
	private Dimension worldSize;
	private int thds;
	private int generation;

	
	public GameOfLifeGrid() {
	}

	@Override
	public boolean getCell(int col, int row) {
		return liveCells.containsKey(new Dimension(col,row));
	}

	@Override
	public void setCell(int col, int row, boolean cell) {
		Dimension dim = new Dimension(col, row);
		if(cell && !getCell(col,row)){
			liveCells.put(dim,new Cell(col,row));
			
		}else{
			if(!cell){
				liveCells.remove(dim);
			}
		}
	}

	@Override
	public Dimension getDimension() {
		return worldSize;
	}

	@Override
	public void resize(int col, int row) {
		if (!(col>=worldSize.getWidth() && row>=worldSize.getHeight())){
			Map <Dimension,Cell> temp = transferLives(col,row);
			liveCells.clear();
			liveCells.putAll(temp);
		}
		worldSize = new Dimension(col,row);
		
	}
	
	private boolean fits (int col, int row, int posibleCol, int posibleRow ){
		return  (0 <= posibleCol && posibleCol <= col ) && (0 <= posibleRow && posibleRow <= row ); 
	}
	
	private Cell cloneCell(int col, int row, Cell celula){
		Cell nueva = new Cell(col, row);
		nueva.setVecinos(celula.getVecinos());
		return nueva;
	}
 	
	private Map <Dimension,Cell> transferLives(int newCol, int newRow) {
		Map <Dimension,Cell> transfer = new HashMap<Dimension, Cell>();
		int deltaCol = (int) ((newCol - worldSize.getWidth())/2);
		int deltaRow = (int) ((newRow - worldSize.getHeight())/2);
		
		for (Map.Entry<Dimension, Cell> entry : liveCells.entrySet()) {
			int posibleCol = (int)entry.getKey().getWidth() + deltaCol;
			int posibleRow = (int)entry.getKey().getHeight() + deltaRow;
			
			if (fits(newCol,newRow,posibleCol, posibleRow)){
				transfer.put(new Dimension(posibleCol, posibleRow),cloneCell(posibleCol, posibleRow,entry.getValue()));
			}
		}
		return transfer;
	}

	
	@Override
	public void clear() {
		liveCells.clear();
		generation = 0;
	}

	@Override
	public void setThreads(int threads) {
		thds = threads;
		
		shareTheLoad();
		
	}

	private void shareTheLoad() {
		
		

	}

	@Override
	public int getGenerations() {
		return generation;
	}

	@Override
	public void next() {
		nextLiveCells.clear();
		generation++;
		
		for (Map.Entry<Dimension, Cell> entry : liveCells.entrySet()) {
			
		}
		
		/*
		for (int i= 0 ; i < worldSize.getWidth(); i++){
			for (int j= 0 ; j < worldSize.getHeight(); j++){
				
			}
		}*/
	
	}

}