function hide(element) {
	
	element.style.display = 'none'


}

function show(className) {

let element = document.querySelector('.'+className)
	element.style.display = 'block'
}

function setStorage(idUsuario) {
	var clienteConfigurado = JSON.parse(localStorage.getItem("cliente"))
	if(!clienteConfigurado || clienteConfigurado[0].usuario.id !== idUsuario) {
		var xhttp = new XMLHttpRequest();
		xhttp.open("POST", "cadastro", true);
		xhttp.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
		xhttp.onreadystatechange = function(data){
			if(this.readyState == 4 && this.status == 200){
				var cliente = JSON.parse(data.currentTarget.response);
				console.log(cliente)
				localStorage.setItem("cliente", JSON.stringify(cliente))
				retornaIdCarrinho()
			}
		}
		xhttp.send("operacao=CONSULTAR&idUsuario="+idUsuario);		
	}
}

function retornaIdCarrinho() {
	var cliente = JSON.parse(localStorage.getItem("cliente"))
	if(cliente)
		document.getElementById("inputIdCarrinho").value = cliente[0].usuario.carrinho.id
		
}

function retornaDadosCliente(){
	var cliente = JSON.parse(localStorage.getItem("cliente"))
	if(cliente){
		console.log(cliente)
		document.getElementById("cliente").value = cliente[0].nome
		document.getElementById("cep").value = cliente[0].cep
		document.getElementById("id-cliente").value = cliente[0].id
		document.getElementById("id-usr").value = cliente[0].usuario.id
		document.getElementById("id-car").value = cliente[0].usuario.carrinho.id
		console.log(document.getElementById("cliente").value = cliente[0].nome)
		console.log(document.getElementById("cep").value = cliente[0].cep)
	}
	let listValorTotal = document.getElementsByName("valor-total")
	if(listValorTotal) {
		let total = 0
		for(let valor of listValorTotal) {
			console.log(valor.value)
			total += parseFloat(valor.value)
		}
		console.log(total)
		var formatter = new Intl.NumberFormat(
			'pt-BR', {
				style: 'currency', 
				currency: 'BRL', 
				minimumFractionDigits: 2, 
				minimumFractionDigits: 2
			}
		)
		document.getElementById("total-pedido").value = formatter.format(total)
	}
}


function clear() {
	localStorage.clear();
}

function loadProdutos(){
	
	var xhttp = new XMLHttpRequest();
	xhttp.open("POST", "produto", true);
	xhttp.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
	xhttp.send("operacao=CONSULTAR");
	xhttp.onreadystatechange = function(data){
		if(this.readyState == 4 && this.status == 200){
			var listaProdutos = JSON.parse(data.currentTarget.response);

			for(var index in listaProdutos) {
				var divNode = document.createElement("div");
				var imgNode = document.createElement("img");
				var h1Node = document.createElement("h1");
				var h2Node = document.createElement("h2");
				var buttonNode = document.createElement("button");
				var inputQtdNode = document.createElement("input");
				var labelValorNode = document.createElement("label");
				var formNode = document.createElement("form")
				
				h2Node.id = listaProdutos[index].id + "___valor";
				inputQtdNode.id = listaProdutos[index].id + "___inputQtd";
				labelValorNode.id = listaProdutos[index].id + "___label_valor";
				
				inputQtdNode.addEventListener("keyup", function calcula(event){
					
					var id = event.path[0].id.substring(0, 4);
					var valor = document.getElementById(id + "valor");
					var nValor = valor.textContent.trim().replace("R$", "");
					var total = document.getElementById(id + "label_valor");
					total.textContent = "R$ " + (event.path[0].value * nValor).toFixed(2);
				});

				h1Node.className = "h1-produto";
				h2Node.className = "h2-produto";
				divNode.className = "produto";
				imgNode.src = listaProdutos[index].foto;
				imgNode.alt = listaProdutos[index].descricao;
				h1Node.textContent = listaProdutos[index].descricao;
				h2Node.textContent = "R$ " + listaProdutos[index].precoVenda;
				buttonNode.className = "button";
				buttonNode.textContent = "Adicionar ao Carrinho";
				buttonNode.id = listaProdutos[index].id + "___idButton"
				buttonNode.name = "operacao";
				buttonNode.value = "CARRINHO";
				buttonNode.onclick = (event) => {
					event.preventDefault()
					var input = event.path[1].firstElementChild
					var idProduto = input.id.substring(0, 4).split("_")[0]
					var quantidade = input.value
					var cliente = JSON.parse(localStorage.getItem("cliente"))
					var parametros = "operacao=ALTERAR&idProduto=" + idProduto 
						+ "&idUsuario=" + cliente[0].usuario.id
						+ "&idCarrinho=" + cliente[0].usuario.carrinho.id
						+ "&quantidade=" + quantidade
					console.log(parametros)
					var xhttp = new XMLHttpRequest();
					xhttp.open("POST", "carrinho", true);
					xhttp.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
					xhttp.send(parametros);
					xhttp.onreadystatechange = function(data){
						event.path[1].children[0].value = ""
						event.path[1].children[1].textContent = "Total R$"
					}
					alert("Produto adicionado ao carrinho.")
				}
				inputQtdNode.textContent = "Quantidade";
				labelValorNode.textContent = "Total R$ ";
				inputQtdNode.className = "input-quantidade";
				labelValorNode.className = "label-total";
				inputQtdNode.placeholder = "Quant";
				
				divNode.appendChild(imgNode);
				divNode.appendChild(h1Node);
				divNode.appendChild(h2Node);
				formNode.appendChild(inputQtdNode);
				formNode.appendChild(labelValorNode);
				formNode.appendChild(buttonNode);
				divNode.appendChild(formNode);
				
				document.getElementById("produtos").appendChild(divNode);
			}
		}
	}
}



// function hide(className) {
// 	let element = document.querySelector('.'+className)
// 	element.addEventListener('click', () => {
// 			element.style.display = 'none'
// 	})
// }


//netstat -aon | find /i "listening"
