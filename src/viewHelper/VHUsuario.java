package viewHelper;

import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.FileItem;

import dominio.EntidadeDominio;
import dominio.Usuario;
import util.Resultado;

public class VHUsuario implements IViewHelper{

	@Override
	public EntidadeDominio getEntidade(HttpServletRequest request) {
		
		String stEmail = request.getParameter("email");
		String stSenha = request.getParameter("senha");
		String stConfirmaSenha = request.getParameter("conf-senha");
		Integer idUsuario = request.getParameter("idUsuario") != null ? Integer.parseInt(request.getParameter("idUsuario")) : null;
		
		Usuario usuario = new Usuario();
		
		try {
			if(idUsuario != null)
				usuario.setId(idUsuario);
			if(stEmail != null) {
				usuario.setEmail(stEmail.toLowerCase());				
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		usuario.setSenha(stSenha);
		usuario.setConfirmaSenha(stConfirmaSenha);
		usuario.setAdmin(0);
		
		return usuario;
	}

	@Override
	public void setView(Resultado resultado, HttpServletRequest request, HttpServletResponse response, String operacao) {
				
		try {	
			
			Usuario usuario = (Usuario) resultado.getEntidade();
			String mensagem[] = resultado.getMensagem().split("\n");
			
			if (resultado.getErro()) {
				request.setAttribute("erro", mensagem);
				request.setAttribute("usuario", usuario);
				RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
				rd.forward(request, response);
			} else { // sucessso
				String tela = "";
				if(1 == usuario.getAdmin()) {
					tela = "consulta-produto.jsp";
				} else {
					tela = "catalogo.jsp";
				}
				request.setAttribute("usuario", usuario);
				RequestDispatcher rd = request.getRequestDispatcher(tela);
				rd.forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public EntidadeDominio getEntidade(Map<String, List<FileItem>> multiparts, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}


}
