package beans;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DadosSocioReuni {

	private Boolean valid;
	private Core core;
	private List<SocioReuni> data = null;
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	public Boolean getValid() {
	return valid;
	}

	public void setValid(Boolean valid) {
	this.valid = valid;
	}

	public Core getCore() {
	return core;
	}

	public void setCore(Core core) {
	this.core = core;
	}

	public List<SocioReuni> getData() {
	return data;
	}

	public void setData(List<SocioReuni> data) {
	this.data = data;
	}

	public Map<String, Object> getAdditionalProperties() {
	return this.additionalProperties;
	}

	public void setAdditionalProperty(String name, Object value) {
	this.additionalProperties.put(name, value);
	}
}
