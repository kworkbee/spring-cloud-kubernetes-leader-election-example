package me.g1tommy.leaderelection.context;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.integration.leader.Context;
import org.springframework.integration.leader.event.OnGrantedEvent;
import org.springframework.integration.leader.event.OnRevokedEvent;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ManagedContext {

  private Context context;

  @EventListener(OnGrantedEvent.class)
  void onGranted(final OnGrantedEvent event) {
    log.debug("Leadership {} granted", event.getRole());

    this.context = event.getContext();
  }

  @EventListener(OnRevokedEvent.class)
  void onRevoked(final OnRevokedEvent event) {
    log.debug("Leadership {} revoked", event.getRole());

    this.context = null;
  }

  public boolean isLeader() {
    return context != null && context.isLeader();
  }
}
