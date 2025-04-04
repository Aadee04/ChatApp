package com.bezkoder.springjwt.payload.response;

/**
 * This class represents a generic response message,
 * typically used for sending success or error messages in API responses.
 */
public class MessageResponse {

  // The message to be sent in the response
  private String message;

  /**
   * Constructor to initialize the MessageResponse with a given message.
   *
   * @param message The message to be included in the response
   */
  public MessageResponse(String message) {
    this.message = message;
  }

  /**
   * Retrieves the response message.
   *
   * @return The message as a String
   */
  public String getMessage() {
    return message;
  }

  /**
   * Updates the response message.
   *
   * @param message The new message to set
   */
  public void setMessage(String message) {
    this.message = message;
  }
}
