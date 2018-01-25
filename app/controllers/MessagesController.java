package controllers;

import models.Message;
import models.User;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.books.create;
import views.html.books.message;

import javax.inject.Inject;

public class MessagesController extends Controller {

    @Inject
    FormFactory formFactory;

    //To save books
    public Result save(Integer receiverid){
        Form<Message> messageForm = formFactory.form(Message.class).bindFromRequest();
        if(messageForm.hasErrors()){
            flash("danger", "Please correct the form below" );
            return badRequest(message.render(messageForm, receiverid, Secured.isLoggedIn(ctx()), Secured.getUser(ctx())));
        }
        messageForm.get().authorid = Secured.getUserId(ctx());
        messageForm.get().receiverid = receiverid;
        messageForm.get().author = Secured.getUser(ctx());
        User user = User.find.byId(receiverid);
        messageForm.get().receivername = user.username;
        Message message = messageForm.get();
        message.save();
        flash("success", "Message Sent Successfully");
        return redirect(routes.BooksController.index());
    }

    //To delete books
    public Result destroy(Integer id){

        Message message = Message.find.byId(id);
        if ( message == null){
            flash("danger", "Book Not Found");
            return notFound();
        }
        message.delete();
        flash("info", "Message Deleted Successfully");
        return redirect(routes.UsersController.mailbox());
    }


}
