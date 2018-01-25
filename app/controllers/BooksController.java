package controllers;

import models.Book;
import models.Message;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.books.*;


import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class BooksController extends Controller{

    @Inject
    FormFactory formFactory;

    //Showing all books to user
    public Result index(){
        List<Book> books = Book.find.all();
        Form<Message> bookForm = formFactory.form(Message.class);
        return ok(index.render(bookForm, books , Secured.isLoggedIn(ctx()), Secured.getUser(ctx())));
    }

    //Showing all books to user
    public Result titlesearch(){
        List<Book> books = Book.find.all();
        List<Book> filteredbooks = new ArrayList<Book>();
        Form<Message> bookForm = formFactory.form(Message.class).bindFromRequest();
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).title.equals(bookForm.get().title)){
                filteredbooks.add(books.get(i));
            }
        }
        return ok(index.render(bookForm, filteredbooks , Secured.isLoggedIn(ctx()), Secured.getUser(ctx())));
    }

    //Showing all books to user
    public Result authorsearch(){
        List<Book> books = Book.find.all();
        List<Book> newbooks = new ArrayList<Book>();
        Form<Message> bookForm = formFactory.form(Message.class).bindFromRequest();
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).author.equals(bookForm.get().author)){
                newbooks.add(books.get(i));
            }
        }
        return ok(index.render(bookForm, newbooks , Secured.isLoggedIn(ctx()), Secured.getUser(ctx())));
    }

    //Showing all books from user
    public Result userindex(){
        List<Book> books = Book.find.all();
        Form<Message> bookForm = formFactory.form(Message.class);
        return ok(userindex.render(bookForm, books , Secured.isLoggedIn(ctx()), Secured.getUserId(ctx()), Secured.getUser(ctx())));
    }

    //Go to message screen
    public Result messages(Integer ownerId){
        Form<Message> messageForm = formFactory.form(Message.class);
        return ok(message.render(messageForm, ownerId , Secured.isLoggedIn(ctx()), Secured.getUser(ctx())));
    }

    //To create books
    @Security.Authenticated(Secured.class)
    public Result create(){
        Form<Book> bookForm = formFactory.form(Book.class);
        return ok(create.render(bookForm, Secured.isLoggedIn(ctx()), Secured.getUser(ctx())));
    }

    //To save books
    public Result save(){
        Form<Book> bookForm = formFactory.form(Book.class).bindFromRequest();
        if(bookForm.hasErrors()){
            flash("danger", "Please correct the form below");
            return badRequest(create.render(bookForm, Secured.isLoggedIn(ctx()), Secured.getUser(ctx())));
        }
        bookForm.get().userid = Secured.getUserId(ctx());
        Book book = bookForm.get();
        book.ownername = Secured.getUser(ctx());
        book.save();
        flash("success", "Book Saved Successfully");
        return redirect(routes.BooksController.userindex());
    }

    //To edit books
    public Result edit(Integer id){
        Book book = Book.find.byId(id);
        if (book == null){
            return notFound("Book Not Found");
        }
        Form<Book> bookForm = formFactory.form(Book.class).fill(book);
        return ok(edit.render(bookForm, Secured.isLoggedIn(ctx()), id, Secured.getUser(ctx())));
    }

    //To update books
    public Result update(Integer id){
        Form<Book> bookForm = formFactory.form(Book.class).bindFromRequest();

        if(bookForm.hasErrors()){
            flash("danger", "Please correct the form below");
            return badRequest(edit.render(bookForm, Secured.isLoggedIn(ctx()), id, Secured.getUser(ctx())));
        }
        Book book = bookForm.get();
        Book oldBook = Book.find.byId(id);
        if (oldBook == null){
            return notFound("Book Not Found");
        }
        oldBook.title = book.title;
        oldBook.price = book.price;
        oldBook.author = book.author;
        oldBook.availability = book.availability;
        oldBook.description = book.description;
        oldBook.update();
        flash("success", "Book Updated Successfully");
        return redirect(routes.BooksController.userindex());
    }

    //To delete books
    public Result destroy(Integer id){

        Book book = Book.find.byId(id);
        if ( book == null){
            flash("danger", "Book Not Found");
            return notFound();
        }
        book.delete();
        flash("info", "Book Deleted Successfully");
        return redirect(routes.BooksController.index());
    }

    //for book details
    @Security.Authenticated(Secured.class)
    public Result show(Integer id){
        Book book = Book.find.byId(id);
        if (book == null){
            return notFound("Book Not Found");
        }
        return ok(show.render(book, Secured.isLoggedIn(ctx()), Secured.getUser(ctx()), Secured.getUserId(ctx())));
    }
}
