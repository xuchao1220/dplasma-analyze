package com.dag.convert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class DagAnalyze {
	public Dag dag;
	
	public HashMap<String,List<Event>> ThreadMap;

	public DagAnalyze(Dag dag) {
		this.dag = dag;
		ThreadMap = new HashMap<String,List<Event>>();
		generateThreadMap(dag.eventList);
		sortThreadLine(ThreadMap);
	}
	
	
	private void generateThreadMap(List<Event> eventList){
		String threadName = null;
		for(Event event:eventList){
			threadName = event.getNodeId() +"_"+event.getThreadId();
			if(ThreadMap.containsKey(threadName)){
			   List<Event> ThreadLine = ThreadMap.get(threadName);
			   ThreadLine.add(event);
			}else{
			   List<Event> ThreadLine = new ArrayList<Event>();
			   ThreadLine.add(event);
			   ThreadMap.put(threadName, ThreadLine);
			}
		}	
	}
	
	private void sortThreadLine(HashMap<String,List<Event>> threadMap){
		for(Entry<String,List<Event>> threadItem:ThreadMap.entrySet()){
			List<Event> threadLine = threadItem.getValue();
			Collections.sort(threadLine,new Comparator<Event>(){
				@Override
				public int compare(Event o1, Event o2) {
					// TODO Auto-generated method stub
					if(o1.getStartTime() > o2.getStartTime())
						return 1;
					else if(o1.getStartTime() < o2.getStartTime())
						return -1;
					return 0;
				}		
			});
		}
	}
	
	public void printThreadLine(){
		for(Entry<String,List<Event>> threadItem:ThreadMap.entrySet()){
			System.out.println("--- ThreadLine ---");
			
			String threadName = threadItem.getKey();
			List<Event> threadLine = threadItem.getValue();
			
			System.out.print("ThreadName"+threadName+":");
			for(Event event:threadLine){
				System.out.print(event.getEventType()+event.getId()+" ");
			}
			System.out.println();
		}
		
	}
	
	public void analyzeEdge(){
		
		System.out.println("--- analyze ---");
		for(Edge edge:dag.edgeList){
			String threadFlag = " thread";
			if(isInSameThread(edge))
				threadFlag = "same" + threadFlag;
			else
				threadFlag = "diff" + threadFlag;
			if(isInsertByOtherEvent(edge)){
	//		  System.out.println(threadFlag + ":" +"  delay  " + (edge.tail.getStartTime()-edge.head.getEndTime()));
			}else{
			  System.out.println(threadFlag + ":" +" undelay " + (edge.tail.getStartTime()-edge.head.getEndTime()) + "  " 
			+ edge.head.getEventType()+edge.head.getId() +"->"+edge.tail.getEventType()+edge.tail.getId() );
			}
		}
		
	}
	
	private boolean isInsertByOtherEvent(Edge edge){
		Event head = edge.head;
		Event tail = edge.tail;
		String headThreadName = head.getNodeId()+"_"+head.getThreadId();
		String tailThreadName = tail.getNodeId()+"_"+tail.getThreadId();
		List<Event> headThreadLine = null;
		List<Event> tailThreadLine = null;
		if(ThreadMap.containsKey(headThreadName) && ThreadMap.containsKey(tailThreadName)){
			headThreadLine = ThreadMap.get(headThreadName);
		    tailThreadLine = ThreadMap.get(tailThreadName);
		}
		else{
			 System.err.println("Thread Line Don't have the thread name");
			 return false;
			 
		}
		
		if(headThreadName.equals(tailThreadName)){//same node
			if(1 == tailThreadLine.indexOf(tail) - headThreadLine.indexOf(head))
				return false;
			else 
				return true;
		}else{//diff node 
			int tailPrevIndex = tailThreadLine.indexOf(tail)-1;
			long preEventEndTime;
			if(tailPrevIndex < 0)
				preEventEndTime = 0;
			else
				preEventEndTime = tailThreadLine.get(tailPrevIndex).getEndTime();
			
			if(preEventEndTime <= head.getEndTime())
				return false;
			else 
				return true;
		}
	}
	
	private boolean isInSameThread(Edge edge){
		String headThreadName = edge.head.getNodeId()+"_"+edge.head.getThreadId();
		String tailThreadName = edge.tail.getNodeId()+"_"+edge.tail.getThreadId();
		if(headThreadName.equals(tailThreadName))
			return true;
		else
			return false;
	}
	
	
}

