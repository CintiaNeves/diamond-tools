package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import command.CmdAlterar;
import command.CmdConsultar;
import command.CmdConsultarByCod;
import command.CmdExcluir;
import command.CmdSalvar;
import command.ICommand;
import dominio.EntidadeDominio;
import util.Resultado;
import viewHelper.IViewHelper;
import viewHelper.VHProduto;

@SuppressWarnings("serial")
@WebServlet( urlPatterns = {"/produto"} )

public class ServletProduto extends HttpServlet{

	private Map<String, ICommand> mapCommand;
	
	public ServletProduto(){
		
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
			IViewHelper VHProduto =  new VHProduto();
			EntidadeDominio entidade = null;
			String operacao = "";
			
			OperacaoController op = new OperacaoController();
			ICommand command;
			
			if (ServletFileUpload.isMultipartContent(request)) {
				Map<String, List<FileItem>> multiparts = new ServletFileUpload(new DiskFileItemFactory())
					.parseParameterMap(request);
				
				operacao = op.getOperacao(multiparts);
				command = mapCommand.get(operacao);
				entidade = VHProduto.getEntidade(multiparts,request);
				
			}else{
				
				operacao = op.getOperacao(request);
				command = mapCommand.get(operacao);
				entidade = VHProduto.getEntidade(request);			
			}
			
			Resultado resultado = command.executar(entidade);
			VHProduto.setView(resultado, request, response, operacao);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

	
}