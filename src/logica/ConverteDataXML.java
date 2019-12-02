package logica;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class ConverteDataXML implements Converter {

	@Override
	public void marshal(Object obj, HierarchicalStreamWriter writer, MarshallingContext mc) {
		//Qunado transforma objeto em arquivo
		Date data = (Date)obj;
		String dataFormatada = new SimpleDateFormat("dd/MM/yyyy").format(data);
		writer.setValue(dataFormatada);
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader leXML,UnmarshallingContext arg1) {
		//Qunado transforma arquivo em objeto
		//Date data = null;
		java.util.Date data = null;
		
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			data = sdf.parse(leXML.getValue());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return data;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(Class type) {
		return type.equals(Date.class);
	}
}
