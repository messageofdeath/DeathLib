package me.messageofdeath.lib.blocks;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class Cuboid {

	private String worldName;
	private Point point1;
	private Point point2;

	public Cuboid(Point a, Point b) {
		this.worldName = a.getWorldName();
		this.point1 = a;
		this.point2 = b;
	}

	public World getWorld() {
		return Bukkit.getWorld(worldName);
	}

	public Point getMinimumPoint() {
		return this.point1;
	}

	public Point getMaximumPoint() {
		return this.point2;
	}

	public boolean contains(Location loc) {
		return (loc.getBlockX() >= this.point1.getBlockX()) && (loc.getBlockX() <= this.point2.getBlockX())
				&& (loc.getBlockY() >= this.point1.getBlockY()) && (loc.getBlockY() <= this.point2.getBlockY())
				&& (loc.getBlockZ() >= this.point1.getBlockZ()) && (loc.getBlockZ() <= this.point2.getBlockZ());
	}

	@Override
	public String toString() {
		return getCuboidToString(this);
	}

	public static String getCuboidToString(Cuboid cuboid) {
		return cuboid.point1.toString() + ";" + cuboid.point2.toString();
	}

	public static Cuboid getStringToCuboid(String cube) {
		String[] split = cube.split(";");
		if (split.length == 2) {
			return new Cuboid(Point.stringToPoint(split[0]), Point.stringToPoint(split[1]));
		}
		return null;
	}
}
