<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<title>Painel Administrador</title>
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
			<li><a href="relatorio.jsp">Produto mais Vendido</a></li>
			<li><a href="relatorio.jsp">Lucro</a></li>
			<li><a href="index.jsp">Site</a></li>
           </ul>
    </nav>
    <div>
		<c:forEach items="${erro}" var="msg">
			<div onclick="hide(this)" class="mensagem-erro">
				<label style="color: red;">${msg}</label>
				<i class="material-icons">
					clear
				</i>
			</div>
			<br />
		</c:forEach>
		<c:forEach items="${sucesso}" var="msg">
			<div onclick="hide(this)" class="mensagem-sucesso">
				<label style="color: blue;">${msg}</label> 
				<i class="material-icons">
				 	done 
				</i>
			</div>
		</c:forEach>
	</div>

	<footer class="footer">
		&copy; Cíntia Neves 2019
	</footer>
</body>
</html>