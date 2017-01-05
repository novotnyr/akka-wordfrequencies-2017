package sk.upjs.ics.novotnyr.akka;

import akka.actor.ActorRef;
import akka.actor.PoisonPill;
import akka.actor.Props;
import akka.actor.Terminated;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.routing.Broadcast;
import akka.routing.RoundRobinPool;

import java.util.HashMap;
import java.util.Map;

public class MasterActor extends UntypedActor {
    private LoggingAdapter logger = Logging.getLogger(getContext().system(), this);

	private ActorRef sentenceCounter = getContext()
			                            .actorOf(Props.create(SentenceCountActor.class)
                                                      .withRouter(new RoundRobinPool(3)));

    private Map<String, Integer> allFrequencies = new HashMap<>();

    @Override
    public void preStart() throws Exception {
        super.preStart();
        getContext().watch(sentenceCounter);
    }

    @Override
	public void onReceive(Object message) throws Exception {
	    if (message instanceof String) {
	        sentenceCounter.tell(message, getSelf());
        } else if (message instanceof Map) {
            @SuppressWarnings("unchecked")
	        Map<String, Integer> frequencies = (Map<String, Integer>) message;

            allFrequencies = MapUtils.aggregate(frequencies, allFrequencies);
        } else if (message instanceof EofMessage) {
	        sentenceCounter.tell(new Broadcast(PoisonPill.getInstance()), getSelf());
        } else if (message instanceof Terminated) {
            logger.info(this.allFrequencies.toString());
            getContext().system().terminate();
        } else {
	        unhandled(message);
        }
	}

}