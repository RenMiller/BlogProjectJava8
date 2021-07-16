package models;

import play.data.validation.Email;
import play.data.validation.Required;
import play.modules.morphia.Model;

import org.mongodb.morphia.annotations.Entity;

import java.util.List;

@Entity
public class User extends Model {

    @Email
    @Required
    public String email;

    @Required
    public String password;

    public String fullname;

    public boolean isAdmin;

    public User(String email, String password, String fullname) {
        this.email = email;
        this.password = password;
        this.fullname = fullname;
    }

    public static User connect(String email, String password) {
        MorphiaQuery query = User.q();
        query.field("email").equal(email);
        query.field("password").equal(password);
        return query.first();

    }

    public String toString() {
        return email;
    }

}