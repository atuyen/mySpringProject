package com.seasolutions.stock_management.model.support;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class SortOptions {
    @Builder.Default
    private List<String> sorts = new ArrayList<>();
    @Builder.Default
    private List<String> orders = new ArrayList<>();
}
