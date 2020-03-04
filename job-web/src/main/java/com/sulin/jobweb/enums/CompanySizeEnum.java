package com.sulin.jobweb.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author sulin
 * @time 2020-03-04
 */
@Getter
@AllArgsConstructor
public enum CompanySizeEnum {
    ONE("0-50", "one"),
    TWO("50-150", "two"),
    THREE("150-500", "three"),
    FOUR("500-1000", "four"),
    FIVE("1000-5000", "five"),
    SIX("5000-10000", "six"),
    SEVEN("10000-0", "seven");


    private String companySize;
    private String level;

    public static CompanySizeEnum get(String companySize) {
        for (CompanySizeEnum companySizeEnum : CompanySizeEnum.values()) {
            if (companySizeEnum.companySize.equals(companySize)) {
                return companySizeEnum;
            }
        }
        return null;
    }
}
