package command;

import dominio.EntidadeDominio;
import util.Resultado;

public class CmdConsultar extends AbstractCommand{

	@Override
	public Resultado executar(EntidadeDominio entidade) {
		
		return fachada.consultar(entidade);
	}

}
