package com.noxinfinity.pdating.Applications.Hobbies;

import com.noxinfinity.pdating.Domains.UserDataManagement.UserHobbies.IHobbiesService;
import com.noxinfinity.pdating.graphql.types.Hobbies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HobbiesApp implements IHobbiesApp {

    private final IHobbiesService _hobbiesService;

    @Autowired
    public HobbiesApp(IHobbiesService hobbiesService) {
        _hobbiesService = hobbiesService;
    }
    @Override
    public List<Hobbies> getAllHobbies() {
        List<com.noxinfinity.pdating.Entities.Hobbies> hobbies = _hobbiesService.getAllHobbies();

        return hobbies.stream().map(hobby -> Hobbies.newBuilder()
                .id(hobby.getId().intValue())
                .title(hobby.getTitle())
                .iconUrl(hobby.getIconUrl())
                .build()
        ).toList();

    }
}
