package sk.upjs.ics.novotnyr.akka;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class Runner {
    public static void main(String[] args) throws Exception {
		ActorSystem system = ActorSystem.create();
		ActorRef master = system.actorOf(Props.create(MasterActor.class));
		
		master.tell("The quick brown fox tried to jump over the lazy dog and fell on the dog", ActorRef.noSender());
		master.tell("Dog is man's best friend", ActorRef.noSender());
		master.tell("Dog and Fox belong to the same family", ActorRef.noSender());


	}
}