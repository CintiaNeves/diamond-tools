package negocio;

import dominio.Cliente;
import dominio.EntidadeDominio;

public class StValidaDadosCliente implements IStrategy{

	@Override
	public String processar(EntidadeDominio entidade) {
		
		Cliente cliente = (Cliente) entidade;
		StringBuilder mensagem = new StringBuilder();
		
		if(cliente.getNome() == null || cliente.getNome().trim().equals("")) {
			mensagem.append("* Nome � um campo obrigat�rio!\n");		
		}
		if(cliente.getEmail() == null || cliente.getEmail().trim().equals("")) {
			mensagem.append("* E-mail � um campo obrigat�rio!\n");
		}
		if(cliente.getCpf() == null || cliente.getCpf().trim().equals("")) {
			mensagem.append("* Cpf � um campo obrigat�rio!\n");
		}
		if(cliente.getCep() == null || cliente.getCep().trim().equals("")) {
			mensagem.append("* CEP � um campo obrigat�rio!\n");
		}
		if(cliente.getUsuario().getSenha() == null || cliente.getUsuario().getSenha().trim().equals("")) {
			mensagem.append("* Senha � um campo obrigat�rio!\n");
		}if(cliente.getUsuario().getConfirmaSenha() == null|| cliente.getUsuario().getConfirmaSenha().trim().equals("")) {
			mensagem.append("* Confirma senha � uma campo obrigat�rio!\n");
		}
		
		
		return mensagem.toString();
	}

}
