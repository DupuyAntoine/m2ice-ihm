package drawing;

import java.awt.geom.Point2D.Double;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;

import fr.dgac.ivy.Ivy;
import fr.dgac.ivy.IvyApplicationListener;
import fr.dgac.ivy.IvyClient;
import fr.dgac.ivy.IvyException;
import fr.dgac.ivy.IvyMessageListener;

public class Recognizer {

	Ivy bus;
	int state;
	Stroke stroke;
	HashMap<Stroke, String> strokes;
	int distance;
	String result;

	@SuppressWarnings("unchecked")
	public Recognizer() throws IOException, ClassNotFoundException {
		this.state = 0;
		distance = 0;
		FileInputStream file = new FileInputStream ("C:\\Users\\AdminEtu\\Desktop\\Antoine\\M2ICE\\IHM\\Projet\\forms.ser"); 
		ObjectInputStream in = new ObjectInputStream (file); 
		
		// Method for deserialization of object
		try {
			strokes = (HashMap<Stroke, String>) in.readObject(); 
		} catch(ClassCastException e) {
			e.printStackTrace();
		}

		in.close();
		file.close();
		System.out.println("Object has been deserialized\n"
		       + "Data after Deserialization.");
		Ivy bus = new Ivy("GestureRecognizer", "...Initialisation GestureRecognizer...", new IvyApplicationListener() {
	
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
			
			bus.bindMsg("Palette:MouseReleased x=(.*) y=(.*)", new IvyMessageListener() {

				@Override
				public void receive(IvyClient client, String[] args) {
					state = 0;
					stroke.normalize();
					double min = 999999999; // Max value for double
					double d = 0;
					double x = 0;
					double y = 0;
					String type = "";
					for (HashMap.Entry<Stroke, String> m : strokes.entrySet()) {
						d = 0;
						ArrayList<Double> s =  ((Stroke) m.getKey()).getPoints();
						ArrayList<Double> s2 =  stroke.getPoints();
						for (int cpt = 0; cpt < stroke.getPoints().size(); cpt++) {

							x = Math.abs(s.get(cpt).getX() - s2.get(cpt).getX());
							y = Math.abs(s.get(cpt).getY() - s2.get(cpt).getY());
							d += x + y;

						}

						if (d < min) {
							type = (String) m.getValue();
							min = d;
						}
			        }
					
					System.out.println(min);
					
					
					if (min < 1000) {
						try {
							if (type.equals("rectangle")) {
								System.out.println("rectangle");
								bus.sendMsg("Recognizer:Rectangle");
								
							} else if (type.equals("ellipse")) {
								System.out.println("ellipse");
								bus.sendMsg("Recognizer:Ellipse");
								
							}else if (type.equals("supprimer")) {
								System.out.println("supprimer");
								bus.sendMsg("Recognizer:Supprimer");
							}
						} catch (IvyException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}				
			});
			
		} catch (IvyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws ClassNotFoundException, IOException {
		new Recognizer();
	}

}
