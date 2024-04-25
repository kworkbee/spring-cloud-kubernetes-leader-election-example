package me.g1tommy.leaderelection.config;

import lombok.extern.slf4j.Slf4j;
import me.g1tommy.leaderelection.service.DataProcessor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextClosedEvent;
import reactor.core.Disposable;

@Slf4j
@Configuration
public class AppConfiguration {

  @Bean
  public Disposable dataProcessorDisposable(final DataProcessor dataProcessor) {
    return dataProcessor.getNumbersFlux().subscribe(item -> log.debug("Emitted {}", item));
  }

  @Bean
  public ApplicationListener<ContextClosedEvent> closedEventApplicationListener(
      final Disposable dataProcessorDisposable) {
    return event -> {
      log.debug("Stopping data processor");
      if (!dataProcessorDisposable.isDisposed()) {
        dataProcessorDisposable.dispose();
      }
    };
  }
}
