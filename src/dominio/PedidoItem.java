package dominio;

import java.util.List;

public class PedidoItem extends EntidadeDominio{

	private List<Produto> produtos;
	private int idPedido;
	
	public List<Produto> getProdutos() {
		return produtos;
	}
	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}
	public int getIdPedido() {
		return idPedido;
	}
	public void setIdPedido(int idPedido) {
		this.idPedido = idPedido;
	}
}
