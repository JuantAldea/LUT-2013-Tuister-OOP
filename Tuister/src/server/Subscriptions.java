package server;

import java.util.HashSet;
import java.util.Hashtable;

import common.User;

public class Subscriptions {

    protected Hashtable<User, HashSet<User>> subscriptions;

    public boolean subscribe(User user, User target) {
        HashSet<User> userSubscriptions = this.subscriptions.get(user);

        if (userSubscriptions == null) {
            return false;
        }

        return userSubscriptions.contains(target) ? true : userSubscriptions.add(target);
    }

    public HashSet<User> getSubscriptions(User user) {
        return this.subscriptions.get(user);
    }

    public boolean removeSubscription(User user, User target) {
        HashSet<User> userSubscriptions = this.subscriptions.get(user);
        return userSubscriptions != null ? userSubscriptions.remove(target) : false;
    }
}
