package de.aso.restinplanets.net;

public class DataPack {
	public static final int PLANET_PACK = 1;
	public static final int LOGIN_REQUEST = 3;
	public static final int LOGIN_VERIFICATION = 4;
	public static final int REQUEST_PACK = 0;
	public static final int RESPONSE_PACK = 2;
	public static final int PLANET_REQUEST = 5;
	public static final int INVALID_PLANET_ID = 6;
	public static final int INVALID_LOGIN = 7;

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
		return "Booleans: " + booleans.length
				+ " Bytes: " + bytes.length
				+ " Integers: " + integers.length
				+ " Longs: " + longs.length
				+ " Strings: " + strings.length;
	}
}
