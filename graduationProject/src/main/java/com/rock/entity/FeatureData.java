package com.rock.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeatureData {
    private String fid;
    private int overdueCount;
    private int BFlagCount;
    private int PhoneBFCount;
    private int OneDimenRelationshipBFCount;
    private int OneDimenRelationshipPhoneBFCount;
    private int TwoDimenRelationshipBFCount;
    private int TwoDimenRelationshipPhoneBFCount;
    private int label;
}
