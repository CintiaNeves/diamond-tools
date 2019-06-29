package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dominio.EntidadeDominio;
import dominio.Produto;
import util.ConnectionFactory;
import util.Resultado;

public class ProdutoDAO implements IDAO {

	@Override
	public Resultado alterar(EntidadeDominio entidade) {
		Produto produto = (Produto) entidade;
		Resultado resultado = new Resultado();

		String sql = "UPDATE PRODUTOS  SET PROD_COD = ?, PROD_UN_MEDIDA = ?, PROD_DESC = ?, PROD_PRECO_COMPRA = ? , "
				+ "PROD_PRECO_VENDA = ?, PROD_FOTO = ? WHERE PROD_COD_BARRAS = '" + produto.getCodBarras() + "'";

		try (Connection connection = new ConnectionFactory().getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql)) {

			stmt.setInt(1, produto.getCodigo());
			stmt.setString(2, produto.getUnidadeMedida());
			stmt.setString(3, produto.getDescricao());
			stmt.setDouble(4, produto.getPrecoCompra());
			stmt.setDouble(5, produto.getPrecoVenda());
			stmt.setString(6, produto.getFoto());
			stmt.execute();
			resultado.setSucesso("Cadastro Atualizado com Sucesso.");
			resultado.setEntidade(produto);
		} catch (Exception e) {
			resultado.setErro("Não foi possivel Atualizar o cadastro.\n");
			e.printStackTrace();
		}
		return resultado;

	}

	@Override
	public Resultado consultar(EntidadeDominio entidade) {

		Produto produto = (Produto) entidade;
		
		Resultado resultado = new Resultado();
		List<EntidadeDominio> produtos = new ArrayList<>();

		String sql = "SELECT * FROM PRODUTOS ";
		
		boolean contemId = produto != null && produto.getId() > 0;
		
		if(contemId) {
			sql += "where prod_id = ?";
		}
		try (Connection connection = new ConnectionFactory().getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql)) {

			if(contemId) {
				stmt.setInt(1, produto.getId());
			}
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Produto p = new Produto();
				p.setId(rs.getInt("PROD_ID"));
				p.setCodigo(rs.getInt("PROD_COD"));
				p.setUnidadeMedida(rs.getString("PROD_UN_MEDIDA"));
				p.setDescricao(rs.getString("PROD_DESC"));
				p.setPrecoCompra(rs.getDouble("PROD_PRECO_COMPRA"));
				p.setPrecoVenda(rs.getDouble("PROD_PRECO_VENDA"));
				p.setCodBarras(rs.getString("PROD_COD_BARRAS"));
				p.setFoto(rs.getString("PROD_FOTO"));
				produtos.add(p);
			}
			rs.close();
			resultado.setSucesso("");
			resultado.setListEntidade(produtos);
		} catch (SQLException e) {
			resultado.setErro("Não foi possivel encotrar o cadastro.\n");
			e.printStackTrace();
		}
		return resultado;

	}

	@Override
	public Resultado excluir(EntidadeDominio entidade) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Resultado salvar(EntidadeDominio entidade) {

		Produto produto = (Produto) entidade;
		Resultado resultado = new Resultado();

		String sql = "INSERT INTO PRODUTOS (PROD_COD, PROD_UN_MEDIDA, PROD_DESC, PROD_PRECO_COMPRA, PROD_PRECO_VENDA, PROD_COD_BARRAS,PROD_FOTO) VALUES (?, ?, ?, ?, ?, ?, ?)";

		try (Connection connection = new ConnectionFactory().getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql)) {

			stmt.setInt(1, produto.getCodigo());
			stmt.setString(2, produto.getUnidadeMedida());
			stmt.setString(3, produto.getDescricao());
			stmt.setDouble(4, produto.getPrecoCompra());
			stmt.setDouble(5, produto.getPrecoVenda());
			stmt.setString(6, produto.getCodBarras());
			stmt.setString(7, produto.getFoto());
			stmt.execute();
			resultado.setSucesso("Cadastro Realizado com Sucesso.");
			resultado.setEntidade(produto);
		} catch (Exception e) {
			resultado.setErro("Não foi possivel realizar o cadastro.");
			e.printStackTrace();
		}
		return resultado;

	}

	@Override
	public Resultado consultarByCod(EntidadeDominio entidade) {
		
		Produto produto = (Produto) entidade;
		Resultado resultado = new Resultado();
		List<EntidadeDominio> produtos = new ArrayList<>();
		
		if (produto.getCodBarras() != null || (!produto.getCodBarras().trim().equals(""))) {

			String sql = "SELECT * FROM PRODUTOS WHERE PROD_COD_BARRAS = ?";

			try (Connection connection = new ConnectionFactory().getConnection();
					PreparedStatement stmt = connection.prepareStatement(sql)) {

				stmt.setString(1, produto.getCodBarras());

				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					Produto p = new Produto();
					p.setId(rs.getInt("PROD_ID"));
					p.setCodigo(rs.getInt("PROD_COD"));
					p.setUnidadeMedida(rs.getString("PROD_UN_MEDIDA"));
					p.setDescricao(rs.getString("PROD_DESC"));
					p.setPrecoCompra(rs.getDouble("PROD_PRECO_COMPRA"));
					p.setPrecoVenda(rs.getDouble("PROD_PRECO_VENDA"));
					p.setCodBarras(rs.getString("PROD_COD_BARRAS"));
					p.setFoto(rs.getString("PROD_FOTO"));
					produtos.add(p);
				}
				rs.close();
				if(produtos.size() == 0) {
					resultado.setSucesso("Produto não cadastrado!\n");
				}else {
					resultado.setSucesso("");
				}
				resultado.setListEntidade(produtos);
			} catch (SQLException e) {
				resultado.setErro("Não foi possivel encotrar o cadastro.");
				e.printStackTrace();
			}
			return resultado;
		}
		return resultado;

	}

}
