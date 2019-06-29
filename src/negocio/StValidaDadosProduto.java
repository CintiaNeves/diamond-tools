package negocio;

import dominio.EntidadeDominio;
import dominio.Produto;

public class StValidaDadosProduto implements IStrategy{

	@Override
	public String processar(EntidadeDominio entidade) {
		
		Produto produto = (Produto) entidade;
		StringBuilder mensagem = new StringBuilder();
		
		if(produto.getFoto().trim().length() < 5) {
			mensagem.append("* Obrigatório selecionar uma imagem para o produto\n");
		}else {
			String path = produto.getFoto().replace(".jpg.jpg", ".jpg");
			path = path.replace("\\", "\\\\");
			produto.setFoto(path);
		}
			
		if(produto.getCodBarras() == null || produto.getCodBarras().trim().equals("")) {
			mensagem.append("* Código de barras é um campo obrigatório!\n");		
		}
		
		if(produto.getCodigo() == 0) {
			mensagem.append("* Código é um campo numérico e não pode ser vazio ou igual a zero!\n");
		}
		
		if(produto.getUnidadeMedida() == null || produto.getUnidadeMedida().trim().equals("")) {
			mensagem.append("* Unidade de medida é um campo obrigatório!\n");
		}
		
		if(produto.getDescricao() == null || produto.getDescricao().trim().equals("")) {
			mensagem.append("* Descrição é um campo obrigatório!\n");
		}
		
		if(produto.getPrecoCompra() == 0) {
			mensagem.append("* Preço de compra é um campo numérico e não pode ser vazio ou igual a zero!\n");
		}
		
		if(produto.getPrecoVenda() == 0) {
			mensagem.append("* Preço de venda é um campo numérico e não pode ser vazio ou igual a zero\n");
		}
		if((produto.getPrecoVenda() - produto.getPrecoCompra() <= 0)){
			mensagem.append("* Preço de venda deve ser maior que o preço de compra!\n");
		}
		
		return mensagem.toString();
		}
						
}
