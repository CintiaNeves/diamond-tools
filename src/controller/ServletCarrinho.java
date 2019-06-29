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
import command.CmdConsultarByCod;
import command.CmdExcluir;
import command.CmdSalvar;
import command.ICommand;
import dominio.EntidadeDominio;
import util.Resultado;
import viewHelper.IViewHelper;
import viewHelper.VHCarrinho;

@SuppressWarnings("serial")
@WebServlet( urlPatterns = {"/carrinho"} )

public class ServletCarrinho extends HttpServlet{
	
private Map<String, ICommand> mapCommand;
	
	public ServletCarrinho(){
		
		mapCommand = new HashMap<String, ICommand>();
		
		mapCommand.put("ALTERAR", new CmdAlterar());
		mapCommand.put("CONSULTAR", new CmdConsultar());
		mapCommand.put("EXCLUIR", new CmdExcluir());
		mapCommand.put("SALVAR", new CmdSalvar());
		mapCommand.put("CONSULTARBYCOD", new CmdConsultarByCod());
		
	}
	

	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		
		try {
			IViewHelper VHCarrinho =  new VHCarrinho();
			EntidadeDominio entidade = null;
			String operacao = "";
			OperacaoController op = new OperacaoController();
			ICommand command;
			
			operacao = op.getOperacao(request);
			command = mapCommand.get(operacao);
			entidade = VHCarrinho.getEntidade(request);			
		
			Resultado resultado = command.executar(entidade);
			VHCarrinho.setView(resultado, request, response, operacao);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

	

}
