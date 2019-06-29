package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import command.CmdAlterar;
import command.CmdConsultar;
import command.CmdExcluir;
import command.CmdSalvar;
import command.ICommand;
import dominio.EntidadeDominio;
import util.Resultado;
import viewHelper.IViewHelper;
import viewHelper.VHUsuario;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = { "/login" })

public class ServeltLogin extends HttpServlet {

	private Map<String, ICommand> mapCommand;
	
	public ServeltLogin() {
		mapCommand = new HashMap<String, ICommand>();

		mapCommand.put("ALTERAR", new CmdAlterar());
		mapCommand.put("CONSULTAR", new CmdConsultar());
		mapCommand.put("EXCLUIR", new CmdExcluir());
		mapCommand.put("SALVAR", new CmdSalvar());
	}

	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String operacao = request.getParameter("operacao");
		ICommand command = mapCommand.get(operacao);
		IViewHelper VHUsuario = new VHUsuario();
		EntidadeDominio entidade = VHUsuario.getEntidade(request);
		Resultado resultado = command.executar(entidade);
		VHUsuario.setView(resultado, request, response, operacao);

	}

}
