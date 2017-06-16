package me.messageofdeath.lib;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;

public class Utilities {

    private static final String alpha = "^[a-zA-Z]*$";
	private static final String alphaNumeric = "^[a-zA-Z0-9]*$";
	private static final String intRegex = "[+-]?\\d+";
	private static final String floatRegex = "[+-]?([0-9]+([.][0-9]*)?|[.][0-9]+)";
	private static final String doubleRegex = ("[\\x00-\\x20]*"+"[+-]?("+"NaN|"+"Infinity|"+"((("+"(\\p{Digit}+)"+"(\\.)?("+"(\\p{Digit}+)"+"?)("+"[eE][+-]?(\\p{Digit}+)"+
			")?)|"+"(\\.("+"(\\p{Digit}+)"+")("+"[eE][+-]?(\\p{Digit}+)"+")?)|"+"(("+"(0[xX]"+"(\\p{XDigit}+)"+"(\\.)?)|"+"(0[xX]"+"(\\p{XDigit}+)"+"?(\\.)"+
			"(\\p{XDigit}+)"+")"+")[pP][+-]?"+"(\\p{Digit}+)"+"))"+"[fFdD]?))"+"[\\x00-\\x20]*");
	
	public static boolean isAlpha(String total) {
		return total.matches(alpha);
	}
	
	public static boolean isAlphanumeric(String total) {
		return total.matches(alphaNumeric);
	}

	public static boolean isByte(String total) {
		if(isInteger(total)) {
			int i = Integer.parseInt(total);
			return i >= -128 && i <= 127;
		}
		return false;
	}

	public static boolean isShort(String total) {
		if(isInteger(total)) {
			int i = Integer.parseInt(total);
			return i >= -32_768 && i <= 32_767;
		}
		return false;
	}
	
	public static boolean isInteger(String total) {
		return total.matches(intRegex);
	}
	
	public static boolean isFloat(String total) {
		return total.matches(floatRegex);
	}
	
	public static boolean isDouble(String total) {
		return total.matches(doubleRegex);
	}
	
	public static boolean isNumeric(String total) {
		return isInteger(total) || isFloat(total) || isDouble(total);
	}

	public static String getColorized(String input) {
		return ChatColor.translateAlternateColorCodes('&', input);
	}

	public static String getCompletionBar(double percent) {
		StringBuilder bui = new StringBuilder();
		for(int i = 1; i < 11; i++) {
			bui.append(percent >= i * 10 ? "&a&l" : "&c&l").append("\\u2588");
		}
		return getColorized(bui.toString());
	}

	public static String getFormattedPercentage(double percentage) {
		return String.format("%.2f", percentage);
	}
	
	public static float normalizeDegree(float newDegree) {
		while(newDegree > 359) newDegree -= 360; 
		while(newDegree < 0) newDegree += 360;
		return newDegree; 
	}

	public static String getOrdinal(int n) {
		char[] args = (n + "").toCharArray();
		char last = args[args.length - 1];
		if(last == '1') {
			return n + "st";
		}else if(last == '2') {
			return n + "nd";
		}else if(last == '3') {
			return n + "rd";
		}else{
			return n + "th";
		}
	}
	
	//In seconds
	public static String getTimeBySeconds(int time) {
		return Utilities.getTimeByMiliseconds(time * 1000);
	}
	
	//In miliseconds
	public static String getTimeByMiliseconds(long time) {
		HashMap<String, String> map = new HashMap<>();
		int decades = (int) (time >= 314_496_000_000L ? time / 314_496_000_000L : 0);
		time -= decades*314_496_000_000L;
		map.put("decade", String.valueOf(decades));
		byte years = (byte) (time >= 31_449_600_000L ? time / 31_449_600_000L : 0);
		time -= years*31_449_600_000L;
		map.put("year", String.valueOf(years));
		byte weeks = (byte) (time >= 604_800_000L ? time / 604_800_000L : 0);
		time -= weeks*604_800_000L;
		map.put("week", String.valueOf(weeks));
		byte days = (byte) (time >= 86_400_000L ? time / 86_400_000L : 0);
		time -= days*86_400_000L;
		map.put("day", String.valueOf(days));
		byte hours = (byte) (time >= 3_600_000L ? time / 3_600_000L : 0);
		time -= hours*3_600_000L;
		map.put("hour", String.valueOf(hours));
		byte minutes = (byte) (time >= 60_000L ? time / 60_000L : 0);
		time -= minutes*60_000L;
		map.put("minute", String.valueOf(minutes));
		byte seconds = (byte) (time >= 1_000L ? time / 1_000L : 0);
		time -= seconds*1_000L;
		map.put("second", String.valueOf(seconds));
		short miliseconds = (short)time;
		map.put("milisecond", String.valueOf(miliseconds));
		StringBuilder bui = new StringBuilder();
		map.keySet().forEach(str -> {
			String key = map.get(str);
			if(key.charAt(0) != '0') {
				bui.append(key);
				if(key.charAt(0) == '1' && key.length() == 1) {
					bui.append(str).append(" ");
				}else{
					bui.append(str).append("s").append(" ");
				}
			}
		});
		/*return (decades != 0 ? decades + (decades == 1 ? " decade" : " decades") : "") + (years != 0 ? years + (years == 1 ? " year" : " years") : "") +
				(weeks != 0 ? weeks + (weeks == 1 ? " week" : " weeks") : "") + (days != 0 ? days + (days == 1 ? " day" : " days") : "") +
				(hours != 0 ? hours + (hours == 1 ? " hour" : " hours") : "") + (minutes != 0 ? minutes + (minutes == 1 ? " minute" : " minutes") : "") +
				(seconds != 0 ? seconds + (seconds == 1 ? " second" : " seconds") :
						(miliseconds != 0 ? miliseconds + (miliseconds == 1 ? " milisecond" : " miliseconds") : ""));*/
		return bui.toString().trim();
	}
	
