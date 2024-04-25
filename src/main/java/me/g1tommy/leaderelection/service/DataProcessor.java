package me.g1tommy.leaderelection.service;

import java.time.Duration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.g1tommy.leaderelection.context.ManagedContext;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataProcessor {

  private final ManagedContext context;

  public Flux<Long> getNumbersFlux() {
    if (!context.isLeader()) {
      log.debug("It's not a leader");
      return Flux.empty();
    }

    return Flux.interval(Duration.ofSeconds(1)).take(100_000);
  }
}
