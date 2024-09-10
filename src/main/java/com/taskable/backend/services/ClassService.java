package com.taskable.backend.services;

import com.taskable.backend.repositories.ClassRepository;
import com.taskable.protobufs.ClassroomProto.GetMembersResponse;
import com.taskable.protobufs.ClassroomProto.GetClassesResponse;
import com.taskable.protobufs.ClassroomProto.CreateClassRequest;
import com.taskable.protobufs.ClassroomProto.GetClassResponse;
import com.taskable.protobufs.PersistenceProto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClassService {
    @Autowired
    private ClassRepository classRepository;

    public GetClassResponse createClass(CreateClassRequest req, Integer userId) {
        var classroom = classRepository.storeClass(userId, req.getClassName(), req.getTimestamp(), req.getClassDesc());
        var classResponseBuilder = buildClassResponse(classroom);
        return classResponseBuilder.setRole("OWNER").build();
    }

    public GetClassesResponse getClasses(Integer userId) {
        var classesRolesList = classRepository.getClassesAndRolesByUserId(userId);
            return GetClassesResponse.newBuilder().addAllResponses(classesRolesList.stream().map(p -> {
                        var classResponseBuilder = buildClassResponse(p.getLeft());
                        return classResponseBuilder.setRole(p.getRight()).build();
                    }).toList()).build();

    }

    private GetClassResponse.Builder buildClassResponse(PersistenceProto.Classroom classroom) {
        return GetClassResponse.newBuilder()
                .setId(classroom.getId())
                .setClassName(classroom.getName())
                .setClassDesc(classroom.getDescription())
                .setRole("OWNER")
                .setArchived(classroom.getArchived())
                .setCreatedAt(classroom.getCreatedAt());
    }

    public boolean addMemberToClass(Integer assignerId, Integer assigneeId, Integer classId) {
        // assignerId potentially used for logging
        return classRepository.addUserToClass(assigneeId, classId, "STUDENT");
    }

    public GetMembersResponse getMembersInClass(Integer classId) {
        return GetMembersResponse.newBuilder().addAllClassMembers(classRepository.getMembersFromClass(classId)).build();
    }

    public void deleteMemberFromClass(Integer memberId, Integer classId) {
        classRepository.deleteMemberFromClass(memberId, classId);
    }

    public void updateMemberRoleInClass(Integer memberId, Integer classId, String role) {
        classRepository.updateMemberRole(memberId, classId, role);
    }
}
