package de.aso.restinplanets.net;

import java.util.ArrayList;

public class Building {

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
				+ " Titanium Per Tick: " + resourcesChange[0]
				+ " Aluminium Per Tick: " + resourcesChange[1]
				+ " Silicon Per Tick: " + resourcesChange[2]
				+ " Asoium Per Tick: " + resourcesChange[3];
	}

}
