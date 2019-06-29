package negocio;

import dominio.Cliente;
import dominio.EntidadeDominio;

public class StValidaCep implements IStrategy{

	@Override
	public String processar(EntidadeDominio entidade) {
		
		Cliente cliente = (Cliente) entidade;
		
		if(cliente.getCep().length() != 8) {
			return "* CEP Inv�lido!";
		}else {
			return null;
		}	
	}

}
