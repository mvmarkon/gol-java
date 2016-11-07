package edu.unq.pconc.gameoflife.solution;

import java.awt.Dimension;
import java.util.Map;

public class Watcher extends Thread {
	
	private Cells posibleNext;
	private Dimension boundaries;
	private Map<Dimension,Integer> portionToWatch;
	
	public void run(){
		for(Map.Entry<Dimension, Integer> entry : this.portionToWatch.entrySet()){
			Dimension k = entry.getKey();
//			if(posibleNext.isAlive(k)){
//				posibleNext.addCompanion(k);
//			}else{
//				posibleNext.addCell(k,0);				
//			}
			setPosibles(k.getWidth()-1,k.getHeight()-1);
			setPosibles(k.getWidth()-1,k.getHeight());
			setPosibles(k.getWidth()-1,k.getHeight()+1);
			setPosibles(k.getWidth(),k.getHeight()-1);
			setPosibles(k.getWidth(),k.getHeight()+1);
			setPosibles(k.getWidth()+1,k.getHeight()-1);
			setPosibles(k.getWidth()+1,k.getHeight());
			setPosibles(k.getWidth()+1,k.getHeight()+1);			
		};
	}
	private void setPosibles(double width, double height) {
		Dimension dim =new Dimension();
		dim.setSize(width, height);
		if (fits(dim)){
			//Si entra en el tablero y se encuentra entre las vivas o entre las posibles aumento las lindantes
			if (posibleNext.isAlive(dim)){
				System.out.println("Ya esta y se agrega lindante: " + (int)dim.getWidth()+" - "+(int)dim.getHeight());
				posibleNext.addCompanion(dim);
			}else{
				System.out.println("Podria vivir y se agrega lindante: " + (int)dim.getWidth()+" - "+(int)dim.getHeight());
				posibleNext.addCell(dim,1);
			}
		}
	}
	
	private boolean fits(Dimension k){
		return (k.getWidth()<=this.boundaries.getWidth() &&
				k.getWidth()>= 0 &&
				k.getHeight()<=this.boundaries.getHeight()&&
				k.getHeight()>=0);
	}
	
	public Watcher (Map<Dimension,Integer> toWatch, Dimension worldSize, Cells posiblelives){
		this.posibleNext = posiblelives;
		this.boundaries = worldSize;
		this.portionToWatch = toWatch;
	}
}
