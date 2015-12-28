package com.dag.convert;

import com.dag.storage.GraphLink;

public class MainConvert {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        
		FileOperate fileOperate = new FileOperate();
	    String rootPath = System.getProperty("user.dir");
	   
	    String readPath = rootPath + "/OriginalFile";	
	    String event_xml_path = readPath + "/out.xml";
	    String dag_dot_path = readPath + "/DotFiles";
	    fileOperate.readEvent(event_xml_path);
	    fileOperate.readDag(dag_dot_path);
	   	
//	    fileOperate.praser.dag.testTransTime();
	    
	   	String writePath = rootPath + "/TargetFile/";
	   	String eventPath = writePath + "Event.txt";
	   	String edgePath = writePath + "Edge.txt";
	   	fileOperate.writeEvents(eventPath);
	   	fileOperate.writeEdges(edgePath);
	   	
	   	DagAnalyze dagAnalyze = new DagAnalyze(fileOperate.praser.dag);
	   	dagAnalyze.printThreadLine();
	   	dagAnalyze.analyzeEdge();
	   	
	/*   	GraphLink graphLink = new GraphLink(fileOperate.praser.dag);
	   	graphLink.printGraph();
	   	graphLink.findKeyRoute();
	   	graphLink.printKeyRoute();*/
	   	
	}

}
