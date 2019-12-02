package beans;

import java.util.HashMap;
import java.util.Map;

public class Core {
	private Integer idNucleo;
	private String uuid;
	private String txNucleoNome;
	private Integer idNucleoTipo;
	private Integer idRegiao;
	private String txRegiao;
	private String dtCriacao;
	private String txEndereco;
	private String txCidade;
	private String txEstado;
	private String txPais;
	private Object nuLat;
	private Object nuLong;
	private String txCnpj;
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	public Integer getIdNucleo() {
	return idNucleo;
	}

	public void setIdNucleo(Integer idNucleo) {
	this.idNucleo = idNucleo;
	}

	public String getUuid() {
	return uuid;
	}

	public void setUuid(String uuid) {
	this.uuid = uuid;
	}

	public String getTxNucleoNome() {
	return txNucleoNome;
	}

	public void setTxNucleoNome(String txNucleoNome) {
	this.txNucleoNome = txNucleoNome;
	}

	public Integer getIdNucleoTipo() {
	return idNucleoTipo;
	}

	public void setIdNucleoTipo(Integer idNucleoTipo) {
	this.idNucleoTipo = idNucleoTipo;
	}

	public Integer getIdRegiao() {
	return idRegiao;
	}

	public void setIdRegiao(Integer idRegiao) {
	this.idRegiao = idRegiao;
	}

	public String getTxRegiao() {
	return txRegiao;
	}

	public void setTxRegiao(String txRegiao) {
	this.txRegiao = txRegiao;
	}

	public String getDtCriacao() {
	return dtCriacao;
	}

	public void setDtCriacao(String dtCriacao) {
	this.dtCriacao = dtCriacao;
	}

	public String getTxEndereco() {
	return txEndereco;
	}

	public void setTxEndereco(String txEndereco) {
	this.txEndereco = txEndereco;
	}

	public String getTxCidade() {
	return txCidade;
	}

	public void setTxCidade(String txCidade) {
	this.txCidade = txCidade;
	}

	public String getTxEstado() {
	return txEstado;
	}

	public void setTxEstado(String txEstado) {
	this.txEstado = txEstado;
	}

	public String getTxPais() {
	return txPais;
	}

	public void setTxPais(String txPais) {
	this.txPais = txPais;
	}

	public Object getNuLat() {
	return nuLat;
	}

	public void setNuLat(Object nuLat) {
	this.nuLat = nuLat;
	}

	public Object getNuLong() {
	return nuLong;
	}

	public void setNuLong(Object nuLong) {
	this.nuLong = nuLong;
	}

	public String getTxCnpj() {
	return txCnpj;
	}

	public void setTxCnpj(String txCnpj) {
	this.txCnpj = txCnpj;
	}

	public Map<String, Object> getAdditionalProperties() {
	return this.additionalProperties;
	}

	public void setAdditionalProperty(String name, Object value) {
	this.additionalProperties.put(name, value);
	}

}
