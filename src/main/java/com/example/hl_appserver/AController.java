package com.example.hl_appserver;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.tyrus.server.Server;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class AController{
	//public LobbyRestapiConnector lobbyRestApiConnector = new LobbyRestapiConnector(this);
	static String contextRoot = "/app";
	static String protocol = "ws";
	static int port = 8080;

	public static final String restUri = "http://localhost:8081";




	public static void main(String[] args) throws Exception{
		Server server = new Server(protocol, port, contextRoot, null, AServerConnector.class);
		//final ResourceConfig rc = new ResourceConfig().packages("");
		//final HttpServer restServer = GrizzlyHttpServerFactory.createHttpServer(URI.create(restUri), rc);

		try{
			server.start();
			System.in.read();
		}finally{
			server.stop();
			//restServer.shutdownNow();
		}

	}


}
