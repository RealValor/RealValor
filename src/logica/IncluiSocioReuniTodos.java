package logica;

import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.SocioReuniDAO;
import beans.Login;
import beans.Socio;
import beans.SocioReuni;

public class IncluiSocioReuniTodos implements LogicaDeNegocio{

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequestDispatcher rd = null;

		String mens = "Associados não incluídos devido a ausência do cpf, ou cpf incorreto";
		String pgJsp = "/listaSocioReuni.jsp";

		HttpSession sessao = request.getSession();

		Login objOperador = new Login();
		objOperador = (Login)sessao.getAttribute("objUsu");

		ArrayList<SocioReuni> listaSocioReuni = new ArrayList<SocioReuni>();
		listaSocioReuni = SocioReuniDAO.listarAuxiliarSocioReuni(objOperador.getNucleo()); 

		String cpfString="";
		for (SocioReuni socioReuni : listaSocioReuni) {

			SocioReuni objSocioReuni = new SocioReuni();
			objSocioReuni=SocioReuniDAO.consultarSocioReuni(socioReuni);

			Socio objSocio = new Socio();

			objSocio = PopulaSocio.alimentaDadosSocio(0, socioReuni.getCpf(), objSocioReuni);

			if(objSocio.getCpf()>0){

				cpfString=objSocio.getCpfStr().replace(".","");
				cpfString=cpfString.replace("-", "");
				
				if(VerificaCPF.verificarCPF(cpfString)==1){
					ArrayList<Socio> listaSocioAuxiliar = BuscaNomeSemelhante.buscaNomeSemelhante(objSocio, socioReuni, objOperador);
					//Incluir apenas os sócios com nome único. Os que possueirem nome semelhante deixar pra escolha pelo botao incluir individualizado
					sessao.setAttribute("listaSocioAuxiliar", listaSocioAuxiliar);
				}
			}
		}

		sessao.setAttribute("gravaTodos", -1);

		listaSocioReuni = SocioReuniDAO.listarAuxiliarSocioReuni(objOperador.getNucleo()); 

		if(listaSocioReuni!=null){
			sessao.setAttribute("listaSocioReuni", listaSocioReuni);
			sessao.setAttribute("totalSocios", listaSocioReuni.size());
		}

		request.setAttribute("retorno", mens);
		rd = request.getRequestDispatcher(pgJsp);
		rd.forward(request, response);
	}
}
