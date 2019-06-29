package facade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.CarrinhoDAO;
import dao.ClienteDAO;
import dao.IDAO;
import dao.ItemCarrinhoDAO;
import dao.PedidoDAO;
import dao.PedidoItemDAO;
import dao.ProdutoDAO;
import dao.RelatorioDAO;
import dao.UsuarioDAO;
import dominio.Carrinho;
import dominio.Cliente;
import dominio.EntidadeDominio;
import dominio.ItemCarrinho;
import dominio.Pedido;
import dominio.PedidoItem;
import dominio.Produto;
import dominio.Relatorio;
import dominio.Usuario;
import negocio.IStrategy;
import negocio.StMontaCarrinho;
import negocio.StValidaCpf;
import negocio.StValidaDadosCliente;
import negocio.StValidaDadosProduto;
import negocio.StValidaDadosUsuario;
import negocio.StValidaLoginExistente;
import negocio.StValidaSenha;
import util.Resultado;

public class Fachada implements IFachada{

	Map<String, IDAO> mapDAO;
	Map<String, List<IStrategy>> mapProdutoStrategy;
	Map<String, List<IStrategy>> mapClienteStrategy;
	Map<String, List<IStrategy>> mapUsuarioStrategy;
	Map<String, List<IStrategy>> mapCarrinhoStrategy;
	Map<String, List<IStrategy>> mapPedidoStrategy;
	Map<String, List<IStrategy>> mapPedidoItemStrategy;
	Map<String, List<IStrategy>> mapRelatorioStrategy;
	Map<String, Map<String, List<IStrategy>>> mapEntidadeCRUDStrategy;
	
	public Fachada() {
		// inicializa mapas
		mapDAO = new HashMap<String, IDAO>();
		mapProdutoStrategy = new HashMap<String, List<IStrategy>>();
		mapClienteStrategy = new HashMap<String, List<IStrategy>>();
		mapUsuarioStrategy = new HashMap<String, List<IStrategy>>();
		mapCarrinhoStrategy = new HashMap<String, List<IStrategy>>();
		mapPedidoStrategy = new HashMap<String, List<IStrategy>>();
		mapPedidoItemStrategy = new HashMap<String, List<IStrategy>>();
		mapRelatorioStrategy = new HashMap<String, List<IStrategy>>();
		mapEntidadeCRUDStrategy = new HashMap<String, Map<String, List<IStrategy>>>();
		
		// lista produto salvar
		List<IStrategy> listStrategySalvarProduto = new ArrayList<>();
		listStrategySalvarProduto.add(new StValidaDadosProduto());
		
		// lista produto consultar
		//List<IStrategy> listStrategyConsultarProduto = new ArrayList<>();

		// listas cliente salvar
		List<IStrategy> listStrategySalvarCliente = new ArrayList<>();
		listStrategySalvarCliente.add(new StValidaDadosCliente());
		listStrategySalvarCliente.add(new StValidaCpf());
		listStrategySalvarCliente.add(new StValidaSenha());
		
		// listas cliente consultar
		//List<IStrategy> listStrategyConsultarCliente = new ArrayList<>();
		
		// listas usuario salvar
		//List<IStrategy> listStrategySalvarUsuario = new ArrayList<>();
		
		// listas usuario consultar
		List<IStrategy> listStrategyConsultarUsuario = new ArrayList<>();
		listStrategyConsultarUsuario.add(new StValidaDadosUsuario());
		
		// lista usuario consultar#existencia
		List<IStrategy> listStrategyConsultarValidarUsuario = new ArrayList<>();
		listStrategyConsultarValidarUsuario.add(new StValidaLoginExistente());
		
		// lista carrinho alterar
		List<IStrategy> listStrategyAlterarCarrinho = new ArrayList<>();
		listStrategyAlterarCarrinho.add(new StMontaCarrinho());
		
		// mapa produto
		mapProdutoStrategy.put("SALVAR", listStrategySalvarProduto);
		
		
		// mapa cliente
		mapClienteStrategy.put("SALVAR", listStrategySalvarCliente);
		
		// mapa usuario
		mapUsuarioStrategy.put("CONSULTAR", listStrategyConsultarUsuario);
		mapUsuarioStrategy.put("CONSULTAR#EXISTENCIA", listStrategyConsultarValidarUsuario);
		
		// mapa carrinho 
		mapCarrinhoStrategy.put("SALVAR", listStrategyAlterarCarrinho);
		
		// mapa entidades
		mapEntidadeCRUDStrategy.put(Produto.class.getSimpleName(), mapProdutoStrategy);
		mapEntidadeCRUDStrategy.put(Cliente.class.getSimpleName(), mapClienteStrategy);
		mapEntidadeCRUDStrategy.put(Usuario.class.getSimpleName(), mapUsuarioStrategy);
		mapEntidadeCRUDStrategy.put(Carrinho.class.getSimpleName(), mapCarrinhoStrategy);
		mapEntidadeCRUDStrategy.put(Pedido.class.getSimpleName(), mapPedidoStrategy);
		mapEntidadeCRUDStrategy.put(PedidoItem.class.getSimpleName(), mapPedidoItemStrategy);
		
		mapDAO.put(Produto.class.getSimpleName(), new ProdutoDAO());
		mapDAO.put(Cliente.class.getSimpleName(), new ClienteDAO());
		mapDAO.put(Usuario.class.getSimpleName(), new UsuarioDAO());
		mapDAO.put(Carrinho.class.getSimpleName(), new CarrinhoDAO());
		mapDAO.put(Pedido.class.getSimpleName(), new PedidoDAO());
		mapDAO.put(ItemCarrinho.class.getSimpleName(), new ItemCarrinhoDAO());
		mapDAO.put(PedidoItem.class.getSimpleName(), new PedidoItemDAO());
		mapDAO.put(Relatorio.class.getSimpleName(), new RelatorioDAO());
	}
	
