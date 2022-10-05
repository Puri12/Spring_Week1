package com.puri12.spring1.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

@Getter
public enum Category {

    UNKNOWN(0,"UNKNOWN"),
    CATEGORY1(1,"categoty1"),
    CATEGORY2(2,"categoty2"),
    CATEGORY3(3,"categoty3"),
    CATEGORY4(4,"categoty4"),
    CATEGORY5(5,"categoty5"),
    CATEGORY6(6,"categoty6"),
    CATEGORY7(7,"categoty7"),
    CATEGORY8(8,"categoty8"),
    CATEGORY9(9,"categoty9"),
    CATEGORY10(10,"categoty10"),
    CATEGORY11(11,"categoty11"),
    CATEGORY12(12,"categoty12"),
    CATEGORY13(13,"categoty13"),
    CATEGORY14(14,"categoty14"),
    CATEGORY15(15,"categoty15"),
    CATEGORY16(16,"categoty16"),
    CATEGORY17(17,"categoty17"),
    CATEGORY18(18,"categoty18"),
    CATEGORY19(19,"categoty19"),
    CATEGORY20(20,"categoty20");

    private int id;
    private String name;

    Category(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Category findById(int id) {
        return Arrays.stream(values())
                .filter(category -> category.id == id)
                .findAny()
                .orElse(UNKNOWN);
    }

    public static Category findByName(String name) {
        return Arrays.stream(values())
                .filter(category -> category.name.equals(name))
                .findAny()
                .orElse(UNKNOWN);
    }

}
