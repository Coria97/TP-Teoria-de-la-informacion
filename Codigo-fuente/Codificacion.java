package TPE;

import java.util.ArrayList;
import java.io.*;
import java.util.PriorityQueue;

public class Codificacion {
    private int emisiones[];
    private int cant_simbolos;
    private ArrayList<Par> simbolos = new ArrayList<Par>();
    private ArrayList<Tupla> codificacion_huffman = new ArrayList<Tupla>();
    private String mensaje_codificado_huffman = "";
    private HuffmanNodo arbol_huffman;
    private ArrayList<Par> codificacion_RLC = new ArrayList<Par>();

    public Codificacion(int[] emisiones) {
    	//Constructor de la clase codificacion
        this.emisiones = emisiones;
        this.extraerDatos();
    }
    
	private void extraerDatos() {
		//Se encarga de cargar la cantidad de simbolos que haya sin repetir en datos y agregar el simbolo a el arraylist simbolo
		for(int i=0 ; i < this.emisiones.length ; i++) {
			Par aux_par = new Par(Integer.valueOf(this.emisiones[i]), 1);
			if(!this.simbolos.contains(aux_par)) {
				this.cant_simbolos++;
				this.simbolos.add(aux_par);
			}
			else 
			{
				int posicion = this.simbolos.indexOf(aux_par);
				this.simbolos.get(posicion).setCantApariciones((int) (this.simbolos.get(posicion).getCantApariciones() + 1));
			}
		}
	}
	
	public double getCantEmisiones() {
		return (double) this.emisiones.length;
	}
	
	//Calculo distribucion de probabilidades
	
    public ArrayList<Par> calcularDistribucionProbabilidad() {
    	ArrayList<Par> distribucion_probabilidades = new ArrayList<Par>();
    	//Recorro mi arraylist y los agrego a un arraylist auxiliar pero dividido por la cantidad de emisiones. 
    	for(int i = 0; i < this.simbolos.size() ; i++) { 
    		Par aux_par = new Par(this.simbolos.get(i).getSimbolo(),(this.simbolos.get(i).getCantApariciones()));
    		distribucion_probabilidades.add(aux_par);
    	}
        return distribucion_probabilidades;
    }
    
    //Codificacion Huffmand semi-estatico
    
    private HuffmanNodo generaArbol() {	
    
    	//Creo una cola la cual va a tener todos los nodos con su probabilidad en orden ascendente
    	PriorityQueue<HuffmanNodo> cola = new PriorityQueue<>(this.codificacion_huffman.size(),new Comparador());
    	//Recorro mi arraylist para crear las hojas del arbol y agregar el huffman nodo
    	for(int i = 0; i < this.codificacion_huffman.size(); i++) {
    		HuffmanNodo hnode = new HuffmanNodo(this.codificacion_huffman.get(i).getPrimerDatoTupla(),this.codificacion_huffman.get(i).getSegundoDatoTupla());
    		cola.add(hnode);
    	}
    	HuffmanNodo hraiz = null;
    	//recorro la cola hasta que en ella solo quede la raiz
    	while(cola.size() > 1) {
    		//Agarro el primer nodo de menor probabilidad y lo saco 
    		HuffmanNodo hnodo_x = cola.peek();
    		cola.remove();
    		//Agarro el nuevo primer nodo de menor probabilidad y lo saco
    		HuffmanNodo hnodo_y = cola.peek();
    		cola.remove();
    		//Creo un nuevo nodo el cual va a tener la nueva probabilidad y de simbolo -1 porque es un intermedio
    		HuffmanNodo hnodo_a_agregar = new HuffmanNodo(-1,hnodo_x.getProbabilidad() + hnodo_y.getProbabilidad());
    		hnodo_a_agregar.setNodoIzq(hnodo_x);
    		hnodo_a_agregar.setNodoDer(hnodo_y);
    		//Actualizo mi raiz 
    		hraiz = hnodo_a_agregar;
    		//Agrego mi nuevo nodo generado a la cola
    		cola.add(hraiz);	
    	}
    	//devuelvo el arbol generado
    	return hraiz;
    }
    

    private void codificaSimboloHuffman(HuffmanNodo hnodo, String cod) {
    	//recorre recursivamente el arbol y va generando el codigo, si se va por la rama de abajo le asigna un 1 y si va por la de arriba 0
    	if (hnodo.getSimbolo() > -1) {	
    		for(int i = 0; i < this.simbolos.size() ; i++) 
    			if(hnodo.getSimbolo() == this.codificacion_huffman.get(i).getPrimerDatoTupla()) 
    				this.codificacion_huffman.get(i).setTercerDatoTupla(cod);
    	}
    	else {
    		if(hnodo.esNull()) 
    			codificaSimboloHuffman(hnodo.getNodoIzq(),cod+"0");
    		if(hnodo.esNull()) 
    			codificaSimboloHuffman(hnodo.getNodoDer(),cod+"1");
		}
    }
 
