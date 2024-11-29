package com.noxinfinity.pdating.Applications.Grade;

import com.noxinfinity.pdating.Domains.GradeManagment.GradeRepository;
import com.noxinfinity.pdating.Domains.GradeManagment.MajorRepository;
import com.noxinfinity.pdating.graphql.types.Grade;
import com.noxinfinity.pdating.graphql.types.Major;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GradeService {
    private final GradeRepository repository;
    private final MajorRepository majorRepository;

    @Autowired
    public GradeService(GradeRepository repository, MajorRepository majorRepository) {
        this.repository = repository;
        this.majorRepository = majorRepository;
    }
    public List<Grade> findAll() {
        List<com.noxinfinity.pdating.Entities.Grades> grades = repository.findAll();
        List<Grade> result = new ArrayList<>();
        for (com.noxinfinity.pdating.Entities.Grades grade : grades) {
            result.add(Grade.newBuilder().id(Math.toIntExact(grade.getId())).name(grade.getName()).build());
        }
        return result;
    }
    public List<Major> findAllMajors() {
        List<com.noxinfinity.pdating.Entities.Majors> majors = majorRepository.findAll();
        List<Major> result = new ArrayList<>();
        for (com.noxinfinity.pdating.Entities.Majors major : majors) {
            result.add(Major.newBuilder().id(Math.toIntExact(major.getId())).name(major.getName()).iconUrl(major.getIconUrl()).build());
        }
        return result;
    }
}
