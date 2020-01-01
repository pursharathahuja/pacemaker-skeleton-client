package parsers;

import java.util.Collection;
import java.util.List;

import models.*;

public class Parser {

  public void println(String s) {
    System.out.println(s);
  }

  public void renderUser(User user) {
    System.out.println(user.toString());
  }

  public void renderUsers(Collection<User> users) {
    System.out.println(users.toString());
  }

  public void renderActivity(Activity activities) {
    System.out.println(activities.toString());
  }

  public void renderFriend(Friend friends) {
    System.out.println(friends.toString());
  }

  public void renderFriends(Collection<Friend> friends) {
    System.out.println(friends.toString());
  }

  public void renderMessage(Message messages) {
    System.out.println(messages.toString());
  }

  public void renderMessages(Collection<Message> messages) {
    System.out.println(messages.toString());
  }

  public void renderLeaderboard(Collection<Leaderboard> leaderboard) {
    System.out.println(leaderboard.toString());
  }

  public void renderActivities(Collection<Activity> activities) {
    System.out.println(activities.toString());
  }

  public void renderLocations(List<Location> locations) {
    System.out.println(locations.toString());
  }
}