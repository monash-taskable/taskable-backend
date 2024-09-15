package com.taskable.backend.services;

import com.taskable.backend.repositories.ClassRepository;
import com.taskable.backend.repositories.UserRepository;
import com.taskable.protobufs.ClassroomProto.AddMembersResponse;
import com.taskable.protobufs.ClassroomProto.GetMembersResponse;
import com.taskable.protobufs.ClassroomProto.GetClassesResponse;
import com.taskable.protobufs.ClassroomProto.CreateClassRequest;
import com.taskable.protobufs.ClassroomProto.GetClassResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClassService {
    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private UserRepository userRepository;

    public GetClassResponse createClass(CreateClassRequest req, Integer userId) {

        var classroom = classRepository.storeClass(userId, req.getClassName(), req.getTimestamp(), req.getClassDesc());
        return GetClassResponse.newBuilder()
                .setClassroom(classroom)
                .setRole("OWNER")
                .build();
    }

    public GetClassesResponse getClasses(Integer userId) {
        var classesRolesList = classRepository.getClassesAndRolesByUserId(userId);
            return GetClassesResponse.newBuilder().addAllResponses(classesRolesList.stream()
                    .map(p -> GetClassResponse.newBuilder()
                                .setClassroom(p.getLeft())
                                .setRole(p.getRight()).build()
                    ).toList()).build();

    }

    public GetClassResponse getClassroom(Integer classId) {
        var classroom = classRepository.getClassById(classId);
        return GetClassResponse.newBuilder()
                .setClassroom(classroom)
                .build();
    }

    public AddMembersResponse addMembersToClass(Integer assignerId, List<String> userEmails, Integer classId) {
        // assignerId potentially used for logging
        List<String> invalid_emails = new ArrayList<>();
        for (var email : userEmails) {
            var userId = userRepository.getUserIdByEmail(email);
            if (userId == null) {
                invalid_emails.add(email);
            }
            else {
                classRepository.addUserToClass(userId, classId, "STUDENT");
            }
        }
        return AddMembersResponse.newBuilder()
                .addAllInvalidEmails(invalid_emails)
                .build();
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
