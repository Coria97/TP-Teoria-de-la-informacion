package TPE;

public class Tupla implements Comparable<Tupla>{
	private int simbolo;
	private double probabilidad;
	private String cod;

	public Tupla(int simbolo, double probabilidad) {
		this.simbolo = simbolo;
		this.probabilidad = probabilidad; 
		this.cod = "";
	}
	
	public int getPrimerDatoTupla() {
		return this.simbolo;
	}
	
	public double getSegundoDatoTupla() {
		return this.probabilidad;
	}
	
	public String getTercerDatoTupla() {
		return this.cod;
	}
	
	public void setPrimerDatoTupla(int simbolo) {
		this.simbolo = simbolo;
	}
	
	public void setSegundoDatoTupla(double probabilidad) {
		this.probabilidad = probabilidad; 
	}
	
	public void setTercerDatoTupla(String codigo) {
		this.cod = codigo;
	}
	
	public Tupla getTupla() {
		return this;
	}
	
	public void setTupla(Tupla p) {
		this.setPrimerDatoTupla(p.getPrimerDatoTupla());
		this.setSegundoDatoTupla(p.getSegundoDatoTupla());
	}
	
	@Override
	public int compareTo(Tupla p) {
		if (this.probabilidad <= p.getSegundoDatoTupla())
			return 0;
		else return -1;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this.simbolo == ((Tupla) obj).getPrimerDatoTupla())
			return true;
		else 
			return false;
	}

}