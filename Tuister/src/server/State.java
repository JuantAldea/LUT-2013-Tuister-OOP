package server;

import java.util.ArrayList;

public abstract class State implements Cloneable {
    protected ArrayList<String> listJoke = new ArrayList<String>();
    protected boolean jokeListTainted = false;

    @Override
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    public synchronized boolean isListTainted() {
        return jokeListTainted;
    }

}
