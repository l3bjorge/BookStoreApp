package controllers;

import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;

import static play.mvc.Controller.flash;

public class Secured extends Security.Authenticator{

    @Override
    public String getUsername(Http.Context ctx) {
        return ctx.session().get("username");
    }

    @Override
    public Result onUnauthorized(Http.Context context) {
        flash("danger", "Please Login or Register to access ");
        return redirect(routes.UsersController.logins());
    }

    public static String getUser(Http.Context ctx) {
        return ctx.session().get("username");
    }

    public static Integer getUserId(Http.Context ctx) {
        return Integer.parseInt(ctx.session().get("Id"));
    }

    public static boolean isLoggedIn(Http.Context ctx) {
        return (getUser(ctx) != null);
    }

    }

