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
public class Applicant {
    @Id
    private String id;
    private String amount;
    private String term;
   //private Integer term;
    private String job;
    private String city;
    @Relationship(type = "PARENT_PHONE", direction = Relationship.INCOMING)
    private String parent_phone;
    @Relationship(type = "COLLEAGUE_PHONE", direction = Relationship.INCOMING)
    private String colleague_phone;
    @Relationship(type = "COMPANY_PHONE", direction = Relationship.INCOMING)
    private String company_phone;
    private String applicant;
    private String status;
}
