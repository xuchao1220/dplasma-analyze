package com.dag.storage;

import com.dag.convert.Event;

public class TableNode {
	public Event node;
	public long value;
	public TableNode next;
	
	public TableNode(Event node, long value) {
		super();
		this.node = node;
		this.value = value;
		this.next = null;
	}
	
	
	

}
