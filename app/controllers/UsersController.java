package controllers;

import io.ebean.Ebean;
import models.Message;
import models.User;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.users.*;

import javax.inject.Inject;
import java.util.List;

public class UsersController extends Controller {

    @Inject
    FormFactory formFactory;

    //register
    public  Result register() {
        Form<User> userForm = formFactory.form(User.class);
        return ok(register.render(userForm));
    }

    //To save user
    public Result save(){
        Form<User> userForm = formFactory.form(User.class).bindFromRequest();

        if(userForm.hasErrors()){
            flash("danger", "Please correct the form below");
            return badRequest(register.render(userForm));
        }
        User user = userForm.get();

        try {
            user.save();
        }catch(io.ebean.DataIntegrityException e){
            flash("danger", "Username or email already exists");
            return badRequest(register.render(userForm));
        }

        flash("success", "Registered Successfully");
        return redirect(routes.BooksController.index());
    }

    //Go to login screen
    public Result logins(){
        Form<User> userForm = formFactory.form(User.class);
        return ok(login.render(userForm));
    }

    //To login user
    public Result login(){
        Form<User> userForm = formFactory.form(User.class).bindFromRequest();

        if(userForm.hasErrors()){
            flash("danger", "Please correct the form below");
            return badRequest(login.render(userForm));
        }


        User userlogin = userForm.get();
        List<User> users = Ebean.find(User.class)
                .where()
                .eq("username", userlogin.username )
                .findList();

        if (users == null){
            return notFound("There is no user with that name");
        }

        User user = users.get(0);

        if(user.password.equals(userlogin.password)){
            session().clear();
            session("username", userForm.get().username);
            session("Id", "" + user.id);
            flash("success", "You logged in successfully");
            return redirect(routes.BooksController.index());
        }
        flash("danger", "Incorrect user or password");
        return redirect(routes.UsersController.logins());
    }

    public Result updateuser(){
        Form<User> userForm = formFactory.form(User.class).bindFromRequest();

        if(userForm.hasErrors()){
            flash("danger", "Please correct the form below");
            return badRequest(profile.render(userForm, Secured.isLoggedIn(ctx()), Secured.getUser(ctx())));
        }
        User user = userForm.get();
        User oldUser = User.find.byId(Secured.getUserId(ctx()));
        oldUser.username = user.username;
        oldUser.password = user.password;
        oldUser.email = user.email;
        oldUser.country = user.country;
        oldUser.fthemes = user.fthemes;
        oldUser.pinfo = user.pinfo;
        oldUser.update();
        flash("success", "Profile Updated Successfully");
        return redirect(routes.UsersController.profile());
    }

    @Security.Authenticated(Secured.class)
    public Result logout() {
        session().clear();
        return redirect(routes.UsersController.logins());
    }

    @Security.Authenticated(Secured.class)
    public Result profile() {
        User user = User.find.byId(Secured.getUserId(ctx()));
        Form<User> userForm = formFactory.form(User.class).fill(user);
        return ok(profile.render(userForm, Secured.isLoggedIn(ctx()), Secured.getUser(ctx())));
    }

    @Security.Authenticated(Secured.class)
    public Result mailbox() {
        List<Message> mailbox = Ebean.find(Message.class)
                .where()
                .eq("receiverid", Secured.getUserId(ctx()) )
                .findList();
        return ok(messages.render(mailbox, Secured.isLoggedIn(ctx()), Secured.getUser(ctx())));
    }

}
