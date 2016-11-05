package edu.unq.pconc.gameoflife.solution;

import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;

import edu.unq.pconc.gameoflife.CellGrid;

public class GameOfLifeGrid implements CellGrid{

	private Cells liveCells;
	private Cells nextLiveCells;
	private Cells checkedCells;

	
	private Dimension worldSize;
	private int thds;
	private int generation;

	
	public GameOfLifeGrid() {
	}

	@Override
	public boolean getCell(int col, int row) {
		return liveCells.isAlive(new Dimension(col,row));
	}

	@Override
	public void setCell(int col, int row, boolean cell) {
		Dimension dim = new Dimension(col, row);
		if(cell && !getCell(col,row)){
			liveCells.addCell(dim,0);			
		}else{
			if(!cell){
				liveCells.buryCell(dim);
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
			Map <Dimension,Integer> temp = transferLives(col,row);
			clear();
			liveCells.addAllCells(temp);
		}
		worldSize = new Dimension(col,row);
	}
	
	private boolean fits (int col, int row, int posibleCol, int posibleRow ){
		return  (0 <= posibleCol && posibleCol <= col ) && (0 <= posibleRow && posibleRow <= row ); 
	}
	
	private Map <Dimension,Integer> transferLives(int newCol, int newRow) {
		Map <Dimension,Integer> transfer = new HashMap<Dimension, Integer>();
		int deltaCol = (int) ((newCol - worldSize.getWidth())/2);
		int deltaRow = (int) ((newRow - worldSize.getHeight())/2);
	
		for (Map.Entry<Dimension, Integer> entry : liveCells.asSet()) {
			int posibleCol = (int)entry.getKey().getWidth() + deltaCol;
			int posibleRow = (int)entry.getKey().getHeight() + deltaRow;
			
			if (fits(newCol,newRow,posibleCol, posibleRow)){
				transfer.put(new Dimension(posibleCol, posibleRow),0);
			}
		}
		return transfer;
	}

	
	@Override
	public void clear() {
		liveCells.clear();
		this.generation = 0;
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
		this.generation++;
		
		for (Map.Entry<Dimension, Integer> entry : liveCells.asSet()) {
			
		}
		
		/*
		for (int i= 0 ; i < worldSize.getWidth(); i++){
			for (int j= 0 ; j < worldSize.getHeight(); j++){
				
			}
		}*/
	
	}

}