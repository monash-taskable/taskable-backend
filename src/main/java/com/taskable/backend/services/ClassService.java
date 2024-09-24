package com.taskable.backend.services;

import com.taskable.backend.repositories.ClassRepository;
import com.taskable.backend.repositories.UserRepository;
import com.taskable.protobufs.AnnouncementProto.*;
import com.taskable.protobufs.ClassroomProto.*;
import com.taskable.protobufs.PersistenceProto.Classroom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ClassService {
    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private UserRepository userRepository;

    public GetClassResponse createClass(CreateClassRequest req, Integer userId) {

        var classroom = classRepository.storeClass(userId, req.getClassName(), req.getTimestamp(), req.getClassDesc());
        classRepository.addUserToClass(userId, classroom.getId(), "OWNER");
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
        Map<String, Integer> emailToId = userRepository.getUserIdsByEmails(userEmails);
        List<String> invalidEmails = new ArrayList<>();
        List<Integer> userIdsToAdd = new ArrayList<>();
        for (var email : userEmails) {
            var userId = emailToId.get(email);
            if (userId == null) {
                invalidEmails.add(email);
            }
            else {
                userIdsToAdd.add(userId);
            }
        }
        // TODO: TEST LARGE BULK INSERT
        classRepository.addUsersToClass(userIdsToAdd, classId, "STUDENT");
        return AddMembersResponse.newBuilder()
                .addAllInvalidEmails(invalidEmails)
                .build();
    }

    public void updateClassDetails(Integer classId, UpdateClassRequest req) {
        classRepository.updateClassDetails(classId,
                req.hasClassName()  ? req.getClassName() : null,
                req.hasClassDesc() ? req.getClassDesc() : null,
                req.hasArchived() ? req.getArchived() : null);
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

    public void deleteClassroom(Integer classId) {
        classRepository.deleteClassById(classId);
    }

    public CreateAnnouncementResponse createAnnouncement(Integer userId, Integer classId, CreateAnnouncementRequest req) {
        return CreateAnnouncementResponse.newBuilder()
            .setId(classRepository.createAnnouncement(classId, userId, req.getTitle(), req.getContent(), req.getSentAt()))
            .build();
    }

    public GetAnnouncementsResponse getAnnouncements(Integer classId) {
        return GetAnnouncementsResponse.newBuilder()
            .addAllAnnouncements(classRepository.getAnnouncementsInClass(classId))
            .build();
    }

    public GetAnnouncementResponse getAnnouncement(Integer announcementId) {
        return GetAnnouncementResponse.newBuilder()
            .setAnnouncement(classRepository.getAnnouncement(announcementId))
            .build();
    }

    public void deleteAnnouncement(Integer announcementId) {
        classRepository.deleteAnnouncementById(announcementId);
    }

    public void updateAnnouncement(Integer announcementId, UpdateAnnouncementRequest req) {
        classRepository.updateAnnouncement(announcementId,
            req.hasTitle() ? req.getTitle() : null,
            req.hasContent() ? req.getContent() : null);
    }
}
