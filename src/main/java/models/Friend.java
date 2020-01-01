package models;
import static com.google.common.base.MoreObjects.toStringHelper;
import java.io.Serializable;
import com.google.common.base.Objects;


public class Friend implements Serializable {

    public String id;
    public String useremail;
    public String friendemail;

    public Friend() {

    }

    public Friend(String useremail, String friendemail) {
        this.useremail = useremail;
        this.friendemail = friendemail;
    }

    public String getId() {
        return id;
    }

    public String getUseremail() {
        return useremail;
    }

    public String getFriendemail() {
        return friendemail;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof Friend) {
            final Friend other = (Friend) obj;
            return Objects.equal(useremail, other.useremail)
                    && Objects.equal(friendemail, other.friendemail);
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return toStringHelper(this).addValue(id)
                .addValue(useremail)
                .addValue(friendemail)
                .toString();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.id, this.useremail, this.friendemail);
    }
}
