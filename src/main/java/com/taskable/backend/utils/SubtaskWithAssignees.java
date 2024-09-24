package com.taskable.backend.utils;

import com.taskable.protobufs.PersistenceProto.Subtask;

import java.util.List;

public record SubtaskWithAssignees(Subtask subtask, List<Integer> assigneeIds) {}