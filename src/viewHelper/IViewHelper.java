package viewHelper;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.FileItem;

import dominio.EntidadeDominio;
import util.Resultado;

public interface IViewHelper {

	public EntidadeDominio getEntidade(HttpServletRequest request);

	public EntidadeDominio getEntidade(Map<String, List<FileItem>> multiparts,HttpServletRequest request);

	public void setView(Resultado resultado, HttpServletRequest request, HttpServletResponse response, String operacao);
}
