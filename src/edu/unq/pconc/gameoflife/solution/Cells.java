package edu.unq.pconc.gameoflife.solution;

import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Cells {
	
	private Map <Dimension, Integer> cells = new HashMap <Dimension, Integer>();
	
	public void clear() {
		this.cells.clear();
	}

	public void addAllCells(Map<Dimension, Integer> temp) {
		this.cells.putAll(temp);
	}

	public Set<Entry<Dimension,Integer>>asSet() {
		return this.cells.entrySet();
	}

	public boolean isAlive(Dimension dimension) {
		return this.cells.containsKey(dimension);
	}

	public void addCell(Dimension dim, int i) {
		this.cells.put(dim, i);
	}

	public void buryCell(Dimension dim) {
		this.cells.remove(dim);
	}
	public Map <Dimension, Integer> getCells(){
		return this.cells;
	}
	
}
