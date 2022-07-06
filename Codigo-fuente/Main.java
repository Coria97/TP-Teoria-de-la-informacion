package TPE;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import com.sun.glass.ui.Menu;
import com.sun.jndi.url.dns.dnsURLContext;

public class Main {

	static Fuente fuente_ETH;
	static Fuente fuente_BTC;
	static Codificacion codificacion_ETH;
	static Codificacion codificacion_BTC;
	static Canales canal_BTC_ETH;
	
	//Metodo de impresion
	
	static void escribirMatrizEnTxt(String nombre_archivo, double matriz[][]) {
		//Escribe la matriz en un archivo txt 
		try {
			File file = new File(nombre_archivo);
			file.delete();
			PrintWriter writer = new PrintWriter(nombre_archivo, "UTF-8");
			for (int i = 0; i < matriz.length; i++)
			{
				for (int j = 0; j < matriz.length; j++)
					writer.print(matriz[i][j] + " ");
				writer.println("\n");
			}
		    writer.close();
		} catch (Exception e) {
			System.out.println("No se pudo imprimir la matriz dada");
		}
	}

	static void escribirArregloEnTxt(String nombre_archivo, double arreglo[], String texto) {
		//Se encarga de guardar los valores de la autocorrelacion cruzada
		try {
			File file = new File(nombre_archivo);
			file.delete();
			PrintWriter writer = new PrintWriter(nombre_archivo, "UTF-8");
		    for (int i=0; i < arreglo.length; i++) {
		        writer.println(texto + (i+1) + " : "+ arreglo[i]);
		    }
		    writer.close();
		} catch (Exception e) {
			System.out.println("No se pudo imprimir el arreglo dado");
		}
	}
	
	static void escribirArregloEnTxt(String nombre_archivo, double arreglo[],String texto, int t_incremento) {
		//Se encarga de imprimir los valores de la correlacion cruzada
		try {
			File file = new File(nombre_archivo);
			file.delete();
			PrintWriter writer = new PrintWriter(nombre_archivo, "UTF-8");
		    for (int i=0; i < arreglo.length; i++) {
		        writer.println(texto + t_incremento*i + ": " + arreglo[i]);
		    }
		    writer.close();
		} catch (Exception e) {
			System.out.println("No se pudo imprimir el arreglo dado");
		}
	}
	
	static void escribirArrayListParesEnTxt(String nombre_archivo, ArrayList<Par> distribucion_probabilidades, double cant_emisiones) {
		//Se encarga de imprimir los valores del simbolo y su distribucion de probabilidades
		try {
			File file = new File(nombre_archivo);
			file.delete();
			PrintWriter writer = new PrintWriter(nombre_archivo, "UTF-8");
			/*for(int i = 0; i < distribucion_probabilidades.size(); i++) {
				System.out.println("dist : " + distribucion_probabilidades.get(i).getCantApariciones());
			}*/
		    for (Par p: distribucion_probabilidades) {
		        writer.println("El simbolo " + p.getSimbolo() + " tiene una distribucion de: " + p.getCantApariciones()/cant_emisiones);
		    }
		    writer.close();
		} catch (Exception e) {
			System.out.println("Excepcion alcanzada");
		}
	}
	
	static void escribirCodificacionRLCEnTxt(String nombre_archivo, ArrayList<Par> codificacion_RLC) {
		//Se encarga de imprimir los simbolo y su cantidad de repeticiones
		try {
			File file = new File(nombre_archivo);
			file.delete();
			PrintWriter writer = new PrintWriter(nombre_archivo, "UTF-8");
			for(Par p : codificacion_RLC) {
				writer.println("( " + p.getSimbolo() + " , " + p.getCantApariciones() + " )");
			}
		    writer.close();
		} catch (Exception e) {
			System.out.println("Excepcion alcanzada");
		}
	}
	
