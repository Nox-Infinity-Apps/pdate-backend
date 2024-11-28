package com.noxinfinity.pdating.Applications.Match;

import com.noxinfinity.pdating.Domains.ConversationManagement.ConversationRepository;
import com.noxinfinity.pdating.Domains.ConversationManagement.ConversationService;
import com.noxinfinity.pdating.Domains.MatchManagement.UserLikeRepository;
import com.noxinfinity.pdating.Domains.MatchManagement.UserMatchRepository;
import com.noxinfinity.pdating.Domains.UserManagement.UserDataRepository;
import com.noxinfinity.pdating.Entities.*;
import com.noxinfinity.pdating.graphql.types.LikeUserResponse;
import com.noxinfinity.pdating.graphql.types.StatusEnum;
import com.noxinfinity.pdating.graphql.types.UnLikeUserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MatchServices implements IMatch{
    private final UserLikeRepository userLikeRepository;
    private final UserMatchRepository userMatchRepository;
    private final UserDataRepository userDataRepository;
    private final ConversationRepository conversationRepository;
    private final ConversationService conversationService;

    @Autowired
    public MatchServices(UserLikeRepository userLikeRepository,
                         UserDataRepository userDataRepository,
                         UserMatchRepository userMatchRepository,
                         ConversationRepository conversationRepository,
                         ConversationService conversationService) {
        this.userLikeRepository = userLikeRepository;
        this.userDataRepository = userDataRepository;
        this.userMatchRepository = userMatchRepository;
        this.conversationRepository = conversationRepository;
        this.conversationService = conversationService;
    }

    @Transactional
    @Override
    public LikeUserResponse like(String current, String target) {
        UserData currentUser = userDataRepository.findById(current).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        UserData targetUser = userDataRepository.findById(target).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        boolean isMutualLike = userLikeRepository.existsByCurrentUserIdAndTargetUserId(
                target, current
        );
        try {
            UserLikes userLike = new UserLikes();
            userLike.setCurrentUser(currentUser);
            userLike.setTargetUser(targetUser);
            userLikeRepository.save(userLike);
            LikeUserResponse.Builder response = LikeUserResponse.newBuilder().isMatched(isMutualLike ? 1 : 0).message("success").status(StatusEnum.SUCCESS);
            if (isMutualLike) {
                response.conversationId(this.match(currentUser, targetUser).getId());
            }
            return response.build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");
        }
    }

    @Override
    public UnLikeUserResponse unlike(String current, String target) {
        try {
            Optional<UserLikes> userLikes = userLikeRepository.findByCurrentUserIdAndTargetUserId(
                    current, target
            );
            if (userLikes.isPresent()) {
                userLikeRepository.delete(userLikes.get());
                return UnLikeUserResponse.newBuilder().message("success").status(StatusEnum.SUCCESS).build();
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found");
            }
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getMessage());
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");
        }
    }

    @Transactional
    protected Conversations match(UserData user1, UserData user2){
        try {
            UserMatched userMatched = new UserMatched();
            userMatched.setUser1(user1).setUser2(user2);
            userMatchRepository.save(userMatched);
            Conversations conversation = new Conversations();

            UserInConversation userInConversation1 = new UserInConversation();
            userInConversation1.setConversation(conversation).setUserData(user1);

            UserInConversation userInConversation2 = new UserInConversation();
            userInConversation2.setConversation(conversation).setUserData(user2);

            conversation.setUsersInConversation(List.of(userInConversation1, userInConversation2));
            ArrayList<String> usersInConversationList = new ArrayList<>();
            usersInConversationList.add(user1.getUserId());
            usersInConversationList.add(user2.getUserId());
            var result = conversationRepository.save(conversation);
            conversationService.createConversationForCouple(
                    result.getId().toString()
                    , usersInConversationList);
            return result;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");
        }
    }
}
