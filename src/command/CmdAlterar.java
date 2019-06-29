package command;

import dominio.EntidadeDominio;
import util.Resultado;

public class CmdAlterar extends AbstractCommand{

	@Override
	public Resultado executar(EntidadeDominio entidade) {
		
		return fachada.alterar(entidade);
	}

}
