package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dominio.Carrinho;
import dominio.EntidadeDominio;
import dominio.ItemCarrinho;
import dominio.Pedido;
import util.ConnectionFactory;
import util.Resultado;

public class PedidoDAO implements IDAO{

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
		double valorTotal = 0;
		Pedido p = (Pedido) entidade;
		Carrinho c = p.getCarrinho();
		IDAO daoItem = new ItemCarrinhoDAO();
		Resultado r = daoItem.consultar(c);
		List<EntidadeDominio> entidades =  r.getListEntidade();
		List<ItemCarrinho> itensCarrinho = new ArrayList<ItemCarrinho>();
		
		for(EntidadeDominio e: entidades) {
			ItemCarrinho i = (ItemCarrinho) e;
			valorTotal += i.getQuantidade() * i.getProduto().getPrecoVenda();
			itensCarrinho.add(i);
		}
		c.setListItemCarrinho(itensCarrinho);
		p.setCarrinho(c);
		p.setValorTotal(valorTotal);
		
		String sql = "INSERT INTO PEDIDOS (VALOR_TOTAL, STATUS, DATA_PEDIDO, ID_CLIENTE) values (?, ?, ?, ?)";
		
		try(Connection connection = new ConnectionFactory().getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
			
			stmt.setDouble(1, p.getValorTotal());
			stmt.setString(2, p.getStatus());
			stmt.setString(3, p.getDataPedido());
			stmt.setInt(4, p.getClienteID());
			
			stmt.execute();

			ResultSet rs = stmt.getGeneratedKeys();
			int id = 0;
			if(rs.next()) {
				id = rs.getInt(1);
			}
			p.setId(id);
			IDAO daoPedidoItem = new PedidoItemDAO();
			resultado = daoPedidoItem.salvar(p);			
			
		}catch(Exception e) {
			resultado.setErro("Não foi possivel salvar o carrinho.");
			e.printStackTrace();
		}
		return resultado;
	}

	@Override
	public Resultado consultarByCod(EntidadeDominio entidade) {
		// TODO Auto-generated method stub
		return null;
	}

}
