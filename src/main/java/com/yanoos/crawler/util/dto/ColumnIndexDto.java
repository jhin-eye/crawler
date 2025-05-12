package com.yanoos.crawler.util.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ColumnIndexDto {
    private int noIndex;
    private int titleIndex;
    private int detailButtonIndex;
    private int writeDateIndex;
    private int departmentIndex;
    private int urlIndex;
}
