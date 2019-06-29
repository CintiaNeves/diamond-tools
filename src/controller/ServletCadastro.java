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
import viewHelper.VHCliente;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = { "/cadastro" })

public class ServletCadastro extends HttpServlet{
	
	private Map<String, ICommand> mapCommand;

	public ServletCadastro() {
		
		mapCommand = new HashMap<String, ICommand>();
		
		mapCommand.put("ALTERAR", new CmdAlterar());
		mapCommand.put("CONSULTAR", new CmdConsultar());
		mapCommand.put("EXCLUIR", new CmdExcluir());
		mapCommand.put("SALVAR", new CmdSalvar());
	}
	
	@Override
	public void service(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException{
		
		String operacao = request.getParameter("operacao");
		IViewHelper vhCliente = new VHCliente();
		EntidadeDominio entidade = vhCliente.getEntidade(request);
		ICommand command = mapCommand.get(operacao);
		Resultado resultado = command.executar(entidade);
		vhCliente.setView(resultado, request, response, operacao);	
	}

}
