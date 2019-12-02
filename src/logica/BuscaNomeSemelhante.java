package logica;

import java.util.ArrayList;

import DAO.LoginDAO;
import DAO.SocioDAO;
import DAO.SocioReuniDAO;
import beans.Login;
import beans.Socio;
import beans.SocioReuni;

public class BuscaNomeSemelhante {

	public static ArrayList<Socio> buscaNomeSemelhante(Socio pSocio, SocioReuni pSocioReuni, Login pOperador) throws Exception{

		ArrayList<Socio> listaSocioAuxiliarReuni = new ArrayList<Socio>();

		if(pSocio.getCpf()>0){

			Socio objSocio = pSocio;
			
			listaSocioAuxiliarReuni = ProcuraNomeSemelhante.buscaSemelhantes(objSocio, pOperador.getNucleo());
			//Procurar socio semelhante. Busca o primeiro nome, se encontrar igual, compara o primeiro sobrenome com todos os sobrenomes, 
			//se não encontrar grava
			
			if(listaSocioAuxiliarReuni.size() == 0){
				//Não existindo nome semelhante, grava na base local.
				
				SocioDAO objSocioDAO = new SocioDAO();
				
				objSocioDAO.incluirSocio(objSocio);
				//inclui o sócio. 
				
				objSocio = objSocioDAO.consultarCodigoSocio(objSocio);
				objSocioDAO.incluirSocioNucleo(objSocio.getCodigo(),pOperador.getNucleo().getCodigo());
				//vinclula sócio ao núcleo. 
				
				Login objSocioOperador = new Login();
				
				objSocioOperador.setUsuario(objSocio.getCodigo());
				objSocioOperador.setCpf(objSocio.getCpf());
				objSocioOperador.setNucleo(pOperador.getNucleo());

				LoginDAO objLoginDAO = new LoginDAO();
				
				if(objLoginDAO.consultarUsuNome(objSocioOperador)==null){

					objSocioOperador.setSenha(Encripta.encriptaDados("123456"));
					objSocioOperador.setNivel(1);

					objLoginDAO.incluirLogin(objSocioOperador);
				}

				//-----------------------------------

				SocioReuniDAO.excluirSocioReuni(pSocioReuni, pOperador.getNucleo());
				//Baixa da tb_auxilliar_reuni
			}

		}
		return listaSocioAuxiliarReuni;
	}
}
