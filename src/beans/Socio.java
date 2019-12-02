package beans;

import java.util.Date;

import DAO.StuDAOException;

import logica.CPFString;

public class Socio implements java.io.Serializable {

	private static final long serialVersionUID = 40100701L;
	private int codigo;
	//private int nucleo; //Será controlado na sessão, pelo login. 
	private String nome;
	private String telefone;
	private long cpf;
	private String email;
	private String sexo;
	private Date dataNasc;
	private Date dataAsso;
	private String grau;
	private Cargo cargo;
	private String situacao;
	private String senha;
	private String observacao;
	private String isencao;
	private String avatar;
	private String notifica;

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public long getCpf() {
		return cpf;
	}

	public void setCpf(long cpf) {
		this.cpf = cpf;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public Date getDataNasc() {
		return dataNasc;
	}

	public void setDataNasc(Date dataNasc) {
		this.dataNasc = dataNasc;
	}

	public Date getDataAsso() {
		return dataAsso;
	}

	public void setDataAsso(Date dataAsso) {
		this.dataAsso = dataAsso;
	}
	public String getGrau() {
		return grau;
	}

	public void setGrau(String grau) {
		this.grau = grau;
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public String getCpfStr() throws StuDAOException {
		return CPFString.converteCPF(this.cpf);
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public String getIsencao() {
		return isencao;
	}

	public void setIsencao(String isencao) {
		this.isencao = isencao;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getNotifica() {
		return notifica;
	}

	public void setNotifica(String notifica) {
		this.notifica = notifica;
	}
	
}
