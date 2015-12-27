package com.dag.convert;

import java.util.ArrayList;
import java.util.List;

public class EventTable {
	public List<Event> GEMMList;
	public List<Event> SYRKList;
	public List<Event> TRSMList;
	public List<Event> POTRFList;
	
	public EventTable() {
		GEMMList = new ArrayList<Event>();
		SYRKList = new ArrayList<Event>();
		TRSMList = new ArrayList<Event>();
		POTRFList = new ArrayList<Event>();
	}
	
	public void addEvent(Event event){
		String type = event.getEventType();
		if(type.equals("GEMM"))
			GEMMList.add(event);
		else if(type.equals("SYRK"))
			SYRKList.add(event);
		else if(type.equals("TRSM"))
			TRSMList.add(event);
		else if(type.equals("POTRF"))
			POTRFList.add(event);
		else 
			System.err.println("Type err when add to event table");
	}
	
	public Event getEvent(Node node){
		Event event = null;
		String type = node.getNodeType();
		if(type.equals("GEMM"))
			event = getEventByType(GEMMList,node);
		else if(type.equals("SYRK"))
			event = getEventByType(SYRKList,node);
		else if(type.equals("TRSM"))
			event = getEventByType(TRSMList,node);
		else if(type.equals("POTRF"))
			event = getEventByType(POTRFList,node);
		return event;
	}
	
	private Event getEventByType(List<Event> eventList,Node node){
		Event resultEvent = null;
		int flag = 0;
		for(Event event:eventList)
			if(event.getId() == node.getId()){
				if(event.getThreadId() != node.getThreadId() )
					System.out.println("---thread id maybe mismatch---");
				flag++;
				resultEvent = event; 
			}	
		if(flag == 0)
			System.err.println("event mismatch");
		else if(flag > 1)
			System.err.println("event duplicate match");
		
		return resultEvent;
	}
}
