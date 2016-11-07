package edu.unq.pconc.gameoflife.solution;

import java.awt.Dimension;
import java.util.Map;

public class Breeder extends Thread{

	private Cells alive;
	private Map<Dimension,Integer> portionToReGenerate;
	private Cells posibleGen;

	public void run(){
		this.portionToReGenerate.forEach((k,v)->{
			if(shouldLive(k,v)){
				System.out.println("Debe vivir: " + (int)k.getWidth()+" - "+(int)k.getHeight());
				this.alive.addCell(k, 0);
			}else{
				System.out.println("Debe morir: " + (int)k.getWidth()+" - "+(int)k.getHeight());
				this.alive.buryCell(k);
			}
			
		});
	}
	
	private boolean shouldLive(Dimension k, Integer v) {
		//Si esta viva y linda con 2 o 3 celulas, o si esta muerta y linda con 3 celulas vivas
		boolean salida = ((this.alive.isAlive(k) && (v<=3 && v>=2)) || (!this.alive.isAlive(k) && v== 3));
		return salida;
	}	
	
	public Breeder (Cells living, Map<Dimension, Integer> next, Cells nextLiveCells){
		this.alive = living;
		this.posibleGen = nextLiveCells;
		this.portionToReGenerate = next;
	}
	
	
}
