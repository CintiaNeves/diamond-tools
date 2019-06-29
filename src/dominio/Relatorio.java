package dominio;

public class Relatorio extends EntidadeDominio{
	
	private String dataInicio;
	private String dataFim;
	private String descProduto;
	private double valorUnitario;
	private int quantidade;
	private double totalVendido;
	private double faturamentoBruto;
	private double custo;
	private double lucroLiquido;
	
	public String getDataInicio() {
		return dataInicio;
	}
	public void setDataInicio(String dataInicio) {
		this.dataInicio = dataInicio;
	}
	public String getDataFim() {
		return dataFim;
	}
	public void setDataFim(String dataFim) {
		this.dataFim = dataFim;
	}
	public String getDescProduto() {
		return descProduto;
	}
	public void setDescProduto(String descProduto) {
		this.descProduto = descProduto;
	}
	public double getValorUnitario() {
		return valorUnitario;
	}
	public void setValorUnitario(double valorUnitario) {
		this.valorUnitario = valorUnitario;
	}
	public int getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	public double getTotalVendido() {
		return totalVendido;
	}
	public void setTotalVendido(double totalVendido) {
		this.totalVendido = totalVendido;
	}
	public double getFaturamentoBruto() {
		return faturamentoBruto;
	}
	public void setFaturamentoBruto(double faturamentoBruto) {
		this.faturamentoBruto = faturamentoBruto;
	}
	public double getCusto() {
		return custo;
	}
	public void setCusto(double custo) {
		this.custo = custo;
	}
	public double getLucroLiquido() {
		return lucroLiquido;
	}
	public void setLucroLiquido(double lucroLiquido) {
		this.lucroLiquido = lucroLiquido;
	}
	
	
	
}
