import aircraft.Aircraft;
import aircraft.Plane;
import aircraft.Status;
import dispatcher.Dispatcher;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class App {

    private static int counter = 1;

    private static List<Aircraft> allCrafts = new ArrayList<>();

    public static void main(String[] args) {

        Dispatcher dispatcher = new Dispatcher();
        for (int i = 1; i < 21; i++) {
            dispatcher.addAircraft(new Plane(dispatcher, getRandomStatus(), counter++));
        }

        System.out.println("\n");

        while (!dispatcher.getAircrafts().isEmpty()) {
            allCrafts.clear();
            allCrafts.addAll(dispatcher.getAircrafts().values());
            System.out.println("\nAirCrafts quantity = " + allCrafts.size());

            if (!allCrafts.isEmpty()) {
                allCrafts.get(0).inform();
            }
            System.out.println("");

            if (!dispatcher.getAircrafts().isEmpty() && (dispatcher.getAircrafts().size() % 4 == 0)) {
                dispatcher.addAircraft(new Plane(dispatcher, getRandomStatus(), counter++));
            }

        }

    }

    private static Status getRandomStatus() {
        int num = (int) (Math.random() * 2);
        return num == 0 ? Status.ARRIVE : Status.DEPART;
    }

}
