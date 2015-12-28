package com.dag.convert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Dag {
     public List<Node> nodeList;//����
     public List<Event> eventList;//
     public EventTable eventTable;
     public List<Edge> edgeList;//��   
     public HashMap<String,Node> nodeHashMap;
     
     public Dag() {
    	eventList = new ArrayList<Event>();
    	eventTable = new EventTable();
		nodeList = new ArrayList<Node>();
		edgeList = new ArrayList<Edge>();
		nodeHashMap = new HashMap<String,Node>();
	}
    

	public Node getNodeByName(String name){
		 if(nodeHashMap.containsKey(name))
			 return nodeHashMap.get(name);
		 else
			 System.err.println("Node hashMap error");
		 return null;
     }
	
	public void generateNodeId(){
		for(int i = 0;i < nodeList.size();i++){
			 nodeList.get(i).id = i;
		}
	}
	
	public void printEvents(){
		System.out.println("--- events ---");
		for(Event event:eventList)
		{
			event.printEventInfo();
		}
		System.out.println("------");
	}
	
	public void printEdges(){
		System.out.println("--- edges ---");
		for(Edge edge:edgeList){
			System.out.println(edge.head.getEventType()+edge.head.getId() + "->" + edge.tail.getEventType()+edge.tail.getId() + " ");
		}
		System.out.println("------");
	}
   
	public void testTransTime(){
		System.out.println("---transTime---");
		for(Edge edge:edgeList){
			if(edge.head.getNodeId() == edge.tail.getNodeId())
				System.out.println("same node:"+(edge.tail.getStartTime()-edge.head.getEndTime()));
			else
				System.out.println("diff node:"+(edge.tail.getStartTime()-edge.head.getEndTime()));
		}
		System.out.println("------");
	}
//	public void printFormId(){
//		for(Node node:nodeList)
//			System.out.print(node.id+" ");
//		System.out.println();
//		System.out.println("--------------------");
//		for(Edge edge:edgeList)
//			System.out.println(edge.head.id + "->" + edge.tail.id + " ");
//		System.out.println();
//	}
//	
//	public void print(){
//		for(Node node:nodeList)
//			System.out.print(node.nodeName+" ");
//		System.out.println();
//		System.out.println("--------------------");
//		for(Edge edge:edgeList)
//			System.out.println(edge.head.nodeName + "->" + edge.tail.nodeName + " ");
//		System.out.println();
//	}
}
