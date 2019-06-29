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
import util.ConnectionFactory;
import util.Resultado;

public class CarrinhoDAO implements IDAO {

	@Override
	public Resultado alterar(EntidadeDominio entidade) {

		Carrinho c = (Carrinho) entidade;
		Resultado resultado = new Resultado();
		
		String sql = "update carrinhos set expirado = ? where id = ?";
		try (Connection connection = new ConnectionFactory().getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql)) {
		
			stmt.setBoolean(1, c.isExpirado());
			stmt.setInt(2, c.getId());
			
			stmt.execute();
			
			resultado.setEntidade(c);
		
		} catch (Exception e) {
			resultado.setErro("Erro de alterar");
			e.printStackTrace();
			return resultado;
		}
		return resultado;
	}

	@Override
	public Resultado consultar(EntidadeDominio entidade) {
		Carrinho carrinho = (Carrinho) entidade;
		Resultado resultado = new Resultado();
		
		String sql = "SELECT * FROM carrinhos ";
		boolean contemId = carrinho != null && carrinho.getId() > 0;
		
		if(contemId) 
			sql += "WHERE id = ?";
		
		try (Connection connection = new ConnectionFactory().getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql)) {

			if(contemId) 
				stmt.setInt(1, carrinho.getId());
			
			ResultSet rs = stmt.executeQuery();

			Carrinho c = new Carrinho();
			while (rs.next()) {
				c.setId(rs.getInt("ID"));
				c.setExpirado(rs.getBoolean("EXPIRADO"));
			}
			rs.close();
			
			ItemCarrinhoDAO itemCarrinhoDAO = new ItemCarrinhoDAO();
			Resultado r = itemCarrinhoDAO.consultar(c);
			List<ItemCarrinho> listItemCarrinho = new ArrayList<>();
			if(r.getListEntidade().size() > 0) {
				for(EntidadeDominio e : r.getListEntidade()) {
					listItemCarrinho.add((ItemCarrinho) e);					
				}
			}
			c.setListItemCarrinho(listItemCarrinho);
			resultado.setEntidade(c);
			resultado.setSucesso("");
			return resultado;

		} catch (Exception e) {
			resultado.setErro("Erro de consulta");
			e.printStackTrace();
			return resultado;
		}

	}

	@Override
	public Resultado excluir(EntidadeDominio entidade) {
		
		Resultado resultado = new Resultado();
		Carrinho c = (Carrinho) entidade;
		String sql = "DELETE FROM ITENS_CARRINHO WHERE ID = ?";
		
		
		try (Connection connection = new ConnectionFactory().getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql)) {
			
			stmt.setInt(1, c.getListItemCarrinho().get(0).getId());
			stmt.execute(); 
			IDAO daoItem = new ItemCarrinhoDAO();
			Resultado r = daoItem.consultar(c);
			List<EntidadeDominio> entidades =  r.getListEntidade();
			List<ItemCarrinho> itensCarrinho = new ArrayList<ItemCarrinho>();
			for(EntidadeDominio e: entidades) {
				ItemCarrinho i = (ItemCarrinho) e;
				itensCarrinho.add(i);
			}
			c.setListItemCarrinho(itensCarrinho);
			resultado.setEntidade(c);
			resultado.setSucesso("");
			return resultado;
			
		}catch (Exception e) {
			resultado.setErro("Erro de consulta");
			e.printStackTrace();
			return resultado;
		}
	}

	@Override
	public Resultado salvar(EntidadeDominio entidade) {
		
		Resultado resultado = new Resultado();
		Carrinho carrinho = (Carrinho) entidade;
		
		String sql = "insert into carrinhos (expirado) values (?)";
		
		try(Connection connection = new ConnectionFactory().getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			stmt.setBoolean(1, carrinho.isExpirado());
			stmt.execute();

			ResultSet rs = stmt.getGeneratedKeys();
			int id = 0;
			if(rs.next()) {
				id = rs.getInt(1);
			}
			carrinho.setId(id);
			resultado.setSucesso("Carrinho criado com sucesso.");
			resultado.setEntidade(carrinho);
		} catch(Exception e) {
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
