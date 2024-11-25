package com.noxinfinity.pdating.Primary.DatingTarget;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.noxinfinity.pdating.Applications.DatingTarget.DatingTargetService;
import com.noxinfinity.pdating.GraphQL.Guard.ValidateToken;
import com.noxinfinity.pdating.graphql.types.DatingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@DgsComponent
public class DatingTargetMutation {
    private DatingTargetService datingTargetService;

    @Autowired
    public DatingTargetMutation(DatingTargetService datingTargetService) {
        this.datingTargetService = datingTargetService;
    }

    @DgsQuery
    @ValidateToken
    public List<DatingTarget> getListDatingTarget() {
        return this.datingTargetService.getListDatingTargets();
    }

}
