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
import dominio.Produto;
import util.ConnectionFactory;
import util.Resultado;

public class ItemCarrinhoDAO implements IDAO {

	@Override
	public Resultado alterar(EntidadeDominio entidade) {

		ItemCarrinho ic = (ItemCarrinho) entidade;
		Resultado r = new Resultado();
		String sql = "update itens_carrinho set quantidade = ? where id = ?";

		try (Connection connection = new ConnectionFactory().getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql)) {
			
			stmt.setInt(1,  ic.getQuantidade());
			stmt.setInt(2, ic.getId());
			stmt.execute();
			
		} catch(Exception e) {
			r.setErro("Erro ao alterar itens do carrinho");
			e.printStackTrace();
		}
		return r;
	}

	@Override
	public Resultado consultar(EntidadeDominio entidade) {
		
		Resultado resultado = new Resultado();
		
		if(entidade instanceof Carrinho) {
			Carrinho c = (Carrinho) entidade;
			
			String sql = "select * "
					+ "from itens_carrinho ic "
					+ "join carrinhos c on ic.id_carrinho=c.id "
					+ "where ic.id_carrinho = ?";
		
			try (Connection connection = new ConnectionFactory().getConnection();
					PreparedStatement stmt = connection.prepareStatement(sql)) {
			
				stmt.setInt(1, c.getId());
				ResultSet rs = stmt.executeQuery();
				List<EntidadeDominio> listItemCarrinho = new ArrayList<>();
				
				IDAO pDAO = new ProdutoDAO();
				while(rs.next()) {
					
					ItemCarrinho ic = new ItemCarrinho();
					ic.setId(rs.getInt("ID"));
					ic.setQuantidade(rs.getInt("QUANTIDADE"));
					
					Produto p = new Produto();
					p.setId(rs.getInt("ID_PRODUTO"));
					p = (Produto) pDAO.consultar(p).getEntidade();
					ic.setProduto(p);
					ic.setCarrinho(c);
					listItemCarrinho.add(ic);
				}
				
				resultado.setListEntidade(listItemCarrinho);
				
				rs.close();
			} catch(Exception e) {
				resultado.setErro("Erro ao consultar itens do carrinho");
				e.printStackTrace();
			}
		} else if(entidade instanceof ItemCarrinho) {
			
			ItemCarrinho itemCarrinho = (ItemCarrinho) entidade;
			boolean contemId = itemCarrinho != null 
					&& itemCarrinho.getId() > 0;
			boolean contemCarrinho = itemCarrinho != null 
					&& itemCarrinho.getCarrinho() != null 
					&& itemCarrinho.getCarrinho().getId() > 0;
			boolean contemProduto = itemCarrinho != null 
					&& itemCarrinho.getProduto() != null 
					&& itemCarrinho.getProduto().getId() > 0;
			
			String sql = "select * from itens_produto ip ";
			
			if(contemId)
				sql += "where id = ? ";
			
			try (Connection connection = new ConnectionFactory().getConnection();
					PreparedStatement stmt = connection.prepareStatement(sql)) {
				
				if(contemId)
					stmt.setInt(1, itemCarrinho.getId()); 

				List<EntidadeDominio> listItemCarrinho = new ArrayList<>();
				ResultSet rs = stmt.executeQuery();
				IDAO cDAO = new CarrinhoDAO();
				IDAO pDAO = new ProdutoDAO();
				while(rs.next()) {
					ItemCarrinho ic = new ItemCarrinho();
					Carrinho c = new Carrinho();
					Produto p = new Produto();

					ic.setId(rs.getInt("ID"));
					ic.setQuantidade(rs.getInt("QUANTIDADE"));
					if(contemCarrinho) {
						c.setId(itemCarrinho.getCarrinho().getId());
						Resultado r = cDAO.consultar(c);
						ic.setCarrinho((Carrinho) r.getEntidade());
					}
					if(contemProduto) {
						p.setId(itemCarrinho.getProduto().getId());
						Resultado r = pDAO.consultar(p);
						ic.setProduto((Produto) r.getEntidade()); 
					}
					listItemCarrinho.add(ic);
				}
				rs.close();
			} catch(Exception e) {
				resultado.setErro("Erro ao consultar itens do carrinho");
				e.printStackTrace();
			}
		}
		return resultado;
	}

	@Override
	public Resultado excluir(EntidadeDominio entidade) {
		
		Resultado resultado = new Resultado();
		Carrinho carrinho = (Carrinho) entidade;
		String sql = "DELETE FROM ITENS_CARRINHO WHERE ID_CARRINHO = ?";
		
		try (Connection connection = new ConnectionFactory().getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql)){
			stmt.setInt(1, carrinho.getId());
			stmt.execute();
			resultado.setSucesso("Pedido Finalidado com sucesso, enviaremos os dados de rastreio para o e-mail cadastrado");
			resultado.setEntidade(null);
			
		}catch (Exception e) {
			resultado.setErro("Não foi possivel esvaziar o carrinho.\n");
			e.printStackTrace();
		}
		return resultado;
	}

	@Override
	public Resultado salvar(EntidadeDominio entidade) {

		ItemCarrinho ic = (ItemCarrinho) entidade;
		Resultado r = new Resultado();
		String sql = "insert into itens_carrinho (quantidade, id_carrinho, id_produto) values (?, ?, ?)";
		
		try (Connection connection = new ConnectionFactory().getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			
			stmt.setInt(1, ic.getQuantidade());
			stmt.setInt(2, ic.getCarrinho().getId());
			stmt.setInt(3, ic.getProduto().getId());
			
			stmt.execute();

			ResultSet rs = stmt.getGeneratedKeys();
			int id = 0;
			if(rs.next()) {
				id = rs.getInt(1);
			}
			ic.setId(id);
			r.setEntidade(ic);
			rs.close();
		} catch(Exception e) {
			r.setErro("Erro ao salvar itens do carrinho");
			e.printStackTrace();
		}
		return r;
	}

	@Override
	public Resultado consultarByCod(EntidadeDominio entidade) {
		// TODO Auto-generated method stub
		return null;
	}

}
