package ivy;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import javax.swing.JOptionPane;

import fr.dgac.ivy.Ivy;
import fr.dgac.ivy.IvyApplicationListener;
import fr.dgac.ivy.IvyClient;
import fr.dgac.ivy.IvyException;
import fr.dgac.ivy.IvyMessageListener;

public class IvyApp {
	
	Ivy bus;
	int state;
	Stroke stroke;
	HashMap<Stroke, String> strokes;
	
	public IvyApp() {
		this.state = 0;
		strokes = new HashMap<Stroke, String>();
		Ivy bus = new Ivy("Test", "Premier message", new IvyApplicationListener() {
	
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
			
			bus.bindMsg("Palette:MouseDragged x=(.*) y=(.*)", new IvyMessageListener() {

				@Override
				public void receive(IvyClient client, String[] args) {
					if (state == 1 || state == 2) {
						state = 2;
						stroke.addPoint(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
					}
				}
			});
			
			bus.bindMsg("Palette:MousePressed x=(.*) y=(.*)", new IvyMessageListener() {

				@Override
				public void receive(IvyClient client, String[] args) {
					state = 1;
					stroke = new Stroke();
				}
			});
			
			bus.bindMsg("Palette:MouseClicked x=(.*) y=(.*)", new IvyMessageListener() {

				@Override
				public void receive(IvyClient client, String[] args) {
					System.out.println("ici");
				}
			});
			
			bus.bindMsg("Palette:MouseReleased x=(.*) y=(.*)", new IvyMessageListener() {

				@Override
				public void receive(IvyClient client, String[] args) {
					state = 0;
					stroke.normalize();
					String inputString = JOptionPane.showInputDialog(null, "Donnez un nom à la commande");
					if (inputString.equals("exit"))
					{
						save();
					}
					strokes.put(stroke, inputString);
				}
			});
			
		} catch (IvyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void save (){
		try {
	         FileOutputStream fileOut =
	         new FileOutputStream("database.ser");
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(strokes);
	         out.close();
	         fileOut.close();
	         System.out.printf("Serialized data is saved");
	        } catch (IOException i) {
	           i.printStackTrace();
	        }
		System.exit(0);
	}

	public static void main(String[] args) {
		new IvyApp();
	}

}
