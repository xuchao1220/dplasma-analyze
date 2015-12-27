package com.dag.convert;

public class Edge {
	public Event head;
	public Event tail;
	public int trans;//���ϵ�������
	public Edge(Event head, Event tail) {
		this.head = head;
		this.tail = tail;
		this.trans = 0;
	}
	
	
}