	static void escribirSimboloCodificadoTxt(String nombre_archivo, ArrayList<Tupla> codigos) {
		//Se encarga de imprimir el simbolo con su codigo
		try {
			File file = new File(nombre_archivo);
			file.delete();
			PrintWriter writer = new PrintWriter(nombre_archivo, "UTF-8");
			for(Tupla t : codigos) {
				writer.println("Para el simbolo " + t.getPrimerDatoTupla() +" su codificacion es: " + t.getTercerDatoTupla());
			}
		    writer.close();
		} catch (Exception e) {
			System.out.println("Excepcion alcanzada");
		}
	}
	
	static void escribirCodificacionHuffmanTxt(String nombre_archivo, String codigo ,String comentario) {
		//Se encarga de imprimir el codigo generado
		try {
			File file = new File(nombre_archivo);
			file.delete();
			PrintWriter writer = new PrintWriter(nombre_archivo, "UTF-8");
			writer.println(comentario);
			writer.println(codigo);
		    writer.close();
		} catch (Exception e) {
			System.out.println("Excepcion alcanzada");
		}
	}
	
	static void escribirDecodificacionHuffmanYRLCTXT(String nombre_archivo, ArrayList<String> mensaje) {
		//Se encarga de imprimir la decodificacion
		try {
			File file = new File(nombre_archivo);
			file.delete();
			PrintWriter writer = new PrintWriter(nombre_archivo, "UTF-8");
			for(String m : mensaje) {
				writer.println(m);
			}
		    writer.close();
		} catch (Exception e) {
			System.out.println("Excepcion alcanzada");
		}
	}
	
	static void escribirCanalAsociadoTXT(String nombre_archivo, double[] canal_probabilidades , String texto) {
		//Se encarga de imprimir el valor de las probabilidades de canal de entrada y salida
		try {
			File file = new File(nombre_archivo);
			file.delete();
			PrintWriter writer = new PrintWriter(nombre_archivo, "UTF-8");
			for(int i = 0; i < canal_probabilidades.length; i++) {
				writer.println(texto + i + " = " + canal_probabilidades[i]);
			}
		    writer.close();
		} catch (Exception e) {
			System.out.println("Excepcion alcanzada");
		}
	}
	
	static void escribirPerdidaORuidoTXT(String nombre_archivo, double[] arreglo , String texto) {
		//Se encarga de imprimir el valor del ruido o perdida del canal
		try {
			File file = new File(nombre_archivo);
			file.delete();
			PrintWriter writer = new PrintWriter(nombre_archivo, "UTF-8");
			for(int i = 0; i < arreglo.length; i++) {
				if(i == 0)
					writer.println(texto + " en el canal para el estado bajo es de : " + arreglo[i]);
				else 
					if(i == 1)
						writer.println(texto + " en el canal para el estado mantuvo es de : " + arreglo[i]);
					else 
						writer.println(texto + " en el canal para el estado subio es de : " + arreglo[i]);
			}
		    writer.close();
		} catch (Exception e) {
			System.out.println("Excepcion alcanzada");
		}
	}
	
	
	static void escribirTransicionesDelCanalAsociadoTXT(String nombre_archivo,double prob_trans_bajo[], double prob_trans_mantuvo[],double prob_trans_subio[]){
		//Se encarga de imprimir el valor de las probabilidades de canal de entrada y salida
		try {
			File file = new File(nombre_archivo);
			file.delete();
			PrintWriter writer = new PrintWriter(nombre_archivo, "UTF-8");
			double arreglo_prob[];
			int j=0;
			String mensaje = "";
			String estado = "";
			while(j < 3) {
				if(j == 0) {
					arreglo_prob = prob_trans_bajo;
					mensaje= "Probabilidad de que dado que bajo transicione al canal ";
				}
				else
					if(j == 1) {
						arreglo_prob = prob_trans_mantuvo;
						mensaje= "Probabilidad de que dado que se mantuvo transicione al canal ";
					}
					else {
						arreglo_prob = prob_trans_subio;
						mensaje= "Probabilidad de que dado que subio transicione al canal ";
					}
				
				for(int i = 0; i < arreglo_prob.length; i++) {
					if(i == 0)
						estado="bajo";
					else
						if(i == 1 )
							estado="mantuvo";
						else
							estado="subio";
					writer.println(mensaje + estado + " = " + arreglo_prob[i]);
				}
				j++;
			}
		    writer.close();
		} catch (Exception e) {
			System.out.println("Excepcion alcanzada");
		}
	}
	
