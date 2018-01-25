package models;




import io.ebean.Finder;
import io.ebean.Model;
import play.data.validation.*;

import javax.persistence.*;
import javax.validation.Constraint;

@Entity
public class User extends Model {

    @Id
    @GeneratedValue
    public Integer id;
    @Constraints.Required
    @Column(unique=true)
    public String username;
    @Constraints.Required
    public String password;
    @Column(unique=true)
    public String email;
    public String country;
    public String fthemes;
    public String pinfo;




    public static Finder<Integer, User> find = new Finder<>(User.class);
}
