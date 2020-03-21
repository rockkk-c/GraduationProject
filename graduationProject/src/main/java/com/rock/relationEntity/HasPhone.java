package com.rock.relationEntity;

import com.rock.nodeEntity.Person;
import com.rock.nodeEntity.Phone;
import lombok.Builder;
import lombok.Data;
import org.neo4j.ogm.annotation.*;

@Data
@Builder
@RelationshipEntity(type = "HAS_PHONE")
public class HasPhone {
    @Id
    @GeneratedValue
    private Long id;

    @StartNode
    private Person person;

    @EndNode
    private Phone phone;

}
