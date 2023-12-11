package com.markswell.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class FollowerPerUserResposnse {

    private Integer countingFollowers;
    private List<FollowerResponse> followersResponse;

}
