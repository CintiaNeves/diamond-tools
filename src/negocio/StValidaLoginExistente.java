package negocio;

import dominio.EntidadeDominio;
import dominio.Usuario;

public class StValidaLoginExistente implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		Usuario u = (Usuario) entidade;
		if(u == null || u.getId() == 0) {
			return "Usu�rio ou senha inv�lidos!";
		}
		return null;
	}
}
