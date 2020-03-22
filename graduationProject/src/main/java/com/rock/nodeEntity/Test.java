package com.rock.nodeEntity;

import lombok.Builder;
import lombok.Data;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
@Builder
@Data
public class Test {
    @Id
    private  String id;
    private String name;
}
