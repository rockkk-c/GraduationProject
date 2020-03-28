package com.rock.nodeEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {
    @Id
    private String id;
    private String name;
    private String sex;
   // @Relationship(type = "HAS_PHONE", direction = Relationship.INCOMING)
   // private Phone phone;
    private String number;
    private String flag;

}
