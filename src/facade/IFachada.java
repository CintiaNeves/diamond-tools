package facade;

import dominio.EntidadeDominio;
import util.Resultado;

public interface IFachada {

	public Resultado alterar (EntidadeDominio entidade);
	public Resultado consultar (EntidadeDominio entidade);
	public Resultado excluir (EntidadeDominio entidade);
	public Resultado salvar (EntidadeDominio entidade);
	public Resultado consultarByCod(EntidadeDominio entidade);
}
