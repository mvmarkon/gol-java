package edu.unq.pconc.gameoflife.solution;

import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Cells {
	private int waitingModifiers = 0;
	private int watchers = 0;
	private int modifiers = 0;
	
	private Map <Dimension, Integer> cells = new HashMap <Dimension, Integer>();
	
	private boolean canWatch(){
		return modifiers == 0 && waitingModifiers == 0;
	}
	
	private boolean canModify(){
		return modifiers == 0 && watchers == 0;
	}
	
	
	
	/*public void clear(){
		this.cells.clear();
	
	public void addAllCells(Map<Dimension, Integer> temp){
		this.cells.putAll(temp);
		
	}
		
	public Set<Entry<Dimension,Integer>> asSet() {
		return this.cells.entrySet();
	}
	public boolean isAlive(Dimension dimension) {
		return this.cells.containsKey(dimension);

	}
	
	public void addCell(Dimension dim, int i) {
		this.cells.put(dim, i);
	}
	public void buryCell(Dimension dim) {
		System.out.println("bury");
		this.cells.remove(dim);
	}
	public void addCompanion(Dimension dim) {
		this.cells.put(dim, this.cells.get(dim)+1);
	}
	public synchronized Map <Dimension, Integer> getCells(){
		return this.cells;
	}*/
	public synchronized void clear() {
			while (!canModify()){
				waitingModifiers++;
				try {
					this.wait();
				} catch (InterruptedException e) {
					waitingModifiers--;
					System.out.println("Excepcion en clear: " + e);
					return;
				}
				waitingModifiers--;
			}
		this.modifiers = 1;
		this.cells.clear();
		this.modifiers--;
		this.notifyAll();
	}
	public synchronized void addAllCells(Map<Dimension, Integer> temp) {
		while (!canModify()){
			waitingModifiers++;
			try {
				this.wait();
			} catch (InterruptedException e) {
				waitingModifiers--;
				System.out.println("Excepcion en addAllCells: " + e);
				return;
			}
			waitingModifiers--;
		}
		this.modifiers =1;
		this.cells.putAll(temp);
		this.modifiers--;
		this.notifyAll();
	}

	public synchronized Set<Entry<Dimension,Integer>>asSet() {
		while(!canWatch()){
			try {
				this.wait();
			} catch (InterruptedException e) {
				System.out.println("Excepcion en asSet:" + e);
			}
		}
		this.watchers++;
		Set<Entry<Dimension,Integer>> salida = this.cells.entrySet();
		this.watchers--;
		this.notify();
		return salida;
	}

	public synchronized boolean isAlive(Dimension dimension) {
		while(!canWatch()){
			try {
				this.wait();
			} catch (InterruptedException e) {
				System.out.println("Excepcion en isLive: " + e);
			}
		}
		this.watchers++;
		boolean salida = this.cells.containsKey(dimension);
		this.watchers--;
		this.notify();
		return salida;
	}
	public synchronized void addCell(Dimension dim, int i) {
		while (!canModify()){
			waitingModifiers++;
			try {
				this.wait();
			} catch (InterruptedException e) {
				waitingModifiers--;
				System.out.println("Excepcion en addCell:" + e);
				return;
			}
			waitingModifiers--;
		}
		this.modifiers =1;
		this.cells.put(dim, i);
		this.modifiers--;
		this.notifyAll();
	}
	
	public synchronized void buryCell(Dimension dim) {
		while (!canModify()){
			waitingModifiers++;
			try {
				this.wait();
			} catch (InterruptedException e) {
				waitingModifiers--;
				System.out.println("Excepcion en buryCell:" + e);
				return;
			}
			waitingModifiers--;
		}
		this.modifiers =1;
		this.cells.remove(dim);
		this.modifiers--;
		this.notify();
	}
	
	public synchronized Map <Dimension, Integer> getCells(){
		while(!canWatch()){
			try {
				this.wait();
			} catch (InterruptedException e) {
				//no hay que hacer nada(teoricamente)
				System.out.println("Excepcion en getCells:" + e);
			}
		}
		this.watchers++;
		Map <Dimension, Integer> onlyCells = this.cells;
		this.watchers--;
		this.notify();
		return onlyCells;
	}

	public synchronized void addCompanion(Dimension dim) {
		while (!canModify()){
			waitingModifiers++;
			try {
				this.wait();
			} catch (InterruptedException e) {
				waitingModifiers--;
				System.out.println("Excepcion en addCompanion:" + e);
				return;
			}
			waitingModifiers--;
		}
		this.modifiers =1;
		this.cells.put(dim, this.cells.get(dim)+1);
		this.modifiers--;
		this.notify();
	}
	
}
