package br.com.insite.test_akka.controller;

import static akka.actor.Actors.actorOf;

import java.util.concurrent.CountDownLatch;

import akka.actor.ActorRef;
import br.com.insite.test_akka.actors.factories.BossFactory;
import br.com.insite.test_akka.messages.InitMessage;

public class TestAkkaControler {

	public void calculatePi (final int nrOfWorkers, final int nrOfElements, final int nrOfMessages) throws Exception {
		
		// Marca quando o codigo foi concluido
		final CountDownLatch latch = new CountDownLatch(1);
		
		ActorRef boss = actorOf(new BossFactory(nrOfWorkers, nrOfMessages, nrOfElements, latch)).start();
		boss.sendOneWay(new InitMessage());
		
		// espera o boss parar
		latch.await();
	}
}
