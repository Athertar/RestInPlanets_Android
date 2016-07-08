package de.aso.restinplanets.net;

import java.util.Arrays;

public class Planet {

	private static final int TONS = 1000;
	private static final int TITANIUM = 0;
	private static final int ALUMINIUM = 1;
	private static final int SILICON = 2;
	private static final int ASOIUM = 3;
	private static final int AMOUNT_RESOURCES = 4;

	private long planetID;
	private long lastIterated;
	private String owner;
	private long[] resources = new long[AMOUNT_RESOURCES];
	private int[] resourcesChange = new int[AMOUNT_RESOURCES];

	public Planet(long planetID, long[] resources, int[] resourcesChange, long lastIterated, String owner) {
		this.planetID = planetID;
		this.resources = resources;
		this.resourcesChange = resourcesChange;
		this.lastIterated = lastIterated;
		this.owner = owner;
	}

	public Planet(DataPack dataPack) {
		this.set(dataPack);
	}

	public synchronized void update(float delta) {
		for (int i = 0; i < resources.length; i++) {
			resources[i] += resourcesChange[i];
		}
	}

	public void update(long iterationCount) {
		long delta = iterationCount - lastIterated;
		//TODO Save Math function with Data for integration
		for (int i = 0; i < resources.length; i++) {
			resources[i] += resourcesChange[i] * delta;
		}
		lastIterated = iterationCount;
	}

	public void set(DataPack dataPack) {
		planetID = dataPack.longs[0];
		for (int i = 0; i < resources.length; i++) {
			resources[i] = dataPack.longs[i + 2];
		}
		System.arraycopy(dataPack.integers, 0, resourcesChange, 0, resourcesChange.length);
		dataPack.integers = Arrays.copyOf(resourcesChange, resourcesChange.length);
		owner = dataPack.strings[0];
	}

	public DataPack getUpdatePack() {
		DataPack dataPack = new DataPack(DataPack.PLANET_PACK);
		dataPack.longs = new long[resources.length + 2];
		dataPack.longs[0] = planetID;
		for (int i = 0; i < resources.length; i++) {
			dataPack.longs[i + 2] = resources[i];
		}
		dataPack.integers = Arrays.copyOf(resourcesChange, resourcesChange.length);
		dataPack.strings = new String[] { this.owner };
		return dataPack;
	}

	public boolean takeResources(int[] costs) {
		for (int i = 0; i < resources.length; i++) {
			if (costs[i] > resources[i]) return false;
		}
		for (int i = 0; i < resources.length; i++) {
			resources[i] -= costs[i];
		}
		return true;
	}

	public void addResourcesChange(int[] change) {
		for (int i = 0; i < resources.length; i++) {
			resourcesChange[i] += change[i];
		}
	}

	@Override
	public String toString() {
		return "Planet " + planetID + " (" + owner + "):"
				+ " Titanium: " + getTitaniumTons() + " tons"
				+ " Aluminium: " + getAluminiumTons() + " tons"
				+ " Silicon: " + getSiliconTons() + " tons"
				+ " Asoium: " + getAsoiumTons() + " tons";
	}

	public long getAluminium() {
		return resources[ALUMINIUM];
	}

	public long getTitanium() {
		return resources[TITANIUM];
	}

	public long getSilicon() {
		return resources[SILICON];
	}

	public long getAsoium() {
		return resources[ASOIUM];
	}

	public long getPlanetID() {
		return planetID;
	}

	public long getLastIterated() {
		return lastIterated;
	}

	public long getAluminiumTons() {
		return getAluminium() / TONS;
	}

	public long getTitaniumTons() {
		return getTitanium() / TONS;
	}

	public long getSiliconTons() {
		return getSilicon() / TONS;
	}

	public long getAsoiumTons() { return getAsoium() / TONS; }
}