package logica;

import java.util.ArrayList;

import DAO.SocioDAO;
import DAO.StuDAOException;
import beans.Nucleo;
import beans.Socio;

public class ProcuraNomeSemelhante {

	public static ArrayList<Socio> buscaSemelhantes(Socio pSocio, Nucleo pNucleo) throws StuDAOException{
		
		ArrayList<Socio> nomesSemelhantes = new ArrayList<Socio>();

		int finalPalavra = pSocio.getNome().indexOf(" ");
		String trechoNome = pSocio.getNome().substring(0, finalPalavra);

		//System.out.println("aqui "+pSocio.getNome());
		
		nomesSemelhantes = SocioDAO.listarSociosSemelhantes(trechoNome, pNucleo.getCodigo());
		
		//Traz todos os sócios com o primeiro nome igual, se existirem na base local
		//(exceto exatamente igual, porque em existindo já foi incluído) 

		ArrayList<Socio> listaAuxiliar = new ArrayList<Socio>();
		
		if(nomesSemelhantes!=null){
			//existe primeiro nome igual 

			for (Socio socio : nomesSemelhantes) {

				boolean existe = false;
				int inicio = pSocio.getNome().indexOf(" ");
				do{

					trechoNome = pSocio.getNome().substring(inicio);					
					trechoNome = trechoNome.trim();
					
					finalPalavra = trechoNome.indexOf(" ");
					
					if(finalPalavra>1){
						trechoNome = trechoNome.substring(0,finalPalavra);
					}else{
						trechoNome = trechoNome.substring(0);
					}

					inicio = inicio+finalPalavra+1;
					
					if(socio.getNome().toUpperCase().contains(" "+trechoNome.toUpperCase())&&trechoNome.length()>3){
						existe = true;
					}
				}while(finalPalavra>0);
				
				if (existe) {
					listaAuxiliar.add(socio);
				}
			}
		}
		return listaAuxiliar;
	}

}
