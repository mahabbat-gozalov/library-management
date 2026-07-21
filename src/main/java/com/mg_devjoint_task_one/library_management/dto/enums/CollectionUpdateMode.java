package com.mg_devjoint_task_one.library_management.dto.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Mode for updating collections: ADD appends new items, REPLACE overwrites existing ones")
public enum CollectionUpdateMode {
    ADD,
    REPLACE
}
