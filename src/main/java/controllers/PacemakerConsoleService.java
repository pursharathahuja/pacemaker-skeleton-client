package controllers;

import com.google.common.base.Optional;

import asg.cliche.Command;
import asg.cliche.Param;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import models.*;
import parsers.AsciiTableParser;
import parsers.Parser;

public class PacemakerConsoleService {

	//private PacemakerAPI paceApi = new PacemakerAPI("https://secure-depths-94899.herokuapp.com/");
	private PacemakerAPI paceApi = new PacemakerAPI("http://localhost:7000/");

	private Parser console = new AsciiTableParser();
	private User loggedInUser = null;

	public PacemakerConsoleService() {
	}

	// Starter Commands
	@Command(description = "Register: Create an account for a new user")
	public void registerUser(@Param(name = "first name") String firstName, @Param(name = "last name") String lastName, @Param(name = "email") String email, @Param(name = "password") String password) {
		Optional<User> checkEmail = Optional.fromNullable(paceApi.getUserByEmail(email));
		if (checkEmail.isPresent()) {
			console.println("User registration failed! Email already registered.");
		}else {
			console.renderUser(paceApi.createUser(firstName, lastName, email, password));
		}
	}

	@Command(description = "List Users: List all users emails, first and last names")
	public void getUsers() {
		console.renderUsers(paceApi.getUsers());

	}

	@Command(description = "Login: Log in a registered user in to pacemaker")
	public void loginUser(@Param(name = "email") String email, @Param(name = "password") String password) {
		Optional<User> user = Optional.fromNullable(paceApi.getUserByEmail(email));
		if (user.isPresent()) {
			if (user.get().password.equals(password)) {
				loggedInUser = user.get();
				console.println("Logged in " + loggedInUser.email);
				console.println("ok");
			} else {
				console.println("Error on login");
			}
		}
	}

	@Command(description = "Logout: Logout current user")
	public void logout() {
		console.println("Logging out " + loggedInUser.email);
		console.println("ok");
		loggedInUser = null;
	}

	@Command(description = "Add activity: create and add an activity for the logged in user")
	public void addActivity(@Param(name = "type") String type, @Param(name = "location") String location, @Param(name = "distance") double distance) {
		Optional<User> user = Optional.fromNullable(loggedInUser);
		if (user.isPresent()) {
			console.renderActivity(paceApi.createActivity(user.get().id, type, location, distance));
		}else{
			console.println("User must be logged in to add an Activity!");
		}
	}

	@Command(description = "List Activities: List all activities for logged in user")
	public void listActivities() {
		Optional<User> user = Optional.fromNullable(loggedInUser);
		if (user.isPresent()) {
			if(paceApi.getActivities(user.get().id).isEmpty()){
				console.println("Logged in user doesn't have any associated activities! ");
			}else {
				console.renderActivities(paceApi.getActivities(user.get().id));
			}
		}else{
			console.println("User must be logged in to list its Activities! ");
		}
	}

	// Baseline Commands

	@Command(description = "Add location: Append location to an activity")
	public void addLocation(@Param(name = "activity-id") String id, @Param(name = "longitude") double longitude, @Param(name = "latitude") double latitude) {
		Optional<Activity> activity = Optional.fromNullable(paceApi.getActivity(loggedInUser.getId(), id));
		if (activity.isPresent()) {
			paceApi.addLocation(loggedInUser.getId(), activity.get().id, latitude, longitude);
			console.println("Location added to Activity having id = '"+id+"'");
		} else {
			console.println("Activity with id " +id+"  not found");
		}
	}

	@Command(description = "List all locations for a specific activity")
	public void listActivityLocations(@Param(name = "activity-id") String id) {
		Optional<Activity> activity = Optional.fromNullable(paceApi.getActivity(loggedInUser.getId(), id));
		if (activity.isPresent()) {
			console.renderLocations((List<Location>) paceApi.listActivityLocations(id));
		}else{
			console.println("Activity with id " +id+"  not found");
		}
	}

	@Command(description = "ActivityReport: List all activities for logged in user, sorted alphabetically by type")
	public void activityReport() {
		Optional<User> user = Optional.fromNullable(loggedInUser);
		if (user.isPresent()) {
			paceApi.listActivities(user.get().id, "type");
			console.renderActivities(paceApi.listActivities(user.get().id, "type"));
		}
	}

	@Command(description = "Activity Report: List all activities for logged in user by type. Sorted longest to shortest distance")
	public void activityReport(@Param(name = "byType: type") String type) {
		Optional<User> user = Optional.fromNullable(loggedInUser);
		if (user.isPresent()) {
			List<Activity> reportActivities = new ArrayList<>();
			Collection<Activity> usersActivities = paceApi.getActivities(user.get().id);
			usersActivities.forEach(a -> {
				if (a.type.equals(type))
					reportActivities.add(a);
			});
			reportActivities.sort((a1, a2) -> {
				if (a1.distance >= a2.distance)
					return -1;
				else
					return 1;
			});
			console.renderActivities(reportActivities);
		}
	}

	@Command(description = "Follow Friend: Follow a specific friend")
	public void follow(@Param(name = "email") String email) {
		Optional<User> user = Optional.fromNullable(loggedInUser);
		if (user.isPresent()) {
			Optional<User> userToFollow = Optional.fromNullable(paceApi.getUserByEmail(email));
			if (userToFollow.isPresent()) {
				if (user.get().email.equals(userToFollow.get().email)){
					console.println("Logged in user can't follow himself!");
				}else {
					console.renderFriend(paceApi.followFriend(user.get().id, user.get().email, email));
					console.println("User " + email +" followed successfully!");
				}
			} else {
				console.println("User " + email + " Does not exist!");
			}
		}else{
			console.println("User must be logged in to follow a friend!");
		}
	}

