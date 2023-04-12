package com.wecheck.common.page;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public class Pagination {
    private Integer startRow;
    private Integer endRow;
    private Integer perPage;
    private Integer totalCount;
}
