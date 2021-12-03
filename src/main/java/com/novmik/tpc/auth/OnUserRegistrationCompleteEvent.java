package com.novmik.tpc.auth;

import com.novmik.tpc.client.Client;
import org.springframework.context.ApplicationEvent;
import org.springframework.web.util.UriComponentsBuilder;

public class OnUserRegistrationCompleteEvent extends ApplicationEvent {

  private transient UriComponentsBuilder redirectUrl;
  private Client user;

  public OnUserRegistrationCompleteEvent(final Client user,
      final UriComponentsBuilder redirectUrl) {
    super(user);
    this.redirectUrl = redirectUrl;
    this.user = user;
  }
}
