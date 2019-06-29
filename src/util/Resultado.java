package util;

import java.util.ArrayList;
import java.util.List;

import dominio.EntidadeDominio;

public class Resultado{
	
	private List<EntidadeDominio> listEntidade;
	private boolean erro;
	private String mensagem;
	
	
	public void setErro(String mensagem) {
		erro = true;
		this.mensagem = mensagem;
	}

	public void setSucesso(String mensagem) {
		erro = false;
		this.mensagem = mensagem;
	}
	
	public boolean getErro() {
		return erro;
	}
	public String getMensagem() {
		return mensagem;
	}
	
	public List<EntidadeDominio> getListEntidade() {
		return listEntidade;
	}
	public void setListEntidade(List<EntidadeDominio> listEntidade) {
		this.listEntidade = listEntidade;
	}
	public EntidadeDominio getEntidade() {
		return listEntidade.get(0);
	}
	public void setEntidade(EntidadeDominio entidade) {
		if(listEntidade == null)
			listEntidade = new ArrayList<>();
		this.listEntidade.add(entidade);
	}
	public void clear() {
		if(listEntidade != null)
			listEntidade.clear();
	}
}
