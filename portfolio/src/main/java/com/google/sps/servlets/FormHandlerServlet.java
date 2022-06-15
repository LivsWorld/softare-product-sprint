package com.google.sps.servlets;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.FullEntity;
import com.google.cloud.datastore.KeyFactory;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;
import com.google.cloud.datastore.StructuredQuery.OrderBy;
import com.google.cloud.datastore.Entity;

@WebServlet("/form-handler")
public class FormHandlerServlet extends HttpServlet {

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

    // Get the values entered in the form.
    String name = request.getParameter("name-input");
    String email = request.getParameter("email-input");
    String textValue = request.getParameter("text-input");
    long timestamp = System.currentTimeMillis();

    // package contact info into "Contact" entity
    Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
    KeyFactory keyFactory = datastore.newKeyFactory().setKind("Contact");
    FullEntity contactEntity =
        Entity.newBuilder(keyFactory.newKey())
            .set("name", name)
            .set("email", email)
            .set("message", textValue)
            .set("timestamp", timestamp)
            .build();
    // store "Contact" in Datastore
    datastore.put(contactEntity);

    // load all contacts stored in Datastore, sorted by timestamp
    Query<Entity> query = 
          Query.newEntityQueryBuilder().setKind("Contact")
          .setOrderBy(OrderBy.desc("timestamp")).build();
    QueryResults<Entity> results = datastore.run(query);

    // Redirect user to home page
    response.sendRedirect("/");
  }
}