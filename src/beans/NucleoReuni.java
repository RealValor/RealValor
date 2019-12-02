package beans;

import java.util.Date;

public class NucleoReuni {

	private int id_nucleo;
	private String tx_nucleo_nome;
	private int id_nucleo_tipo;
	private int id_regiao;
	private String tx_regiao;
	private String dt_criacao;
	private String tx_endereco;
	private String tx_cidade;
	private String tx_estado;
	private String tx_pais;
	private String nu_lat;
 	private String nu_long;
 	private String tx_cnpj;
 	private String tx_repre_resp;
 	private Date dt_atualizacao_socios;
 	
	public int getId_nucleo() {
		return id_nucleo;
	}
	public void setId_nucleo(int id_nucleo) {
		this.id_nucleo = id_nucleo;
	}
	public String getTx_nucleo_nome() {
		return tx_nucleo_nome;
	}
	public void setTx_nucleo_nome(String tx_nucleo_nome) {
		this.tx_nucleo_nome = tx_nucleo_nome;
	}
	public int getId_nucleo_tipo() {
		return id_nucleo_tipo;
	}
	public void setId_nucleo_tipo(int id_nucleo_tipo) {
		this.id_nucleo_tipo = id_nucleo_tipo;
	}
	public int getId_regiao() {
		return id_regiao;
	}
	public void setId_regiao(int id_regiao) {
		this.id_regiao = id_regiao;
	}
	public String getTx_regiao() {
		return tx_regiao;
	}
	public void setTx_regiao(String tx_regiao) {
		this.tx_regiao = tx_regiao;
	}
	public String getDt_criacao() {
		return dt_criacao;
	}
	public void setDt_criacao(String dt_criacao) {
		this.dt_criacao = dt_criacao;
	}
	public String getTx_endereco() {
		return tx_endereco;
	}
	public void setTx_endereco(String tx_endereco) {
		this.tx_endereco = tx_endereco;
	}
	public String getTx_cidade() {
		return tx_cidade;
	}
	public void setTx_cidade(String tx_cidade) {
		this.tx_cidade = tx_cidade;
	}
	public String getTx_estado() {
		return tx_estado;
	}
	public void setTx_estado(String tx_estado) {
		this.tx_estado = tx_estado;
	}
	public String getTx_pais() {
		return tx_pais;
	}
	public void setTx_pais(String tx_pais) {
		this.tx_pais = tx_pais;
	}
	public String getNu_lat() {
		return nu_lat;
	}
	public void setNu_lat(String nu_lat) {
		this.nu_lat = nu_lat;
	}
	public String getNu_long() {
		return nu_long;
	}
	public void setNu_long(String nu_long) {
		this.nu_long = nu_long;
	}
	public String getTx_cnpj() {
		return tx_cnpj;
	}
	public void setTx_cnpj(String tx_cnpj) {
		this.tx_cnpj = tx_cnpj;
	}
	public String getTx_repre_resp() {
		return tx_repre_resp;
	}
	public void setTx_repre_resp(String tx_repre_resp) {
		this.tx_repre_resp = tx_repre_resp;
	}
	public Date getDt_atualizacao_socios() {
		return dt_atualizacao_socios;
	}
	public void setDt_atualizacao_socios(Date dt_atualizacao_socios) {
		this.dt_atualizacao_socios = dt_atualizacao_socios;
	}
 		
}
