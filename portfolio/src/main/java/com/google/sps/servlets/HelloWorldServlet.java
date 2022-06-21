package com.google.sps.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Handles requests sent to the /hello URL. Try running a server and navigating to /hello! */
@WebServlet("/hello")
public class HelloWorldServlet extends HttpServlet {
  /** List of random messages to send */
  private ArrayList<String> messageList = new ArrayList<>(
    Arrays.asList(
                  "Painting",
                  "Biking around Davis",
                  "My plants",
                  "Eating with friends",
                  "Walking in nature",
                  "Church community",
                  "Good conversations",
                  "Family"
    ));

  /** When /hello is GET requested, returns messageList to client as JSON string */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType("application/json;");
    response.getWriter().println(convertToJson(messageList));
  }

  /** Converts ArrayList of messages into JSON string with Gson library */
  private String convertToJson(ArrayList<String> messages) {
    Gson gson = new Gson();
    return gson.toJson(messages);
  }
}
