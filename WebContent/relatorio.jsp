<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<title>Relatórios</title>
	<meta charset="utf-8">
	<link rel="icon" href="img/favicon.png">
	<link rel="stylesheet" href="css/reset.css">
	<link rel="stylesheet" href="css/login.css">
	<link href="https://fonts.googleapis.com/icon?family=Material+Icons"rel="stylesheet">
	<script src="./js/catalogo.js"></script>
</head>
<body>
	<nav id="menu">
		<ul>
			<li><a href="cadastro-produto.jsp">Novo Produto</a></li>
			<li><a href="consulta-produto.jsp">Consulta Produto</a></li>
			<li><a href="index.jsp">Site</a></li>
		</ul>
    </nav>
 	<div class="main-carrinho">
    		<div class="carrinho-box">
    			<div class="box-label">Relatórios</div>
    			<div class="div-prod-descricao">
    				<form action="relatorio" method="POST">
		    			<div>
		    				<label id="lbl-relato">Produto mais vendido de:</label>
		    				<input class="input-data" type="date" name="data-inicio">
		    				<label id="lbl-relato">a</label>
		    				<input class="input-data" type="date" name="data-fim">
		    				<button type="submit" class="button-relatorio-1" name="operacao" value="CONSULTAR">Gerar Relatório</button>
		    			</div>
		    			<div class="div-prod-descricao">
		    				<div>
		    					<label id="lbl-desc">Produto</label>
			    				<label id="lbl-valor-uni">Valor Unitário R$</label>
			    				<label id= "lbl-qtd">Quantidade Vendida</label>
			    				<label id= "lbl-valor-total">Total em R$ </label>
		    				</div>
		    					<div>
		    						<input disabled value="${relatorio.descProduto}" class="input-prod-desc" type="text" name="nome" >
		    						<input disabled value="${relatorio.valorUnitario}" class="input-relato-1" name="valor-unitario">
		    						<input disabled value="${relatorio.quantidade}" class="input-relato-2" name="quantidade">
									<input disabled value="${relatorio.totalVendido}" class="input-relato-3" type="number" name="valor-total">
		    					</div>
							</div>
						</form>
					</div>
				<div class="div-prod-descricao">
					<form action="relatorio" method="POST">
						<div>
		    				<label id="lbl-relato">Faturamento de:</label>
		    				<input class="input-data" type="date" name="data-inicio">
		    				<label id="lbl-relato">até</label>
		    				<input class="input-data" type="date" name="data-fim">
		    				<button type="submit" class="button-relatorio-2" name="operacao" value="CONSULTARBYCOD">Gerar Relatório</button>
		    			</div>
		    			<div class="div-prod-descricao">
		    				<div>
			    				<label id="lbl-valor-bruto">Faturamento Bruto R$</label>
			    				<label id= "lbl-custo">Custo R$</label>
			    				<label id= "lbl-lucro-liquido">Lucro Líquido R$ </label>
		    				</div>
		    					<div>
		    						<input disabled id="input-valor-bruto" value="${relatorio.faturamentoBruto}" name="valor-bruto">
		    						<input disabled id= "input-custo" value="${relatorio.custo}" name="custo">
		    						<input disabled id= "input-lucro-liquido" value="${relatorio.lucroLiquido}" name="lucro-liquido">
		    					</div>
							</div>
						</form>
					</div>
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

	<footer class="footer">
		&copy; Cíntia Neves 2019
	</footer>
</body>
</html>