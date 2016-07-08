package de.aso.restinplanets.net;

import java.util.ArrayList;

public class Building {
	private static final float TONS = 1000f;
	private static final float MILLISECONDS_PER_SECOND = 1000f;
	private static final float TICK_TIME = 16f;

	private long planetID;
	private long buildingType;
	private int[] resourcesChange;
	private int[] costs;
	private String name;

	private Building(long planetID, long buildingType, int[] resourcesChange, String name) {
		this.planetID = planetID;
		this.buildingType = buildingType;
		this.resourcesChange = resourcesChange;
		this.name = name;
	}

	public Building(long buildingType, int[] resourcesChange, String name, int[] costs) {
		this(0, buildingType, resourcesChange, name);
		this.costs = costs;
	}

	public static ArrayList<Building> getFromDataPack(DataPack dataPack) {
		ArrayList<Building> buildings = new ArrayList<Building>();
		for (int i = 1, j = 0; i < dataPack.longs.length && j < dataPack.integers.length; i++, j += 4) {
			int[] resChange = new int[4];
			System.arraycopy(dataPack.integers, j, resChange, 0, 4);
			buildings.add(new Building(dataPack.longs[0], dataPack.longs[i], resChange, dataPack.strings[i - 1]));
		}
		return buildings;
	}

	public int[] getCosts() {
		return costs;
	}

	public int[] getResourcesChange() {
		return resourcesChange;
	}

	@Override
	public String toString() {
		return name
				+ " Titanium: " + getTitaniumTonsPerSecond() + " tons per second"
				+ " Aluminium: " + getAluminiumTonsPerSecond() + " tons per second"
				+ " Silicon: " + getSiliconTonsPerSecond() + " tons per second"
				+ " Asoium: " + getAsoiumTonsPerSecond() + " tons per second";
	}

	public float getTitaniumTonsPerSecond() {
		return (resourcesChange[0] * MILLISECONDS_PER_SECOND) / (TONS * TICK_TIME);
	}

	public float getAluminiumTonsPerSecond() {
		return (resourcesChange[1] * MILLISECONDS_PER_SECOND) / (TONS * TICK_TIME);
	}

	public float getSiliconTonsPerSecond() {
		return (resourcesChange[2] * MILLISECONDS_PER_SECOND) / (TONS * TICK_TIME);
	}

	public float getAsoiumTonsPerSecond() {
		return (resourcesChange[3] * MILLISECONDS_PER_SECOND) / (TONS * TICK_TIME);
	}

}


