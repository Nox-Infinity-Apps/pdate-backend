package com.noxinfinity.pdating.Primary.Grade;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.noxinfinity.pdating.Applications.Base.BaseServices;
import com.noxinfinity.pdating.Applications.Grade.GradeService;
import com.noxinfinity.pdating.GraphQL.Guard.ValidateToken;
import com.noxinfinity.pdating.Primary.Base.Base;
import com.noxinfinity.pdating.graphql.types.Grade;
import com.noxinfinity.pdating.graphql.types.Major;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@DgsComponent
public class GradeMutation {
    private GradeService gradeService;

    @Autowired
    public GradeMutation(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    @DgsQuery
    public List<Grade> getListGrade(){
        return gradeService.findAll();
    }

    @DgsQuery
    public List<Major> getListMajor(){
        return gradeService.findAllMajors();
    }
}
