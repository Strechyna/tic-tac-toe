package com.example.tictactoe.battleservice.validator.group;

import jakarta.validation.GroupSequence;
import jakarta.validation.groups.Default;

@GroupSequence({Default.class, ConsistentData.class})
public interface ConstraintGroups {
}
