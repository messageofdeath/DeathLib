package me.messageofdeath.lib.blocks;

import me.messageofdeath.lib.Main;
import me.messageofdeath.lib.Utilities;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;

public class Point {

	private final String worldName;
	private final World world;
	private final double x;
	private final double y;
	private final double z;
	private final float yaw;
	private final float pitch;

	public Point(World world, double x, double y, double z) {
		this(world, x, y, z, 0, 0);
	}
	
	public Point(World world, double x, double y, double z, float yaw, float pitch) {
		this.worldName = world.getName();
		this.world = world;
		this.x = x;
		this.y = y;
		this.z = z;
		this.yaw = yaw;
		this.pitch = pitch;
	}
	
	public String getWorldName() {
		return this.worldName;
	}
	
	public World getWorld() {
		return this.world;
	}
	
	public double getX() {
		return this.x;
	}
	
	public double getY() {
		return this.y;
	}
	
	public double getZ() {
		return this.z;
	}
	
	public float getYaw() {
		return this.yaw;
	}
	
	public float getPitch() {
		return this.pitch;
	}
	
	public int getBlockX() {
		return (int) Math.floor(this.x);
	}
	
	public int getBlockY() {
		return (int) Math.floor(this.y);
	}
	
	public int getBlockZ() {
		return (int) Math.floor(this.z);
	}
	
	public double distance(Point point) {
		return this.toLocation().distance(point.toLocation());
	}
	
	public Point add(double x, double y, double z) {
		return new Point(this.world, this.x + x, this.y + y, this.z + z);
	}
	
	public Point sub(double x, double y, double z) {
		return new Point(this.world, this.x - x, this.y - y, this.z - z);
	}
	
	public boolean equals(Point point) {
		return point.getWorldName().equalsIgnoreCase(this.worldName) && point.getX() == this.x && point.getY() == this.y && point.getZ() == this.z;
	}
	public boolean equalsBlock(Point point) {
		return point.getWorld().getName().equalsIgnoreCase(this.world.getName()) && point.getBlockX() == this.getBlockX() && 
				point.getBlockY() == this.getBlockY() && point.getBlockZ() == this.getBlockZ();
	}
	
	public Location toLocation() {
		return new Location(this.world, this.x, this.y, this.z);
	}
	
	public Location toBlockLocation() {
		return new Location(this.world, this.getBlockX(), this.getBlockY(), this.getBlockZ());
	}
	
	public Location toSpawnLocation() {
		return new Location(this.world, this.x, this.y, this.z, this.yaw, this.pitch);
	}
	
	@Override
	public String toString() {
		return Point.pointToString(this);
	}
	
	//--------------- Location to Point ---------------
	
