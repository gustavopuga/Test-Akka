package br.com.insite.test_akka.actors;

import br.com.insite.test_akka.messages.ResultMessage;
import br.com.insite.test_akka.messages.WorkMessage;
import akka.actor.UntypedActor;

public class Worker extends UntypedActor {

	//Manipula a mensagem
	@Override
	public void onReceive(Object message) throws Exception {
		
		if (message instanceof WorkMessage) {

			WorkMessage workMessage = (WorkMessage) message;
			
			double result = calculatePiFor(workMessage.getStartNumber(), workMessage.getNumberOfElements());

			getContext().replyUnsafe(new ResultMessage(result));
		} 
		
		else{
			throw new IllegalArgumentException("Mensagem desconhecida [" + message + "]");
		}
	}

	//Define a tarefa
	private double calculatePiFor(int startNumber, int numberOfElements) {
		
		double pi = 0.0;
		
		for (int i = startNumber * numberOfElements; i <= ((startNumber + 1) * numberOfElements - 1); i++) {
			pi += 4.0 * (1 - (i % 2) * 2) / (2 * i + 1);
		}
		
		return pi;
	}

}
