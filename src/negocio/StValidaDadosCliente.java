package negocio;

import dominio.Cliente;
import dominio.EntidadeDominio;

public class StValidaDadosCliente implements IStrategy{

	@Override
	public String processar(EntidadeDominio entidade) {
		
		Cliente cliente = (Cliente) entidade;
		StringBuilder mensagem = new StringBuilder();
		
		if(cliente.getNome() == null || cliente.getNome().trim().equals("")) {
			mensagem.append("* Nome é um campo obrigatório!\n");		
		}
		if(cliente.getEmail() == null || cliente.getEmail().trim().equals("")) {
			mensagem.append("* E-mail é um campo obrigatório!\n");
		}
		if(cliente.getCpf() == null || cliente.getCpf().trim().equals("")) {
			mensagem.append("* Cpf é um campo obrigatório!\n");
		}
		if(cliente.getCep() == null || cliente.getCep().trim().equals("")) {
			mensagem.append("* CEP é um campo obrigatório!\n");
		}
		if(cliente.getUsuario().getSenha() == null || cliente.getUsuario().getSenha().trim().equals("")) {
			mensagem.append("* Senha é um campo obrigatório!\n");
		}if(cliente.getUsuario().getConfirmaSenha() == null|| cliente.getUsuario().getConfirmaSenha().trim().equals("")) {
			mensagem.append("* Confirma senha é uma campo obrigatório!\n");
		}
		
		
		return mensagem.toString();
	}

}
