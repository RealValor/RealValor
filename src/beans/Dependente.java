package beans;


public class Dependente implements java.io.Serializable {

	private static final long serialVersionUID = 40777070L;
	private Socio socio;
	private Socio dependente;
	private GrauParentesco grauparentesco;
	
	public Socio getSocio() {
		return socio;
	}
	public void setSocio(Socio socio) {
		this.socio = socio;
	}
	public Socio getDependente() {
		return dependente;
	}
	public void setDependente(Socio dependente) {
		this.dependente = dependente;
	}
	public GrauParentesco getGrauparentesco() {
		return grauparentesco;
	}
	public void setGrauparentesco(GrauParentesco grauparentesco) {
		this.grauparentesco = grauparentesco;
	}
	
	
}
