package com.dag.convert;

public class Event {
	
	private String eventType;
	private int nodeId; //�ڵ���
	private int threadId;//Thread���
	private int id;//Event���
	private long startTime;
	private long endTime;
	
	public Event(String eventType, int nodeId, int threadId) {
		super();
		this.eventType = eventType;
		this.nodeId = nodeId;
		this.threadId = threadId;
	}
	
	public void printEventInfo(){
		System.out.println("Event--"+" Type:"+eventType+" NodeID:"+nodeId+" ThreadID:"+threadId+" ID:"+id+" StartTime:"+startTime+" EndTime:"+endTime);
	}
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	
	public int getNodeId() {
		return nodeId;
	}
	public void setNodeId(int nodeNum) {
		this.nodeId = nodeNum;
	}
	public int getThreadId() {
		return threadId;
	}
	public void setThreadId(int threadNum) {
		this.threadId = threadNum;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public long getStartTime() {
		return startTime;
	}
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	public long getEndTime() {
		return endTime;
	}
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
	
}
