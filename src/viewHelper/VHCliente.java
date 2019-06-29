package viewHelper;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.FileItem;

import dominio.Carrinho;
import dominio.Cliente;
import dominio.EntidadeDominio;
import dominio.Usuario;
import util.Resultado;

public class VHCliente implements IViewHelper {

	@Override
	public EntidadeDominio getEntidade(HttpServletRequest request) {

		String stNome = request.getParameter("nome");
		String stEmail = request.getParameter("email");
		String stCpf = request.getParameter("cpf");
		String stCep = request.getParameter("cep");
		IViewHelper vhUsuario = new VHUsuario();

		Usuario usuario = (Usuario) vhUsuario.getEntidade(request);

		Cliente cliente = new Cliente();

		if (stNome != null)
			cliente.setNome(stNome.toUpperCase());
		if (stEmail != null)
			cliente.setEmail(stEmail.toUpperCase());
		if (stCpf != null)
			cliente.setCpf(stCpf.toUpperCase());
		if (stCep != null)
			cliente.setCep(stCep.toUpperCase());
		if (usuario != null)
			cliente.setUsuario(usuario);
		return cliente;
	}

	@Override
	public void setView(Resultado resultado, HttpServletRequest request, HttpServletResponse response,
			String operacao) {
		String mensagem[] = null;
		if(resultado.getMensagem() != null)
			mensagem = resultado.getMensagem().split("\n");

		if (operacao.equals("CONSULTAR")) {
			if (resultado.getErro()) {
				request.setAttribute("erro", mensagem);
			} else {
				try {
					String json = "[";
					Cliente c = (Cliente) resultado.getEntidade();
					json = json.concat("{\"id\" : \"").concat(String.valueOf(c.getId())).concat("\",");
					Method methods[] = c.getClass().getDeclaredMethods();
					Field fields[] = c.getClass().getDeclaredFields();
					for (Field field : fields) {
						for (Method method : methods) {
							if (method.getName().startsWith("getUsuario")
									&& method.getName().toUpperCase().contains(field.getName().toUpperCase())) {
								Usuario u = c.getUsuario();
								if (u != null) {
									json = json.concat("\"usuario\": {\"id\" : \"").concat(String.valueOf(u.getId())).concat("\",");
									Method uMethods[] = u.getClass().getDeclaredMethods();
									Field uFields[] = u.getClass().getDeclaredFields();
									for (Field uField : uFields) {
										for (Method uMethod : uMethods) {
											if(uMethod.getName().startsWith("getCarrinho") 
													&& uMethod.getName().toUpperCase().contains(uField.getName().toUpperCase())) {
												Carrinho ca = u.getCarrinho();
												if(c != null) {
													json = json.concat("\"carrinho\": {\"id\" : \"").concat(String.valueOf(ca.getId())).concat("\",");
													Method caMethods[] = ca.getClass().getDeclaredMethods();
													Field caFields[] = ca.getClass().getDeclaredFields();
													for (Field caField : caFields) {
														for (Method caMethod : caMethods) {
															if (caMethod.getName().startsWith("get") && caMethod.getName().toUpperCase()
																	.contains(caField.getName().toUpperCase())) {
																json = json.concat("\"").concat(caField.getName()).concat("\": \"")
																		.concat(String.valueOf(caMethod.invoke(ca))).concat("\", ");																
															}
														}
													}
													json = json.substring(0, json.length() - 2);
													json = json.concat("}, ");
												}
											} else if (uMethod.getName().startsWith("get") 
													&& uMethod.getName().toUpperCase().contains(uField.getName().toUpperCase()) 
													&& !json.toUpperCase().contains(uField.getName().toUpperCase())) {
												json = json.concat("\"").concat(uField.getName()).concat("\": \"")
														.concat(String.valueOf(uMethod.invoke(u))).concat("\", ");
											}
										}
									}
									json = json.substring(0, json.length() - 2);
									json = json.concat("}, ");
								}
							} else if (method.getName().startsWith("get")
									&& method.getName().toUpperCase().contains(field.getName().toUpperCase())) {

								json = json.concat("\"").concat(field.getName()).concat("\": \"")
										.concat(String.valueOf(method.invoke(c))).concat("\", ");
							}
						}
					}
					json = json.substring(0, json.length() - 2);
					json = json.concat("}, ");
					json = json.substring(0, json.length() - 2);
					json = json.concat("]");
					response.setContentType("application/json");
					response.setCharacterEncoding("utf-8");
					response.getWriter().write(json);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else {
			if (resultado.getErro()) {
				request.setAttribute("erro", mensagem);
			} else if ((resultado.getMensagem().length() == 0)) {
				request.setAttribute("cliente", (Cliente) resultado.getListEntidade().get(0));
			} else {
				request.setAttribute("sucesso", mensagem);
			}
			if (resultado.getErro()) {
				request.setAttribute("cliente", (Cliente) resultado.getListEntidade().get(0));
			}

			try {
				RequestDispatcher rd = request.getRequestDispatcher("cadastro-cli.jsp");
				rd.forward(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public EntidadeDominio getEntidade(Map<String, List<FileItem>> multiparts, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

}
