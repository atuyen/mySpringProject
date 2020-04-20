package com.seasolutions.stock_management.model.support.enumerable;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum LanguageType {
    En("en"),
    Sv("sv");
    private String value;

}
