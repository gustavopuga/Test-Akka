package br.com.insite.test_akka.actors;

import static akka.actor.Actors.actorOf;
import static akka.actor.Actors.poisonPill;

import java.util.concurrent.CountDownLatch;

import org.apache.log4j.Logger;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.routing.Routing.Broadcast;
import br.com.insite.test_akka.messages.InitMessage;
import br.com.insite.test_akka.messages.ResultMessage;
import br.com.insite.test_akka.messages.WorkMessage;

public class Master extends UntypedActor {

	private static final Logger log = Logger.getLogger(Master.class);

	private final int numberOfMessages;
	private final int numberOfElements;
	private final CountDownLatch latch;

	private double pi;
	private int numberOfResults;
	private long startTime;

	private ActorRef router;

	public Master(int numberOfWorkers, int numberOfMessages,
			int numberOfElements, CountDownLatch latch) {

		this.numberOfMessages = numberOfMessages;
		this.numberOfElements = numberOfElements;
		this.latch = latch;

		final ActorRef[] workers = createWorkers(numberOfWorkers);

		router = actorOf(new WorkerFactory(workers)).start();

	}

	private ActorRef[] createWorkers(int numberOfWorkers) {
		final ActorRef[] workers = new ActorRef[numberOfWorkers];
		for (int i = 0; i < numberOfWorkers; i++) {
			workers[i] = actorOf(Worker.class).start();
		}
		return workers;
	}

	@Override
	public void onReceive(Object message) throws Exception {

		if (message instanceof InitMessage) {
			
			for (int startNumber = 0; startNumber < numberOfMessages; startNumber++) {
				router.sendOneWay(new WorkMessage(startNumber, numberOfElements), getContext());
			}
			
			router.sendOneWay(new Broadcast(poisonPill()));
			router.sendOneWay(poisonPill());
		}

		else if (message instanceof ResultMessage) {
			
			ResultMessage result = (ResultMessage) message;
			pi += result.getValue();
			numberOfResults += 1;

			if (numberOfResults == numberOfMessages)
				getContext().stop();
		}

		else {
			throw new IllegalArgumentException("Unknown message [" + message + "]");
		}

	}

	@Override
	public void preStart() {
		startTime = System.currentTimeMillis();
	}

	@Override
	public void postStop() {

		log.info(String.format(
				"\n\tEstimativa PI: \t\t%s\n\tCalculation time: \t%s millis",
				pi, (System.currentTimeMillis() - startTime)));
		latch.countDown();
	}

}