	public static Point locationToPoint(PointType type, Location loc) {
		if(type == PointType.POINT) {
			return new Point(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ());
		}else if(type == PointType.BLOCK) {
			return new Point(loc.getWorld(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
		}else if(type == PointType.EXACT) {
			return new Point(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
		}else{
			return null;
		}
	}
	
	public static ArrayList<Point> locationsToPoints(PointType type, ArrayList<Location> locations) {
		ArrayList<Point> points = new ArrayList<>();
		for(Location loc : locations) {
			if(type == PointType.POINT) {
				points.add(new Point(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ()));
			}else if(type == PointType.BLOCK) {
				points.add(	new Point(loc.getWorld(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()));
			}else if(type == PointType.EXACT) {
				points.add(new Point(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch()));
			}
		}
		return points;
	}
	
	//--------------- Point to Location ---------------
	
	public static Location pointToLocation(PointType type, Point point) {
		if(type == PointType.POINT) {
			return new Location(point.getWorld(), point.getX(), point.getY(), point.getZ());
		}else if(type == PointType.BLOCK) {
			return new Location(point.getWorld(), point.getBlockX(), point.getBlockY(), point.getBlockZ());
		}else if(type == PointType.EXACT) {
			return new Location(point.getWorld(), point.getX(), point.getY(), point.getZ(), point.getYaw(), point.getPitch());
		}else{
			return null;
		}
	}
	
	public static ArrayList<Location> pointsToLocations(PointType type, ArrayList<Point> points) {
		ArrayList<Location> locations = new ArrayList<>();
		for(Point point : points) {
			if(type == PointType.POINT) {
				locations.add(new Location(point.getWorld(), point.getX(), point.getY(), point.getZ()));
			}else if(type == PointType.BLOCK) {
				locations.add(new Location(point.getWorld(), point.getBlockX(), point.getBlockY(), point.getBlockZ()));
			}else if(type == PointType.EXACT) {
				locations.add(new Location(point.getWorld(), point.getX(), point.getY(), point.getZ(), point.getYaw(), point.getPitch()));
			}
		}
		return locations;
	}
	
	//--------------- Serialization ---------------
	
	public static String pointToString(Point point) {
		if(point == null) {
			return "";
		}
		return point.getWorldName() + "~" + point.getX() + "~" + point.getY() + "~" + point.getZ() + 
				(point.getYaw() != 0 || point.getPitch() != 0 ? "~" + point.getYaw() + "~" + point.getPitch() : "");
	}
	
	public static String pointsToString(ArrayList<Point> points) {
		if(points == null || points.isEmpty()) {
			return "";
		}
		StringBuilder total = new StringBuilder();
		for(Point point : points) {
			if(total.length() > 0) {
				total.append("%%");
			}
			total.append(Point.pointToString(point));
		}
		return total.toString();
	}
	
	public static Point stringToPoint(String total) {
		if(total != null && !total.isEmpty()) {
			String[] args = total.split("~");
			if(args.length == 4) {
				return new Point(getWorld(args[0]), getDouble(args[1]), getDouble(args[2]), getDouble(args[3]));
			}
			if(args.length == 6) {
				return new Point(getWorld(args[0]), getDouble(args[1]), getDouble(args[2]), getDouble(args[3]), getFloat(args[4]), getFloat(args[5]));
			}
			Main.logError("Point Serialization", "Point", "stringToPoint(String)", "Invalid string of text to convert to a Point");
		}
		return null;
	}
	
	public static ArrayList<Point> stringToPoints(String total) {
		if(total != null && !total.isEmpty()) {
			ArrayList<Point> points = new ArrayList<>();
			String[] pointTotal = total.split("%%");
			for (String aPointTotal : pointTotal) {
				points.add(Point.stringToPoint(aPointTotal));
			}
			return points;
		}
		return null;
	}
	
	public static boolean isStringToPoint(String total) {
		return total != null && (total.split("~").length == 4 || total.split("~").length == 6);
	}
	
	public static boolean isStringToPoints(String total) {
		if(total == null) {
			return false;
		}
		for(String args : total.split("%%")) {
			if(!Point.isStringToPoint(args)) {
				return false;
			}
		}
		return true;
	}
	
	private static World getWorld(String world) {
		if(Bukkit.getServer().getWorld(world) != null) {
			return Bukkit.getServer().getWorld(world);
		}else{
			Main.logError("Point Serialization", "Point", "getWorld(String)", "No available world by '"+world+"'. Getting default world instead.");
			return Bukkit.getServer().getWorlds().get(0);
		}
	}
	
	private static double getDouble(String total) {
		if(Utilities.isDouble(total)) {
			return Double.parseDouble(total);
		}else{
			Main.logError("Point Serialization", "Point", "getDouble(String)", "Input for a Point must be a double for x,y,z!");
			return 0;
		}
	}
	
	private static float getFloat(String total) {
		if(Utilities.isFloat(total)) {
			return Float.parseFloat(total);
		}else{
			Main.logError("Point Serialization", "Point", "getFloat(String)", "Input for a Point must be a float for yaw,pitch!");
			return 0;
		}
	}
}
