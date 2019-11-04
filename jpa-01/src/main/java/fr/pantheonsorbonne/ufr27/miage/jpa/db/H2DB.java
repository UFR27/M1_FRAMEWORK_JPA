
package fr.pantheonsorbonne.ufr27.miage.jpa.db;

import java.sql.Connection;
import java.sql.SQLException;

import org.h2.tools.Server;

public class H2DB {

	static private Server server;
	static private Connection con;
	static {

		try {
			server = Server.createWebServer("-webAllowOthers");
			server = server.start();

			

		} catch (SQLException e) {
			System.err.println("failed to start H2 server");
			e.printStackTrace();
			System.exit(-2);
		}

	}
}