	@Command(description = "List Friends: List all of the friends of the logged in user")
	public void listFriends() {
        Optional<User> user = Optional.fromNullable(loggedInUser);
        if (user.isPresent()) {
            if(paceApi.listFriends(user.get().id).isEmpty()){
                console.println("Logged in user doesn't have any associated friends! ");
            }else {
                console.renderFriends(paceApi.listFriends(user.get().id));
            }
        }else{
            console.println("User must be logged in to list its Activities! ");
        }

	}

	@Command(description = "Friend Activity Report: List all activities of specific friend, sorted alphabetically by type)")
	public void friendActivityReport(@Param(name = "email") String email) {
        Optional<User> user = Optional.fromNullable(loggedInUser);
        if (user.isPresent()) {
            if(paceApi.listFriends(user.get().id).isEmpty()){
                console.println("Logged in user doesn't have any associated friends! ");
            }else {
                Optional<Friend> ExtractedFriend = Optional.fromNullable(paceApi.findFriend(user.get().id, email));
                if (ExtractedFriend.isPresent()) {
                    User ExtractedFriendDetails = paceApi.getUserByEmail(ExtractedFriend.get().friendemail);
                    if (user.isPresent()) {
                        console.renderActivities(paceApi.listActivities(ExtractedFriendDetails.id, "type"));
                    }else{
                        console.println("Friend with email "+ ExtractedFriend.get().friendemail + " not found! ");
                    }
                }else {
                    console.println("User with email '"+ email + "' not found in friendList! ");
                }
            }
        }else{
            console.println("User must be logged in to see friendActivityReport! ");
        }
	}

	// Good Commands

	@Command(description = "Unfollow Friend: Stop following a friend")
    public void unfollowFriend(@Param(name = "email") String email) {
        Optional<User> currentUser = Optional.fromNullable(loggedInUser);
        if (currentUser.isPresent()) {
            String removedFriend = paceApi.unfollowFriend(currentUser.get().id, email);
            System.out.println("User "+ removedFriend + " unfollowed sucessfully!");
        }else{
            console.println("User must be logged in to Unfollow a friend! ");
        }
    }

    @Command(description = "Unfollow Friend: Stop following all friends")
    public void unfollowFriend() {
        Optional<User> currentUser = Optional.fromNullable(loggedInUser);
        if (currentUser.isPresent()) {
            paceApi.unfollowFriend(currentUser.get().id);
            System.out.println("All Users unfollowed successfully!");
        }else{
            console.println("User must be logged in to Unfollow a friend! ");
        }
    }

	@Command(description = "Message Friend: send a message to a friend")
	public void messageFriend(@Param(name = "email") String email, @Param(name = "message") String message) {
        Optional<User> currentUser = Optional.fromNullable(loggedInUser);
        AtomicReference<Friend> selecteFriend = new AtomicReference<>();
        if (currentUser.isPresent()) {
            paceApi.listFriends(currentUser.get().id).forEach(friend -> {
                if(friend.friendemail.equals(email)){
                    selecteFriend.set(friend);
                }
            });
            if(!selecteFriend.toString().equals("null")){
                console.renderMessage(paceApi.messageFriend(currentUser.get().getId(), currentUser.get().email, email, message));
            }else{
                System.out.println("friend with this email not found");
            }
        }else{
            console.println("User must be logged in to Unfollow a friend! ");
        }
	}

	@Command(description = "List Messages: List all messages for the logged in user")
	public void listMessages() {
        Optional<User> currentUser = Optional.fromNullable(loggedInUser);
        if (currentUser.isPresent()) {
            console.renderMessages(paceApi.listMessages(currentUser.get().getId()));
        }else{
            console.println("User must be logged in to see messages!");
        }
	}

	@Command(description = "Distance Leader Board: list summary distances of all friends, sorted longest to shortest")
    public void distanceLeaderBoard() {
        Optional<User> currentUser = Optional.fromNullable(loggedInUser);
        if (currentUser.isPresent()) {
            console.renderLeaderboard(paceApi.distanceLeaderBoard(currentUser.get().getId()));
        }else{
            console.println("User must be logged in to see messages!");
        }
	}

	// Excellent Commands

	@Command(description = "Distance Leader Board: distance leader board refined by type")
	public void distanceLeaderBoardByType(@Param(name = "byType: type") String type) {
        Optional<User> currentUser = Optional.fromNullable(loggedInUser);
        if (currentUser.isPresent()) {
            console.renderLeaderboard(paceApi.distanceLeaderBoardByType(currentUser.get().getId(), type));
        }else{
            console.println("User must be logged in to see messages!");
        }
	}

	@Command(description = "Message All Friends: send a message to all friends")
	public void messageAllFriends(@Param(name = "message") String message) {
        Optional<User> currentUser = Optional.fromNullable(loggedInUser);
        if (currentUser.isPresent()) {
            paceApi.listFriends(currentUser.get().getId()).forEach(friend -> {
                paceApi.messageFriend(currentUser.get().getId(),
                        currentUser.get().getEmail(), friend.friendemail, message);
            });
            console.println("Message sent to all friends!");
        }else{
            console.println("User must be logged in to see messages!");
        }
	}

	@Command(description = "Location Leader Board: list sorted summary distances of all friends in named location")
	public void locationLeaderBoard(@Param(name = "location") String location) {
        Optional<User> currentUser = Optional.fromNullable(loggedInUser);
        if (currentUser.isPresent()) {
            console.renderLeaderboard(paceApi.distanceLeaderBoardByLocation(currentUser.get().getId(), location));
        }else{
            console.println("User must be logged in to see messages!");
        }

	}

	// Outstanding Commands

	// Todo
}