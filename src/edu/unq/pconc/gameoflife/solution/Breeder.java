package edu.unq.pconc.gameoflife.solution;

import java.awt.Dimension;
import java.util.Map;

public class Breeder extends Thread{

	private Cells alive;
	private Dimension boundaries;
	private Map<Dimension,Integer> portionToReGenerate;
	private Cells posibleGen;

	public void run(){
		this.portionToReGenerate.forEach((k,v)->{
			if(fits(k) && shouldLive(k,v)){
				this.alive.addCell(k, 0);
			}
		});
	}
	
	private boolean shouldLive(Dimension k, Integer v) {
		//Si esta viva y linda con 2 o 3 celulas, o sis esta muerta y linda con 3 celulas vivas
		return (this.alive.isAlive(k) && (v<=3 && v>=2))||(!this.alive.isAlive(k) && v== 3);
	}
	
	private boolean fits(Dimension k){
		return (k.getWidth()<=this.boundaries.getWidth() && k.getHeight()<=this.boundaries.getHeight());
	}
	
	
	public Breeder (Cells living, Map<Dimension, Integer> next, Dimension worldSize, Cells nextLiveCells){
		this.alive = living;
		this.boundaries = worldSize;
		this.posibleGen = nextLiveCells;
		this.portionToReGenerate = next;
	}
	
	
}
