package edu.unq.pconc.gameoflife.solution;

import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Cells {
	
	private Map <Dimension, Integer> cells = new HashMap <Dimension, Integer>();
	
	public void clear() {
		cells.clear();
	}

	public void addAllCells(Map<Dimension, Integer> temp) {
		cells.putAll(temp);
	}

	public Set<Entry<Dimension,Integer>>asSet() {
		return cells.entrySet();
	}

	public boolean isAlive(Dimension dimension) {
		return cells.containsKey(dimension);
	}

	public void addCell(Dimension dim, int i) {
		cells.put(dim, i);
	}

	public void buryCell(Dimension dim) {
		cells.remove(dim);		
	}
	
	
}
