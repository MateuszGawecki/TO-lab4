package com.company.human;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
@AllArgsConstructor(staticName = "of")
public class HumanData {
    private Map<String, Double> distances;
    private Map<String, Integer> times;

    public HumanData getCopy(){
        return HumanData.of(new HashMap<>(distances), new HashMap<>(times));
    }
}
