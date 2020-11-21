package voice;

import java.util.ArrayList;
import java.util.Arrays;

import fr.dgac.ivy.Ivy;
import fr.dgac.ivy.IvyApplicationListener;
import fr.dgac.ivy.IvyClient;
import fr.dgac.ivy.IvyException;
import fr.dgac.ivy.IvyMessageListener;


public class VoiceRecognizer {

	Ivy bus;
	ArrayList<String> colors = new ArrayList<String>(
			Arrays.asList("rouge", "bleu", "vert", "noir"));
	ArrayList<String> positions = new ArrayList<String>(
			Arrays.asList("ici", "la", "a cette position"));
	ArrayList<String> vocalClicColors = new ArrayList<String>(
			Arrays.asList("de cette couleur"));
	
	public VoiceRecognizer() {
		Ivy bus = new Ivy("VoiceRecognizer", "...Initialisation VoiceRecognizer...", new IvyApplicationListener() {
			
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
			bus.bindMsg("sra5 Text=(.*) Confidence=(.*)", new IvyMessageListener() {

				@Override
				public void receive(IvyClient client, String[] args) {
					try {
						String word = args[0];
						String readableFloat = args[1].replace(",", ".");
						Float confidence = Float.parseFloat(readableFloat);
						if (confidence > 0.5 && !word.contains("...")) {
							if (colors.contains(word)) {
								bus.sendMsg("VoiceRecognizer:Color=" + word);
							} else if (positions.contains(word)) {
								bus.sendMsg("VoiceRecognizer:Position=" + word);
							} else if (vocalClicColors.contains(word)) {
								bus.sendMsg("VoiceRecognizer:ThisColor=" + word);
							}
						}
					} catch (IvyException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});	
		} catch (IvyException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new VoiceRecognizer();
	}

}
