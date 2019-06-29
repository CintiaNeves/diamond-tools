package negocio;

import dominio.Cliente;
import dominio.EntidadeDominio;

public class StValidaSenha implements IStrategy{

	@Override
	public String processar(EntidadeDominio entidade) {
		
		Cliente cliente = (Cliente) entidade;
		
		if(!cliente.getUsuario().getSenha().equals(cliente.getUsuario().getConfirmaSenha())) {
			return "* Senhas não conferem!\n";
		}else if(cliente.getUsuario().getSenha().length() < 8) {
			return "* Senha deve conter pelo menos 8 dígitos!\n";
		}else {
			return null;
		}
		
	}

}
