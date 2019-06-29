package viewHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import dominio.EntidadeDominio;
import dominio.Produto;
import util.Resultado;

public class VHProduto implements IViewHelper {

	@Override
	public EntidadeDominio getEntidade(HttpServletRequest request) {

		Produto produto = new Produto();
		if (request.getParameter("operacao") == null) {

			if (ServletFileUpload.isMultipartContent(request)) {

				try {

					Map<String, List<FileItem>> multiparts = new ServletFileUpload(new DiskFileItemFactory())
							.parseParameterMap(request);

					for (List<FileItem> itens : multiparts.values()) {
						for (FileItem item : itens) {
							if (item.isFormField()) {
								if (item.getFieldName().equals("cod")) {
									try {
										produto.setCodigo(Integer.parseInt(item.getString()));
									} catch (NumberFormatException e) {
										e.printStackTrace();
									}
								} else if (item.getFieldName().equals("unidade-medida")) {
									produto.setUnidadeMedida(item.getString().toUpperCase());
								} else if (item.getFieldName().equals("descricao")) {
									produto.setDescricao(item.getString().toUpperCase());
								} else if (item.getFieldName().equals("preco-compra")) {
									try {
										String precoCompra = item.getString().replace(",", ".");
										produto.setPrecoCompra(Double.parseDouble(precoCompra));
									} catch (NumberFormatException e) {
										e.printStackTrace();
									}
								} else if (item.getFieldName().equals("preco-venda")) {
									try {
										String precoVenda = item.getString().replace(',', '.');
										produto.setPrecoVenda(Double.parseDouble(precoVenda));
									} catch (NumberFormatException e) {
										e.printStackTrace();
									}

								} else if (item.getFieldName().equals("cod-barras")) {
									produto.setCodBarras(item.getString());
								}
							} else {
								try {
									produto.setFoto("img" + File.separator + item.getName());
									String path = request.getServletContext().getRealPath("img") + File.separator
											+ item.getName();
									item.write(new File(path));
								} catch (FileNotFoundException e) {
									e.printStackTrace();
								}
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else if (request.getParameter("operacao").equals("CONSULTAR")) {

		}
		return produto;
	}

	@Override
	public void setView(Resultado resultado, HttpServletRequest request, HttpServletResponse response,
			String operacao) {
		
		Produto produto;
		String mensagem[] = resultado.getMensagem().split("\n");
		String op = operacao;
		try {
			if (op.equals("SALVAR")) {

				produto = (Produto) resultado.getEntidade();
				
				if (resultado.getErro()) {
					request.setAttribute("erro", mensagem);
					request.setAttribute("produto", produto);
				} else {
					request.setAttribute("sucesso", mensagem);
				}
				RequestDispatcher rd = request.getRequestDispatcher("cadastro-produto.jsp");
				rd.forward(request, response);

			} else if (op.equals("CONSULTAR")) {

				String json = "[";
				for (EntidadeDominio ed : resultado.getListEntidade()) {
					Produto p = (Produto) ed;
					json = json.concat("{\"id\" : \"").concat(String.valueOf(p.getId())).concat("\",");
					Method methods[] = p.getClass().getDeclaredMethods();
					Field fields[] = p.getClass().getDeclaredFields();
					// [{"codigo": "1490015", "unidadeMedida": "UN", "descricao": "KIT ALICATE
					// RJ45", "

					for (Field field : fields) {
						for (Method method : methods) {
							if (method.getName().startsWith("get")
									&& method.getName().toUpperCase().contains(field.getName().toUpperCase())) {
								json = json.concat("\"").concat(field.getName()).concat("\": \"")
										.concat(String.valueOf(method.invoke(p))).concat("\", ");
							}
						}
					}
					json = json.substring(0, json.length() - 2);
					json = json.concat("}, ");
				}
				json = json.substring(0, json.length() - 2);
				json = json.concat("]");
				response.setContentType("application/json");
				response.setCharacterEncoding("utf-8");
				response.getWriter().write(json);

			} else if (op.equals("CONSULTARBYCOD")) {
				
				if (resultado.getListEntidade().size() != 0) {
					
					produto = (Produto) resultado.getEntidade();
					request.setAttribute("produto", produto);
					RequestDispatcher rd = request.getRequestDispatcher("consulta-produto.jsp");
					rd.forward(request, response);
					
				}else {
					request.setAttribute("sucesso", mensagem);
					RequestDispatcher rd = request.getRequestDispatcher("consulta-produto.jsp");
					rd.forward(request, response);
				}
				
			}else if (op.equals("ALTERAR")) {
					produto = (Produto) resultado.getEntidade();
					
					if (resultado.getErro()) {
						request.setAttribute("erro", mensagem);
						request.setAttribute("produto", produto);
					} else {
						request.setAttribute("sucesso", mensagem);
					}
					RequestDispatcher rd = request.getRequestDispatcher("consulta-produto.jsp");
					rd.forward(request, response);
				}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	@Override
	public EntidadeDominio getEntidade(Map<String, List<FileItem>> multiparts, HttpServletRequest request) {

		Produto produto = new Produto();
		String nomeImagem = "";
		FileItem fileItem = null;
		try {
			for (List<FileItem> itens : multiparts.values()) {
				for (FileItem item : itens) {
					if (item.isFormField()) {
						if (item.getFieldName().equals("cod")) {
							try {
								produto.setCodigo(Integer.parseInt(item.getString()));
							} catch (NumberFormatException e) {
								e.printStackTrace();
							}
						} else if (item.getFieldName().equals("unidade-medida")) {
							produto.setUnidadeMedida(item.getString().toUpperCase());
						} else if (item.getFieldName().equals("descricao")) {
							produto.setDescricao(item.getString().toUpperCase());
						} else if (item.getFieldName().equals("preco-compra")) {
							try {
								String precoCompra = item.getString().replace(",", ".");
								produto.setPrecoCompra(Double.parseDouble(precoCompra));
							} catch (NumberFormatException e) {
								e.printStackTrace();
							}
						} else if (item.getFieldName().equals("preco-venda")) {
							try {
								String precoVenda = item.getString().replace(',', '.');
								produto.setPrecoVenda(Double.parseDouble(precoVenda));
							} catch (NumberFormatException e) {
								e.printStackTrace();
							}

						} else if (item.getFieldName().equals("cod-barras")) {
							produto.setCodBarras(item.getString());
						}else if(item.getFieldName().equals("nome-imagem")) {
							nomeImagem = item.getString();
						}
					} else {
						fileItem = item;
					}
				}
			}
			try {
				if(fileItem.getName().equals("")) {
					nomeImagem = nomeImagem.replace("img\\\\", "");
					produto.setFoto("img" + File.separator + nomeImagem);
					String path = request.getServletContext().getRealPath("img") + File.separator + nomeImagem;
					fileItem.write(new File(path));
				}else {
					produto.setFoto("img" + File.separator + fileItem.getName());
					String path = request.getServletContext().getRealPath("img") + File.separator
							+ fileItem.getName();
					fileItem.write(new File(path));
					System.out.println(path);
				}
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return produto;
	}

}
