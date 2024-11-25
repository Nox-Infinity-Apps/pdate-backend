package com.noxinfinity.pdating.Domains.ConversationManagement;

import io.getstream.chat.java.exceptions.StreamException;
import io.getstream.chat.java.models.Channel;

import java.util.ArrayList;

public interface IConversationService {
    Channel.ChannelGetResponse createConversationForCouple(String conversationId, ArrayList<String> user) throws StreamException;
}
