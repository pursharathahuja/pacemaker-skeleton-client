package controllers;

import java.util.*;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import models.*;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.*;

interface PacemakerInterface
{
  @GET("/users")
  Call<List<User>> getUsers();

  @POST("/users")
  Call<User> registerUser(@Body User User);

  @POST("/users/{id}/activities")
  Call<Activity> addActivity(@Path("id") String id, @Body Activity activity);

  @GET("/users/{id}/activities/{activityId}")
  Call<Activity> getActivity(@Path("id") String id, @Path("activityId") String activityId);

  @GET("/users/{id}/activities")
  Call<List<Activity>> getActivities(@Path("id") String id);

  @POST("/users/{id}/friends")
  Call<Friend> followFriend(@Path("id") String id, @Body Friend friend);

  @GET("/users/{id}/messages")
  Call<List<Message>> listMessages(@Path("id") String id);

  @POST("/users/{id}/message")
  Call<Message> messageFriend(@Path("id") String id, @Body Message message);

  @POST("/users/{id}/friends/{friendEmail}")
  Call<String> unfollowFriend(@Path("id") String id, @Path("friendEmail") String friendEmail);

  @DELETE("/users/{id}/unfollowfriends")
  Call<String> unfollowFriends(@Path("id") String id);

  @GET("/users/{id}/friends")
  Call<List<Friend>> listFriends(@Path("id") String id);

  @GET("/users/{id}/activities/{activityId}/locations")
  Call<List<Location>> listActivityLocations(@Path("activityId") String activityId);

  @POST("/users/{id}/activities/{activityId}/locations")
  Call<Location> addLocation(@Path("id") String id, @Path("activityId") String activityId, @Body Location location);

  @DELETE("/users")
  Call<User> deleteUsers();

  @DELETE("/users/{id}")
  Call<User> deleteUser(@Path("id") String id);

  @GET("/users/{id}")
  Call<User> getUser(@Path("id") String id);

  @DELETE("/users/{id}/activities")
  Call<String> deleteActivities(@Path("id") String id);
}

public class PacemakerAPI {

  PacemakerInterface pacemakerInterface;

