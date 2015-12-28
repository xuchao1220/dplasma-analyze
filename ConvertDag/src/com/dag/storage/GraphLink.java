package com.dag.storage;

import java.util.ArrayList;
import java.util.List;

import com.dag.convert.Dag;
import com.dag.convert.Edge;
import com.dag.convert.Event;

public class GraphLink {
	public Dag dag;
	public List<Edge> keyRoute;
	public List<HeadNode> headList;

	public GraphLink(Dag dag) {
      this.dag = dag;
	  headList = new ArrayList<HeadNode>();
	  keyRoute = new ArrayList<Edge>();
	  generateHeadList(dag.eventList);
	  generateTableList(dag.edgeList);
	}

	private void generateHeadList(List<Event> eventList){
    	for(Event event:eventList){
    		HeadNode headNode = new HeadNode(event);
    		headList.add(headNode);
    	}
    }
	
	private void generateTableList(List<Edge> edgeList){
	    for(Edge edge:edgeList){
	    	HeadNode headNode = getHeadNode(edge.tail) ;
	    	if(headNode != null){
	    	    if(headNode.firstNode == null){
	    	    	TableNode tableNode = new TableNode(edge.head,edge.tail.getStartTime()-edge.head.getEndTime());
	    	    	headNode.firstNode = tableNode;	
		    		headNode.lastNode = tableNode;
	    	    }
	    	    else{
		    		TableNode tableNode = new TableNode(edge.head,edge.tail.getStartTime()-edge.head.getEndTime());
		    		headNode.lastNode.next = tableNode;
		    		headNode.lastNode = tableNode;
	    	    }
	    	}	    	 
	    	else 
	    	 System.err.println("edge node is not in headList");
	    }
	}
	
	private HeadNode getHeadNode(Event event){
		HeadNode node = null;
		for(HeadNode headNode:headList){
			if(headNode.node == event){
	            node = headNode;
				break;
			}
		}
        return node;
	}

	public void printGraph(){
		System.out.println("---GraphLink---");
		for(HeadNode headNode:headList){
			System.out.print(headNode.node.getEventType()+headNode.node.getId()+ ": ");
			TableNode tableNode = headNode.firstNode;
			while(tableNode != null){
				System.out.print(tableNode.node.getEventType()+tableNode.node.getId()+ " ");
				tableNode = tableNode.next;
			}
			System.out.println();
		}
		System.out.println("------");
		System.out.println("NodeNum:"+headList.size());
	}

	public void findKeyRoute(){
		Event lastEvent = dag.eventTable.POTRFList.get(0);
		for(Event event:dag.eventTable.POTRFList){
			if(event.getStartTime() > lastEvent.getStartTime())
			lastEvent = event;
		}
		HeadNode headNode = getHeadNode(lastEvent);
		TableNode tableNode = headNode.firstNode;
		while(tableNode != null){
			TableNode keyTableNode = tableNode;
			while(tableNode.next != null){
				if(tableNode.next.value < tableNode.value)
					keyTableNode = tableNode.next;	
				tableNode = tableNode.next;
			}
			keyRoute.add(new Edge(keyTableNode.node,headNode.node));
			headNode = getHeadNode(keyTableNode.node);
			tableNode = headNode.firstNode;
		 }
		}
	
	public void printKeyRoute(){
		System.out.println("--- KeyRoute ---");
		for(Edge edge:keyRoute){
			if(edge.head.getThreadId() == edge.tail.getThreadId())
			    System.out.println("same " +edge.head.getEventType()+edge.head.getId() + "->" + edge.tail.getEventType()+edge.tail.getId() + " "+ (edge.tail.getStartTime()-edge.head.getEndTime()));
			else
				  System.out.println("diff " +edge.head.getEventType()+edge.head.getId() + "->" + edge.tail.getEventType()+edge.tail.getId() + " "+ (edge.tail.getStartTime()-edge.head.getEndTime()));
		}
		System.out.println("------");
		
	}	
}
