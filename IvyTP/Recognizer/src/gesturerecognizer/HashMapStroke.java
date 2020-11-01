/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gesturerecognizer;

import gesturerecognizer.Stroke;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.lang.Math;
/**
 *
 * @author AdminEtu
 */
public class HashMapStroke implements Serializable {

    private static final long serialVersionUID = 1L;
    private HashMap<Stroke, String> strokes;

    public HashMapStroke() {
        strokes = new HashMap<Stroke, String>();
    }
    
    public void addStroke(Stroke s, String command) {
        strokes.put(s, command);
    }
    
    public void setStrokes(HashMap<Stroke, String> map)
    {
        strokes = map;
    }
    public String compareToHashMap(Stroke stroke) {
        Iterator it = strokes.entrySet().iterator();
        String result = "";
        double maxDiff = 0;
        System.out.println(it);
        while (it.hasNext()) {
            System.out.println("Je suis dans le while");
            double diff = 0;
            Map.Entry pair = (Map.Entry)it.next();
            result = (String)pair.getValue();
            for (int i = 0; i < stroke.size(); i++) {
                Stroke entrystroke = (Stroke)pair.getKey();
                diff += Math.abs(entrystroke.getPoint(i).distance(stroke.getPoint(i)));
                if (maxDiff < diff) {
                    maxDiff = diff;
                    result = (String)pair.getValue();
                }
            }
            it.remove();
        }
        return result;
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
        HashMap map = null;
        HashMapStroke strokeMap = new HashMapStroke();
        try {
            FileInputStream fis = new FileInputStream("strokes.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            map = (HashMap)ois.readObject();
            strokeMap.setStrokes(map);
            ois.close();
            fis.close();
        } catch(IOException ioe) {
            ioe.printStackTrace();
        } catch(ClassNotFoundException c) {
            System.out.println("Class not found");
            c.printStackTrace();
        }
        System.out.println("Deserialized HashMapStrokes...");
        return strokeMap;
    }
}
