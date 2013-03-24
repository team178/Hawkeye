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

	public void connect() throws IOException {
		this.sc = (SocketConnection) Connector.open("socket://" + this.ip + ":" + this.port);
		sc.setSocketOption(SocketConnection.LINGER, 5);

		this.is = sc.openInputStream();
		this.os = sc.openOutputStream();

		this.isConnected = true;
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

		if (!this.isConnected()) {
			return "Not connected";
		}

		try {
			StringBuffer sb = new StringBuffer();
			int c;
			while ( ((c = is.read()) != '\n') && (c != -1) ) {
				if (c != '\r') {
					sb.append((char) c);
				}
			}

			result = sb.toString();
		} catch (IOException e) {
			System.out.println(e);
		}

		return result;
	}

}