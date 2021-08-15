package net.osgg.starter;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public class RestVerticle extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
	  Router router = Router.router(vertx);
      router.get("/api/v1/convert/:value").handler(this::getFahrenheit);
      router.get("/api/v1/greeting/:name").handler(this::getName);
      
	    vertx.createHttpServer()
	      .requestHandler(router)
	      .listen(8888)
	      .onSuccess(server ->
	        System.out.println(
	          "HTTP server started on port " + server.actualPort()
	        )
	      );
	  }
  
  
  private void getFahrenheit(RoutingContext routingContext) {
      String value = routingContext.request().getParam("value");
      float celsius = Float.valueOf(value);
      float f =  (celsius * 9 / 5) + 32;
      Result r = new Result(String.valueOf(f));
      routingContext.response()
              .putHeader("content-type", "application/json")
              .setStatusCode(200)
              .end(Json.encodePrettily(r));
  }
  
  
  private void getName(RoutingContext routingContext) {
      String value = routingContext.request().getParam("name");
      Result r = new Result("Hello "+value+"!");
      routingContext.response()
              .putHeader("content-type", "application/json")
              .setStatusCode(200)
              .end(Json.encodePrettily(r));
  }
  
}

