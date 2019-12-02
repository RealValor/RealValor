package beans;

import java.util.HashMap;
import java.util.Map;


public class SocioReuni implements java.io.Serializable{
	
	private static final long serialVersionUID = 201070777021L;

	private int id;
	private String name;
	private String cpf;
	private String avatar;
	private String nickname;
	private String gender;
	private String birth;
	private String age;
	private int id_degree;
	private String degree;
	private int id_status;
	private String status;
	private String cellphone;
	private String phone;
	private String email;
	private String email_alt;
	private String address;
	
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
	return name;
	}

	public void setName(String name) {
	this.name = name;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getAvatar() {
	return avatar;
	}

	public void setAvatar(String avatar) {
	this.avatar = avatar;
	}

	public String getNickname() {
	return nickname;
	}

	public void setNickname(String nickname) {
	this.nickname = nickname;
	}

	public String getGender() {
	return gender;
	}

	public void setGender(String gender) {
	this.gender = gender;
	}

	public String getBirth() {
	return birth;
	}

	public void setBirth(String birth) {
	this.birth = birth;
	}

	public String getAge() {
	return age;
	}

	public void setAge(String age) {
	this.age = age;
	}

	public String getDegree() {
	return degree;
	}

	public void setDegree(String degree) {
	this.degree = degree;
	}

	public String getStatus() {
	return status;
	}

	public void setStatus(String status) {
	this.status = status;
	}

	public String getCellphone() {
	return cellphone;
	}

	public void setCellphone(String cellphone) {
	this.cellphone = cellphone;
	}

	public String getPhone() {
	return phone;
	}

	public void setPhone(String phone) {
	this.phone = phone;
	}

	public String getEmail() {
	return email;
	}

	public void setEmail(String email) {
	this.email = email;
	}

	public String getAddress() {
	return address;
	}

	public void setAddress(String address) {
	this.address = address;
	}

	public int getId_degree() {
		return id_degree;
	}

	public void setId_degree(int id_degree) {
		this.id_degree = id_degree;
	}

	public int getId_status() {
		return id_status;
	}

	public void setId_status(int id_status) {
		this.id_status = id_status;
	}

	public String getEmail_alt() {
		return email_alt;
	}

	public void setEmail_alt(String email_alt) {
		this.email_alt = email_alt;
	}

	public Map<String, Object> getAdditionalProperties() {
		return additionalProperties;
	}

	public void setAdditionalProperties(Map<String, Object> additionalProperties) {
		this.additionalProperties = additionalProperties;
	}
	
}