	//Metodos relacionados con archivos
	
	static int cantFilas(File archivo) {
		//Obtiene la cantidad de filas en el archivo para poder especificar despues la longitud del arreglo.
		try {
			BufferedReader br = new BufferedReader(new FileReader(archivo));
			int i= (int) br.lines().count();
			br.close();
			return i;
		}
		catch (IOException exception) {
	        System.out.println("No se pudo leer el archivo");
	        return 0;
	    }
	}
	
	static void leerDatos(File archivo,int datos[]){
		//Se encarga de leer el dato en la fila del archivo y copiarlo en el arreglo.
	    int i=0;
	    String valor= " ";
	    try {
	    	BufferedReader br = new BufferedReader(new FileReader(archivo));
	    	valor=br.readLine();
	    	while(valor!=null) {
	    		datos[i]= Integer.valueOf(valor);
	    		valor= br.readLine();
	    		i = i + 1; 
	    	}
	    	br.close();
	    }
	    catch (IOException exception) {
	        System.out.println("No se pudo leer el archivo");
	    }
	}
	
	//Metodos relacionados con el menu y el calculo
	static void guardarMatriz() {
		//Matriz de pasaje ETH
		escribirMatrizEnTxt("Matriz de pasaje ETH",fuente_ETH.getMatPasaje());
		//Matriz de pasaje BTC
		escribirMatrizEnTxt("Matriz de pasaje BTC",fuente_BTC.getMatPasaje());
	}
	
	static void autocorrelacion() {
		//Calculo de la autocorrelacion de cada moneda
		int tau = 50;
		double auto_correlacion_ETH[] = new double[tau];
		double auto_correlacion_BTC[] = new double[tau];
		for(int i = 0 ; i < tau; i++) {
			auto_correlacion_BTC[i] = 0;
			auto_correlacion_ETH[i] = 0;
		}
		auto_correlacion_ETH = fuente_ETH.calcularAutoCorrelacion(tau);
		auto_correlacion_BTC = fuente_BTC.calcularAutoCorrelacion(tau);
		
		//Autocorrelacion ETH
		escribirArregloEnTxt("Autocorrelacion ETH", auto_correlacion_ETH, "Valor de la autocorrelacion con un tau de ");
		
		//Autocorrelacion BTC
		escribirArregloEnTxt("Autocorrelacion BTC", auto_correlacion_BTC, "Valor de la autocorrelacion con un tau de ");
		
	}
	
	static void correlacionCruzada() {
		//Calculo de la correlacion cruzada respecto de cada moneda
		int saltos  = 5; 
		int t_incremento = 50;
		double correlacion_cruzada_ETH[] = new double[saltos];
		double correlacion_cruzada_BTC[] = new double[saltos];
		for(int i = 0 ; i < saltos; i++) {
			correlacion_cruzada_ETH[i] = 0;
			correlacion_cruzada_BTC[i] = 0;
		}
		for(int t = 0; t < saltos; t++){
			correlacion_cruzada_ETH[t] = fuente_ETH.calcularCorrelacionCruzada(fuente_BTC,(t_incremento * t));
			correlacion_cruzada_BTC[t] = fuente_BTC.calcularCorrelacionCruzada(fuente_ETH,(t_incremento * t));
		}
		
		//Correlacion Cruzada ETH
		escribirArregloEnTxt("Correlacion Cruzada ETH", correlacion_cruzada_ETH, "Valor de la correlacion cruzada con un incremento de ", t_incremento);
		
		//Correlacion Cruzada BTC
		escribirArregloEnTxt("Correlacion Cruzada BTC", correlacion_cruzada_BTC, "Valor de la correlacion cruzada con un incremento de ", t_incremento);
	}
	
