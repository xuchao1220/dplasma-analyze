package com.dag.convert;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileOperate {
	
	FilePrase praser;
	
	public FileOperate() {
		// TODO Auto-generated constructor stub
		praser = new FilePrase();
	}
	
    public void readEvent(String filePath){
    	File file = new File(filePath);
    	if(!file.exists())
			System.err.println("read xml file path error");
    	praser.praseXmlEvent(file);
    }
    
	public void readDag(String filePath){
		File folder = new File(filePath);
		if(!folder.exists()&&folder.isDirectory())
			System.err.println("read dot folder path error");
		File[] files = folder.listFiles();
		for(File dotfile:files){
			praser.praserDagNode(dotfile);
		}
		for(File dotfile:files){
			praser.praserDagEdge(dotfile);
		}
		praser.dag.printEdges();
					
	}
	
	public void writeEvents(String filePath){
		File file = new File(filePath);
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				System.err.println("Create file failed");
			}
	    }
		
		try {
			FileWriter fileWriter = new FileWriter(file);
			BufferedWriter writer = new BufferedWriter(fileWriter);
			writer.write("EventName	NodeID	ThreadID	StartTime	Duration\n");
			for(Event event:praser.dag.eventList)
				writer.write(event.getEventType()+event.getId()+" "+event.getNodeId()+" "+event.getThreadId()+" "+event.getStartTime()+" "+(event.getEndTime()-event.getStartTime())+"\n");
			writer.close();
			fileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public void writeEdges(String filePath){
			File file = new File(filePath);
			if(!file.exists()){
				try {
					file.createNewFile();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					System.err.println("Create file failed");
				}
		    }
			
			try {
				FileWriter fileWriter = new FileWriter(file);
				BufferedWriter writer = new BufferedWriter(fileWriter);
				writer.write("head	tail	transtime\n");
				for(Edge edge:praser.dag.edgeList)
					writer.write(edge.head.getEventType()+edge.head.getId()+" --> "+edge.tail.getEventType()+edge.tail.getId()+" "+(edge.tail.getStartTime() - edge.head.getEndTime())+"\n");
				writer.close();
				fileWriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
	}
	
	
	/*public void writeDags(String filePath,String fileName){
		
		File file = new File(filePath + fileName);
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				System.err.println("Create file failed");
			}
	    }
		FileWriter fileWriter;
		BufferedWriter writer;
		
		try {
			fileWriter = new FileWriter(file);
			writer = new BufferedWriter(fileWriter);
			if(fileName.equals("EventTable.txt")){
				writer.write("GEMM" + " " + praser.eventTable.GEMMResult[0]
						+ " " + praser.eventTable.GEMMResult[1] + " "
						+ praser.eventTable.GEMMResult[2] + "\n");
				writer.write("SYRK" + " " + praser.eventTable.SYRKResult[0]
						+ " " + praser.eventTable.SYRKResult[1] + " "
						+ praser.eventTable.SYRKResult[2] + "\n");
				writer.write("TRSM" + " " + praser.eventTable.TRSMResult[0]
						+ " " + praser.eventTable.TRSMResult[1] + " "
						+ praser.eventTable.TRSMResult[2] + "\n");
				writer.write("POTRF" + " " + praser.eventTable.POTRFResult[0]
						+ " " + praser.eventTable.POTRFResult[1] + " "
						+ praser.eventTable.POTRFResult[2] + "\n");
			}else if(fileName.equals("Node.txt")){
				for(Node node:praser.dag.nodeList){
					writer.write(node.id + " " + node.type + "\n");
				}
			}else if(fileName.equals("Edge.txt")){
				for(Edge edge:praser.dag.edgeList)
				    writer.write(edge.head.id + " " + edge.tail.id + " " + edge.trans + "\n");
			}else if(fileName.equals("EventDuration.txt")){
				writer.write("GEMM"+" ");
				for(Event event:praser.eventTable.GEMMList)
					writer.write(event.getDuration()+" ");
				writer.write("\nSYRK"+" ");
				for(Event event:praser.eventTable.SYRKList)
					writer.write(event.getDuration()+" ");
				writer.write("\nTRSM"+" ");
				for(Event event:praser.eventTable.TRSMList)
					writer.write(event.getDuration()+" ");
				writer.write("\nPOTRF"+" ");
				for(Event event:praser.eventTable.POTRFList)
					writer.write(event.getDuration()+" ");
			}else{
				System.out.println("Output file name error");
			}			
			writer.close();
			fileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("Write event table error!");
		}
		
		
		
		
		
	}*/
	
	

}
