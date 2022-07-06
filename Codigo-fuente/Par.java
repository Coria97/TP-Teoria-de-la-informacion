package TPE;


public class Par {
	private int simbolo;
	private int cant_apariciones_simbolo;
	
	public Par(int simbolo, int cant_apariciones_simbolo) {
		this.simbolo = simbolo; 
		this.cant_apariciones_simbolo = cant_apariciones_simbolo;
	}
	
	public int getCantApariciones() {
		return this.cant_apariciones_simbolo;
	}
	
	public int getSimbolo() {
		return this.simbolo;
	}
	
	public void setSimbolo(int simbolo) {
		this.simbolo = simbolo;
	}
	
	public void setCantApariciones(int cant_apariciones_simbolo) {
		this.cant_apariciones_simbolo = cant_apariciones_simbolo;
	}
	
	@Override
	public boolean equals(Object obj) {
		return (this.simbolo == ((Par) obj).getSimbolo());
	}
}	
