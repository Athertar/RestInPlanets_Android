package de.aso.restinplanets.net;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class Client {

	private Socket socket;
	private DataPackReader dataPackReader;
	private DataPackWriter dataPackWriter;

	public static Client connect(InetAddress inetAddress, int port, String username, String password) throws IOException {
		try {
			Socket socket = new Socket(inetAddress, port);
			DataPackReader dataPackReader  = new DataPackReader(socket.getInputStream());
			DataPackWriter dataPackWriter = new DataPackWriter(socket.getOutputStream());

			Random r = new Random();
			long salt = r.nextLong();

			MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
			password = password + salt;
			messageDigest.update(password.getBytes());DataPack loginRequest = new DataPack(DataPack.LOGIN_REQUEST);
			loginRequest.strings = new String[] {username};
			loginRequest.bytes = messageDigest.digest();
			loginRequest.longs = new long[] {salt};
			dataPackWriter.writeDataPack(loginRequest);

			DataPack verification =  dataPackReader.readDataPack();
			if (verification.longs[0] == loginRequest.longs[0]) {
				return new Client(socket, dataPackReader, dataPackWriter);
			} else {
				return null;
			}
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}

	private Client(Socket socket, DataPackReader dataPackReader, DataPackWriter dataPackWriter) throws IOException {
		this.socket = socket;
		this.dataPackReader = dataPackReader;
		this.dataPackWriter = dataPackWriter;
	}

	public Planet receivePlanet(long ID) throws IOException {
		DataPack planetRequest = new DataPack(DataPack.PLANET_REQUEST);
		planetRequest.longs = new long[] {ID};
		dataPackWriter.writeDataPack(planetRequest);
		return new Planet(dataPackReader.readDataPack());
	}

}
