package ftt.ec;

public class Estado {
	private String valor;
	private Estado anterior;

	public Estado getAnterior() {
		return anterior;
	}

	public void setAnterior(Estado anterior) {
		this.anterior = anterior;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}
}
