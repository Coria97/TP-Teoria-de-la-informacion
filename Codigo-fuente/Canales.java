package TPE;

import java.util.ArrayList;
import java.math.*;

public class Canales {
	private ArrayList<Canal> canales_btc = new ArrayList<Canal>();
	double mat_ruido[][];
	double mat_perdida[][];
	private double cant_emisiones;
	
	public Canales(int valores_btc[],int valores_eth[]) {
		for(int i = 0; i < 3 ; i++) {
			Canal aux_canal = new Canal(i); //0 bajo, 1 se mantuvo y 2 subio
			this.canales_btc.add(aux_canal);
		}	
		this.cant_emisiones = valores_btc.length-1;
		this.setProbabilidadesCanales(valores_btc,valores_eth);
	}
	
	private int getCotizacion(int valor1, int valor2) {
		//Devuelve el valor de la cotizacion del momento actual, comparado con t - 1
		if(valor1 > valor2)
			return 0;
		else
			if(valor1 == valor2)
				return 1;
			else 
				return 2;
	}
	
	private void setProbabilidadesCanales(int valores_btc[],int valores_eth[]) {
		//Recorre el arreglo btc y a medida que va recorriendo va calculando el estado del btc y dependiendo el estado se encarga de sumarle a la probabilidad de lo que pase en ETH
		double cant_apariciones[] = {0,0,0}; 
		for(int i = 1; i < valores_btc.length ; i++) {
			int estado_del_btc = this.getCotizacion(valores_btc[i-1], valores_btc[i]);
			int estado_del_eth = this.getCotizacion(valores_eth[i-1], valores_eth[i]);
			this.canales_btc.get(estado_del_btc).setCanalETH(estado_del_eth, (1));
			cant_apariciones[estado_del_btc]++;
		}	
		this.setProbabilidadesCanalEntrada(cant_apariciones);
		this.setProbabilidadesCanalSalida();
		this.setMatRuido();
		this.setMatPerdida();
	};
	
	private void setProbabilidadesCanalEntrada(double cant_apariciones[]){
		//Se encarga de cargar las probabilidades de emision de los simbolos de entrada
		for(int i = 0; i < this.canales_btc.size(); i++)
			this.canales_btc.get(i).setProbCanalBTC((cant_apariciones[i]/this.cant_emisiones));
	}
	
	private void setProbabilidadesCanalSalida() {
		//Se encarga de cargar las probabilidades de emision de los simbolos de salida
		for(int i = 0; i < this.canales_btc.size(); i++) {
			double probabilidad = 0; 
			probabilidad += this.canales_btc.get(0).getProbEmisionlBTC() * (this.canales_btc.get(0).getProbTransCanalETH(i)/this.cant_emisiones);
			probabilidad += this.canales_btc.get(1).getProbEmisionlBTC() * (this.canales_btc.get(1).getProbTransCanalETH(i)/this.cant_emisiones);
			probabilidad += this.canales_btc.get(2).getProbEmisionlBTC() * (this.canales_btc.get(2).getProbTransCanalETH(i)/this.cant_emisiones);
			this.canales_btc.get(0).setProbEmisionETH(i,probabilidad);
			this.canales_btc.get(1).setProbEmisionETH(i,probabilidad);
			this.canales_btc.get(2).setProbEmisionETH(i,probabilidad);
		}
	}
	
	public double[] getProbabilidadesCanalEntrada() {
		//Se encarga de retornar las probabilidades de emision de los simbolos de entrada
		double probabilidades_entrada[] = {0,0,0};
		for(int i = 0 ; i < probabilidades_entrada.length ; i++){
			probabilidades_entrada[i] = this.canales_btc.get(i).getProbEmisionlBTC();
		}
		return probabilidades_entrada;
	}
	
	public double[] getProbabilidadesCanalSalida() {
		//Se encarga de retornar las probabilidades de emision de los simbolos de entrada
		double probabilidades_salida[] = {0,0,0};
		for(int i = 0 ; i < probabilidades_salida.length ;i++) {
			probabilidades_salida[i] = this.canales_btc.get(0).getProbEmisionETH(i);
		}
		return probabilidades_salida;
	}
	
	public double[] getProbabilidadesTransicion(int estado) {
		//Recive el estado de si bajo, se mantuvo o subio y retorna la probabilidad de transicionar de un canal a otro
		double probabilidades_transicion[] = {0,0,0};
		for(int i = 0 ; i < probabilidades_transicion.length ;i++) {
			probabilidades_transicion[i] = this.canales_btc.get(estado).getProbTransCanalETH(i)/this.cant_emisiones; 
		}
		return probabilidades_transicion;
	}
	
	private void setMatRuido() {
		//Se encarga de setear los valores de la matriz y/x
		this.mat_ruido = new double[3][3];
		for(int i = 0; i < 3 ; i++) {
			for(int j = 0; j < 3 ; j++) {
				this.mat_ruido[j][i] = this.canales_btc.get(i).getProbTransCanalETH(j)/this.cant_emisiones;
			}
		}
	}
	
	private void setMatPerdida(){
		//Se encarga de setear los valores de la matriz x/y
		this.mat_perdida = new double[3][3];
		for(int i = 0; i < 3 ; i++) {
			for(int j = 0; j < 3 ; j++) {
				this.mat_perdida[j][i] = (this.mat_ruido[j][i] * this.canales_btc.get(i).getProbEmisionlBTC())/(this.canales_btc.get(0).getProbEmisionETH(j)); 	
			}
		}
	}
	
	public double[] getPerdida() {
		double perdida[] = {0,0,0};
		double valor = 0;
		for(int i = 0; i < 3 ; i++) {
			for(int j = 0; j < 3 ; j++) {
				if(this.mat_perdida[i][j] != 0)
					valor += (-1)*(this.mat_perdida[i][j] * (Math.log10(this.mat_perdida[i][j])/Math.log10(2)));
			}
			perdida[i] = valor;
			valor = 0; 
		}
		return perdida;
	}
	
	public double[] getRuido() {
		double ruido[] = {0,0,0};
		double valor = 0;
		for(int i = 0; i < 3 ; i++) {
			for(int j = 0; j < 3 ; j++) {
				if(this.mat_ruido[j][i] != 0)
					valor += (-1)*(this.mat_ruido[j][i] * (Math.log10(this.mat_ruido[j][i])/Math.log10(2)));
			}
			ruido[i] = valor;
			valor = 0; 
		}
		return ruido;
		
	}
}