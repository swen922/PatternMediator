package aircraft;

import dispatcher.Dispatcher;

public interface Aircraft {

    public void inform();
    public void setAction(AirAction airAction);

    public int getId();
    public AirAction getAirAction();
    public Status getStatus();

}
