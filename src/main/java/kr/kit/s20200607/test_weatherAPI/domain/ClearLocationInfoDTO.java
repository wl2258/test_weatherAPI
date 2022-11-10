package kr.kit.s20200607.test_weatherAPI.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClearLocationInfoDTO {
    private String temp;
    private String location;
}
