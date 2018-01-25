package models;

import io.ebean.*;
import play.data.validation.Constraints;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.Constraint;

@Entity
public class Message extends Model {

    @Id
    @GeneratedValue
    public Integer id;
    public String title;
    public String message;
    public Integer authorid;
    public Integer receiverid;
    public String author;
    public String receivername;


    public static Finder<Integer, Message> find = new Finder<>(Message.class);
}
