/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gesturerecognizer;

import fr.dgac.ivy.*;
import gesturerecognizer.Stroke;
import java.awt.geom.Point2D;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author AdminEtu
 */
public class GestureRecognizer {

    Ivy bus;
    Stroke stroke;
    HashMapStroke commands;
    
    public GestureRecognizer() {
        stroke = new Stroke();
        stroke.init();
        commands = new HashMapStroke();
        bus = new Ivy("apprentissage", "Learner waiting...", new IvyApplicationListener() {
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
            bus.bindMsg("^Palette:MousePressed x=(.*) y=(.*)", new IvyMessageListener() {
                @Override
                public void receive(IvyClient arg0, String[] args) {
                    System.out.println("Mouse pressed at x = " + args[0] + " y = " + args[1]);
                    try {
                        stroke.addPoint(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
                        bus.sendMsg("Palette:CreerEllipse x=" + (Integer.parseInt(args[0]) - 2) + " y=" + (Integer.parseInt(args[1]) - 2) + " longueur=4 hauteur=4 couleurFond=green couleurContour=green");
                    } catch(IvyException e) {
                        e.printStackTrace();
                    }
                }
            });
            bus.bindMsg("^Palette:MouseDragged x=(.*) y=(.*)", new IvyMessageListener() {
                @Override
                public void receive(IvyClient arg0, String[] args) {
                    System.out.println("Mouse dragged at x = " + args[0] + " y = " + args[1]);
                    try {
                        stroke.addPoint(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
                        bus.sendMsg("Palette:CreerEllipse x=" + (Integer.parseInt(args[0]) - 2) + " y=" + (Integer.parseInt(args[1]) - 2) + " longueur=4 hauteur=4 couleurFond=gray couleurContour=gray");
                    } catch(IvyException e) {
                        e.printStackTrace();
                    }
                }
            });
            bus.bindMsg("^Palette:MouseReleased x=(.*) y=(.*)", new IvyMessageListener() {
                @Override
                public void receive(IvyClient arg0, String[] args) {
                    System.out.println("Mouse released at x = " + args[0] + " y = " + args[1]);
                    try {
                        stroke.addPoint(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
                        stroke.normalize();                       
                        
                        bus.sendMsg("Palette:CreerEllipse x=" + (Integer.parseInt(args[0]) - 2) + " y=" + (Integer.parseInt(args[1]) - 2) + " longueur=4 hauteur=4 couleurFond=red couleurContour=red");
                        for (int i = 0; i < stroke.size(); i++) {
                            Point2D.Double point = stroke.getPoint(i);
                            String color = "blue";
                            bus.sendMsg("Palette:CreerEllipse x=" + (int)point.getX() + " y=" + (int)point.getY() + " longueur=4 hauteur=4 couleurFond=" + color + " couleurContour=" + color);
                        }
                        
                        String command = Popup();
                        commands.addStroke(stroke, command);
                        commands.save();
                        stroke.init();
                    } catch(IvyException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch(IvyException e) {
            e.printStackTrace();
        }
    }
    
    String Popup()
    {
        final JFrame parent = new JFrame();
        String command = JOptionPane.showInputDialog(parent, "Command name ?", null); 
        return command;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        GestureRecognizer agent = new GestureRecognizer();
    }
    
}