	static void distribucionDeProbabilidad() {
		//Calculo de las distribucion de las probabilidades
		//Distribucion de probabilidades ETH
		ArrayList<Par> distribucion_ETH = codificacion_ETH.calcularDistribucionProbabilidad();
		escribirArrayListParesEnTxt("Distribucion probabilidades ETH",distribucion_ETH,codificacion_ETH.getCantEmisiones());
		ArrayList<Par> distribucion_BTC = codificacion_BTC.calcularDistribucionProbabilidad();
		//Distribucion de probabilidades BTC
		escribirArrayListParesEnTxt("Distribucion probabilidades BTC",distribucion_BTC,codificacion_BTC.getCantEmisiones());
	}
	
	static void codificacionHuffman() {
		String emision_codificada_ETH = "";
		String emision_codificada_BTC = "";
		emision_codificada_ETH = codificacion_ETH.calcularCodificacionHuffman();
		emision_codificada_BTC = codificacion_BTC.calcularCodificacionHuffman();
		ArrayList<Tupla> codificacion_Huffman_ETH = codificacion_ETH.getCodigosHuffman();
		ArrayList<Tupla> codificacion_Huffman_BTC = codificacion_BTC.getCodigosHuffman();
		ArrayList<String> decodificacion_Huffman_ETH = codificacion_ETH.getDecodificacionHuffman();
		ArrayList<String> decodificacion_Huffman_BTC = codificacion_BTC.getDecodificacionHuffman();
		//Codificacion Huffman Semi estatico ETH
		escribirCodificacionHuffmanTxt(" Mensaje Encriptado con Huffman ETH  ", emision_codificada_ETH ,"El mensaje encriptado a partir de los valores de ETH en binario es: "); 
		escribirSimboloCodificadoTxt("Codificacion Huffman ETH" , codificacion_Huffman_ETH);
		escribirDecodificacionHuffmanYRLCTXT("Decodificacion Huffman ETH ", decodificacion_Huffman_ETH);
		//Codificacion Huffman Semi estatico BTC
		escribirCodificacionHuffmanTxt(" Mensaje Encriptado con Huffman BTC  ", emision_codificada_BTC ,"El mensaje encriptado a partir de los valores de BTC en binario es: "); 
		escribirSimboloCodificadoTxt("Codificacion Huffman BTC" , codificacion_Huffman_BTC);
		escribirDecodificacionHuffmanYRLCTXT("Decodificacion Huffman BTC" , decodificacion_Huffman_BTC);
	}
	
	static void codificacionRLC() {
		//Calculo de la codificacion RLC
		ArrayList<Par> codificacion_RLC_ETH = codificacion_ETH.getCodificacionRLC();
		ArrayList<Par> codificacion_RLC_BTC = codificacion_BTC.getCodificacionRLC();
		//Calculo de la decodificacion RLC
		ArrayList<String> decodificacion_ETH = codificacion_ETH.getDecodificacionRLC();
		ArrayList<String> decodificacion_BTC = codificacion_BTC.getDecodificacionRLC();
		//impersion de la codificacion RLC
		escribirCodificacionRLCEnTxt("Codificacion RLC ETH ", codificacion_RLC_ETH);
		escribirCodificacionRLCEnTxt("Codificacion RLC BTC ", codificacion_RLC_BTC);
		//impresion de la decodificacion RLC
		escribirDecodificacionHuffmanYRLCTXT("Decodificacion RLC ETH",decodificacion_ETH);
		escribirDecodificacionHuffmanYRLCTXT("Decodificacion RLC BTC",decodificacion_BTC);
	}
	
