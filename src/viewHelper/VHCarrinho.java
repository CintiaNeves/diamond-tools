package viewHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.FileItem;

import dominio.Carrinho;
import dominio.EntidadeDominio;
import dominio.ItemCarrinho;
import dominio.Produto;
import dominio.Usuario;
import util.Resultado;

public class VHCarrinho implements IViewHelper{

	@Override
	public EntidadeDominio getEntidade(HttpServletRequest request) {
		
		Carrinho carrinho = new Carrinho();
		Integer id = null;
		Integer idCar = null;
		Integer idItem = null;
		Integer idProd = null;
		String operacao = request.getParameter("operacao");
		
		if(operacao.equals("CONSULTAR")) {
			id = request.getParameter("idCarrinho") != null ? Integer.parseInt(request.getParameter("idCarrinho")) : null;
			carrinho.setId(id);
		} else if(operacao.equals("ALTERAR")) {
			id = request.getParameter("idCarrinho") != null ? Integer.parseInt(request.getParameter("idCarrinho")) : null;
			Integer idProduto = request.getParameter("idProduto") != null ? Integer.parseInt(request.getParameter("idProduto")) : null;
			Integer idUsuario = request.getParameter("idUsuario") != null ? Integer.parseInt(request.getParameter("idUsuario")) : null;
			Integer idCarrinho = request.getParameter("idCarrinho") != null ? Integer.parseInt(request.getParameter("idCarrinho")) : null;
			Integer quantidade = request.getParameter("quantidade") != null ? Integer.parseInt(request.getParameter("quantidade")) : null;
			
			Produto p = new Produto();
			p.setId(idProduto);
			Usuario u = new Usuario();
			u.setId(idUsuario);
			carrinho.setId(idCarrinho);
			ItemCarrinho ic = new ItemCarrinho();
			ic.setQuantidade(quantidade);
			
			ic.setProduto(p);
			ic.setCarrinho(carrinho);
			List<ItemCarrinho> listItemCarrinho = new ArrayList<>();
			listItemCarrinho.add(ic);
			carrinho.setListItemCarrinho(listItemCarrinho);
			u.setCarrinho(carrinho);
		}else if(operacao.equals("EXCLUIR")) {
			
			idCar = request.getParameter("id-carrinho") != null ? Integer.parseInt(request.getParameter("id-carrinho")) : null;
			idItem = request.getParameter("id-item")!= null ? Integer.parseInt(request.getParameter("id-item")): null;
			idProd = request.getParameter("id-produto") != null ? Integer.parseInt(request.getParameter("id-produto")) : null;
			
			Produto p = new Produto();
			p.setId(idProd);
			carrinho.setId(idCar);
			ItemCarrinho ic = new ItemCarrinho();
			ic.setId(idItem);
			
			ic.setProduto(p);
			ic.setCarrinho(carrinho);
			List<ItemCarrinho> listItemCarrinho = new ArrayList<>();
			listItemCarrinho.add(ic);
			carrinho.setListItemCarrinho(listItemCarrinho);
		}
		return carrinho;
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
			if(operacao.equals("CONSULTAR") || operacao.equals("ALTERAR") || operacao.equals("EXCLUIR")) {
				if(!resultado.getErro()) {
					request.setAttribute("carrinho", resultado.getEntidade());
		
					RequestDispatcher rd = request.getRequestDispatcher("carrinho.jsp");
						rd.forward(request, response);
				} 					
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
