package models;

import com.google.common.base.Objects;

import java.io.Serializable;

import static com.google.common.base.MoreObjects.toStringHelper;


public class Leaderboard implements Serializable {

    public String id;
    public String userid;
    public String useremail;
    public String friendid;
    public String friendemail;
    public String activityid;
    public String activitytype;
    public String activitylocation;
    public Double activitydistance;

    public Leaderboard() {

    }

    public Leaderboard(String userid, String useremail, String friendid, String friendemail, String activityid,
                       String activitytype, String activitylocation, Double activitydistance ) {
        this.userid = userid;
        this.useremail = useremail;
        this.friendid = friendid;
        this.friendemail = friendemail;
        this.activityid = activityid;
        this.activitytype = activitytype;
        this.activitylocation = activitylocation;
        this.activitydistance = activitydistance;
    }

    public String getUserid() {
        return userid;
    }
    public String getUseremail() {
        return useremail;
    }
    public String getFriendid() {
        return friendid;
    }
    public String getFriendemail() {
        return friendemail;
    }
    public String getActivityid() {
        return activityid;
    }
    public String getActivitytype() {
        return activitytype;
    }
    public String getActivitylocation() {
        return activitylocation;
    }
    public Double getActivitydistance() {
        return activitydistance;
    }


    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof Leaderboard) {
            final Leaderboard other = (Leaderboard) obj;
            return Objects.equal(userid, other.userid)
                    && Objects.equal(useremail, other.useremail)
                    && Objects.equal(friendid, other.friendid)
                    && Objects.equal(friendemail, other.friendemail)
                    && Objects.equal(activityid, other.activityid)
                    && Objects.equal(activitytype, other.activitytype)
                    && Objects.equal(activitylocation, other.activitylocation)
                    && Objects.equal(activitydistance, other.activitydistance);
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return toStringHelper(this)
                .addValue(userid)
                .addValue(useremail)
                .addValue(friendid)
                .addValue(friendemail)
                .addValue(activityid)
                .addValue(activitytype)
                .addValue(activitylocation)
                .addValue(activitydistance)
                .toString();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.userid, this.useremail, this.friendid
                , this.friendemail, this.activityid, this.activitytype,
                this.activitylocation, this.activitydistance);
    }
}
