package TPE;

import java.util.Comparator;

public class Comparador implements Comparator<HuffmanNodo>{
	public int compare(HuffmanNodo x, HuffmanNodo y){ 
		//Se encarga de comparar si la probabilidad de dos nodos es mayor,menor o igual
	    if (x.getProbabilidad()<y.getProbabilidad())
	    	return -1;
	    if (x.getProbabilidad()>y.getProbabilidad())
	    	return 1;
	   	return 0;
	} 
}   
