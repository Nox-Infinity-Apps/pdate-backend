package com.noxinfinity.pdating.Domains.ConversationManagement;

import com.noxinfinity.pdating.ThirdServices.StreamChat;
import io.getstream.chat.java.exceptions.StreamException;
import io.getstream.chat.java.models.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ConversationService implements IConversationService {
    private final StreamChat streamChat;

    @Autowired
    public ConversationService(StreamChat streamChat) {
        this.streamChat = streamChat;
    }


    @Override
    public Channel.ChannelGetResponse createConversationForCouple(String conversationId, ArrayList<String> user) throws StreamException {
        return streamChat.createConversation(conversationId, user);
    }
}
