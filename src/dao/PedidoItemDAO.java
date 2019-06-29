package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import dominio.Carrinho;
import dominio.EntidadeDominio;
import dominio.ItemCarrinho;
import dominio.Pedido;
import util.ConnectionFactory;
import util.Resultado;

public class PedidoItemDAO implements IDAO{

	@Override
	public Resultado alterar(EntidadeDominio entidade) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Resultado consultar(EntidadeDominio entidade) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Resultado excluir(EntidadeDominio entidade) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Resultado salvar(EntidadeDominio entidade) {
		
		Resultado resultado = new Resultado();
		Pedido pedido = (Pedido) entidade;
		Carrinho carrinho = pedido.getCarrinho();
		List<ItemCarrinho> itens = carrinho.getListItemCarrinho();
		Connection connection = new ConnectionFactory().getConnection();
		
		String sql = "INSERT INTO PEDIDO_ITEM (ID_PRODUTO, ID_PEDIDO, QUANTIDADE) values (?, ?, ?)";
		
		for(ItemCarrinho i : itens) {
			
			try (
					PreparedStatement stmt = connection.prepareStatement(sql)){
					stmt.setInt(1, i.getProduto().getId());
					stmt.setInt(2, pedido.getId());
					stmt.setInt(3, i.getQuantidade());
					stmt.execute();
					resultado.setSucesso("Itens do pedido salvos com sucesso.");
					resultado.setEntidade(pedido);
			}catch (Exception e) {
				resultado.setErro("Não foi possivel gravar os itens do pedido.\n");
				e.printStackTrace();
			}
			
		}
		IDAO daoItemCarrinho = new ItemCarrinhoDAO();
		resultado = daoItemCarrinho.excluir(carrinho);	
		return resultado;
		
	}

	@Override
	public Resultado consultarByCod(EntidadeDominio entidade) {
		// TODO Auto-generated method stub
		return null;
	}

}
