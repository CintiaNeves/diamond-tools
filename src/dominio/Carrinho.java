package dominio;

import java.util.List;

public class Carrinho extends EntidadeDominio {

	private boolean expirado;
	private List<ItemCarrinho> listItemCarrinho;
	
	public boolean isExpirado() {
		return expirado;
	}

	public void setExpirado(boolean expirado) {
		this.expirado = expirado;
	}

	public List<ItemCarrinho> getListItemCarrinho() {
		return listItemCarrinho;
	}

	public void setListItemCarrinho(List<ItemCarrinho> listItemCarrinho) {
		this.listItemCarrinho = listItemCarrinho;
	}

}