  public PacemakerAPI(String url) {
    Gson gson = new GsonBuilder().create();
    Retrofit retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create(gson)).build();
    pacemakerInterface = retrofit.create(PacemakerInterface.class);
  }

  public Collection<User> getUsers() {
    Collection<User> users = null;
    try {
      Call<List<User>> call = pacemakerInterface.getUsers();
      Response<List<User>> response = call.execute();
      users = response.body();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return users;
  }

  public User createUser(String firstName, String lastName, String email, String password) {
    User returnedUser = null;
    try {
      Call<User> call = pacemakerInterface.registerUser(new User(firstName, lastName, email, password));
      Response<User> response = call.execute();
      returnedUser = response.body();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return returnedUser;
  }

  public Activity createActivity(String id, String type, String location, double distance) {
    Activity returnedActivity = null;
    try {
      Call<Activity> call = pacemakerInterface.addActivity(id, new Activity(type, location, distance));
      Response<Activity> response = call.execute();
      returnedActivity = response.body();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return returnedActivity;
  }

  public Message messageFriend(String id, String from, String to, String message) {
    Message returnedMessage = null;
    try {
      Call<Message> call = pacemakerInterface.messageFriend(id, new Message(from, to, message));
      Response<Message> response = call.execute();
      returnedMessage = response.body();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return returnedMessage;
  }

  public Collection<Message> listMessages(String id) {
    Collection<Message> messages = null;
    try {
      Call<List<Message>> call = pacemakerInterface.listMessages(id);
      Response<List<Message>> response = call.execute();
      messages = response.body();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return messages;
  }

  public Friend followFriend(String id, String user_email, String friend_email) {
    Friend returnedFriend = null;
    try {
      Call<Friend> call = pacemakerInterface.followFriend(id, new Friend(user_email, friend_email));
      Response<Friend> response = call.execute();
      returnedFriend = response.body();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return returnedFriend;
  }

  public String unfollowFriend(String user_id, String friendEmail) {
    String returnedFriend = null;
    try {
      Call<String> call = pacemakerInterface.unfollowFriend(user_id, friendEmail);
      Response<String> response = call.execute();
      returnedFriend = String.valueOf(response.body());
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return returnedFriend;
  }

  public String unfollowFriend(String user_id) {
    String returnedFriend = null;
    try {
      Call<String> call = pacemakerInterface.unfollowFriends(user_id);
      Response<String> response = call.execute();
      returnedFriend = String.valueOf(response.body());
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return returnedFriend;
  }

  public Collection<Friend> listFriends(String id) {
    Collection<Friend> friends = null;
    try {
      Call<List<Friend>> call = pacemakerInterface.listFriends(id);
      Response<List<Friend>> response = call.execute();
      friends = response.body();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return friends;
  }

  public Collection<Activity> getActivities(String id) {
    Collection<Activity> activities = null;
    try {
      Call<List<Activity>> call = pacemakerInterface.getActivities(id);
      Response<List<Activity>> response = call.execute();
      activities = response.body();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return activities;
  }

  public Collection<Location> listActivityLocations(String activityId) {
    Collection<Location> Locations = null;
    try {
      Call<List<Location>> call = pacemakerInterface.listActivityLocations(activityId);
      Response<List<Location>> response = call.execute();
      Locations = response.body();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return Locations;
  }

  public Activity getActivity(String userId, String activityId) {
    Activity activity = null;
    try {
      Call<Activity> call = pacemakerInterface.getActivity(userId, activityId);
      Response<Activity> response = call.execute();
      activity = response.body();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return activity;
  }

  public List<Activity> listActivities(String userId, String sortBy) {
    List<Activity> activities = null;
    try {
      Call<List<Activity>> call = pacemakerInterface.getActivities(userId);
      Response<List<Activity>> response = call.execute();
      activities = response.body();
      activities.sort(new TypeComparator());
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return activities;
  }

  public List<Leaderboard> distanceLeaderBoard(String userId) {
    Collection<Activity> activities = null;
    List<Leaderboard> userDetailsWithActivities = new ArrayList<>();
    List<Leaderboard> sortedListByDistance = null;
    try {
      Collection<Friend> Friends = listFriends(userId);
      for (Friend Friend : Friends) {
        activities = getActivities(getUserByEmail(Friend.getFriendemail()).getId());
        for (Activity eachActivity : activities) {
          userDetailsWithActivities.add(new Leaderboard(
                  getUserByEmail(Friend.getUseremail()).getId(),
                  getUserByEmail(Friend.getUseremail()).getEmail(),
                  Friend.getId(),
                  Friend.getFriendemail(),
                  eachActivity.getId(),
                  eachActivity.getType(),
                  eachActivity.getLocation(),
                  eachActivity.getDistance()
          ));
        }
      }

      sortedListByDistance = userDetailsWithActivities.stream()
                    .sorted(Comparator.comparing(Leaderboard::getActivitydistance).reversed())
                    .collect(Collectors.toList());
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return sortedListByDistance;
  }

  public List<Leaderboard> distanceLeaderBoardByType(String userId, String type) {
    Collection<Activity> Extractedactivities = null;
    List<Leaderboard> userDetailsWithActivities = new ArrayList<>();
    List<Leaderboard> filteredResults = null;
    try {
      Collection<Friend> Friends = listFriends(userId);
      for (Friend Friend : Friends) {
        Extractedactivities = getActivities(getUserByEmail(Friend.getFriendemail()).getId());
        for (Activity eachActivity : Extractedactivities) {
          userDetailsWithActivities.add(new Leaderboard(
                  getUserByEmail(Friend.getUseremail()).getId(),
                  getUserByEmail(Friend.getUseremail()).getEmail(),
                  Friend.getId(),
                  Friend.getFriendemail(),
                  eachActivity.getId(),
                  eachActivity.getType(),
                  eachActivity.getLocation(),
                  eachActivity.getDistance()
          ));
        }
      }

      filteredResults = userDetailsWithActivities.stream()
                      .filter(each -> each.getActivitytype().equals(type))
                      .sorted(Comparator.comparing(Leaderboard::getActivitydistance).reversed())
                      .collect(Collectors.toList());
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return filteredResults;
  }

  public List<Leaderboard> distanceLeaderBoardByLocation(String userId, String location) {
    Collection<Activity> Extractedactivities = null;
    List<Leaderboard> userDetailsWithActivities = new ArrayList<>();
    List<Leaderboard> filteredResults = null;
    try {
      Collection<Friend> Friends = listFriends(userId);
      for (Friend Friend : Friends) {
        Extractedactivities = getActivities(getUserByEmail(Friend.getFriendemail()).getId());
        for (Activity eachActivity : Extractedactivities) {
          userDetailsWithActivities.add(new Leaderboard(
                  getUserByEmail(Friend.getUseremail()).getId(),
                  getUserByEmail(Friend.getUseremail()).getEmail(),
                  Friend.getId(),
                  Friend.getFriendemail(),
                  eachActivity.getId(),
                  eachActivity.getType(),
                  eachActivity.getLocation(),
                  eachActivity.getDistance()
          ));
        }
      }

      filteredResults = userDetailsWithActivities.stream()
                      .filter(each -> each.getActivitylocation().equals(location))
                      .sorted(Comparator.comparing(Leaderboard::getActivitydistance).reversed())
                      .collect(Collectors.toList());
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return filteredResults;
  }

  public void addLocation(String id, String activityId, double latitude, double longitude) {
    try {
      Call<Location> call = pacemakerInterface.addLocation(id, activityId, new Location(latitude, longitude));
      call.execute();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  public User getUserByEmail(String email) {
    Collection<User> users = getUsers();
    User foundUser = null;
    for (User user : users) {
      if (user.email.equals(email)) {
        foundUser = user;
      }
    }
    return foundUser;
  }

  public Friend findFriend(String id, String email){
    Collection<Friend> Friends = listFriends(id);
    Friend foundFriend = null;
    for (Friend Friend : Friends) {
      if (Friend.friendemail.equals(email)) {
        foundFriend = Friend;
      }
    }
    return foundFriend;
  }

  public User getUser(String id) {
    User user = null;
    try {
      Call<User> call = pacemakerInterface.getUser(id);
      Response<User> response = call.execute();
      user = response.body();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return user;
  }

  public void deleteUsers() {
    try {
      Call<User> call = pacemakerInterface.deleteUsers();
      call.execute();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  public User deleteUser(String id) {
    User user = null;
    try {
      Call<User> call = pacemakerInterface.deleteUser(id);
      Response<User> response = call.execute();
      user = response.body();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return user;
  }

  public void deleteActivities(String id) {
    try {
      Call<String> call = pacemakerInterface.deleteActivities(id);
      call.execute();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }


}

class TypeComparator implements Comparator<Activity> {
  public int compare(Activity a, Activity b) {
    return a.type.compareToIgnoreCase(b.type);
  }
}