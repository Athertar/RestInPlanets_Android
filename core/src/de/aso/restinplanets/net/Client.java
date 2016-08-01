package de.aso.restinplanets.net;

import java.io.IOException;
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

	public Client(InetAddress inetAddress, int port) throws IOException {
		this.socket = new Socket(inetAddress, port);
		this.dataPackReader = new DataPackReader(socket.getInputStream());
		this.dataPackWriter = new DataPackWriter(socket.getOutputStream());
	}

	/**
	 * sends a verifications request to the server
	 * @param username
	 * @param password
	 * @return true if verification succeeded
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	public boolean verify(String username, String password) throws NoSuchAlgorithmException, IOException {
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
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Requests a planet from the Server.
	 * @param ID
	 * @return null if planet does not exist
	 * @throws IOException
	 */
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

	/**
	 * Requests all buildings on a specific planet.
	 * List is empty when the planet does not contain any
	 * buildings.
	 * @param ID
	 * @return null if the planet is invalid
	 * @throws IOException
	 */
	public ArrayList<Building> requestBuildingsOnPlanet(long ID) throws IOException {
		DataPack buildingRequest = new DataPack(DataPack.BUILDINGS_ON_PLANET);
		buildingRequest.longs = new long[] {ID};
		dataPackWriter.writeDataPack(buildingRequest);
		return Building.getFromDataPack(dataPackReader.readDataPack());
	}

	/**
	 * Requests the construction of a building on a specific planet.
	 * @param planetID
	 * @param buildingID
	 * @return true if construction succeeded
	 * @throws IOException
	 */
	public boolean requestConstruction(long planetID, long buildingID) throws IOException {
		DataPack constructionRequest = new DataPack(DataPack.CONSTRUCTION_REQUEST);
		constructionRequest.longs = new long[] { planetID, buildingID };
		dataPackWriter.writeDataPack(constructionRequest);
		DataPack constructionResponse = dataPackReader.readDataPack();
		return constructionResponse.dataPackType == DataPack.CONSTRUCTION_SUCCESS;
	}

	public long[] requestPlayerPlanetIDs(String playerName) throws IOException {
		DataPack request = new DataPack(DataPack.REQUEST_PLAYER_PLANET_IDS);
		request.strings = new String[] {playerName};
		dataPackWriter.writeDataPack(request);
		DataPack response = dataPackReader.readDataPack();
		return response.longs;
	}

}
