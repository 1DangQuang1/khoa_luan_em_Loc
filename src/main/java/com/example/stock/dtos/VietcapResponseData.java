package com.example.stock.dtos;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class VietcapResponseData {
    private String co;
    private String s;
    private BigDecimal cei;
    private BigDecimal flo;
    private BigDecimal ref;
    private BigDecimal c;
    private BigDecimal mv;
    private BigDecimal h;
    private BigDecimal l;
    private BigDecimal frbv;
    private BigDecimal frsv;
    private BigDecimal frcrr;
    private BigDecimal vo;
    private BigDecimal va;
    private BigDecimal op;
    private BigDecimal avgp;
    private String trsttc;
    private String trsttg;
    private String orgn;
    private String enorgn;
    private String bp1;
    private BigDecimal bv1;
    private BigDecimal bp2;
    private BigDecimal bv2;
    private BigDecimal bp3;
    private BigDecimal bv3;
    private BigDecimal ap1;
    private BigDecimal av1;
    private BigDecimal ap2;
    private BigDecimal av2;
    private BigDecimal ap3;
    private BigDecimal av3;
    private BigDecimal ptv;
    private BigDecimal pta;
    private String st;
    private String bo;

}