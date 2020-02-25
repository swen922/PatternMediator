package aircraft;

import com.sun.istack.internal.NotNull;
import dispatcher.Dispatcher;

public class Plane implements Aircraft {

    private Dispatcher dispatcher;
    private Status status;
    private int id;
    private AirAction airAction;


    public Plane(Dispatcher dispatcher, Status status, int id) {
        this.dispatcher = dispatcher;
        this.status = status;
        this.id = id;
    }


    @Override
    public void inform() {
        System.out.println("BOARD-" + id + " | Status:"+status + " | asking action: " + airAction);
        dispatcher.inform();

    }

    @Override
    public void setAction(AirAction airAction) {
        this.airAction = airAction;
        System.out.println("BOARD-" + id + " | Status:"+ status + " | " + airAction + " accepted");
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public AirAction getAirAction() {
        return airAction;
    }

    @Override
    public Status getStatus() {
        return status;
    }



}
