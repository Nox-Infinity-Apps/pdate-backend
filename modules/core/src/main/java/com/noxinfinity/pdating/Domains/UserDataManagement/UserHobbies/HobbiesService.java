package com.noxinfinity.pdating.Domains.UserDataManagement.UserHobbies;

import com.noxinfinity.pdating.Entities.Hobbies;
import com.noxinfinity.pdating.Repository.Other.IHobbiesRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HobbiesService implements IHobbiesService {
    private final IHobbiesRespository _hobbiesRespository;

    @Autowired
    public HobbiesService(IHobbiesRespository hobbiesRespository) {
        _hobbiesRespository = hobbiesRespository;
    }
    @Override
    public List<Hobbies> getAllHobbies() {
        return _hobbiesRespository.findAll();
    }
}
