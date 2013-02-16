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
public class TCPClient {

	private String ip;
	private String port;

	private SocketConnection sc;
	private InputStream is;
	private OutputStream os;

	public TCPClient(String ip, String port) {
		this.ip = ip;
		this.port = port;
	}

	public void connect() {
		try {
			this.sc = (SocketConnection) Connector.open("socket://" + this.ip + ":" + this.port);
			sc.setSocketOption(SocketConnection.LINGER, 5);

			this.is = sc.openInputStream();
			this.os = sc.openOutputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String request() {

		String result = "no data";

		try {
			byte[] data = new byte[39];
			this.is.read(data);

			result = new String(data);
			//is.close(); os.close(); sc.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

}