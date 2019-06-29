package command;

import dominio.EntidadeDominio;
import util.Resultado;

public class CmdExcluir extends AbstractCommand{

	@Override
	public Resultado executar(EntidadeDominio entidade) {
	
		return fachada.excluir(entidade);
	}

}
