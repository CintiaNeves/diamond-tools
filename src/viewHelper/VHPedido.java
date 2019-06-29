package viewHelper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.FileItem;

import dominio.Carrinho;
import dominio.Cliente;
import dominio.EntidadeDominio;
import dominio.Pedido;
import dominio.Usuario;
import util.Resultado;

public class VHPedido implements IViewHelper{

	@Override
	public EntidadeDominio getEntidade(HttpServletRequest request) {
		
		Integer idCliente = request.getParameter("id-cliente") != null ? idCliente = Integer.parseInt(request.getParameter("id-cliente")) : null;
		Integer idUsuario = request.getParameter("id-usr") != null ? Integer.parseInt(request.getParameter("id-usr")) : null;
		Integer idCarrinho = request.getParameter("id-car") != null ? Integer.parseInt(request.getParameter("id-car")) : null;
		
		Cliente cliente = new Cliente();
		Carrinho carrinho = new Carrinho();
		Usuario usuario = new Usuario();
		Pedido pedido = new Pedido();
		usuario.setId(idUsuario);
		cliente.setUsuario(usuario);
		cliente.setId(idCliente);
		
		carrinho.setId(idCarrinho);
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dataPedido = now.format(formatter);
        
        pedido.setDataPedido(dataPedido);
		pedido.setClienteID(idCliente);
		pedido.setValorTotal(0);
		pedido.setStatus("Fechado");
		pedido.setFrete(0);
		pedido.setCarrinho(carrinho);
		
		return pedido;
	}

	@Override
	public EntidadeDominio getEntidade(Map<String, List<FileItem>> multiparts, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setView(Resultado resultado, HttpServletRequest request, HttpServletResponse response,
			String operacao) {
		String mensagem[] = resultado.getMensagem().split("\n");
		try {
			if(operacao.equals("SALVAR")) {
				if(!resultado.getErro()) {
					request.setAttribute("sucesso", mensagem);
					RequestDispatcher rd = request.getRequestDispatcher("carrinho.jsp");
						rd.forward(request, response);
				} 					
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
