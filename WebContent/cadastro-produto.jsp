<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>Cadastro Produto</title>
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
			<li><a href="consulta-produto.jsp">Consulta Produto</a></li>
			<li><a href="relatorio.jsp">Relatórios</a></li>
			<li><a href="index.jsp">Site</a></li>
		</ul>
	</nav>
	<div class="main-cadastro">
	<form action="produto" method="POST" enctype="multipart/form-data">
		<div id="cadastro-cli-box">
			<div class="box-label">Novo Produto</div>
			<div class="input-div-prod" id="input-usuario">
				<div>
					<input id="input-cad-small-left" value="${produto.codBarras}" type="text" placeholder="Código de Barras" name="cod-barras"> 
				</div>
				<input value="${produto.unidadeMedida}" id="input-cad-small" type="text" placeholder="Unidade de Medida" name="unidade-medida"> 
				<input value="${produto.codigo}" id="input-cad-small-left" type="text" placeholder="Código" name="cod"> 
				<input value="${produto.descricao}" id="input-cad" type="text" placeholder="Descrição" name="descricao">
				<input value="${produto.precoCompra}" id="input-cad-small-left" placeholder="Preço de Compra" name="preco-compra"> 
				<input value="${produto.precoVenda}" id="input-cad-small" placeholder="Preço de Venda" name="preco-venda"> 
				<input value="${produto.foto}" id="input-cad-small" type="file" placeholder="Foto" name="img">
			</div>
			<div id="buttons-cad-cli">
				<button type="submit" id="button-c" name="operacao" value="SALVAR">Cadastrar</button>
			</div>
			<div>
				<c:forEach items="${erro}" var="msg">
					<div onclick="hide(this)" class="mensagem-erro">
						<label>${msg}</label>
						<i class="material-icons">
							clear
						</i>
					</div>
				</c:forEach>
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
	</form>
	</div>
	<footer class="footer-js">
        &copy; Cíntia Neves 2019
    </footer>
</body>
</html>