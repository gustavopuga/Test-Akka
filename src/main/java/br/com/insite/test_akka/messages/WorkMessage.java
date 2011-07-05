package br.com.insite.test_akka.messages;

public class WorkMessage {

	private final int startNumber;
	private final int numberOfElements;

	public WorkMessage(int start, int nrOfElements) {
		this.startNumber = start;
		this.numberOfElements = nrOfElements;
	}

	public int getStartNumber() {
		return startNumber;
	}

	public int getNumberOfElements() {
		return numberOfElements;
	}

	@Override
	public String toString() {
		
		return "Mensagem que atribui trabalho";
	}
}
