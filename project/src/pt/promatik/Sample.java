package pt.promatik;

import pt.promatik.moss.*;
import pt.promatik.moss.utils.Utils;

public class Sample extends Moss
{
	private enum Commands { stats; }
	private final static String TAG = "SP  "; // Sample Project
	
	
	public static void main(String[] args)
	{
		new Sample(args);
	}
	
	public Sample(String[] args){
		start(args);
		log("Sample MOSS project");
		log("Type help to access command list");
		
		// Register console commands
		console.registerCommands(Commands.class);
		
		// Use login validation
		validateLogin = false;
	}
	
	
	public static void log(String message) {
		Utils.log(message, TAG);
	}
	
	public static void err(String message) {
		Utils.error(message, TAG);
	}
	
	
	@Override
	public void appTimer(){
		
	}
	
	@Override
	public void serverStarted() {
		log("serverStarted dispatched");
	}

	@Override
	public void serverStopped() {
		log("serverStopped dispatched");
	}

	@Override
	public void userConnected(User user) {
		// Alert everyone on the room that a client connected
		invokeOnRoom(user, user.room(), "clientConnect", user.id());
	}

	@Override
	public void userDisconnected(User user) {
		// Alert everyone on the room that a client disconnected
		invokeOnRoom(user, user.room(), "clientDisconnect", user.id());
	}

	@Override
	public void userUpdatedStatus(User user, String status) {
		// Alert everyone on the room that a client updated its status
		invokeOnRoom(user, user.room(), "clientUpdatedStatus", status);
	}

	@Override
	public void userUpdatedAvailability(User user, boolean available) {
		// Alert everyone on the room that a client updated its availability
		invokeOnRoom(user, user.room(), "clientUpdatedAvailability", available ? "1" : "0");
	}

	@Override
	public void userUpdatedRoom(User user, String room) {
		// Alert everyone that a client updated its room
		invokeOnAll(user, "clientUpdatedRoom", room);
	}
	
	@Override
	public void userMessage(User user, String command, String message, String request) {
		switch (command) {
			case "gameSpecialEvent":
				// Deal with gameSpecialEvent
				// This commands are sent by users
				user.invoke(command, "Request received by the server", request);
				break;
			default:
				err("Unhandled event from " + user.id() + ": " + command);
		}
	}

	@Override
	public void commandInput(String command, String value) {
		switch (Commands.valueOf(command)) {
			case stats:
				log(server.getUsers().size() + " online users");
				log(server.getRooms().size() + " active rooms");
				log(server.getRoom(value).users.size() + " users in the room '" + value + "'");
				break;
		}
	}

	@Override
	public String validateLogin(String login, String password, String data) {
		// validateLogin may be used to validate a user login on a database and return its 'id' as a String
		// You must set `validateLogin = true;`
		return null;
	}
}