package TPE;

public class Fuente {
	private double epsilon;
	private int cant_simbolos = 3;
	private double mat_pasaje[][];
	private double mat_acum[][];
	private int emisiones[];
	private int emisiones_sin_refinar[];
	
	public Fuente(int valores[]) {
		this.epsilon = 0.0001;
		this.emisiones_sin_refinar = new int[valores.length];
		this.emisiones_sin_refinar = valores;
		this.refinarEmisiones(valores);
		this.calcularMatPasaje();
		this.calcularMatAcum();
	}
	
	public double[][] getMatPasaje(){
		return this.mat_pasaje;
	}
	
	public int getLongEmision() {
		return this.emisiones.length;
	}
	
	public int getCantSimbolos() {
		return this.cant_simbolos;
	}
	
	public int[] getEmisiones() {
		return this.emisiones;
	}
	
	public int getSimboloEmitidoEmision(int position){
		return this.emisiones[position];
	}
	
	public int getSimboloEmitidoEmisionSinRefinar(int position){
		return this.emisiones_sin_refinar[position];
	}
	
	public double[] calcularAutoCorrelacion(int tau) {
		//Calcula la autoccorelacion desde el tau 1 hasta el tau pasado por parametro
		int aux_tau = 1;
		double autocorrelacion[]= new double[tau];
		double calc_autocorrelacion = 0;
		while (aux_tau <= tau) {
			for(int i = 0; i < this.emisiones_sin_refinar.length; i++) {
				if((i + aux_tau) < this.emisiones_sin_refinar.length) {
					calc_autocorrelacion += this.emisiones_sin_refinar[i] * this.emisiones_sin_refinar[(i+aux_tau)];
				}
			}
			autocorrelacion[aux_tau-1] = calc_autocorrelacion/(double)(this.emisiones_sin_refinar.length - aux_tau);
			calc_autocorrelacion = 0;
			aux_tau++;
			
		}
		return autocorrelacion;
	}
	
	public double calcularCorrelacionCruzada(Fuente fuente,int t_incremento) {

		double valor_pow_fuente_interna = 0;
		double valor_pow_fuente_externa = 0;
		double valor_multiplicacion_fuentes = 0;

		for (int i = 0; i < (fuente.emisiones_sin_refinar.length - t_incremento); i++) {
			valor_pow_fuente_interna += Math.pow(this.getSimboloEmitidoEmisionSinRefinar(i), 2);
			valor_pow_fuente_externa += Math.pow(fuente.getSimboloEmitidoEmisionSinRefinar(i+t_incremento), 2);
			valor_multiplicacion_fuentes += this.getSimboloEmitidoEmisionSinRefinar(i) * fuente.getSimboloEmitidoEmisionSinRefinar(i+t_incremento);
		}
		if ( 0 != (Math.sqrt(valor_pow_fuente_interna * valor_pow_fuente_externa)))
			return  valor_multiplicacion_fuentes / (Math.sqrt(valor_pow_fuente_interna * valor_pow_fuente_externa));
		else
			return valor_multiplicacion_fuentes;
	}
	
	private void calcularMatAcum() {
		//Metodo que se encargara de completar la matriz acumlada de la fuente
		this.mat_acum = new double[this.cant_simbolos][this.cant_simbolos]; 
		double columna_acum = 0;
		for (int i = 0; i < this.cant_simbolos ; i++) {
            for (int j = 0; j < this.cant_simbolos; j++) {
            	columna_acum += this.mat_pasaje[j][i];
            	this.mat_acum[j][i]= columna_acum;
            }
            columna_acum=0;
		}
	}	
	
	private int comparaEstado(double valor_ant, double valor_act) {
		//Compara si los precio de cotizacion con un instante anterior y el actual si es bajo,subio o se mantuvo
		if(valor_ant < valor_act) 
			return 2;
		else
			if(valor_ant > valor_act) 
				return 0;
			else 
				return 1;
	}
	
	private void calcularMatPasaje() {
		//Inicializacion de variables
		double estado[] = new double[this.cant_simbolos]; //0 significa que bajó, 1 que se mantuvo, y 2 que subió.
		for(int i = 0 ; i < this.cant_simbolos ; i++)
			estado[i] = 0 ; 
		this.mat_pasaje= new double[this.cant_simbolos][this.cant_simbolos];
		for(int i = 0 ; i < this.cant_simbolos ; i++)
			for(int j = 0 ; j < this.cant_simbolos ; j++)
				this.mat_pasaje[i][j] = 0;
		
		//Recorre el arreglo de emisiones y suma en la matriz de que estado(columna) hacia que estado fue(fila)
		for(int i = 1 ; i < this.emisiones.length ; i++) {
			this.mat_pasaje[this.emisiones[i]][this.emisiones[i-1]]++;
		}
		
		//Calcula y guarda la cantidad de veces que se pasa de un estado Si a otro estado Sj 
		for(int i = 0 ; i < this.cant_simbolos ; i++)
			for(int j = 0 ; j < this.cant_simbolos ; j++)
				estado[i] += this.mat_pasaje[j][i];
				
		//Calcula las probabilidades de la matriz 
		for(int i = 0 ; i < this.cant_simbolos ; i++)
			for(int j = 0 ; j < this.cant_simbolos ; j++)
				this.mat_pasaje[j][i] = this.mat_pasaje[j][i]/ estado[i];
	}

	private void refinarEmisiones(int valores[]) {
		//Se encarga de refinar los datos en crudo y transformalos en señales de : bajada, subida o se mantuvo.
		//0 significa que bajó, 1 que se mantuvo, y 2 que subió.
		this.emisiones = new int[valores.length - 1];
		double valor_ant = 0;
		double valor_act = 0;
		for(int i= 1; i < valores.length; i++) {
			valor_ant = valores[i-1];
			valor_act = valores[i];
			this.emisiones[i-1] = this.comparaEstado(valor_ant, valor_act);
		}
	}
	
}