package se.experis.academy.sessions.Util;

import java.util.HashSet;

public class SessionKeeper {
    private static SessionKeeper sessionKeeperInstance = null;

    private HashSet<String> validSessions = new HashSet<>();

    public boolean CheckSession(String session) {
        return validSessions.contains(session);
    }

    public void AddSession(String session){
        validSessions.add(session);
    }

    public void RemoveSession(String session){
        if(validSessions.contains(session)) {
            validSessions.remove(session);
        }
    }

    // Singleton
    public static SessionKeeper getInstance(){
        if (sessionKeeperInstance == null){
            sessionKeeperInstance = new SessionKeeper();
        }

        return sessionKeeperInstance;
    }
}



