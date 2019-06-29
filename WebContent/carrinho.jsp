<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<title>Meu carrinho</title>
	<meta charset="utf-8">
	<link rel="icon" href="img/favicon.png">
	<link rel="stylesheet" href="css/reset.css">
	<link rel="stylesheet" href="css/login.css">
	<link href="https://fonts.googleapis.com/icon?family=Material+Icons"rel="stylesheet">
	<script src="./js/catalogo.js"></script>
</head>
<body onload="retornaDadosCliente()">
	<nav id="menu">
        <ul>
            <li><a href="index.jsp">Home</a></li>
        	<li><a href="catalogo.jsp">Produtos</a></li>
            <li><a href="login.jsp">Sair</a></li>
        </ul>
    </nav>
    <div class="main-carrinho">
    		<div class="carrinho-box">
    			<div class="box-label">Meu Carrinho - Itens</div>
    			<div class="div-prod-descricao">
    				<label id="lbl-cliente" for="cliente">Nome</label>
    				<input  class="input-cliente" type="text" name="nome" id="cliente">
    				<label id="lbl-cep" for="cep">CEP</label>
    				<input class="input-cep" type="text" name="cep" id="cep">
    				<div>
    					<label id="lbl-desc">Descrição</label>
	    				<label id="lbl-valor-uni">Valor Unitário R$</label>
	    				<label id= "lbl-qtd">Quantidade</label>
	    				<label id= "lbl-valor-total">Total Item R$ </label>
    				</div>
    				<c:forEach var="ic" items="${carrinho.listItemCarrinho}">
    					<div>
    						<form action="carrinho" method="POST">
    							<input type="hidden" name="id-carrinho" value="${ic.carrinho.id}" />
    							<input type="hidden" name="id-item" value="${ic.id}" />   
    							<input type="hidden" name="id-produto" value="${ic.produto.id}" />							
	    						<input disabled value="${ic.produto.descricao}" class="input-prod-desc" type="text" placeholder="Produto" name="nome" id="descricao">
								<button type="submit" class="button-remover"  name="operacao" value="EXCLUIR">Remover</button>
								<input disabled value="${ic.quantidade * ic.produto.precoVenda}" class="input-prod-numeros" type="number" placeholder="Valor Total" name="valor-total">
								<input disabled value="${ic.quantidade}" class="input-prod-numeros" type="number" placeholder="Quantidade" name="quantidade">
								<input disabled value="${ic.produto.precoVenda}" class="input-prod-numeros" type="number" placeholder="Valor Unitário" name="valor-unitario">
    						</form>
    					</div>
    				</c:forEach>
				</div>
				<div>
					<label id="lbl-total" for="total-pedido">Total Pedido</label>
					<input disabled value="" class="input-valor-total" type="text" name="total-pedido" id="total-pedido">
				</div>
				<form action="pedido" method="POST">
					<div class="buttons">
						<input  type="hidden" name="id-cliente" id="id-cliente">
						<input  type="hidden" name="id-usr" id="id-usr">
						<input  type="hidden" name="id-car" id="id-car">
						<button type="submit" class="button-carrinho" name="operacao" value="SALVAR">Finalizar Compra</button> 
					</div>
				</form>
				<c:forEach items="${sucesso}" var="msg">
					<div onclick="hide(this)" class="mensagem-sucesso">
						<label>${msg}</label> 
						<i class="material-icons">
						 	done 
						</i>
					</div>
				</c:forEach>
    		</div>		
    </div>
    <footer>
        &copy; Cíntia Neves 2019
    </footer>
</body>
</html>