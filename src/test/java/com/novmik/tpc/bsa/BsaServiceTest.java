package com.novmik.tpc.bsa;

import com.novmik.tpc.exception.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.novmik.tpc.bsa.BsaConstant.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BsaServiceTest {

    private BsaService underTest;

    @BeforeEach
    void setUp() {
        underTest = new BsaService();
    }

    @Test
    void canGetMapWithAllBsaMethodsWhenTwoOptions() {
        BsaRequest bsaRequest = new BsaRequest(170,70.0);
        assertThat(underTest.allMethods(bsaRequest)).isNotEmpty();
        assertThat(underTest.allMethods(bsaRequest).size()).isGreaterThanOrEqualTo(10);
    }

    @Test
    void canGetMapWithAllBsaMethodsWhenHeightIsNull() {
        BsaRequest bsaRequest = new BsaRequest(null,70.0);
        assertThat(underTest.allMethods(bsaRequest)).isNotEmpty();
        assertThat(underTest.allMethods(bsaRequest).size()).isGreaterThanOrEqualTo(2);
    }

    @Test
    void canGetMapWithAllBsaMethodsWhenOneOptions() {
        BsaRequest bsaRequest = new BsaRequest(null,-70.0);
        assertThatThrownBy(() -> underTest.allMethods(bsaRequest))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining(BSA_NOT_CORRECT_1_OPTION);
    }

    @Test
    void canGetMapWithAllBsaMethodsWhenWeightIsZero() {
        BsaRequest bsaRequest = new BsaRequest(170,0.0);
        assertThatThrownBy(() -> underTest.allMethods(bsaRequest))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining(BSA_NOT_CORRECT_2_OPTIONS);
    }

    @Test
    void willThrowWhenGetMapWithAllBsaMethodsWithNullOptions() {
        BsaRequest bsaRequest = new BsaRequest();
        assertThatThrownBy(() -> underTest.allMethods(bsaRequest))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining(BSA_REQUEST_NOT_CORRECT);
    }

    @Test
    void willThrowWhenGetMapWithAllBsaMethodsWithNullWeight() {
        BsaRequest bsaRequest = new BsaRequest(170,null);
        assertThatThrownBy(() -> underTest.allMethods(bsaRequest))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining(BSA_REQUEST_NOT_CORRECT);
    }


}