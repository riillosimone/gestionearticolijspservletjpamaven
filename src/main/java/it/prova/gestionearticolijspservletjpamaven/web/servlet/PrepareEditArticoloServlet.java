package it.prova.gestionearticolijspservletjpamaven.web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.math.NumberUtils;

import it.prova.gestionearticolijspservletjpamaven.service.MyServiceFactory;

@WebServlet("/PrepareEditArticoloServlet")
public class PrepareEditArticoloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String parametroIdArticoloToEdit = request.getParameter("idArticolo");
		Long idArticoloToEdit = Long.parseLong(parametroIdArticoloToEdit);

		if (!NumberUtils.isCreatable(parametroIdArticoloToEdit)) {
			request.setAttribute("errorMessage", "Attenzione si è verificato un errore.");
			request.getRequestDispatcher("/index.jsp").forward(request, response);
			return;
		}

		try {
			request.setAttribute("articoloDaInviareAPaginaEdit",
					MyServiceFactory.getArticoloServiceInstance().caricaSingoloElemento(idArticoloToEdit));
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "Attenzione si è verificato un errore.");
			request.getRequestDispatcher("/index.jsp").forward(request, response);
			return;
		}
		request.getRequestDispatcher("/articolo/edit.jsp").forward(request, response);
	}

}
