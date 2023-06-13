package dk.banannus.generators.utils;

public class isInt {
	public static boolean isInt(String s) {
		try {
			Double.parseDouble(s);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}
}
