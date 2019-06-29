package command;

import dominio.EntidadeDominio;
import util.Resultado;

public class CmdSalvar extends AbstractCommand{

	@Override
	public Resultado executar(EntidadeDominio entidade) {
		
		return fachada.salvar(entidade);
	}

}
