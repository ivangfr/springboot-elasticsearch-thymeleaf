package com.ivanfranchin.productui.client.dto;

import java.util.List;

public record MyPage<T>(List<T> content, Integer totalElements, Integer totalPages, Integer size,
                        Integer numberOfElements, Boolean first, Boolean last) {
}
