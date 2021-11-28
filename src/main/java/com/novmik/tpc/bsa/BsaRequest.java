package com.novmik.tpc.bsa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BsaRequest {

    private Integer height;
    private Double weight;

}
