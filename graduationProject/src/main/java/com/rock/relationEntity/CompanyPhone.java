package com.rock.relationEntity;

import com.rock.nodeEntity.Applicant;
import com.rock.nodeEntity.Phone;
import lombok.Builder;
import lombok.Data;
import org.neo4j.ogm.annotation.*;

@Data
@Builder
@RelationshipEntity(type = "COMPANY_PHONE")
public class CompanyPhone {
    @Id
    @GeneratedValue
    private Long id;

    @StartNode
    private Applicant applicant;

    @EndNode
    private Phone phone;

}
