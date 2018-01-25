package models;


import io.ebean.*;
import play.data.validation.Constraints;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.Constraint;

@Entity
public class Book extends Model {

    @Id
    @GeneratedValue
    public Integer id;
    @Constraints.Required
    public String title;
    @Constraints.Required
    public Integer price;
    @Constraints.Required
    public String author;
    public String ownername;
    @OneToOne
    public Integer userid;
    public String description = "Please fill me up in the edit menu";
    public Boolean availability = true;

    public static Finder<Integer, Book> find = new Finder<>(Book.class);
}
