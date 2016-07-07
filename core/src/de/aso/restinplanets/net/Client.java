package de.aso.restinplanets.net;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
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

	public Planet requestPlanet(long ID) throws IOException {
		DataPack planetRequest = new DataPack(DataPack.PLANET_REQUEST);
		planetRequest.longs = new long[] {ID};
		dataPackWriter.writeDataPack(planetRequest);
		DataPack planetResponse = dataPackReader.readDataPack();
		if (planetResponse.dataPackType == DataPack.INVALID_PLANET_ID) {
			return null;
		} else {
			return new Planet(planetResponse);
		}
	}

	public ArrayList<Building> requestBuildingsOnPlanet(long ID) throws IOException {
		DataPack buildingRequest = new DataPack(DataPack.BUILDINGS_ON_PLANET);
		buildingRequest.longs = new long[] {ID};
		dataPackWriter.writeDataPack(buildingRequest);
		return Building.getFromDataPack(dataPackReader.readDataPack());
	}

	public DataPack requestConstruction(long planetID, long buildingID) throws IOException {
		DataPack constructionRequest = new DataPack(DataPack.CONSTRUCTION_REQUEST);
		constructionRequest.longs = new long[] { planetID, buildingID };
		dataPackWriter.writeDataPack(constructionRequest);
		return dataPackReader.readDataPack();
	}

}
