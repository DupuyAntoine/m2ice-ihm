/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ivyapp;
import fr.dgac.ivy.*;
/**
 *
 * @author AdminEtu
 */
public class IvyApp {


    Ivy bus;
    
    public IvyApp() {
        bus = new Ivy("agent", "Premier message", new IvyApplicationListener() {
            @Override
            public void disconnect(IvyClient arg0) {
                
            }
            
            @Override
            public void directMessage(IvyClient arg0, int arg1, String arg2) {
                
            }
            
            @Override
            public void die(IvyClient arg0, int arg1, String arg2) {
                
            }
            
            @Override
            public void connect(IvyClient arg0) {
                
            }
        });
        
        try {
            bus.start("127.255.255.255:2010");
            bus.bindMsg("^Palette:MouseClicked x=(.*) y=(.*)", new IvyMessageListener() {
                @Override
                public void receive(IvyClient arg0, String[] args) {
                    try {
                        System.out.println("Palette:CreerRectangle x=0 y=0 longueur=100 hauteur=50 couleurFond=red couleurContour=black");
                        bus.sendMsg("Palette:CreerRectangle x=" + args[0] + " y=" + args[1] + " longueur=100 hauteur=50 couleurFond=red couleurContour=black");
                    } catch(IvyException e) {
                        e.printStackTrace();
                    }
                }
            });
            bus.bindMsg("^Palette:MouseMoved x=(.*) y=(.*)", new IvyMessageListener() {
                @Override
                public void receive(IvyClient arg0, String[] args) {
                    System.out.println(
                        "Le pointeur de la souris se trouve à la position x=" + args[0] + "y=" + args[1]
                    );
                }
            });
            bus.bindMsg("^Palette:(.*)$", new IvyMessageListener() {
                @Override
                public void receive(IvyClient arg0, String[] args) {
                    System.out.println(
                        arg0.getApplicationName() + ":" +
                        ((args.length > 0) ? args[0] : "")
                    );
                }
            });
        } catch(IvyException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        IvyApp ivyApp = new IvyApp();
    }
    
}