    private String generarMensajeCodificado() {
    	//Se encarga de recorrer el arreglo de emisiones y ver cual es el simbolo emitido para concatenar el codigo
    	for(int i = 0; i < this.emisiones.length; i++) {
    		for(int j = 0; j < this.codificacion_huffman.size(); j++) {
    			if(this.codificacion_huffman.get(j).getPrimerDatoTupla() == this.emisiones[i]) {
    				mensaje_codificado_huffman = mensaje_codificado_huffman + this.codificacion_huffman.get(j).getTercerDatoTupla();
    			}
    		}
    	}
    	return mensaje_codificado_huffman;
    }
    
    public String calcularCodificacionHuffman() {
    	//cargo los simbolos con su distribucion de probabilidades
    	for(int i = 0 ; i < this.simbolos.size(); i++) {
    		Tupla aux_Tupla = new Tupla(this.simbolos.get(i).getSimbolo(),this.simbolos.get(i).getCantApariciones()/(double)this.emisiones.length);
    		this.codificacion_huffman.add(aux_Tupla);
    	}
    	//Creo el arbol
    	this.arbol_huffman = this.generaArbol();
    	//Genero el codigo de cada simbolo a partir del arbol
    	this.codificaSimboloHuffman(this.arbol_huffman,"");
    	//Codifico el mensaje y lo retorno
    	return this.generarMensajeCodificado();
    }
    
    private int getSimboloCodificado(String codigo) {
    	for(int i = 0; i < this.codificacion_huffman.size(); i++)
    		if(this.codificacion_huffman.get(i).getTercerDatoTupla().equals(codigo))
    			return this.codificacion_huffman.get(i).getPrimerDatoTupla();
    	return -1;
    }
    
    private ArrayList<String> decodificarCodigoHuffman() {
    	ArrayList<String> mensaje_decodificado = new ArrayList<String>();
    	String codigo_aux = "";
    	HuffmanNodo copia_harbol = this.arbol_huffman;
    	for(int i = 0; i < mensaje_codificado_huffman.length(); i++) {
    		if(mensaje_codificado_huffman.charAt(i) == '0')
    		{	
    			codigo_aux += "0";
    			copia_harbol = copia_harbol.getNodoIzq();
    		}
    		else {
    			codigo_aux += "1";
				copia_harbol = copia_harbol.getNodoDer();
			}
    		if (!copia_harbol.esNull()) {
    			mensaje_decodificado.add(String.valueOf(this.getSimboloCodificado(codigo_aux)));
    			codigo_aux="";
    			copia_harbol = this.arbol_huffman;
    		}
    	}
    	return mensaje_decodificado;
    }
    
    public ArrayList<String> getDecodificacionHuffman() {
    	return this.decodificarCodigoHuffman();
    }
    
	public ArrayList<Tupla> getCodigosHuffman(){
		return this.codificacion_huffman;
	}
    
    //Codificacion RLC
    
    public void calculoRLC() {
    	//recorro el arreglo de emisiones y cuento cuantas apariciones tiene cada simbolo
    	if (this.codificacion_RLC.isEmpty()) {
	    	for(int i = 0; i < this.emisiones.length; i++) {
	    		int cant_ocurrencias = 1;
	    		Par aux_par = new Par(this.emisiones[i], cant_ocurrencias);
	    		while((i < (this.emisiones.length - 1)) && (this.emisiones[i] == this.emisiones[i+1])){
	    			cant_ocurrencias++;
	    			i++;
	    		}
	    		aux_par.setCantApariciones(cant_ocurrencias);
	    		this.codificacion_RLC.add(aux_par);
	    	}
    	}
    }
    
    public ArrayList<Par> getCodificacionRLC() {
    	this.calculoRLC();
    	return this.codificacion_RLC;
    }
    
    private ArrayList<String> decodificacionRLC()
    {
    	ArrayList<String> mensaje_decodificado = new ArrayList<String>();
    	for (int i = 0; i < this.codificacion_RLC.size(); i++) {
    		Par auxPar = this.codificacion_RLC.get(i);
    		for (int j = 0; j < auxPar.getCantApariciones(); j++) {
    			mensaje_decodificado.add(String.valueOf(auxPar.getSimbolo()));
			}
		}
    	return mensaje_decodificado;
    }
    
    public ArrayList<String> getDecodificacionRLC()
    {
    	return decodificacionRLC();
    }
}
