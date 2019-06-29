package negocio;

import dao.CarrinhoDAO;
import dao.IDAO;
import dao.ItemCarrinhoDAO;
import dominio.Carrinho;
import dominio.EntidadeDominio;
import dominio.ItemCarrinho;
import util.Resultado;

public class StMontaCarrinho implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {

		IDAO cDAO = new CarrinhoDAO();
		IDAO icDAO = new ItemCarrinhoDAO();
		
		Carrinho c = (Carrinho) entidade;
		
		Resultado resultadoCarrinho = cDAO.consultar(c);
		
		for(ItemCarrinho icNovo : c.getListItemCarrinho()) {
			boolean novoItem = true;
			for(ItemCarrinho ic: ((Carrinho) resultadoCarrinho.getEntidade()).getListItemCarrinho()) {
				if(ic.getProduto().getId() == icNovo.getProduto().getId()) {
					novoItem = false;
					ic.setQuantidade(ic.getQuantidade() + icNovo.getQuantidade());
					icDAO.alterar(ic);
				}				
			}
			if(novoItem) {
				// cria novo item no carrinho
				icNovo = (ItemCarrinho) icDAO.salvar(icNovo).getEntidade();
			}
		}
		return null;
	}

}
