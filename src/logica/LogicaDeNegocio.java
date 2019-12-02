package logica;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface LogicaDeNegocio{
		void execute(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
