package org.twuni.web.c2dm;

import java.util.HashMap;
import java.util.Map;

import org.twuni.common.net.http.Method;
import org.twuni.common.net.http.Server;
import org.twuni.common.net.http.responder.RequestMapping;

public class C2DMServer {

	private static final Map<String, String> REGISTRATIONS = new HashMap<String, String>();
	private static final Map<String, String> USERS = new HashMap<String, String>();

	public static void main( String [] args ) {

		RequestMapping mapping = new RequestMapping();

		mapping.map( Method.POST, "/register", new RegistrationResponder( REGISTRATIONS, USERS ) );
		mapping.map( Method.POST, "/message", new MessageResponder( REGISTRATIONS, USERS ) );

		new Server( 8080, mapping ).start();

	}

}
