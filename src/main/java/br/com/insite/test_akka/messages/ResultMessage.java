package br.com.insite.test_akka.messages;

public class ResultMessage {
	
	private final double value;

	public ResultMessage(double value) {
		this.value = value;
	}

	public double getValue() {
		return value;
	}
	
	@Override
	public String toString() {
		
		return String.format("Mensagem que finaliza o calculo: resultado = %d", getValue());
	}
}
