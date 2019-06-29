package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import dominio.EntidadeDominio;
import dominio.Relatorio;
import util.ConnectionFactory;
import util.Resultado;

public class RelatorioDAO implements IDAO{

	@Override
	public Resultado alterar(EntidadeDominio entidade) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Resultado consultar(EntidadeDominio entidade) {
		
		Resultado resultado = new Resultado();
		Relatorio relatorio = (Relatorio) entidade;
		
		String sql = "SELECT TOP 1 P.PROD_DESC, PROD_PRECO_VENDA, SUM(PI.QUANTIDADE) QUANTIDADE " + 
				"FROM PRODUTOS P " + 
				"JOIN PEDIDO_ITEM PI ON P.PROD_ID = PI.ID_PRODUTO " + 
				"JOIN PEDIDOS PD ON PD.ID = PI.ID_PEDIDO AND PD.DATA_PEDIDO BETWEEN ? AND ? " + 
				"GROUP BY P.PROD_DESC, PROD_PRECO_VENDA " + 
				"ORDER BY QUANTIDADE DESC";
		
		try (Connection connection = new ConnectionFactory().getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql)){
			
			stmt.setString(1, relatorio.getDataInicio());
			stmt.setString(2, relatorio.getDataFim());
			
			ResultSet rs = stmt.executeQuery();

			Relatorio r = new Relatorio();
			while (rs.next()) {
				r.setDescProduto(rs.getString("PROD_DESC"));
				r.setValorUnitario(rs.getDouble("PROD_PRECO_VENDA"));
				r.setQuantidade(rs.getInt("QUANTIDADE"));
				r.setTotalVendido(r.getValorUnitario() * r.getQuantidade());
				r.setDataInicio(relatorio.getDataInicio());
				r.setDataFim(relatorio.getDataFim());
			}
			rs.close();
			
			resultado.setEntidade(r);
			
		}catch (Exception e) {
			resultado.setErro("Erro de alterar");
			e.printStackTrace();
			return resultado;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Resultado consultarByCod(EntidadeDominio entidade) {
		Resultado resultado = new Resultado();
		Relatorio relatorio = (Relatorio) entidade;
		
		String sql = "SELECT SUM(PROD_PRECO_VENDA * QUANTIDADE) AS FATURAMENTO, SUM(PROD_PRECO_COMPRA * quantidade) " + 
				"AS CUSTO FROM PRODUTOS P INNER JOIN PEDIDO_ITEM PI ON P.PROD_ID = PI.ID_PRODUTO " + 
				"INNER JOIN PEDIDOS PD ON PD.ID = PI.ID_PEDIDO WHERE PD.DATA_PEDIDO BETWEEN ? AND ?";
		
		try (Connection connection = new ConnectionFactory().getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql)){
			
			stmt.setString(1, relatorio.getDataInicio());
			stmt.setString(2, relatorio.getDataFim());
			
			ResultSet rs = stmt.executeQuery();

			Relatorio r = new Relatorio();
			
			while (rs.next()) {
				r.setFaturamentoBruto(rs.getDouble("FATURAMENTO"));
				r.setCusto(rs.getDouble("CUSTO"));
				r.setLucroLiquido(r.getFaturamentoBruto() - r.getCusto());
			}
			rs.close();
			
			resultado.setEntidade(r);
			
		}catch (Exception e) {
			resultado.setErro("Erro de alterar");
			e.printStackTrace();
			return resultado;
		}
		return resultado;
	}

}
