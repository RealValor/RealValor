package beans;

public class DebitoSocio implements java.io.Serializable{
	
	private static final long serialVersionUID = 20190110L;
	
	private Socio socio;
	private double valormensal;
	private double valoroutros;
	
	public Socio getSocio() {
		return socio;
	}
	public void setSocio(Socio socio) {
		this.socio = socio;
	}
	public double getValormensal() {
		return valormensal;
	}
	public void setValormensal(double valormensal) {
		this.valormensal = valormensal;
	}
	public double getValoroutros() {
		return valoroutros;
	}
	public void setValoroutros(double valoroutros) {
		this.valoroutros = valoroutros;
	}
	
	public double getValorTotal(int mesesEmAberto) {
		return ((mesesEmAberto*this.valormensal)+this.valoroutros);
	}

}
