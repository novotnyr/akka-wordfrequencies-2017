package sk.upjs.ics.novotnyr.akka;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class Runner {
    public static void main(String[] args) throws Exception {
        int numberOfSentences = 5;

		ActorSystem system = ActorSystem.create();
		ActorRef master = system.actorOf(Props.create(MasterActor.class, numberOfSentences));
		
		master.tell("The quick brown fox tried to jump over the lazy dog and fell on the dog", ActorRef.noSender());
		master.tell("Dog is man's best friend", ActorRef.noSender());
		master.tell("Dog and Fox belong to the same family", ActorRef.noSender());
		master.tell("The dog was the first domesticated species", ActorRef.noSender());
		master.tell("The origin of the domestic dog is not clear.", ActorRef.noSender());


	}
}