package edu.unq.pconc.gameoflife.solution;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import edu.unq.pconc.gameoflife.CellGrid;

public class GameOfLifeGrid implements CellGrid {

	private Cells liveCells;
	private Cells nextLiveCells;

	
	private Dimension worldSize;
	private int thds;
	private int generation;

	
	public GameOfLifeGrid() {
		this.worldSize = new Dimension(0,0);
		this.liveCells = new Cells();
		this.nextLiveCells = new Cells();
	}

	@Override
	public boolean getCell(int col, int row) {
		return this.liveCells.isAlive(new Dimension(col,row));
	}

	@Override
	public void setCell(int col, int row, boolean cell) {
		Dimension dim = new Dimension(col, row);
		if(cell && !getCell(col,row)){
			this.liveCells.addCell(dim,0);			
		}else{
			if(!cell){
				this.liveCells.buryCell(dim);
			}
		}
	}

	@Override
	public Dimension getDimension() {
		return this.worldSize;
	}

	@Override
	public void resize(int col, int row) {
		if (!(col>=worldSize.getWidth() && row>=worldSize.getHeight())){
			Map <Dimension,Integer> temp = transferLives(col,row);
			clear();
			this.liveCells.addAllCells(temp);
		}
		this.worldSize = new Dimension(col,row);
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
		this.thds = threads;		
	}

	private List<Map<Dimension,Integer>> shareTheLoad(Set<Entry<Dimension, Integer>> set) {
		
		List<Map<Dimension,Integer>> salida = new ArrayList <Map<Dimension,Integer>>();
		int grups = (set.size()/this.thds);
		if (grups == 0){
			grups =1;
		}
		int cont = 0;
		for(Map.Entry<Dimension, Integer> entry : set){
			if(cont < grups){
				Map<Dimension, Integer> nuevo =new HashMap<Dimension, Integer>();
				nuevo.put(entry.getKey(), entry.getValue());
				salida.add(nuevo);
				cont++;
			}else{
				cont=0;
			}
		}
		System.out.println("set:" + set.toString());
		System.out.println("ShareTheLoad: "+ salida.toString());
		return salida;
	}

	@Override
	public int getGenerations() {
		return generation;
	}

	@Override
	public void next() {
		nextLiveCells.clear();
		
		this.generation++;

		List<Map<Dimension, Integer>> cellshood = shareTheLoad(this.liveCells.asSet());
		//Checkeo alrededor de los vivos, por posibles nuevas celulas. Para esto disparo threads Watchers
		for (Map<Dimension,Integer> cells : cellshood) {
			System.out.println("Map por watcher: "+ cells.toString());
			Watcher w= new Watcher(cells, this.worldSize, this.nextLiveCells);
			w.start();
		}

		List<Map<Dimension, Integer>> nextGen = shareTheLoad(this.nextLiveCells.asSet());
		for(Map<Dimension,Integer> next : nextGen){
			Breeder god = new Breeder(this.liveCells, next, this.nextLiveCells);
			god.start();
		}	
	}

}