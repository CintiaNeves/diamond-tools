package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dominio.Cliente;
import dominio.EntidadeDominio;
import dominio.Usuario;
import util.ConnectionFactory;
import util.Resultado;

public class ClienteDAO implements IDAO{
	
	@Override
	public Resultado alterar(EntidadeDominio entidade) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Resultado consultar(EntidadeDominio entidade) {

		Resultado r = new Resultado();
		Cliente cliente = (Cliente) entidade;
		String sql = "select * from clientes c ";
		
		boolean possuiUsuario = cliente.getUsuario() != null && cliente.getUsuario().getId() > 0;
		
		if(possuiUsuario)
			sql += "join usuarios u on u.usuario_id=c.cli_user_id where u.usuario_id = ?";
		
		try(Connection connection = new ConnectionFactory().getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			
			if(possuiUsuario) {
				IDAO uDAO = new UsuarioDAO();
				cliente.setUsuario((Usuario) uDAO.consultar(cliente.getUsuario()).getEntidade());
				stmt.setInt(1, cliente.getUsuario().getId());
			}
			ResultSet rs = stmt.executeQuery();
			List<EntidadeDominio> listCliente = new ArrayList<>();
			while(rs.next()) {
				Cliente c = new Cliente();
				c.setCep(rs.getString("CLI_CEP"));
				c.setCpf(rs.getString("CLI_CPF"));
				c.setEmail(rs.getString("CLI_EMAIL"));
				c.setId(rs.getInt("CLI_ID"));
				c.setNome(rs.getString("CLI_NOME"));
				listCliente.add(c);
			}
			if(possuiUsuario) {
				((Cliente) listCliente.get(0)).setUsuario(cliente.getUsuario());
			}
			r.setListEntidade(listCliente);
		} catch(Exception e) {
			r.setErro("Não foi possivel realizar o cadastro.");
			e.printStackTrace();
		}
		return r;
		
	}

	@Override
	public Resultado excluir(EntidadeDominio entidade) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Resultado salvar(EntidadeDominio entidade) {
		
		Resultado resultado = new Resultado();
		Cliente cliente = (Cliente) entidade;
		
		IDAO dao = new UsuarioDAO();
		Usuario usuario = cliente.getUsuario();
		dao.salvar(usuario);
		
		String sql = "INSERT INTO CLIENTES (CLI_NOME, CLI_EMAIL, CLI_CPF, CLI_CEP, CLI_USER_ID) VALUES (?, ?, ?, ?, ?)";
		try(Connection connection = new ConnectionFactory().getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			stmt.setString(1, cliente.getNome());
			stmt.setString(2, cliente.getEmail());
			stmt.setString(3, cliente.getCpf());
			stmt.setString(4, cliente.getCep());
			stmt.setInt(5, cliente.getUsuario().getId());
			stmt.execute();
			
			resultado.setSucesso("Cadastro realizado com sucesso.");
			resultado.setEntidade(cliente);
			
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
