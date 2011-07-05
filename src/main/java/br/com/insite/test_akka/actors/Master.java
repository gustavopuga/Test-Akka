package br.com.insite.test_akka.actors;

import static akka.actor.Actors.actorOf;
import akka.actor.ActorRef;
import akka.actor.UntypedActor;

public class Master extends UntypedActor {

	public Master(int numberOfWorkers, int nrOfMessages, int nrOfElements) {

		final ActorRef[] workers = createWorkers(numberOfWorkers);

		ActorRef router = actorOf(new WorkerFactory(workers)).start();

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

	}

	

}
