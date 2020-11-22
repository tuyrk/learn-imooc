package com.tuyrk.stream.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;

@Getter
@AllArgsConstructor
public class PageableResult<T> implements Serializable {
    private static final long serialVersionUID = 4204415271194238129L;
    private final long total;
    private final List<T> data;
}
