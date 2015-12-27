package com.dag.convert;


/**
 * @author XuChao
 * Dagͼ�е�Node��Event��Ӧ
 */
public class Node {
    public String nodeName;//�ڵ�����
    public String alias;//tooltip
    public String nodeType;//�ڵ������
    public int id;//�ڵ�ı��
    public int threadId;//�̱߳��   
    
    
	public Node(String nodeName, String alias, int threadId) {
		super();
		this.nodeName = nodeName;
		this.alias = alias;
		this.nodeType = aliasPart(alias)[0];
		this.id = Integer.parseInt(aliasPart(alias)[1]);
		
		this.threadId = threadId;
	}
	
	private String[] aliasPart(String alias){
		String[] part = new String[2];
		int i = 0;
		for(;i < alias.length();i++){
			if(Character.isDigit(alias.charAt(i)))
				break;
		}
		part[0] = alias.substring(0,i);
		part[1] = alias.substring(i);
		return part;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getNodeType() {
		return nodeType;
	}
	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getThreadId() {
		return threadId;
	}
	public void setThreadId(int threadId) {
		this.threadId = threadId;
	}
    
    
}
