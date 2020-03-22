package com.rock.mapper;

import com.rock.entity.FeatureData;
import org.springframework.stereotype.Repository;

@Repository
public interface FeatureDataMapper{
    int insertFeatureData(FeatureData featureData);
    FeatureData selectById(String applyId);

}
