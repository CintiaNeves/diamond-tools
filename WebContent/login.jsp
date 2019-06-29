<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<title>Login</title>
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
           	<li><a href="index.jsp">Home</a></li>
        	<li><a href="catalogo.jsp">Produtos</a></li>
            <li><a href="cadastro-cli.jsp">Criar Conta</a></li>
        </ul>
    </nav>
	<div class="main-login">
		<div id="login-box">
			<form action="login" method="POST">
				<div class="box-label">Login to Diamond Tolls</div>
				<div class="input-div">
					<input value="${usuario.email}"type="text" placeholder="E-mail" name="email" >
				</div>
				<div class="input-div">
					<input type="password" placeholder="Senha" name="senha">
				</div>
				<div class="buttons">
					<button type="submit" id="button" name="operacao" value="CONSULTAR">Login</button> 
				</div>
			</form>
		</div>
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
	</div>
	<footer class="footer">
		&copy; Cíntia Neves 2019
	</footer>
</body>
</html>