	public Resultado validaStrategy(EntidadeDominio entidade, String operacao) {
		
		Resultado resultado = new Resultado();
		String mensagem = "";
		String mensagens = "";
		
		Map<String, List<IStrategy>> map = mapEntidadeCRUDStrategy.get(entidade.getClass().getSimpleName());
		if(map != null) {			
			List<IStrategy> list = map.get(operacao);
			
			if(list != null) {
				for(IStrategy iStrategy : list) {
					mensagem = iStrategy.processar(entidade);
					if(mensagem != null) {
						mensagens += mensagem;
					}
				}
				if(mensagens.length() > 0) {
					resultado.setErro(mensagens);
				}else {
					resultado.setSucesso("");
				}
				resultado.setEntidade(entidade);
			}
		}
		
		return resultado;
	}
	
	@Override
	public Resultado alterar(EntidadeDominio entidade) {
		
		Resultado resultado = new Resultado();
		resultado = validaStrategy(entidade, "SALVAR");
		if(!resultado.getErro()) {
			IDAO dao = mapDAO.get(entidade.getClass().getSimpleName());
			return dao.alterar(entidade);
		}
		return resultado;
	}

	@Override
	public Resultado consultar(EntidadeDominio entidade) {
		
		Resultado resultado = new Resultado();
		resultado = validaStrategy(entidade, "CONSULTAR");
		
		if(!resultado.getErro()) {
			IDAO dao = mapDAO.get(entidade.getClass().getSimpleName());
			resultado = dao.consultar(entidade);
			Resultado r = validaStrategy(resultado.getEntidade(), "CONSULTAR#EXISTENCIA");
			if(r.getErro()) {
				r.clear();
				r.setEntidade(entidade);
				resultado = r;
			}
		}
		return resultado;
	}

	@Override
	public Resultado excluir(EntidadeDominio entidade) {
		
		IDAO dao = mapDAO.get(entidade.getClass().getSimpleName());
		return dao.excluir(entidade);
	}

	@Override
	public Resultado salvar(EntidadeDominio entidade) {
		
		Resultado resultado = new Resultado();
		resultado = validaStrategy(entidade, "SALVAR");
		if(!resultado.getErro()) {
			IDAO dao = mapDAO.get(entidade.getClass().getSimpleName());
			return dao.salvar(entidade);
		}
		
		return resultado;
	}

	@Override
	public Resultado consultarByCod(EntidadeDominio entidade) {
		
		IDAO dao = mapDAO.get(entidade.getClass().getSimpleName());
		return dao.consultarByCod(entidade);
	}

}