	public static boolean isInsideTri(Location loc1, Location loc2, Location loc3, Location loc) {
		return Utilities.isInsideTri(loc1.getX(), loc1.getZ(), loc2.getX(), loc2.getZ(), loc3.getX(), loc3.getZ(), loc.getX(), loc.getZ());
	}
	
	public static boolean isInsideTri(double x1, double y1, double x2, double y2, double x3, double y3, double x, double y) {
		double totalTesting = Utilities.areaTri(x, y, x2, y2, x3, y3) + Utilities.areaTri(x1, y1, x, y, x3, y3) + Utilities.areaTri(x1, y1, x2, y2, x, y);
		double total = Utilities.areaTri(x1, y1, x2, y2, x3, y3);
		System.out.println(total + " : " + totalTesting);
		return total == totalTesting;
	}
	
	public static double areaTri(double x1, double y1, double x2, double y2, double x3, double y3) {
		return Math.abs((x1*(y2-y3) + x2*(y3-y1)+ x3*(y1-y2))/2.0);
	}
	
	public static ArrayList<IDHolder> sortIDs(ArrayList<IDHolder> holders) {
		if (!holders.isEmpty()) {
			holders.sort(Utilities.compareIDs());
			int lastPosition = 0;
			int difference;
			IDHolder holder;
			for (int i = 0; i < holders.size(); i++) {
				holder = holders.get(i);
				if (lastPosition != holder.getID()) {
					difference = holder.getID() - lastPosition;
					if (difference > 1) {
						for (int x = i; x < holders.size(); x++) {
							holders.get(x).setID(holders.get(x).getID() - difference);
						}
					}
					difference = holder.getID() - lastPosition;
					if (difference == 0) {
						for (int x = i; x < holders.size(); x++) {
							holders.get(x).setID(holders.get(x).getID() + 1);
						}
					}
					lastPosition = holder.getID();
				}else if(lastPosition == holder.getID()){
					holder.setID(holder.getID() + 1);
					lastPosition = holder.getID();
				}
			}
			holders.sort(Utilities.compareIDs());
		}
		return holders;
	}
	
	public static ArrayList<IDHolder> setID(int oldID, int newID, ArrayList<IDHolder> holders) {
		boolean hasNew = false;
		boolean hasOld = false;
		for(IDHolder holder : holders) {
			if(holder.getID() == newID && hasOld && !hasNew) {
				holder.setID(holder.getID() - 1);
				hasNew = true;
				continue;
			}
			if(holder.getID() == newID) {
				holder.setID(holder.getID() + 1);
				hasNew = true;
				continue;
			}
			if(holder.getID() == oldID) {
				holder.setID(newID);
				hasOld = true;
				continue;
			}
			if(hasNew && hasOld) {
				continue;
			}
			if(hasNew) {
				holder.setID(holder.getID() + 1);
			}
			if(hasOld) {
				holder.setID(holder.getID() - 1);
			}
		}
		return Utilities.sortIDs(holders);
	}

	public static int getNextID(ArrayList<IDHolder> holders) {
		ArrayList<Integer> ids = new ArrayList<>();
		for (IDHolder holder : holders) {
			ids.add(holder.getID());
		}
		for(int i = 1; i < ids.size() + 1; i++) {
			if (!ids.contains(i)) {
				return i;
			}
		}
		return 1_000;
	}
	
	private static Comparator<IDHolder> compareIDs() {
		return (holder1, holder2) -> {
            if (holder1.getID() > holder2.getID()) {
                return 1;
            }
            if (holder1.getID() < holder2.getID()) {
                return -1;
            }
            return 0;
        };
	}
}