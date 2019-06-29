package negocio;

import dominio.EntidadeDominio;
import dominio.Usuario;

public class StValidaDadosUsuario implements IStrategy{

	@Override
	public String processar(EntidadeDominio entidade) {
		
		Usuario usuario = (Usuario) entidade;
		StringBuilder mensagem = new StringBuilder();
		
		if(usuario.getEmail() == null) {
			mensagem.append("* E-mail é um campo obrigatório!\n");
		}
		else if(usuario.getEmail().trim().length() < 7) {
			mensagem.append("* Digite um e-mail válido no formato \"email@dominio.com\"\n");
		}
		if(usuario.getSenha() == null || usuario.getSenha().trim().length() < 1) {
			mensagem.append("* Senha é uma campo obrigatório!\n");
		}
		
		return mensagem.toString();
	}

}
