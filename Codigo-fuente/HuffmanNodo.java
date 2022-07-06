package TPE;


import java.util.ArrayList;
import java.util.PriorityQueue;

public class HuffmanNodo {
	private int simbolo;
	private double probabilidad;
	private HuffmanNodo izq;
	private HuffmanNodo der;
	
	public HuffmanNodo(int simbolo,double probabilidad){
		this.simbolo = simbolo; 
		this.probabilidad = probabilidad;
		this.izq = null;
		this.der = null;
	}
	
	public boolean esNull(){
		return ((this.izq != null) || (this.der != null));
	}
	
	public HuffmanNodo getNodoIzq(){
		return this.izq;
	}
	
	public HuffmanNodo getNodoDer(){
		return this.der;
	}
	
	public int getSimbolo() {
		return this.simbolo;
	}
	
	public double getProbabilidad() {
		return this.probabilidad;
	}
	
	public void setNodoIzq(HuffmanNodo izq) {
		this.izq = izq;
	}
	
	public void setNodoDer(HuffmanNodo der) {
		this.der = der;
	}
	
	
}
