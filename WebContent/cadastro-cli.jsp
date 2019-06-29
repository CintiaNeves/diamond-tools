<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<title>Criar Conta</title>
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
            <li><a href="login.jsp">Login</a></li>
        </ul>
    </nav>
    <div class="main-cadastro">
    	<form action="cadastro" method="POST">
    		<div id="cadastro-cli-box">
    			<div class="box-label">Criar Conta</div>
    			<div class="input-div" id="input-usuario">
					<input value="${cliente.nome}" id="input-cad" type="text" placeholder="Nome" name="nome">
					<input value="${cliente.email}" id="input-cad" type="email" placeholder="E-mail" name="email">
					<input value="${cliente.cpf}"id="input-cad-small-left" type="text" placeholder="Cpf" name="cpf">
					<input value="${cliente.cep}"id="input-cad-small" type="text" placeholder="CEP" name="cep">
					<input value="${cliente.usuario.senha}"id="input-cad-small-left" type="password" placeholder="Senha" name="senha">
					<input value="${cliente.usuario.confirmaSenha}"id="input-cad-small" type="password" placeholder="Confirme sua senha" name="conf-senha">
				</div>
				<div id="buttons-cad-cli">
					<button type="submit" id="button" name="operacao" value="SALVAR">Criar Conta</button> 
					<div id="lembrar-senha"><input type="checkbox"/>Receber Ofertas por E-mail</div>
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