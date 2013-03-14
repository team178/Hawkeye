package org.usfirst.frc178.custom;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.microedition.io.Connector;
import javax.microedition.io.SocketConnection;

/**
 *
 * @author Enforcers
 */
public class OculusClient {

	private String ip;
	private String port;

	private SocketConnection sc;
	private InputStream is;
	private OutputStream os;

	private boolean isConnected;

	public OculusClient(String ip, String port) {
		this.ip = ip;
		this.port = port;

		this.isConnected = false;
	}

	public void connect() {
		System.out.println("Connecting to coprocessor");
		try {
			this.sc = (SocketConnection) Connector.open("socket://" + this.ip + ":" + this.port);
			sc.setSocketOption(SocketConnection.LINGER, 5);

			this.is = sc.openInputStream();
			this.os = sc.openOutputStream();

			this.isConnected = true;
		} catch (IOException e) {
		}
	}

	public void disconnect() throws IOException {
		this.is.close();
		this.os.close();
		this.sc.close();

		this.isConnected = false;
	}

	public boolean isConnected() {
		return this.isConnected;
	}

	public String request() {

		String result = "no data";

		try {
			byte[] data = new byte[12];
			this.is.read(data);

			result = new String(data);
		} catch (IOException e) {
		}

		return result;
	}

}