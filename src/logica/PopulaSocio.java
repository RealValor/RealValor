package logica;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import DAO.StuDAOException;
import beans.Cargo;
import beans.Socio;
import beans.SocioReuni;

public class PopulaSocio {
	
	public static Socio alimentaDadosSocio(int pCodigo, String cpfSoc, SocioReuni pSocioReuni) throws StuDAOException, ParseException{
		
		Socio objSocio = new Socio();
		
		objSocio.setCodigo(pCodigo);
		
		String nomeSocioReuni = pSocioReuni.getName();
		
		nomeSocioReuni = nomeSocioReuni.toUpperCase().contains("’")?nomeSocioReuni.replace("’", "`"):nomeSocioReuni;
		nomeSocioReuni = nomeSocioReuni.toUpperCase().contains("'")?nomeSocioReuni.replace("'", "`"):nomeSocioReuni;
		
		objSocio.setNome(nomeSocioReuni);
		
		objSocio.setEmail(pSocioReuni.getEmail());
		
		objSocio.setTelefone(pSocioReuni.getCellphone().length()>50?pSocioReuni.getCellphone().substring(0,50):pSocioReuni.getCellphone());

		String status = pSocioReuni.getStatus().toUpperCase().contains("FREQUENTE")?"A":pSocioReuni.getStatus().toUpperCase().contains("LICENCIADO")?"L":pSocioReuni.getStatus().toUpperCase().contains("AFASTADO")?"F":"O";
		objSocio.setSituacao(status);

		String grau = pSocioReuni.getDegree().substring(1,2).equalsIgnoreCase("D")?"C":pSocioReuni.getDegree().substring(1,2);
		objSocio.setGrau(grau);

		objSocio.setCpf(Long.parseLong("0"));

		if(cpfSoc!=null){
			//O cpf no Reuni é String 
			boolean converte = true;
			for (int i = 0; i < cpfSoc.length(); i++) {
				if(Character.isLetter(cpfSoc.charAt(i))){
					converte = false;
					break;
				}
			}
			if(converte){
				objSocio.setCpf(Long.parseLong(cpfSoc));
			}
		}
		
		objSocio.setSexo(pSocioReuni.getGender());
		objSocio.setDataNasc(null);

		if(!pSocioReuni.getBirth().isEmpty()){

			SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd"); //dd-MM-yyyy
			objSocio.setDataNasc( (Date) formato.parse(pSocioReuni.getBirth()));
		}

		objSocio.setDataAsso(null);

		Cargo objCargo = new Cargo();
		objCargo.setCodigo(0);

		objSocio.setCargo(objCargo);

		objSocio.setIsencao("N");
		
		objSocio.setAvatar(pSocioReuni.getAvatar());

		return objSocio;
	}

}
