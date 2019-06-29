package viewHelper;

import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.FileItem;

import dominio.EntidadeDominio;
import dominio.Relatorio;
import util.Resultado;

public class VHRelatorio implements IViewHelper{

	@Override
	public EntidadeDominio getEntidade(HttpServletRequest request) {

		String stDataInicio = request.getParameter("data-inicio");
		String stDataFim = request.getParameter("data-fim");
		
		Relatorio relatorio = new Relatorio();
		relatorio.setDataInicio(stDataInicio);
		relatorio.setDataFim(stDataFim);
		
		
		
		return relatorio;
	}

	@Override
	public EntidadeDominio getEntidade(Map<String, List<FileItem>> multiparts, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setView(Resultado resultado, HttpServletRequest request, HttpServletResponse response,
			String operacao) {
		
		
		try {
			if(operacao.equals("CONSULTAR") || operacao.equals("CONSULTARBYCOD")) {
				if(!resultado.getErro()) {
					request.setAttribute("relatorio", resultado.getEntidade());
		
					RequestDispatcher rd = request.getRequestDispatcher("relatorio.jsp");
						rd.forward(request, response);
				} 					
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
