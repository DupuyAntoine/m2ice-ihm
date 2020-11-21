package fusion;

import colortranslator.ColorTranslator;
import fr.dgac.ivy.Ivy;
import fr.dgac.ivy.IvyApplicationListener;
import fr.dgac.ivy.IvyClient;
import fr.dgac.ivy.IvyException;
import fr.dgac.ivy.IvyMessageListener;
import geometry.Coords;
import geometry.Ellipse;
import geometry.Rectangle;


public class Fusionner {
	
	int state = 0;
	int timer = 3;
	Coords coordsColor = new Coords();
	String colorPos;
	Coords coordsDeplacement = new Coords();
	String objToMove;
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
					String color;
					switch(state) {
						case 0:
							break;
						case 1:
							System.out.println("Je reçois la couleur " + args[0]);
							color = ColorTranslator.translateColor(args[0]);
							System.out.println("Couleur: " + color);
							command.getGeometry().setColor(color);								
							break;
						case 2:
							break;
						case 3:
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
						case 6:
							System.out.println("Déplacement ici");
							state = 8;
						default: break;
					}
				}

			});
			bus.bindMsg("VoiceRecognizer:ThisColor=(.*)", new IvyMessageListener() {

				@Override
				public void receive(IvyClient client, String[] args) {
					switch(state) {
						case 0:
							break;
						case 1:
							break;
						case 2:
							break;
						case 3:
							try {
								System.out.println("Test point");
								bus.sendMsg("Palette:TesterPoint x=" 
										+ coordsColor.getX()
										+ " y="
										+ coordsColor.getY());
							} catch (IvyException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							break;
						default: break;
					}
				}

			});
			bus.bindMsg("VoiceRecognizer:ThisObject=(.*)", new IvyMessageListener() {

				@Override
				public void receive(IvyClient client, String[] args) {
					switch(state) {
						case 0:
							break;
						case 1:
							break;
						case 2:
							break;
						case 3:
							break;
						case 4:
							System.out.println("Suppression de cet objet");
							state = 5;
							break;
						case 6:
							System.out.println("Déplacement de cet objet");
							state = 7;
							break;
						default: break;
					}
				}

			});

			bus.bindMsg("GestureRecognizer:Forme=(.*)", new IvyMessageListener() {
				
				@Override
				public void receive(IvyClient client, String[] args) {
					switch (state) {
						case 0:
							System.out.println("Forme");
							if (args[0].equals("Rectangle")) {
								
								state = 1;
								Rectangle rect = new Rectangle("R");
								command.setGeometry(rect);
								command.setAction("CreerRectangle");
							}
							else if (args[0].equals("Ellipse")) {
								state = 1;
								Ellipse ellipse = new Ellipse("E");
								command.setGeometry(ellipse);
								command.setAction("CreerEllipse");
							}
							else if (args[0].equals("Supprimer")) {
								command.setAction("SupprimerObjet");
								state = 4;
							}
							else if (args[0].equals("Deplacer")) {
								command.setAction("DeplacerObjetAbsolu");
								state = 6;
							}
							break;
						case 1: break;
						case 2: break;
						case 3: break;
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
							System.out.println("Position couleur");
							coordsColor.setX(Integer.parseInt(args[0]));
							coordsColor.setY(Integer.parseInt(args[1]));
							state = 3;
							break;
						case 2:
							System.out.println("Position geste");
							command.getGeometry().setPosition(
									Integer.parseInt(args[0]),
									Integer.parseInt(args[1]));
							if (colorPos != null) {
								command.getGeometry().setColor(colorPos);
							}
							if (command.getGeometry().getColor() != null) {
								try {
									bus.sendMsg("Palette:" + command.getAction() + " x=" 
											+ command.getGeometry().getPosition().getX() 
											+ " y="
											+ command.getGeometry().getPosition().getY()
											+ " couleurFond=" + command.getGeometry().getColor());
									command.setGeometry(null);
									state = 0;
								} catch (IvyException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							} else {
								state = 1;
							}
							break;
						case 3:
							break;
						case 4:
							break;
						case 5:
							try {
								System.out.println("Test point");
								bus.sendMsg("Palette:TesterPoint x=" 
										+ Integer.parseInt(args[0])
										+ " y="
										+ Integer.parseInt(args[1]));
							} catch (IvyException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							break;
						case 6:
							break;
						case 7:
							try {
								System.out.println("Test point");
								bus.sendMsg("Palette:TesterPoint x=" 
										+ Integer.parseInt(args[0])
										+ " y="
										+ Integer.parseInt(args[1]));
							} catch (IvyException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							break;
						case 8:
							System.out.println("Position déplacement");
							coordsDeplacement.setX(Integer.parseInt(args[0]));
							coordsDeplacement.setY(Integer.parseInt(args[1]));
							if (objToMove != null) {
								try {
									bus.sendMsg("Palette:" 
											+ command.getAction() 
											+ " nom=" + objToMove
											+ " x=" + coordsDeplacement.getX()
											+ " y=" + coordsDeplacement.getY());
									objToMove = null;
									state = 0;
								} catch (IvyException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							} else {
								state = 6;
							}
							break;
						default: break;
					}
				}
			
			});
			bus.bindMsg("Palette:ResultatTesterPoint x=(.*) y=(.*) nom=(.*)", new IvyMessageListener() {

				@Override
				public void receive(IvyClient client, String[] args) {
					switch(state) {
						case 0: break;
						case 1: break;
						case 2: break;
						case 3:
							System.out.println("Résultat point");
							try {
								System.out.println("Demander info");
								bus.sendMsg("Palette:DemanderInfo nom=" + args[2]);
							} catch (IvyException e) {
								e.printStackTrace();
							}
							break;
						case 4:
							break;
						case 5:
							System.out.println("Résultat point");
							try {
								System.out.println("Demander info");
								bus.sendMsg("Palette:" + command.getAction() + " nom=" + args[2]);
								command.setGeometry(null);
								state = 0;
							} catch (IvyException e) {
								e.printStackTrace();
							}
							break;
						case 7:
							System.out.println("Résultat point");
							try {
								System.out.println("Demander info");
								if (coordsDeplacement.getX() != 0 && coordsDeplacement.getY() != 0) {
									bus.sendMsg("Palette:" 
											+ command.getAction() 
											+ " nom=" + args[2]
											+ " x=" + coordsDeplacement.getX()
											+ " y=" + coordsDeplacement.getY());
									objToMove = null;
									state = 0;
								} else {
									objToMove = args[2];
									state = 6;
								}
							} catch (IvyException e) {
								e.printStackTrace();
							}
							break;
						default: break;
					}					
				}
			
			});
			bus.bindMsg("Palette:Info nom=(.*) x=(.*) y=(.*) longueur=(.*) hauteur=(.*) couleurFond=(.*) couleurContour=(.*)", new IvyMessageListener() {

				@Override
				public void receive(IvyClient client, String[] args) {
					switch(state) {
						case 0: break;
						case 1: break;
						case 2: break;
						case 3:
							System.out.println("Infos");
							command.getGeometry().setColor(ColorTranslator.translateColor(args[5]));
							if (command.getGeometry().getPosition().getX() != 0 
									&& command.getGeometry().getPosition().getY() != 0) {
								try {
									bus.sendMsg("Palette:" + command.getAction() + " x=" 
											+ command.getGeometry().getPosition().getX() 
											+ " y="
											+ command.getGeometry().getPosition().getY()
											+ " couleurFond=" + command.getGeometry().getColor());
									command.setGeometry(null);
									state = 0;
								} catch (IvyException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							} else {
								colorPos = args[5];
								state = 1;
							}
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
