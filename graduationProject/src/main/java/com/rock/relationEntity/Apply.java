package com.rock.relationEntity;

import com.rock.nodeEntity.Applicant;
import com.rock.nodeEntity.Person;
import lombok.Builder;
import lombok.Data;
import org.neo4j.ogm.annotation.*;

@Data
@Builder
@RelationshipEntity(type = "APPLY")
public class Apply {
    @Id
    @GeneratedValue
    private Long id;

    @StartNode
    private Person person;

    @EndNode
    private Applicant applicant;

}