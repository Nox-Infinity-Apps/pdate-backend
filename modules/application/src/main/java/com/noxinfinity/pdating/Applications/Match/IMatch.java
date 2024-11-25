package com.noxinfinity.pdating.Applications.Match;



import com.noxinfinity.pdating.graphql.types.LikeUserResponse;
import com.noxinfinity.pdating.graphql.types.UnLikeUserResponse;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface IMatch {
    @Transactional
    LikeUserResponse like(String current, String target);

    UnLikeUserResponse unlike(String current, String target);
}