	static void calculoCanalAsociado() {
		double prob_canal_entrada[] = canal_BTC_ETH.getProbabilidadesCanalEntrada();
		double prob_canal_salida[] = canal_BTC_ETH.getProbabilidadesCanalSalida();
		double prob_trans_bajo[] = canal_BTC_ETH.getProbabilidadesTransicion(0);
		double prob_trans_mantuvo[] = canal_BTC_ETH.getProbabilidadesTransicion(1);
		double prob_trans_subio[] = canal_BTC_ETH.getProbabilidadesTransicion(2);
		
		escribirCanalAsociadoTXT("Probabilidades canal entrada", prob_canal_entrada ,"Probabilidad del canal de entrada ");
		escribirCanalAsociadoTXT("Probabilidades canal salida", prob_canal_salida ,"Probabilidad del canal de salida ");
		escribirTransicionesDelCanalAsociadoTXT("Transiciones de un canal a otro", prob_trans_bajo ,prob_trans_mantuvo,prob_trans_subio);
	}
	
	static void calculoPerdida() {
		double perdida[] = canal_BTC_ETH.getPerdida();
		escribirPerdidaORuidoTXT("Perdida del canal", perdida, "La perdida");			
	}
	
	static void calculoRuido() {
		double ruido[] = canal_BTC_ETH.getRuido();
		escribirPerdidaORuidoTXT("Ruido del canal", ruido, "El ruido");
	}
	
	static void menu() {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		int valor = -1;
		while (valor != 0) {
			switch (valor) {
			case 1:
				guardarMatriz();
				valor = -1;
				break;
			case 2:
				autocorrelacion();
				valor = -1;
				break;
			case 3:
				correlacionCruzada();
				valor = -1;
				break;
			case 4:
				distribucionDeProbabilidad();
				valor = -1;
				break;
			case 5:
				codificacionHuffman();
				valor = -1;
				break;
			case 6:
				codificacionRLC();
				valor = -1;
				break;
			case 7: 
				calculoCanalAsociado();
				valor = -1;
				break;
			case 8: 
				calculoPerdida();
				calculoRuido();
				valor = -1;
				break;	
			case 9:
				guardarMatriz();
				autocorrelacion();
				correlacionCruzada();
				distribucionDeProbabilidad();
				codificacionHuffman();
				codificacionRLC();
				calculoCanalAsociado();
				calculoPerdida();
				calculoRuido();
				valor = -1;
				break;
			default:
				System.out.println("Ingrese el numero de calculo que desea realizar:");
				System.out.println("(0) Salir");
				System.out.println("(1) Guardar matrices de pasajes");
				System.out.println("(2) Calculo de autocorrelacion");
				System.out.println("(3) Calculo de correlacion cruzada");
				System.out.println("(4) Calculo de la distribucion de probabilidad");
				System.out.println("(5) Calculo de la codificacion de Huffman");
				System.out.println("(6) Calculo de la codificacion RLC");
				System.out.println("(7) Calculo del canal asociado");
				System.out.println("(8) Calculo de la perdida y ruido del canal");
				System.out.println("(9) Calcular todo");
				try {
					valor = Integer.parseInt(reader.readLine());
				} catch (Exception e) {
					System.out.println("Excepcion encontrada");
				}
				break;
			}
		}
	}
	
	public static void main(String[] args) throws IOException  {
		
		//Inicializacion de los datos.
        File archivo_ETH; 
		File archivo_BTC; 
        archivo_ETH = new File("ETH.txt");
		archivo_BTC = new File("BTC.txt");
		int datos_ETH[]= new int[cantFilas(archivo_ETH)];
		int datos_BTC[]= new int[cantFilas(archivo_BTC)];
		leerDatos(archivo_ETH,datos_ETH);
		leerDatos(archivo_BTC,datos_BTC);
		
		//Creacion de las fuentes
		fuente_ETH = new Fuente(datos_ETH);
		fuente_BTC = new Fuente(datos_BTC);
		
		//Creacion de las codificaciones
		codificacion_ETH = new Codificacion(datos_ETH);
		codificacion_BTC = new Codificacion(datos_BTC);
		
		//Creacion del canal
		canal_BTC_ETH = new Canales(datos_BTC, datos_ETH);
		
		menu();
		
	}
}