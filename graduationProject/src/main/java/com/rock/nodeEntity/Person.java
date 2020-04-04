package com.rock.nodeEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity(label="Person")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person{
    @Id
    private String id;
    private String name;
    private String sex;
   // @Relationship(type = "HAS_PHONE", direction = Relationship.INCOMING)
   // private Phone phone;
    private String number;
    private String flag;

}
