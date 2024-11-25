package com.noxinfinity.pdating.Applications.User;

import com.noxinfinity.pdating.graphql.types.AddDatingTargetResponse;

public interface IUser {
    AddDatingTargetResponse addDatingTarget(String userId, int id);
}
