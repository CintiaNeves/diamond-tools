package negocio;

import dominio.EntidadeDominio;
import dominio.Produto;

public class StValidaDadosProduto implements IStrategy{

	@Override
	public String processar(EntidadeDominio entidade) {
		
		Produto produto = (Produto) entidade;
		StringBuilder mensagem = new StringBuilder();
		
		if(produto.getFoto().trim().length() < 5) {
			mensagem.append("* Obrigat�rio selecionar uma imagem para o produto\n");
		}else {
			String path = produto.getFoto().replace(".jpg.jpg", ".jpg");
			path = path.replace("\\", "\\\\");
			produto.setFoto(path);
		}
			
		if(produto.getCodBarras() == null || produto.getCodBarras().trim().equals("")) {
			mensagem.append("* C�digo de barras � um campo obrigat�rio!\n");		
		}
		
		if(produto.getCodigo() == 0) {
			mensagem.append("* C�digo � um campo num�rico e n�o pode ser vazio ou igual a zero!\n");
		}
		
		if(produto.getUnidadeMedida() == null || produto.getUnidadeMedida().trim().equals("")) {
			mensagem.append("* Unidade de medida � um campo obrigat�rio!\n");
		}
		
		if(produto.getDescricao() == null || produto.getDescricao().trim().equals("")) {
			mensagem.append("* Descri��o � um campo obrigat�rio!\n");
		}
		
		if(produto.getPrecoCompra() == 0) {
			mensagem.append("* Pre�o de compra � um campo num�rico e n�o pode ser vazio ou igual a zero!\n");
		}
		
		if(produto.getPrecoVenda() == 0) {
			mensagem.append("* Pre�o de venda � um campo num�rico e n�o pode ser vazio ou igual a zero\n");
		}
		if((produto.getPrecoVenda() - produto.getPrecoCompra() <= 0)){
			mensagem.append("* Pre�o de venda deve ser maior que o pre�o de compra!\n");
		}
		
		return mensagem.toString();
		}
						
}
