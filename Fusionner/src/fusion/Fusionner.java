package fusion;

import fr.dgac.ivy.Ivy;
import fr.dgac.ivy.IvyApplicationListener;
import fr.dgac.ivy.IvyClient;
import fr.dgac.ivy.IvyException;
import fr.dgac.ivy.IvyMessageListener;


public class Fusionner {
	
	int state = 0;
	int timer = 3;
	String command = "";
	Boolean isComplete = false;
	
	public Fusionner() {
		Ivy bus = new Ivy("Fusionner", "...Initialisation Fusionner...", new IvyApplicationListener() {
			
			@Override
			public void connect(IvyClient client) {
				// TODO Auto-generated method stub
			}
	
			@Override
			public void disconnect(IvyClient client) {
				// TODO Auto-generated method stub
			}
	
			@Override
			public void die(IvyClient client, int id, String msgarg) {
				// TODO Auto-generated method stub
			}
	
			@Override
			public void directMessage(IvyClient client, int id, String msgarg) {
				// TODO Auto-generated method stub
			}
			
		});
	
		try {
			bus.start("localhost:2010");
			bus.bindMsg("VoiceRecognizer:(.*)", new IvyMessageListener() {
	
				@Override
				public void receive(IvyClient client, String[] args) {
				}
			});
			bus.bindMsg("GestureRecognizer:(.*)", new IvyMessageListener() {
				
				@Override
				public void receive(IvyClient client, String[] args) {
				}
			});
		} catch (IvyException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Fusionner();
	}

}
