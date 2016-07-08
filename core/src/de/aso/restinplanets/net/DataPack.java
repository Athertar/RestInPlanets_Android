package de.aso.restinplanets.net;

public class DataPack {
	public static final int PLANET_PACK = 1;
	public static final int LOGIN_REQUEST = 3;
	public static final int LOGIN_VERIFICATION = 4;
	public static final int PLANET_REQUEST = 5;
	public static final int INVALID_PLANET_ID = 6;
	public static final int INVALID_LOGIN = 7;
	public static final int BUILDINGS_ON_PLANET = 8;
	public static final int CONSTRUCTION_REQUEST = 9;
	public static final int CONSTRUCTION_SUCCESS = 10;
	public static final int CONSTRUCTION_FAILED = 11;
	public static final int REQUEST_PLAYERS_PLANETS = 12;
	public static final int END_PLANET_LIST = 13;
	public static final int INVALID_PLAYER_NAME = 14;

	public int dataPackType;

	public boolean[] booleans;
	public byte[] bytes;
	public int[] integers;
	public long[] longs;
	public String[] strings;

	public DataPack(int dataPackType) {
		this.dataPackType = dataPackType;
	}

	@Override
	public String toString() {
		return "Booleans: " + (booleans != null ? booleans.length : 0)
				+ " Bytes: " + (bytes != null ? bytes.length : 0)
				+ " Integers: " + (integers != null ? integers.length : 0)
				+ " Longs: " + (longs != null ? longs.length : 0)
				+ " Strings: " + (strings != null ? strings.length : 0);
	}
}