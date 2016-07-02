package de.aso.restinplanets.net;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class DataPackWriter {

	private final OutputStream outputStream;
	private final DataOutputStream dataOutputStream;

	public DataPackWriter(OutputStream outputStream) {
		this.outputStream = outputStream;
		this.dataOutputStream = new DataOutputStream(this.outputStream);
	}

	public void writeDataPack(DataPack dataPack) throws IOException {
		synchronized (outputStream) {
			dataOutputStream.writeInt(dataPack.dataPackType);
			if (dataPack.booleans == null) {
				dataOutputStream.writeInt(0);
			} else {
				dataOutputStream.writeInt(dataPack.booleans.length);
				for (int i = 0; i < dataPack.booleans.length; i++) {
					dataOutputStream.writeBoolean(dataPack.booleans[i]);
				}
			}
			if (dataPack.bytes == null) {
				dataOutputStream.writeInt(0);
			} else {
				dataOutputStream.writeInt(dataPack.bytes.length);
				for (int i = 0; i < dataPack.bytes.length; i++) {
					dataOutputStream.writeByte(dataPack.bytes[i]);
				}
			}
			if (dataPack.integers == null) {
				dataOutputStream.writeInt(0);
			} else {
				dataOutputStream.writeInt(dataPack.integers.length);
				for (int i = 0; i < dataPack.integers.length; i++) {
					dataOutputStream.writeInt(dataPack.integers[i]);
				}
			}
			if (dataPack.longs == null) {
				dataOutputStream.writeInt(0);
			} else {
				dataOutputStream.writeInt(dataPack.longs.length);
				for (int i = 0; i < dataPack.longs.length; i++) {
					dataOutputStream.writeLong(dataPack.longs[i]);
				}
			}
			if (dataPack.strings == null) {
				dataOutputStream.writeInt(0);
			} else {
				dataOutputStream.writeInt(dataPack.strings.length);
				for (int i = 0; i < dataPack.strings.length; i++) {
					dataOutputStream.writeUTF(dataPack.strings[i]);
				}
			}
		}
	}
}
