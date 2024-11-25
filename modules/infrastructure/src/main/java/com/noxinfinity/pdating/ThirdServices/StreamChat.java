package com.noxinfinity.pdating.ThirdServices;
import io.getstream.chat.java.exceptions.StreamException;
import io.getstream.chat.java.models.Channel;
import io.getstream.chat.java.models.User;
import io.getstream.chat.java.services.framework.DefaultClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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

    public String signToken(String userId) throws Exception {
        var usersUpsertRequest = User.upsert();
        usersUpsertRequest.user(User.UserRequestObject.builder().id(userId).role("user").build());
        usersUpsertRequest.request();
        return User.createToken(userId, null, null);
    }

    public Channel.ChannelGetResponse createConversation(String conversationId, ArrayList<String> userIdList) throws StreamException {
        List<Channel.ChannelMemberRequestObject> memberList = new ArrayList<>();
        for (String userId : userIdList) {
            memberList.add(
                    Channel.ChannelMemberRequestObject.builder()
                            .user(User.UserRequestObject.builder().id(userId).build())
                            .build()
            );
        }
        return Channel.getOrCreate("messaging", conversationId)
                .data(
                        Channel.ChannelRequestObject.builder()
                                .members(memberList)
                                .build()
                )
                .request();
    }
}
