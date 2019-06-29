package dominio;

public class Usuario extends EntidadeDominio{
	
	private String email;
	private String senha;
	private String confirmaSenha;
	private int admin;
	private Carrinho carrinho;
	
	public Usuario() {
		admin = 0;
	}

	public String getEmail() {
		return email;
	}

	public Carrinho getCarrinho() {
		return carrinho;
	}

	public void setCarrinho(Carrinho carrinho) {
		this.carrinho = carrinho;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public int getAdmin() {
		return admin;
	}

	public void setAdmin(int admin) {
		this.admin = admin;
	}

	public String getConfirmaSenha() {
		return confirmaSenha;
	}

	public void setConfirmaSenha(String confirmaSenha) {
		this.confirmaSenha = confirmaSenha;
	}


	
	
	
}
