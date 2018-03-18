package io.neocdtv.jetty.base.boundary;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.neocdtv.jetty.base.Constants;
import io.neocdtv.jetty.base.control.JacksonObjectMapper;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.websocket.CloseReason;
import javax.websocket.EncodeException;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ExampleWebSocket.
 *
 * @author xix
 * @since 23.12.17
 */
@ApplicationScoped
@ServerEndpoint(value = "/" + Constants.PATH_BASE_EVENTS)
public class ExampleWebSocket {

  private static final Logger LOGGER = Logger.getLogger(ExampleWebSocket.class.getName());
  private static final Set<Session> SESSIONS = Collections.synchronizedSet(new HashSet<Session>());

  @OnOpen
  public void onOpen(final Session session, final EndpointConfig config) {
    LOGGER.log(Level.INFO, String.format("adding session '%s'", session.getId()));
    session.setMaxIdleTimeout(0);
    SESSIONS.add(session);
  }

  @OnClose
  public void onClose(final Session session, CloseReason closeReason) {
    LOGGER.log(Level.INFO, String.format("removing session '%s', reason '%s'", session.getId()), closeReason.getCloseCode());
    SESSIONS.remove(session);
  }

  @OnError
  public void onError(Session session, Throwable t) {
    LOGGER.log(Level.SEVERE, t.getMessage());
  }
  /**
   * Catches all CDI events and rethrow them over a WebSocket connection.
   * These are way to many events, that is why when you build your one app using jetty-base, you should
   * change Object to your desired parent type for you events. Also add @Observes before the the type, which
   * is currently removed, because some internal are not serializable to json.
   *
   * @param event
   * @throws EncodeException
   */
  public void handleStreamEndedEvent(Object event) throws EncodeException {
    LOGGER.log(Level.INFO, String.format("handling CDI event '%s' and rethrowing over WebSocket connection", event.getClass().getSimpleName()));
    for (final Session session : SESSIONS) {
      try {
        final String eventAsJson = toJson(event);
        LOGGER.info(String.format("sending message %s", eventAsJson));
        session.getBasicRemote().sendText(eventAsJson);
      } catch (IOException ex) {
        LOGGER.log(Level.SEVERE, ex.getMessage());
      }
    }
  }

  public String toJson(final Object object) throws JsonProcessingException {
    final ObjectMapper mapper = JacksonObjectMapper.getInstanceWithType();
    final String valueAsString = mapper.writeValueAsString(object);
    return valueAsString;
  }
}

