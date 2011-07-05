package br.com.insite.test_akka.actors.factories;

import java.util.concurrent.CountDownLatch;

import br.com.insite.test_akka.actors.Boss;
import akka.actor.Actor;
import akka.actor.UntypedActorFactory;

public class BossFactory implements UntypedActorFactory {

	private int numberOfWorkers;
	private int numberOfMessages;
	private int numberOfElements;
	private CountDownLatch latch;

	public BossFactory(int numberOfWorkers, int numberOfMessages, int numberOfElements, CountDownLatch latch) {

		this.numberOfWorkers = numberOfWorkers;
		this.numberOfMessages = numberOfMessages;
		this.numberOfElements = numberOfElements;
		this.latch = latch;
	}

	public Actor create() {
		return new Boss(numberOfWorkers, numberOfMessages, numberOfElements, latch);
	}

}
