package fusion;

import java.util.LinkedList;
import java.util.List;

import colortranslator.ColorTranslator;
import fr.dgac.ivy.Ivy;
import fr.dgac.ivy.IvyApplicationListener;
import fr.dgac.ivy.IvyClient;
import fr.dgac.ivy.IvyException;
import fr.dgac.ivy.IvyMessageListener;
import geometry.Ellipse;
import geometry.Geometry;
import geometry.Rectangle;


public class Fusionner {
	
	int state = 0;
	int timer = 3;
	Rectangle rectangle;
	Ellipse ellipse;
	List<Geometry> geometries = new LinkedList<>();
	Command command = new Command();
	
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
			bus.bindMsg("VoiceRecognizer:Color=(.*)", new IvyMessageListener() {
	
				@Override
				public void receive(IvyClient client, String[] args) {
					switch(state) {
						case 0:
							break;
						case 1:
							System.out.println("Je reçois la couleur " + args[0]);
							String color = ColorTranslator.translateColor(args[0]);
							System.out.println("Couleur: " + color);
							if (!(rectangle == null)) {
								rectangle.setColor(color);								
								state = 1;
								
							}
							if (!(ellipse == null)) {
								ellipse.setColor(color);								
								state = 1;
							}
							break;
						case 2:
							break;
						case 3:
							break;
						case 4:
							break;
						default: break;
					}
				}
			});
			
			bus.bindMsg("VoiceRecognizer:Position=(.*)", new IvyMessageListener() {

				@Override
				public void receive(IvyClient client, String[] args) {
					switch(state) {
						case 0:
							break;
						case 1:
							System.out.println("Position vocale");
							state = 2;
							break;
						case 2:
							break;
						case 3:
							break;
						case 4:
							break;
						default: break;
					}
				}

			});

			bus.bindMsg("GestureRecognizer:(.*)", new IvyMessageListener() {
				
				@Override
				public void receive(IvyClient client, String[] args) {
					switch (state) {
						case 0:
							System.out.println("Forme");
							if (args[0].equals("Rectangle")) {
								
								rectangle = new Rectangle(
										"R" + Integer.toString(geometries.size() + 1));
								state = 1;
								
								geometries.add(rectangle);
							}
							if (args[0].equals("Ellipse")) {
								ellipse = new Ellipse(
										"E" + Integer.toString(geometries.size() + 1));
								state = 1;
								System.out.println(ellipse);
								geometries.add(ellipse);
							}
							if (args[0].equals("Supprimer")) {
								try {
									for (Geometry g : geometries) {
										bus.sendMsg("Palette:SupprimerObjet nom=" + g.getName());
									}
								} catch (IvyException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							break;
						case 1:
						case 2:
						case 3:
						default: break;
					}
				}
				
			});
			
			bus.bindMsg("GestureRecognizer:Position x=(.*) y=(.*)", new IvyMessageListener() {

				@Override
				public void receive(IvyClient client, String[] args) {
					switch(state) {
						case 0:
							break;
						case 1:
							break;
						case 2:
							System.out.println("Position geste");
							if (!(rectangle == null)) {
								try {
									rectangle.setPosition(
											Integer.parseInt(args[0]),
											Integer.parseInt(args[1]));
									bus.sendMsg("Palette:CreerRectangle x="
											+ args[0] 
											+ " y=" 
											+ args[1] 
											+ " couleurFond=" + rectangle.getColor());
								} catch (IvyException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							} else if (!(ellipse == null)) {
								ellipse.setPosition(
										Integer.parseInt(args[0]),
										Integer.parseInt(args[1]));

								try {
									bus.sendMsg("Palette:CreerEllipse x="
											+ args[0] 
											+ " y=" 
											+ args[1] 
											+ " couleurFond=" + ellipse.getColor());
								} catch (IvyException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							rectangle = null;
							ellipse = null;
							state = 0;
							break;
						case 3:
							break;
						case 4:
							break;
						default: break;
					}
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
