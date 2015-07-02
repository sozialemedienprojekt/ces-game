package de.hub.cses.ces.websocket.message;

/*
 * #%L
 * CES-Game
 * %%
 * Copyright (C) 2015 Humboldt-Universität zu Berlin,
 * Department of Computer Science,
 * Research Group "Computer Science Education / Computer Science and Society"
 * Sebastian Gross <sebastian.gross@hu-berlin.de>
 * Sven Strickroth <sven.strickroth@hu-berlin.de>
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * #L%
 */
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import org.codehaus.jackson.map.ObjectMapper;

/**
 *
 * @author sgro
 */
public class MessageDecoder implements Decoder.Text<WebSocketMessage> {

    private static final Logger logger = Logger.getLogger(MessageDecoder.class.getName());

    /**
     *
     * @param jsonMessage
     * @return
     * @throws DecodeException
     */
    @Override
    public WebSocketMessage decode(String jsonMessage) throws DecodeException {
        logger.log(Level.INFO, "incoming message: {0}", jsonMessage);
        ObjectMapper mapper = new ObjectMapper();
        try {
            InputStream stream = new ByteArrayInputStream(jsonMessage.getBytes("UTF-8"));
            WebSocketMessage webSocketMessage = mapper.readValue(stream, WebSocketMessage.class);
            return webSocketMessage;
        } catch (IOException ex) {
            logger.log(Level.INFO, null, ex);
            throw new DecodeException(jsonMessage, "JsonMessage could not be decoded");
        }
    }

    /**
     *
     * @param jsonMessage
     * @return
     */
    @Override
    public boolean willDecode(String jsonMessage) {
        try {
            // Check if incoming message is valid JSON
            Json.createReader(new StringReader(jsonMessage)).readObject();
            return true;
        } catch (Exception e) {
            logger.log(Level.WARNING, null, e);
            return false;
        }
    }

    /**
     *
     * @param ec
     */
    @Override
    public void init(EndpointConfig ec) {
        logger.log(Level.INFO, "FeedbackDecoder - init method called");
    }

    /**
     *
     */
    @Override
    public void destroy() {
        logger.log(Level.INFO, "FeedbackDecoder - destroy method called");
    }

}
