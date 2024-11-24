package com.noxinfinity.pdating.ThirdServices;
import io.getstream.chat.java.models.User;
import io.getstream.chat.java.services.framework.DefaultClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class StreamChat {
    @Autowired
    public StreamChat(String streamSecretKey, String streamKey){
        var properties = new Properties();
        properties.put(DefaultClient.API_KEY_PROP_NAME, streamKey);
        properties.put(DefaultClient.API_SECRET_PROP_NAME, streamSecretKey);
        var client = new DefaultClient(properties);
        DefaultClient.setInstance(client);
    }

    public String signToken(String userId){
        return User.createToken(userId, null, null);
    }
}
