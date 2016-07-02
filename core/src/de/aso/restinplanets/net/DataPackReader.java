package de.aso.restinplanets.net;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class DataPackReader {

	private final InputStream inputStream;
	private final DataInputStream dataInputStream;

	public DataPackReader(InputStream inputStream) {
		this.inputStream = inputStream;
		this.dataInputStream = new DataInputStream(this.inputStream);
	}

	public DataPack readDataPack() throws IOException {
		synchronized (inputStream) {
			int packType;
			if ((packType = dataInputStream.readInt()) != -1) {
				DataPack dataPack = new DataPack(packType);
				dataPack.booleans = new boolean[dataInputStream.readInt()];
				for (int i = 0; i < dataPack.booleans.length; i++) {
					dataPack.booleans[i] = dataInputStream.readBoolean();
				}
				dataPack.bytes = new byte[dataInputStream.readInt()];
				for (int i = 0; i < dataPack.bytes.length; i++) {
					dataPack.bytes[i] = dataInputStream.readByte();
				}
				dataPack.integers = new int[dataInputStream.readInt()];
				for (int i = 0; i < dataPack.integers.length; i++) {
					dataPack.integers[i] = dataInputStream.readInt();
				}
				dataPack.longs = new long[dataInputStream.readInt()];
				for (int i = 0; i < dataPack.longs.length; i++) {
					dataPack.longs[i] = dataInputStream.readLong();
				}
				dataPack.strings = new String[dataInputStream.readInt()];
				for (int i = 0; i < dataPack.strings.length; i++) {
					dataPack.strings[i] = dataInputStream.readUTF();
				}
				return dataPack;
			} else {
				return null;
			}
		}
	}
}
