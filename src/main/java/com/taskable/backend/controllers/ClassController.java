package com.taskable.backend.controllers;


import com.taskable.backend.auth.CustomUserDetails;
import com.taskable.backend.services.AuthorizationService;
import com.taskable.backend.services.ClassService;
import com.taskable.protobufs.ClassroomProto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/classes")
public class ClassController {

    @Autowired
    private ClassService classService;

    @Autowired
    private AuthorizationService authorizationService;

    private static final Logger logger = LoggerFactory.getLogger(ClassController.class);

    @PostMapping("/create")
    public GetClassResponse createClass(
            @RequestBody CreateClassRequest req, @AuthenticationPrincipal CustomUserDetails userDetails) {
        return classService.createClass(req, userDetails.userId());
    }

    @GetMapping("")
    public GetClassesResponse getClasses(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return classService.getClasses(userDetails.userId());
    };

    @GetMapping("/{class_id}")
    @PreAuthorize("@authorizationService.userExistsInClass(#userDetails.userId(), #classId)")
    public GetClassResponse getClassroom(@AuthenticationPrincipal CustomUserDetails userDetails,
                                     @PathVariable("class_id") Integer classId) {
        return classService.getClassroom(classId);
    }

    @PostMapping("/{class_id}/members/add")
    @PreAuthorize("@authorizationService.canUserAddToClass(#userDetails.userId(), #classId)")
    public AddMembersResponse addMembersToClass(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                               @PathVariable("class_id") Integer classId,
                                                               @RequestBody AddMembersRequest req) {
        return classService.addMembersToClass(userDetails.userId(), req.getUserEmailsList(), classId);
    }

    @GetMapping("/{class_id}/members")
    @PreAuthorize("@authorizationService.userExistsInClass(#userDetails.userId(), #classId)")
    public GetMembersResponse getMembersInClass(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable("class_id") Integer classId) {
        return classService.getMembersInClass(classId);
    }

    @DeleteMapping("/{class_id}/members/{member_id}/delete")
    @PreAuthorize("@authorizationService.checkOwnerOrAdminInClass(#userDetails.userId(), #classId)")
    public void deleteMemberFromClass(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable("class_id") Integer classId,
            @PathVariable("member_id") Integer memberId) {
        if (!userDetails.userId().equals(memberId)) {
            classService.deleteMemberFromClass(memberId, classId);
        }
    }

    @PostMapping("/{class_id}/members/{member_id}/update-role")
    @PreAuthorize(
            "@authorizationService.canUserUpdateRole(#userDetails.userId(), #memberId, #req.getRole(), #classId)")
    public void updateMemberRoleInClass(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable("class_id") Integer classId,
            @PathVariable("member_id") Integer memberId,
            @RequestBody UpdateMemberRoleRequest req) {
        classService.updateMemberRoleInClass(memberId, classId, req.getRole());
    }

    @PostMapping("/{class_id}/update")
    @PreAuthorize("@authorizationService.checkOwnerInClass(#userDetails.userId(), #classId)")
    public void updateClassDetails(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable("class_id") Integer classId,
            @RequestBody UpdateClassRequest req) {
        classService.updateClassDetails(classId, req);
    }

    @DeleteMapping("/{class_id}/delete")
    @PreAuthorize("@authorizationService.checkOwnerInClass(#userDetails.userId(), #classId)")
    public void deleteClassroom(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable("class_id") Integer classId) {
        classService.deleteClassroom(classId);
    }

}
