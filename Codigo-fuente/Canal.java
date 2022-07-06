package TPE;

import java.util.ArrayList;

public class Canal {
	private int canal_btc;
	private double prob_emision_estado_btc = 0;
	double prob_emision_estado_eth[] = {0,0,0};
	private ArrayList<Par> canal_eth = new ArrayList<Par>();
	
	public Canal(int estado_btc) {
		this.canal_btc = estado_btc;
		for(int i = 0; i < 3 ; i++) {
			Par aux_par = new Par(i,0); //0 bajo, 1 se mantuvo y 2 subio
			this.canal_eth.add(aux_par);
		}
	}
	
	public int getEstadoBTC() {
		//Retorna el estado a que hace referencia el canal: 0 bajo, 1 se mantuvo y 2 subio
		return this.canal_btc;
	}
	
	public double getProbEmisionlBTC() {
		//Retorna la probabilidad de emision del estado
		return this.prob_emision_estado_btc;
	}
	
	public int getProbTransCanalETH(int posicion) {
		//Retorna la probabilidad de transicion que dado el estado del btc, el eth baje, se mantenga o suba
		return this.canal_eth.get(posicion).getCantApariciones();
	}
	
	public double getProbEmisionETH(int i) {
		//retorna la probabilidad de que se emita un bajo, se mantuvo o subio 
		return this.prob_emision_estado_eth[i];
	}
	
	public void setProbEmisionETH(int i , double probabilidad) {
		//almacena la probabilidad de que se emita un bajo, se mantuvo o subio 
		this.prob_emision_estado_eth[i] = probabilidad;
	}
	
	public void setProbCanalBTC(double probabilidad) {
		//set de la probabilidad de emision del estado
		this.prob_emision_estado_btc = probabilidad;
	}
	
	public void setCanalETH(int posicion, int valor) {
		//Set de la probabilidad transicion que dado el estado del btc, el eth baje, se mantenga o suba
		this.canal_eth.get(posicion).setCantApariciones((this.getProbTransCanalETH(posicion) + valor));;
	}
	
}
