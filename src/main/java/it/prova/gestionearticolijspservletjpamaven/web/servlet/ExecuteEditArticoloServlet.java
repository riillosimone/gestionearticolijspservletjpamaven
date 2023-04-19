package it.prova.gestionearticolijspservletjpamaven.web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.prova.gestionearticolijspservletjpamaven.model.Articolo;
import it.prova.gestionearticolijspservletjpamaven.service.MyServiceFactory;
import it.prova.gestionearticolijspservletjpamaven.utility.UtilityArticoloForm;

@WebServlet("/ExecuteEditArticoloServlet")
public class ExecuteEditArticoloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// estraggo input
		String codiceInputParam = request.getParameter("codice");
		String descrizioneInputParam = request.getParameter("descrizione");
		String prezzoInputStringParam = request.getParameter("prezzo");
		String dataArrivoStringParam = request.getParameter("dataArrivo");
		String idArticoloParam = request.getParameter("idArticoloToEdit");

		// preparo un bean (che mi serve sia per tornare in pagina
		// che per inserire) e faccio il binding dei parametri
		Articolo articoloInstance = UtilityArticoloForm.createArticoloFromParamsWithId(idArticoloParam,
				codiceInputParam, descrizioneInputParam, prezzoInputStringParam, dataArrivoStringParam);

		if (!UtilityArticoloForm.validateArticoloBean(articoloInstance)) {
			request.setAttribute("articoloDaInviareAPaginaEdit", articoloInstance);
			request.setAttribute("errorMessage", "Attenzione sono presenti errori di validazione");
			request.getRequestDispatcher("/articolo/edit.jsp").forward(request, response);
			return;
		}
		
		try {
			MyServiceFactory.getArticoloServiceInstance().aggiorna(articoloInstance);
			request.setAttribute("listaArticoliAttribute", MyServiceFactory.getArticoloServiceInstance().listAll());
			request.setAttribute("successMessage", "Operazione effettuata con successo");
	
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "Attenzione si è verificato un errore.");
			request.getRequestDispatcher("/index.jsp").forward(request, response);
			return;
		}
		request.getRequestDispatcher("/articolo/results.jsp").forward(request, response);
	}

}
