/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gesturerecognizer;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;

/**
 *
 * @author AdminEtu
 */
public class HashMapStroke implements Serializable {

    private static final long serialVersionUID = 1L;
    final private HashMap<Stroke, String> strokes;

    public HashMapStroke() {
        strokes = new HashMap<Stroke, String>();
    }
    
    public void addStroke(Stroke s, String command) {
        strokes.put(s, command);
    }
    
    public void save() {
        try {
            FileOutputStream fos = new FileOutputStream("strokes.ser", true);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(strokes);
            oos.close();
            fos.close();
            System.out.printf("Serialized HashMapStrokes is saved in strokes.ser");
        }catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }
    
    public HashMapStroke load() {
        HashMapStroke map = null;
        try {
            FileInputStream fis = new FileInputStream("strokes.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            map = (gesturerecognizer.HashMapStroke)ois.readObject();
            ois.close();
            fis.close();
        } catch(IOException ioe) {
            ioe.printStackTrace();
        } catch(ClassNotFoundException c) {
            System.out.println("Class not found");
            c.printStackTrace();
        }
        System.out.println("Deserialized HashMapStrokes...");
        return map;
    }
}
