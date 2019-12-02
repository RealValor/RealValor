package beans;

public class SocioValor implements java.io.Serializable{
	
	private static final long serialVersionUID = 4019021L;

	private int codSocio;
	private int codEntrada;
	private double valor;
	
	public int getCodSocio() {
		return codSocio;
	}
	public void setCodSocio(int codSocio) {
		this.codSocio = codSocio;
	}
	public int getCodEntrada() {
		return codEntrada;
	}
	public void setCodEntrada(int codEntrada) {
		this.codEntrada = codEntrada;
	}
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
}
