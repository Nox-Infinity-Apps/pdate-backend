package com.noxinfinity.pdating.Primary.ByeByeWorld;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;

@DgsComponent
public class ByebyeQuery {
    @DgsQuery
    public String byebyeWorld(boolean isByebye, String message){
        return "Bye bye "+message;
    }
}
