<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>Produtos</title>
<meta charset="utf-8">
<link rel="icon" href="img/favicon.png">
<link rel="stylesheet" href="css/reset.css">
<link rel="stylesheet" href="css/login.css">
<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
<script src="./js/catalogo.js"></script>
</head>
<body onload="setStorage(${usuario.id}), loadProdutos(), retornaIdCarrinho()">

	<nav id="menu">
		<ul>
			<li><a data-x="" href="index.jsp">Home</a></li>
			<li><a href="cadastro-cli.jsp">Criar Conta</a></li>
			<li><a href="login.jsp">Login</a></li>
			<li> 
				<div class="carrinho">
					<form action="carrinho" method="POST">
						<input type="hidden" name="idCarrinho" id="inputIdCarrinho" />
						<button type="submit" name="operacao" value="CONSULTAR" class="material-icons btn-carrinho">
							shopping_cart
						</button>
					</form>
				</div>
			</li>
		</ul>
	</nav>
	<div id="produtos"></div>
	<footer class="footer-js">
        &copy; Cíntia Neves 2019
    </footer>
</body>
</html>