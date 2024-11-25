package com.noxinfinity.pdating.Applications.DatingTarget;

import com.noxinfinity.pdating.Applications.Base.BaseServices;
import com.noxinfinity.pdating.Domains.DatingTargetManagement.DatingTargetRepository;
import com.noxinfinity.pdating.graphql.types.DatingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DatingTargetService {
    private final DatingTargetRepository datingTargetRepository;

    @Autowired
    public DatingTargetService(DatingTargetRepository datingTargetRepository) {
        this.datingTargetRepository = datingTargetRepository;
    }

    public List<DatingTarget> getListDatingTargets() {
        var list = datingTargetRepository.findAll();
        return BaseServices.mapDatingTargets(list);
    }
}
