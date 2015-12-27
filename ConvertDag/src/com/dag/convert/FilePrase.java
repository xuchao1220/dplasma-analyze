package com.dag.convert;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class FilePrase {
	public Dag dag;	
	public FilePrase(){
		dag = new Dag();
	}
	
	public void praseXmlEvent(File file){
		System.out.println("Start prase xml file");
		SAXReader reader = new SAXReader();
		Document document = null;
		try {
			document = reader.read(file);			
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Element PROFILING = document.getRootElement();
		Iterator PROFILING_Iterator = PROFILING.elementIterator();
		while(PROFILING_Iterator.hasNext()){
			Element PROFILING_Child_Elenment = (Element) PROFILING_Iterator.next();
			if(PROFILING_Child_Elenment.getName().equals("DICTIONARY")){
				Iterator DICTIONARY_Iterator = PROFILING_Child_Elenment.elementIterator();
				while(DICTIONARY_Iterator.hasNext()){
					Element KEY_Element = (Element)DICTIONARY_Iterator.next();
					praseDictionayKEY(KEY_Element);
				}
			}
			else if(PROFILING_Child_Elenment.getName().equals("DISTRIBUTED_PROFILE")){//��ʼ�����ֲ�ʽ���
			Iterator DISTRIBUTED_PROFILE_Iterator = PROFILING_Child_Elenment.elementIterator();
			while(DISTRIBUTED_PROFILE_Iterator.hasNext()){
				Element	NODE_ELement = (Element) DISTRIBUTED_PROFILE_Iterator.next();//��ʼ����Node
				praseNode(NODE_ELement);
			  }
			}
		}
		dag.printEvents();
		System.out.println("Finish prase xml file");
	} 
	private void praseDictionayKEY(Element KEY_Element){
		int typeId = Integer.parseInt(KEY_Element.attributeValue("ID"));
		Iterator KEY_Iterator = KEY_Element.elementIterator();
		Element name = (Element) KEY_Iterator.next();//Name Element
		if(name.getName().equals("NAME")){
		   String type = name.getStringValue();
		   if(type.equals("GEMM"))
			   DictionayEvent.GEMM_ID = typeId;
		   else if(type.equals("TRSM"))
			   DictionayEvent.TRSM_ID = typeId;
		   else if(type.equals("SYRK"))
			   DictionayEvent.SYRK_ID = typeId;
		   else if(type.equals("POTRF"))
			   DictionayEvent.POTRF_ID = typeId;
		   }
		else
			System.err.println("Dictionay Key parse err");
		
	}
	
	private void praseNode(Element NODE_ELement){
		int nodeRANK = Integer.parseInt(NODE_ELement.attributeValue("RANK"));//��ȡ�ڵ��ID(RANK)
		int threadId = -1;
        Iterator PROFILES_Iterator = NODE_ELement.elementIterator();
        Element PROFILES_Element = (Element) PROFILES_Iterator.next();//��ʼ����PROFILES
        Iterator THREAD_Iterator = PROFILES_Element.elementIterator();
        while(THREAD_Iterator.hasNext()){
        	Element THREAD_Element = (Element) THREAD_Iterator.next();//��ʼ����Thread
        	Iterator KEY_Iterator = THREAD_Element.elementIterator();
        	while(KEY_Iterator.hasNext()){
        		Element KEY_Element = (Element) KEY_Iterator.next();
        		if(KEY_Element.getName().equals("IDENTIFIER")){//��ʼ����KEY
             	   String threadInfo = KEY_Element.getStringValue();
             	   if(threadInfo.contains("Thread"))
             	      threadId = Integer.parseInt(threadInfo.split(" ")[2]);//��ȡ�̵߳�ID
             	   else
             		  break;//�������Thread������MPI����Ϣ������Thread
             	}
             	else if(KEY_Element.getName().equals("KEY")){
             		int eventTypeId = Integer.parseInt(KEY_Element.attributeValue("ID"));
             		String eventType = "ERRTYPE";
             		if(eventTypeId == DictionayEvent.GEMM_ID)
             			eventType = "GEMM";
             		else if(eventTypeId == DictionayEvent.SYRK_ID)
             			eventType = "SYRK";
             		else if(eventTypeId == DictionayEvent.TRSM_ID)
             			eventType = "TRSM";
             		else if(eventTypeId == DictionayEvent.POTRF_ID)
             			eventType = "POTRF";
             		else
             			System.err.println("Thread��Event��Type��������");
             		Iterator EVENT_Iterator = KEY_Element.elementIterator();
             		while(EVENT_Iterator.hasNext()){
             			Element EVENT_Element = (Element) EVENT_Iterator.next();//��ʼ����EVENT
             			praseEvent(EVENT_Element,nodeRANK,threadId,eventType);
             		}
        	    }else
        	    	System.err.print("THREAD�Ľ�������");
        	
        		
        	}
        }
		
	}
	
	private void praseEvent(Element EVENT_Element,int nodeId,int threadId,String eventType){
		Event event = new Event(eventType, nodeId, threadId);
		Iterator EVENT_Iterator = EVENT_Element.elementIterator();
		while(EVENT_Iterator.hasNext()){
			Element eventInfo = (Element) EVENT_Iterator.next();
		    if(eventInfo.getName().equals("ID"))
		    	event.setId(Integer.parseInt(eventInfo.getStringValue().split(":")[1]));//��ȡEVENT ID
	        else if(eventInfo.getName().equals("START"))
		    	event.setStartTime(Long.parseLong(eventInfo.getStringValue()));		    
		    else if(eventInfo.getName().equals("END"))
		    	event.setEndTime(Long.parseLong(eventInfo.getStringValue()));	    
		    else
		    	System.err.print("Event�����Խ�������");
		}
		dag.eventTable.addEvent(event);
		dag.eventList.add(event);
		event = null;
	}
	
	
    public void praserDagNode(File file){
		try {
			FileReader fileReader = new FileReader(file);
			BufferedReader reader = new BufferedReader(fileReader);
			String tempLine;
			while((tempLine = reader.readLine()) != null)
			    praserLineNode(tempLine);
			reader.close();
			fileReader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	}
    
    public void praserDagEdge(File file){
		try {
			FileReader fileReader = new FileReader(file);
			BufferedReader reader = new BufferedReader(fileReader);
			String tempLine;
			while((tempLine = reader.readLine()) != null)
			    praserLineEdge(tempLine);
			reader.close();
			fileReader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	}

    
    
    
    public void praserLineNode(String line){
    	if(!line.startsWith("MAP")&&line.length() > 12){
			if(line.contains("shape")){//node line
				String[] nodeInfo = line.split(" ");
				String nodeName = nodeInfo[0];
				int threadId = Integer.parseInt(nodeInfo[1].substring(nodeInfo[1].indexOf("<") + 1,nodeInfo[1].indexOf("/")));
				String alias = line.substring(line.lastIndexOf("tooltip")+9,line.length()-3);
				System.out.println("alias:"+alias);
				Node node = new Node(nodeName,alias,threadId);
				dag.nodeList.add(node);
				dag.nodeHashMap.put(nodeName, node);
			}
		}
    }
    
    private void praserLineEdge(String line){
    	if(!line.startsWith("MAP")&&line.length() > 12){
			if (line.contains("->")) { //edge
				Event startNode, endNode;
				String[] edgeInfo = line.split(" ");
				String nodeName = edgeInfo[0];
				startNode = dag.eventTable.getEvent(dag.nodeHashMap.get(nodeName));
                nodeName = edgeInfo[2];
				endNode = dag.eventTable.getEvent(dag.nodeHashMap.get(nodeName));
				Edge edge = new Edge(startNode, endNode);
				dag.edgeList.add(edge);
			}
    	} 
    }
}
