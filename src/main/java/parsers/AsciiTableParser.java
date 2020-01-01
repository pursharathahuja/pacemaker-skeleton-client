package parsers;

import com.bethecoder.ascii_table.ASCIITable;
import com.bethecoder.ascii_table.impl.CollectionASCIITableAware;
import com.bethecoder.ascii_table.spec.IASCIITableAware;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import models.*;

public class AsciiTableParser extends Parser {

  public void renderUser(User user) {
    if (user != null) {
      renderUsers(Arrays.asList(user));
      System.out.println("ok");
    } else {
      System.out.println("not found");
    }
  }

  public void renderUsers(Collection<User> users) {
    if (users != null) {
      if (!users.isEmpty()) {
        List<User> userList = new ArrayList<User>(users);
        IASCIITableAware asciiTableAware = new CollectionASCIITableAware<User>(userList, "id",
            "firstname",
            "lastname", "email");
        System.out.println(ASCIITable.getInstance().getTable(asciiTableAware));
      }
      System.out.println("ok");
    } else {
      System.out.println("not found");
    }
  }

  public void renderFriend(Friend friend) {
    if (friend != null) {
      renderFriends(Arrays.asList(friend));
      System.out.println("ok");
    } else {
      System.out.println("not found");
    }
  }

  public void renderFriends(Collection<Friend> Friends) {
    if (Friends != null) {
      if (!Friends.isEmpty()) {
        List<Friend> friendsList = new ArrayList(Friends);
        IASCIITableAware asciiTableAware = new CollectionASCIITableAware<>(friendsList,
                "id", "useremail", "friendemail");
        System.out.println(ASCIITable.getInstance().getTable(asciiTableAware));
      }
      System.out.println("ok");
    } else {
      System.out.println("not found");
    }
  }

  public void renderMessage(Message message) {
    if (message != null) {
      renderMessages(Arrays.asList(message));
      System.out.println("ok");
    } else {
      System.out.println("not found");
    }
  }

  public void renderMessages(Collection<Message> Messages) {
    if (Messages != null) {
      if (!Messages.isEmpty()) {
        List<Message> messagesList = new ArrayList(Messages);
        IASCIITableAware asciiTableAware = new CollectionASCIITableAware<>(messagesList,
                "id", "from", "to", "message");
        System.out.println(ASCIITable.getInstance().getTable(asciiTableAware));
      }
      System.out.println("ok");
    } else {
      System.out.println("not found");
    }
  }

  public void renderLeaderboard(Collection<Leaderboard> leaderboard) {
    if (leaderboard != null) {
      if (!leaderboard.isEmpty()) {
        List<Leaderboard> leaderboardList = new ArrayList(leaderboard);
        IASCIITableAware asciiTableAware = new CollectionASCIITableAware<>(leaderboardList,
                "userid", "useremail", "friendid", "friendemail",
                "activityid", "activitytype", "activitylocation", "activitydistance");
        System.out.println(ASCIITable.getInstance().getTable(asciiTableAware));
      }
      System.out.println("ok");
    } else {
      System.out.println("not found");
    }
  }

  public void renderActivity(Activity activity) {
    if (activity != null) {
      renderActivities(Arrays.asList(activity));
      System.out.println("ok");
    } else {
      System.out.println("not found");
    }
  }

  public void renderActivities(Collection<Activity> activities) {
    if (activities != null) {
      if (!activities.isEmpty()) {
        List<Activity> activityList = new ArrayList(activities);
        IASCIITableAware asciiTableAware = new CollectionASCIITableAware<Activity>(activityList,
            "id",
            "type", "location", "distance");
        System.out.println(ASCIITable.getInstance().getTable(asciiTableAware));
      }
      System.out.println("ok");
    } else {
      System.out.println("not found");
    }
  }

  public void renderLocations(List<Location> locations) {
    if (locations != null) {
      if (!locations.isEmpty()) {
        IASCIITableAware asciiTableAware = new CollectionASCIITableAware<Location>(locations,
            "id",
            "latitude", "longitude");
        System.out.println(ASCIITable.getInstance().getTable(asciiTableAware));
      }
      System.out.println("ok");
    } else {
      System.out.println("not found");
    }
  }
}