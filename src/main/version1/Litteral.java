package version1;

import weka.core.Attribute;

public class Litteral {
	
	private String nameAttr;
	private String valueAttr;
	private int indexAttr; //index dans la liste des attrituts du concept
	private double gain; 
	
	public double getGain() {
		return gain;
	}
	public void setGain(double gain) {
		this.gain = gain;
	}
	public String getNameAttr() {
		return nameAttr;
	}
	public void setNameAttr(String nameAttr) {
		this.nameAttr = nameAttr;
	}
	public String getValueAttr() {
		return valueAttr;
	}
	public void setValueAttr(String valueAttr) {
		this.valueAttr = valueAttr;
	}
	public int getIndexAttr() {
		return indexAttr;
	}
	public void setIndexAttr(int indexAttr) {
		this.indexAttr = indexAttr;
	}
	
	public Litteral(Attribute attr, int indexValue){
		nameAttr = attr.name();
		valueAttr = attr.value(indexValue);
		indexAttr = attr.index();
		gain = -1.0;
		
	}
	
	public Litteral(){
		nameAttr = "";
		valueAttr = "";
		indexAttr = -1;
		gain = -1.0;
	}
	
	public String tostring(){
		return (nameAttr + " = " + valueAttr);
	}

}
