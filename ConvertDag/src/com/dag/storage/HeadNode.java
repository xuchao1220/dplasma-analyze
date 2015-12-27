package com.dag.storage;

import com.dag.convert.Event;

public class HeadNode {
	public Event node;
	public TableNode firstNode;
	public TableNode lastNode;
	
	public HeadNode(Event headNode) {
		this.node = headNode;
		this.firstNode = null;
		this.lastNode = null;
	}
	
}
