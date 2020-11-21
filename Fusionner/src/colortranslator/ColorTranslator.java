package colortranslator;

public class ColorTranslator {
	
	public static String translateColor(String color) {
		String tradColor;
		switch(color) {
			case "noir": tradColor = "black"; break;
			case "bleu": tradColor = "blue"; break;
			case "vert": tradColor = "green"; break;
			case "rouge": tradColor = "red"; break;
			default: tradColor = "black"; break;
		}
		return tradColor;
	}
}
