package com.google.sps.servlets;

import com.google.sps.data.Contact;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.FullEntity;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.KeyFactory;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;
import com.google.cloud.datastore.StructuredQuery.PropertyFilter;
import com.google.cloud.datastore.Entity;

@WebServlet("/form-handler")
public class FormHandlerServlet extends HttpServlet {

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

    // Get and sanitize the values entered in the form.
    String name = Jsoup.clean(request.getParameter("name-input"), Safelist.none());
    String email = Jsoup.clean(request.getParameter("email-input"), Safelist.none());
    String textValue = Jsoup.clean(request.getParameter("text-input"), Safelist.none());
    long timestamp = System.currentTimeMillis();

    // package contact info into "Contact" entity
    Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
    // KeyFactory to create unique IDs for entities
    KeyFactory keyFactory = datastore.newKeyFactory().setKind("Contact");
    Key contactKey = datastore.allocateId(keyFactory.newKey());
    FullEntity contactEntity = Entity.newBuilder(contactKey)
        .set("name", name)
        .set("email", email)
        .set("message", textValue)
        .set("timestamp", timestamp)
        .build();
    // store "Contact" in Datastore
    datastore.put(contactEntity);

    // load contact just stored in Datastore
    Query<Entity> query = Query.newEntityQueryBuilder().setKind("Contact")
        .setFilter(PropertyFilter.eq("__key__", contactKey)).build();
    QueryResults<Entity> results = datastore.run(query);

    // extract data from contact entity
    Entity entity = results.next();
    long entityId = entity.getKey().getId();
    String entityName = entity.getString("name");
    String entityEmail = entity.getString("email");
    String entityMessage = entity.getString("message");
    long entityTimestamp = entity.getLong("timestamp");
    Contact contact = new Contact(entityId, entityName, entityEmail, entityMessage, entityTimestamp);

    //Gson gson = new Gson();
    //response.setContentType("application/json;");
    //response.getWriter().println(gson.toJson(contact));

    response.sendRedirect("/thanks.html?name=" + 
    URLEncoder.encode(entityName, StandardCharsets.UTF_8));
  }
}