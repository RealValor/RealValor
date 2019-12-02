package logica;

import java.util.ArrayList;

import DAO.SocioValorDAO;
import DAO.StuDAOException;
import beans.SocioValor;
import beans.TipoEntrada;

public class BuscaSocioValor {
	public static ArrayList<TipoEntrada> buscaSocioValor(int pCodPaga, ArrayList<TipoEntrada> listTEnt) throws StuDAOException{

		SocioValor objSVal = new SocioValor();

		objSVal.setCodSocio(pCodPaga);
		objSVal.setCodEntrada(0);

		//System.out.println("no método 2 "+listTEnt.get(0).getNucleo().getCodigo());
		
		ArrayList<SocioValor> listSValor = SocioValorDAO.listarSocioValor(objSVal,listTEnt.get(0).getNucleo());

		if(listSValor!=null){
			for (SocioValor socioValor : listSValor){
				for (TipoEntrada entrada : listTEnt){
					
					if(entrada.getCodigo()==socioValor.getCodEntrada()){
						listTEnt.get(listTEnt.indexOf(entrada)).setValor(socioValor.getValor());
						break;
					}
				}
			}
		}
		return listTEnt;
		
	}
}
