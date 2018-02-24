package pl.TransportCompanySystem.Client;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientSocket {

	private static int port = 50000;

	public static Object connectToServer(String a, String b, Object ob) throws IOException {
		Socket socket = new Socket("localhost", port);
		ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
		ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

		try {
			if (a == "Add" && b == "User") {
				out.writeObject(a);
				out.flush();
				out.writeObject(b);
				out.flush();
				out.writeObject(ob);
				out.flush();
				ob = in.readObject();
			} else if (a == "Statement") {
				out.writeObject(a);
				out.flush();
				out.writeObject(b);
				out.flush();
				out.writeObject(ob);
				out.flush();
				ob = in.readObject();
			} else if (a == "Check" && b == "Login") {
				out.writeObject(a);
				out.flush();
				out.writeObject(b);
				out.flush();
				out.writeObject(ob);
				out.flush();
				ob = in.readObject();
			} else if (a == "Check" && b == "CourierLogin") {
				out.writeObject(a);
				out.flush();
				out.writeObject(b);
				out.flush();
				out.writeObject(ob);
				out.flush();
				ob = in.readObject();
			} else if (a == "Free" && b == "Login") {
				out.writeObject(a);
				out.flush();
				out.writeObject(b);
				out.flush();
				out.writeObject(ob);
				out.flush();
				ob = in.readObject();
			} else if (a == "Free" && b == "CourierLogin") {
				out.writeObject(a);
				out.flush();
				out.writeObject(b);
				out.flush();
				out.writeObject(ob);
				out.flush();
				ob = in.readObject();
			} else if (a == "CountAll" && b == "Package") {
				out.writeObject(a);
				out.flush();
				out.writeObject(b);
				out.flush();
				out.writeObject(ob);
				out.flush();
				ob = in.readObject();
			} else if (a == "Count" && b == "Package") {
				out.writeObject(a);
				out.flush();
				out.writeObject(b);
				out.flush();
				out.writeObject(ob);
				out.flush();
				ob = in.readObject();
			} else if (a == "Count" && b == "PackageComplaint") {
				out.writeObject(a);
				out.flush();
				out.writeObject(b);
				out.flush();
				out.writeObject(ob);
				out.flush();
				ob = in.readObject();
			} else if (a == "Count" && b == "Price") {
				out.writeObject(a);
				out.flush();
				out.writeObject(b);
				out.flush();
				out.writeObject(ob);
				out.flush();
				ob = in.readObject();
			} else if (a == "CountAverage" && b == "Price") {
				out.writeObject(a);
				out.flush();
				out.writeObject(b);
				out.flush();
				out.writeObject(ob);
				out.flush();
				ob = in.readObject();
			} else if (a == "Edit" && b == "User") {
				out.writeObject(a);
				out.flush();
				out.writeObject(b);
				out.flush();
				out.writeObject(ob);
				out.flush();
				ob = in.readObject();
			} else if (a == "User" && b == "Package") {
				out.writeObject(a);
				out.flush();
				out.writeObject(b);
				out.flush();
				out.writeObject(ob);
				out.flush();
				ob = in.readObject();
			} else if (a == "Package" && b == "Status") {
				out.writeObject(a);
				out.flush();
				out.writeObject(b);
				out.flush();
				out.writeObject(ob);
				out.flush();
				ob = in.readObject();
			} else if (a == "Location" && b == "List") {
				out.writeObject(a);
				out.flush();
				out.writeObject(b);
				out.flush();
				out.writeObject(ob);
				out.flush();
				ob = in.readObject();
			} else if (a == "Location" && b == "ID") {
				out.writeObject(a);
				out.flush();
				out.writeObject(b);
				out.flush();
				out.writeObject(ob);
				out.flush();
				ob = in.readObject();
			} else if (a == "Add" && b == "Package") {
				out.writeObject(a);
				out.flush();
				out.writeObject(b);
				out.flush();
				out.writeObject(ob);
				out.flush();
				ob = in.readObject();
			} else if (a == "CountAll" && b == "Accounts") {
				out.writeObject(a);
				out.flush();
				out.writeObject(b);
				out.flush();
				out.writeObject(ob);
				out.flush();
				ob = in.readObject();
			} else if (a == "CountAll" && b == "Locations") {
				out.writeObject(a);
				out.flush();
				out.writeObject(b);
				out.flush();
				out.writeObject(ob);
				out.flush();
				ob = in.readObject();
			} else if (a == "CountAll" && b == "Couriers") {
				out.writeObject(a);
				out.flush();
				out.writeObject(b);
				out.flush();
				out.writeObject(ob);
				out.flush();
				ob = in.readObject();
			} else if (a == "GetMin" && b == "Salary") {
				out.writeObject(a);
				out.flush();
				out.writeObject(b);
				out.flush();
				out.writeObject(ob);
				out.flush();
				ob = in.readObject();
			} else if (a == "GetMax" && b == "Salary") {
				out.writeObject(a);
				out.flush();
				out.writeObject(b);
				out.flush();
				out.writeObject(ob);
				out.flush();
				ob = in.readObject();
			} else if (a == "CountAll" && b == "Cars") {
				out.writeObject(a);
				out.flush();
				out.writeObject(b);
				out.flush();
				out.writeObject(ob);
				out.flush();
				ob = in.readObject();
			} else if (a == "CountAllType" && b == "Cars") {
				out.writeObject(a);
				out.flush();
				out.writeObject(b);
				out.flush();
				out.writeObject(ob);
				out.flush();
				ob = in.readObject();
			} else if (a == "All" && b == "Packages") {
				out.writeObject(a);
				out.flush();
				out.writeObject(b);
				out.flush();
				out.writeObject(ob);
				out.flush();
				ob = in.readObject();
			} else if (a == "Get" && b == "User") {
				out.writeObject(a);
				out.flush();
				out.writeObject(b);
				out.flush();
				out.writeObject(ob);
				out.flush();
				ob = in.readObject();
			} else if (a == "Get" && b == "Courier") {
				out.writeObject(a);
				out.flush();
				out.writeObject(b);
				out.flush();
				out.writeObject(ob);
				out.flush();
				ob = in.readObject();
			} else if (a == "Get" && b == "Location") {
				out.writeObject(a);
				out.flush();
				out.writeObject(b);
				out.flush();
				out.writeObject(ob);
				out.flush();
				ob = in.readObject();
			} else if (a == "Get" && b == "CouriersList") {
				out.writeObject(a);
				out.flush();
				out.writeObject(b);
				out.flush();
				out.writeObject(ob);
				out.flush();
				ob = in.readObject();
			} else if (a == "Get" && b == "LocationString") {
				out.writeObject(a);
				out.flush();
				out.writeObject(b);
				out.flush();
				out.writeObject(ob);
				out.flush();
				ob = in.readObject();
			} else if (a == "Edit" && b == "Package") {
				out.writeObject(a);
				out.flush();
				out.writeObject(b);
				out.flush();
				out.writeObject(ob);
				out.flush();
				ob = in.readObject();
			} else if (a == "Delete" && b == "Package") {
				out.writeObject(a);
				out.flush();
				out.writeObject(b);
				out.flush();
				out.writeObject(ob);
				out.flush();
				ob = in.readObject();
			} else if (a == "Get" && b == "UsersList") {
				out.writeObject(a);
				out.flush();
				out.writeObject(b);
				out.flush();
				out.writeObject(ob);
				out.flush();
				ob = in.readObject();
			} else if (a == "Delete" && b == "User") {
				out.writeObject(a);
				out.flush();
				out.writeObject(b);
				out.flush();
				out.writeObject(ob);
				out.flush();
				ob = in.readObject();
			} else if (a == "Edit" && b == "Location") {
				out.writeObject(a);
				out.flush();
				out.writeObject(b);
				out.flush();
				out.writeObject(ob);
				out.flush();
				ob = in.readObject();
			} else if (a == "Add" && b == "Location") {
				out.writeObject(a);
				out.flush();
				out.writeObject(b);
				out.flush();
				out.writeObject(ob);
				out.flush();
				ob = in.readObject();
			} else if (a == "TransferPackages") {
				out.writeObject(a);
				out.flush();
				out.writeObject(b);
				out.flush();
				out.writeObject(ob);
				out.flush();
				ob = in.readObject();
			} else if (a == "Delete" && b == "Location") {
				out.writeObject(a);
				out.flush();
				out.writeObject(b);
				out.flush();
				out.writeObject(ob);
				out.flush();
				ob = in.readObject();
			} else if (a == "Location" && b == "Packages") {
				out.writeObject(a);
				out.flush();
				out.writeObject(b);
				out.flush();
				out.writeObject(ob);
				out.flush();
				ob = in.readObject();
			} else if (a == "Courier" && b == "Packages") {
				out.writeObject(a);
				out.flush();
				out.writeObject(b);
				out.flush();
				out.writeObject(ob);
				out.flush();
				ob = in.readObject();
			} else if (a == "Get" && b == "Car") {
				out.writeObject(a);
				out.flush();
				out.writeObject(b);
				out.flush();
				out.writeObject(ob);
				out.flush();
				ob = in.readObject();
			} else if (a == "Cars" && b == "List") {
				out.writeObject(a);
				out.flush();
				out.writeObject(b);
				out.flush();
				out.writeObject(ob);
				out.flush();
				ob = in.readObject();
			} else if (a == "Edit" && b == "Courier") {
				out.writeObject(a);
				out.flush();
				out.writeObject(b);
				out.flush();
				out.writeObject(ob);
				out.flush();
				ob = in.readObject();
			} else if (a == "EditCarStatus") {
				out.writeObject(a);
				out.flush();
				out.writeObject(b);
				out.flush();
				out.writeObject(ob);
				out.flush();
				ob = in.readObject();
			} else if (a == "Delete" && b == "Courier") {
				out.writeObject(a);
				out.flush();
				out.writeObject(b);
				out.flush();
				out.writeObject(ob);
				out.flush();
				ob = in.readObject();
			} else if (a == "Add" && b == "Courier") {
				out.writeObject(a);
				out.flush();
				out.writeObject(b);
				out.flush();
				out.writeObject(ob);
				out.flush();
				ob = in.readObject();
			} else if (a == "Delete" && b == "Car") {
				out.writeObject(a);
				out.flush();
				out.writeObject(b);
				out.flush();
				out.writeObject(ob);
				out.flush();
				ob = in.readObject();
			} else if (a == "Add" && b == "Car") {
				out.writeObject(a);
				out.flush();
				out.writeObject(b);
				out.flush();
				out.writeObject(ob);
				out.flush();
				ob = in.readObject();
			} else if (a == "Edit" && b == "PackageCourier") {
				out.writeObject(a);
				out.flush();
				out.writeObject(b);
				out.flush();
				out.writeObject(ob);
				out.flush();
				ob = in.readObject();
			}

			in.close();
			out.close();
			socket.close();
		} catch (UnknownHostException e) {
			System.err.println("Bład serwer jest nie osiągalny");
			System.exit(1);
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Bład połączenia");
			System.exit(1);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return ob;
	}

}
