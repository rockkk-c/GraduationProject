package com.rock.nodeEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Phone {
    @Id
    private String number;
    private String flag;
}
