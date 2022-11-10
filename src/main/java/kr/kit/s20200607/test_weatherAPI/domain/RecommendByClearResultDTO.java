package kr.kit.s20200607.test_weatherAPI.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RecommendByClearResultDTO {
    private String location;
    private String temp;
    private String travelTitle;
    private String url;
}
