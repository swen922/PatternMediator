package dispatcher;

import aircraft.AirAction;
import aircraft.Aircraft;
import aircraft.Status;
import com.sun.istack.internal.NotNull;

import java.util.*;

public class Dispatcher {

    private Map<Integer, Aircraft>  aircrafts = new LinkedHashMap<>();
    private int taxi = -1;
    private int rise = -1;
    private int arrive = -1;
    private int land = -1;

    public Map<Integer, Aircraft> getAircrafts() {
        return aircrafts;
    }


    public void inform() {

        checkAircrafts();

        for (Aircraft aircraft : aircrafts.values()) {

            AirAction action = aircraft.getAirAction();
            Status st = aircraft.getStatus();

            if (st == Status.ARRIVE) {
                if (action == AirAction.LAND && land == aircraft.getId()) {
                    aircraft.setAction(AirAction.TERMINAL_IN);
                    land = -1;
                }
                else if (action == AirAction.ARRIVE && arrive == aircraft.getId()) {
                    if (land < 0) {
                        aircraft.setAction(AirAction.LAND);
                        land = aircraft.getId();
                    }
                    else {
                        aircraft.setAction(AirAction.WAIT);
                    }
                    arrive = -1;
                }
                else if (action == AirAction.APPROACH) {
                    if (arrive < 0) {
                        aircraft.setAction(AirAction.ARRIVE);
                        arrive = aircraft.getId();
                    }
                    else {
                        aircraft.setAction(AirAction.WAIT);
                    }
                }
                else if (action == AirAction.WAIT) {
                    if (land < 0) {
                        aircraft.setAction(AirAction.LAND);
                        land = aircraft.getId();
                    }
                    else if (arrive < 0) {
                        aircraft.setAction(AirAction.ARRIVE);
                        arrive = aircraft.getId();
                    }

                }
            }
            else if (st == Status.DEPART) {
                if (action == AirAction.RISE && rise == aircraft.getId()) {
                    aircraft.setAction(AirAction.AWAY);
                    rise = -1;
                }
                else if (action == AirAction.TAXI && taxi == aircraft.getId()) {
                    if (rise < 0) {
                        aircraft.setAction(AirAction.RISE);
                        rise = aircraft.getId();
                    }
                    else {
                        aircraft.setAction(AirAction.WAIT);
                    }
                    taxi = -1;
                }
                else if (action == AirAction.TERMINAL_OUT) {

                    if (taxi < 0) {
                        aircraft.setAction(AirAction.TAXI);
                        taxi = aircraft.getId();
                    }
                    else {
                        aircraft.setAction(AirAction.WAIT);
                    }
                }
                else if (action == AirAction.WAIT) {
                    if (rise < 0) {
                        aircraft.setAction(AirAction.RISE);
                        rise = aircraft.getId();
                    }
                    else if (taxi < 0) {
                        aircraft.setAction(AirAction.TAXI);
                        taxi = aircraft.getId();
                    }
                }
            }
        }

    }

    private void checkAircrafts() {

        int deleted = 0;
        // сначала удаляем улетевшие и окончательно прилетевшие (стоящие у терминала)
        Iterator<Map.Entry<Integer, Aircraft>> iter = aircrafts.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<Integer, Aircraft> entry = iter.next();
            if (AirAction.AWAY == entry.getValue().getAirAction()) {
                iter.remove();
            }
            else if (AirAction.TERMINAL_IN == entry.getValue().getAirAction()) {
                iter.remove();
            }
        }

        System.out.println("DELETED AIRCRAFTS = " + deleted);


    }


    public void addAircrafts(@NotNull List<Aircraft> listAircrafts) {
        for (Aircraft ac : listAircrafts) {
            addAircraft(ac);
        }
    }

    public void addAircraft(Aircraft aircraft) {
        if (aircraft.getStatus() == Status.ARRIVE) {
            aircraft.setAction(AirAction.APPROACH);
        }
        else if (aircraft.getStatus() == Status.DEPART) {
            aircraft.setAction(AirAction.TERMINAL_OUT);
        }
        aircrafts.put(aircraft.getId(), aircraft);
    }


}
