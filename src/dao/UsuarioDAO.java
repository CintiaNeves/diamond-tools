package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import dominio.Carrinho;
import dominio.EntidadeDominio;
import dominio.Usuario;
import util.ConnectionFactory;
import util.Resultado;

public class UsuarioDAO implements IDAO{
	
	@Override
	public Resultado alterar(EntidadeDominio entidade) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Resultado consultar(EntidadeDominio entidade) {

		Usuario usuario = (Usuario) entidade;
		Resultado resultado = new Resultado();
		String sql = "SELECT * FROM USUARIOS ";
		
		boolean possuiId = usuario.getId() > 0;
		boolean possuiSenhaEmail = usuario.getSenha() != null && usuario.getEmail() != null;
		boolean and = false;
		
		if(possuiId || possuiSenhaEmail)
			sql += "where ";
		
		if(possuiId && possuiSenhaEmail)
			and = true;
		
		if(possuiSenhaEmail)
			sql += "USUARIO_EMAIL = ? AND USUARIO_SENHA = ? ";
		
		if(possuiId) {
			if(and)
				sql += "and ";
			sql += "usuario_id = ? ";
		}
			
		try (Connection connection = new ConnectionFactory().getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql)) {
			
			int counter = 0;
			if(possuiSenhaEmail) {
				stmt.setString(++counter, usuario.getEmail());
				stmt.setString(++counter, usuario.getSenha());
			}
			if(possuiId) {
				stmt.setInt(++counter, usuario.getId());
			}
			ResultSet rs = stmt.executeQuery();

			Usuario u = new Usuario();
			while (rs.next()) {
				u.setId(rs.getInt("USUARIO_ID"));
				u.setEmail(rs.getString("USUARIO_EMAIL"));
				u.setSenha(rs.getString("USUARIO_SENHA"));
				u.setAdmin(rs.getInt("USUARIO_ADMIN"));
				Carrinho c = new Carrinho();
				c.setId(rs.getInt("ID_CARRINHO"));
				u.setCarrinho(c);
			}
			rs.close();
			resultado.setEntidade(u);
			resultado.setSucesso("");
			return resultado;

		} catch (SQLException e) {
			resultado.setErro("Erro de consulta");
			e.printStackTrace();
			return resultado;
		}

	}

	@Override
	public Resultado excluir(EntidadeDominio entidade) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Resultado salvar(EntidadeDominio entidade) {
		
		IDAO carrinhoDAO = new CarrinhoDAO();
		Carrinho carrinho = new Carrinho();
		carrinho.setExpirado(false);
		Resultado resultadoCarrinho = carrinhoDAO.salvar(carrinho);
		
		Resultado resultado = new Resultado();
		Usuario usuario = (Usuario) entidade;
		String sql = "INSERT INTO USUARIOS (USUARIO_EMAIL, USUARIO_SENHA, USUARIO_ADMIN, ID_CARRINHO) VALUES (?, ?, ?, ?)";
		
		try(Connection connection = new ConnectionFactory().getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			stmt.setString(1, usuario.getEmail());
			stmt.setString(2, usuario.getSenha());
			stmt.setInt(3, usuario.getAdmin());
			stmt.setInt(4, resultadoCarrinho.getEntidade().getId());
			
			stmt.execute();
			
			ResultSet rs = stmt.getGeneratedKeys();
			int idUsuario = 0;
			if(rs.next()) {
				idUsuario = rs.getInt(1);
			}	
			usuario.setId(idUsuario);
			
			resultado.setSucesso("");
			resultado.setEntidade(usuario);
		} catch(Exception e) {
			resultado.setErro("Não foi possivel realizar o cadastro.");
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
