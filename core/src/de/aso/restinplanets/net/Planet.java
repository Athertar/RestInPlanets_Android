package de.aso.restinplanets.net;

import com.badlogic.gdx.Gdx;

import java.util.Arrays;

import sun.rmi.runtime.Log;

public class Planet {

	public static final int TITANIUM = 0;
	public static final int SILICON = 1;
	public static final int ALUMINIUM = 2;
	public static final int ASOIUM = 3;
	public static final int AMOUNT_RESOURCES = 4;

	private long planetID;

	private long[] resources = new long[AMOUNT_RESOURCES];
	private int[] resourcesChange = new int[AMOUNT_RESOURCES];

	public Planet(long planetID, long[] resources, int[] resourcesChange, long lastIterated) {
		this.planetID = planetID;
		this.resources = resources;
		this.resourcesChange = resourcesChange;
	}

	public Planet(DataPack dataPack) {
		this.set(dataPack);
	}

	public long getPlanetID() {
		return planetID;
	}

	public synchronized void update(float delta) {
		for (int i = 0; i < resources.length; i++) {
			resources[i] += resourcesChange[i];
			Gdx.app.log("Aso", "" + delta);
		}
	}

	public synchronized void set(DataPack dataPack) {
		planetID = dataPack.longs[0];
		for (int i = 0; i < resources.length; i++) {
			resources[i] = dataPack.longs[i + 2];
		}
		System.arraycopy(dataPack.integers, 0, resourcesChange, 0, resourcesChange.length);
		dataPack.integers = Arrays.copyOf(resourcesChange, resourcesChange.length);
	}

	public synchronized DataPack getUpdatePack() {
		DataPack dataPack = new DataPack(DataPack.PLANET_PACK);
		dataPack.longs = new long[resources.length + 2];
		dataPack.longs[0] = planetID;
		for (int i = 0; i < resources.length; i++) {
			dataPack.longs[i + 2] = resources[i];
		}
		dataPack.integers = Arrays.copyOf(resourcesChange, resourcesChange.length);
		return dataPack;
	}

	@Override
	public String toString() {
		return " Amount Aluminium: " + resources[ALUMINIUM]
				+ " Amount Titanium: " + resources[TITANIUM]
				+ " Amount Silicon: " + resources[SILICON]
				+ " Amount Asoium: " + resources[ASOIUM];
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

	public long getAsoium() { return resources[ASOIUM]; }

}