package controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.http.fileupload.FileItem;

public class OperacaoController {

	public String getOperacao(HttpServletRequest request) {
		
		String operacao = request.getParameter("operacao");
		return operacao;
	}

	public String getOperacao(Map<String, List<FileItem>> multiparts) {
		String operacao;

		try {


			for (List<FileItem> itens : multiparts.values()) {
				for (FileItem item : itens) {
					if (item.isFormField()) {
						if (item.getFieldName().equals("operacao")) {
							operacao = item.getString();
							return operacao;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
