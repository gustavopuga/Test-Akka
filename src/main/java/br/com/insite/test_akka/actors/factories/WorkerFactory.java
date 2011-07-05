package br.com.insite.test_akka.actors.factories;

import static java.util.Arrays.asList;
import akka.actor.Actor;
import akka.actor.ActorRef;
import akka.actor.UntypedActorFactory;
import akka.routing.CyclicIterator;
import akka.routing.InfiniteIterator;
import akka.routing.UntypedLoadBalancer;

public class WorkerFactory implements UntypedActorFactory {

	private ActorRef[] workers;

	public WorkerFactory(ActorRef[] workers) {
		this.workers = workers;
	}

	public Actor create() {
		return new WorkBalancingRouter(workers);
	}

	static class WorkBalancingRouter extends UntypedLoadBalancer {

		private final InfiniteIterator<ActorRef> workers;

		public WorkBalancingRouter(ActorRef[] workers) {
			this.workers = new CyclicIterator<ActorRef>(asList(workers));
		}

		@Override
		public InfiniteIterator<ActorRef> seq() {
			return workers;
		}

	}
}
