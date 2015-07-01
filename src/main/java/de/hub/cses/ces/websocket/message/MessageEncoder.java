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
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.Logger;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import org.codehaus.jackson.map.ObjectMapper;

/**
 *
 * @author Sebastian Gross <sebastian.gross@hu-berlin.de>
 */
public class MessageEncoder implements Encoder.Text<WebSocketMessage> {

    private static final Logger logger = Logger.getLogger(MessageEncoder.class.getName());

    /**
     *
     * @param messageObject
     * @return
     * @throws EncodeException
     */
    @Override
    public String encode(WebSocketMessage messageObject) throws EncodeException {
        ObjectMapper mapper = new ObjectMapper();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            mapper.writeValue(stream, messageObject);
            return stream.toString("UTF-8");
        } catch (IOException ex) {
            throw new EncodeException(messageObject, "Object could not be encoded");
        }

    }

    /**
     *
     * @param ec
     */
    @Override
    public void init(EndpointConfig ec) {
        //logger.log(Level.INFO, "FeedbackEncoder - init method called");
    }

    /**
     *
     */
    @Override
    public void destroy() {
        //logger.log(Level.INFO, "FeedbackEncoder - destroy method called");
    }

}
