package com.rock.relationEntity;

import com.rock.nodeEntity.Applicant;
import com.rock.nodeEntity.Phone;
import lombok.Builder;
import lombok.Data;
import org.neo4j.ogm.annotation.*;

@Data
@Builder
@RelationshipEntity(type = "COLLEAGUE_PHONE")
public class ColleaguePhone {
    @Id
    @GeneratedValue
    private Long id;

    @StartNode
    private Applicant applicant;

    @EndNode
    private Phone phone;

}