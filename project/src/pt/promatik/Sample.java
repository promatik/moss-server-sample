package pt.promatik;

import pt.promatik.moss.*;

public class Sample extends Moss
{
    public static void main(String[] args)
    {
    	new Sample(args);
    }
    
	public Sample(String[] args){
		start(args);
		System.out.println("Sample MOSS project");
	}
	
	@Override
	public void appTimer(){
		
	}
	
    @Override
    public void serverStarted() {
    	System.out.println("serverStarted");
    }

    @Override
    public void serverStopped() {
    	System.out.println("serverStopped");
    }

    @Override
    public void userConnected(User user) {
    	// Alert everyone on the room that a client connected
    	invokeOnRoom(user, user.room, "clientConnect", user.id);
    }

    @Override
    public void userDisconnected(User user) {
    	// Alert everyone on the room that a client disconnected
    	invokeOnRoom(user, user.room, "clientDisconnect", user.id);
    }

    @Override
    public void userUpdatedStatus(User user, String status) {
    	// Alert everyone on the room that a client updated its status
    	invokeOnRoom(user, user.room, "clientUpdatedStatus", status);
    }
    
    @Override
    public void userMessage(User user, String command, String message) {
    	switch (command) {
			case "gameSpecialEvent":
				// TODO: Deal with gameSpecialEvent 
				break;
			default:
				System.out.println("Unhandled event from " + user.id + ": " + command);
		}
    }
}