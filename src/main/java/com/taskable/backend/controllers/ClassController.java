package com.taskable.backend.controllers;


import com.taskable.backend.auth.CustomUserDetails;
import com.taskable.backend.services.ClassService;
import com.taskable.protobufs.ClassroomProto;
import com.taskable.protobufs.ClassroomProto.CreateClassRequest;
import com.taskable.protobufs.ClassroomProto.GetClassResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ClassController {

    @Autowired
    private ClassService classService;

    private static final Logger logger = LoggerFactory.getLogger(ClassController.class);

    @PostMapping("/create-class")
    public GetClassResponse createClass(
            @RequestBody CreateClassRequest req, @AuthenticationPrincipal CustomUserDetails userDetails) {
        return classService.createClass(req, userDetails.userId());
    }

    @GetMapping("/get-classes")
    public ClassroomProto.GetClassesResponse getClasses(@AuthenticationPrincipal CustomUserDetails userDetails) {
        logger.info("user id from get-classes: " + userDetails.userId());
        return classService.getClasses(userDetails.userId());
    };
}
