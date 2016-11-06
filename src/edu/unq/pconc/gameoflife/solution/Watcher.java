package edu.unq.pconc.gameoflife.solution;

import java.awt.Dimension;
import java.util.Map;

public class Watcher extends Thread {
	
	private Cells alive;
	private Cells posibleNext;
	private Map<Dimension,Integer> portionToWatch;
	
	public void run(){
		this.portionToWatch.forEach((k,v)->{
			setPosibles(k.getWidth()-1,k.getHeight()-1);
			setPosibles(k.getWidth()-1,k.getHeight());
			setPosibles(k.getWidth()-1,k.getHeight()+1);
			setPosibles(k.getWidth(),k.getHeight()-1);
			setPosibles(k.getWidth(),k.getHeight()+1);
			setPosibles(k.getWidth()+1,k.getHeight()-1);
			setPosibles(k.getWidth()+1,k.getHeight());
			setPosibles(k.getWidth()+1,k.getHeight()+1);			
		});
		
	}
	private void setPosibles(double width, double height) {
		Dimension dim =new Dimension();
		dim.setSize(width, height);
		if (posibleNext.isAlive(dim)){
			posibleNext.addCompanion(dim);
		}else{
			posibleNext.addCell(dim,1);
		}
		
	}
	public Watcher (Cells todas, Map<Dimension,Integer> toWatch, Cells posiblelives){
		this.posibleNext = posiblelives;
		this.alive = todas;
		this.portionToWatch = toWatch;
	}
}
