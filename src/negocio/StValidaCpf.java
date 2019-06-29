package negocio;

import java.util.InputMismatchException;

import dominio.Cliente;
import dominio.EntidadeDominio;

public class StValidaCpf implements IStrategy{

	@Override
	public String processar(EntidadeDominio entidade) {
		Cliente cliente = (Cliente) entidade;
		String cpf = cliente.getCpf();

		if (cpf.length() == 0){
			return null;
		}
		
		if (cpf.length() != 11)
			return "CPF inválido\n";

		char dig10, dig11;
		int sm, i, r, num, peso;

		try {
			sm = 0;
			peso = 10;
			for (i = 0; i < 9; i++) {
				num = (int) (cpf.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso - 1;
			}

			r = 11 - (sm % 11);
			if ((r == 10) || (r == 11))
				dig10 = '0';
			else
				dig10 = (char) (r + 48); 

			sm = 0;
			peso = 11;
			for (i = 0; i < 10; i++) {
				num = (int) (cpf.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso - 1;
			}

			r = 11 - (sm % 11);
			if ((r == 10) || (r == 11))
				dig11 = '0';
			else
				dig11 = (char) (r + 48);

			if ((dig10 == cpf.charAt(9)) && (dig11 == cpf.charAt(10)))
				return null;
			else
				return ("CPF inválido\n");
		} catch (InputMismatchException erro) {
			return ("CPF inválido\n");
		}
	}

}
