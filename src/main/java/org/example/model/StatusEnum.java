package org.example.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum StatusEnum {
    TODO(1), IN_PROGRESS(2), DONE(3);

    private int id;

    public static StatusEnum fromTaskId(int id) {
        for (StatusEnum status : values()) {
            if (id == status.id) return status;
        }
        throw new IllegalArgumentException();
    }

}