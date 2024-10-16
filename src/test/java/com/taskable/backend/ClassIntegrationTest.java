package com.taskable.backend;

import com.taskable.backend.auth.CustomUserDetails;
import com.taskable.backend.auth.CustomUserDetailsService;
import com.taskable.backend.auth.config.SecurityConfig; // Import SecurityConfig
import com.taskable.backend.config.ProtobufConfig;
import com.taskable.backend.controllers.ClassController;
import com.taskable.backend.controllers.UserController;
import com.taskable.backend.repositories.ClassRepository;
import com.taskable.backend.repositories.ProjectRepository;
import com.taskable.backend.repositories.TaskRepository;
import com.taskable.backend.repositories.UserRepository;
import com.taskable.backend.services.AuthorizationService;
import com.taskable.backend.services.ClassService;
import com.taskable.backend.services.ProjectService;
import com.taskable.backend.services.TaskService;
import com.taskable.jooq.tables.User;
import com.taskable.jooq.tables.records.UserRecord;
import com.taskable.protobufs.AnnouncementProto.Announcement;
import com.taskable.protobufs.AnnouncementProto.CreateAnnouncementRequest;
import com.taskable.protobufs.AnnouncementProto.CreateAnnouncementResponse;
import com.taskable.protobufs.AnnouncementProto.GetAnnouncementResponse;
import com.taskable.protobufs.ClassroomProto.CreateClassRequest;
import com.taskable.protobufs.ClassroomProto.GetClassResponse;
import configurations.TestDBConfig;
import org.jooq.DSLContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Import(TestDBConfig.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
public class ClassIntegrationTest {

  private static final String PROTOBUF_CONTENT_TYPE = "application/x-protobuf";

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private DSLContext dslContext;

  @Autowired
  private UserRepository userRepository;

  @Test
  public void createClassMakeAnnouncementTest() throws Exception {
    // Define test user attributes
    String userSub = "test-sub-123";
    String userEmail = "testuser@example.com";
    String userFirstName = "Test1";
    String userLastName = "User";

    // Step 1: Insert a test user into the database with 'sub'
    UserRecord testUserRecord = dslContext.newRecord(User.USER);
    testUserRecord.setSub(userSub); // Ensure 'sub' field exists in your schema
    testUserRecord.setFirstName(userFirstName);
    testUserRecord.setLastName(userLastName);
    testUserRecord.setEmail(userEmail);
    // Set other mandatory fields if any
    testUserRecord.store();

    Integer testUserId = testUserRecord.getId();
    assertNotNull(testUserId, "Test user ID should not be null after storing");

    // Step 2: Prepare CreateClassRequest protobuf message
    CreateClassRequest createClassRequest = CreateClassRequest.newBuilder()
        .setClassName("Test Class")
        .setClassDesc("This is a test class")
        .setTimestamp("2024-04-27 11:00") // Ensure the timestamp format matches expected format
        .build();

    byte[] createClassBytes = createClassRequest.toByteArray();

    // Step 3: Perform POST /api/classes/create to create a new class with protobuf binary data
    MvcResult createClassResult = mockMvc.perform(post("/api/classes/create")
            .contentType(PROTOBUF_CONTENT_TYPE)
            .accept(PROTOBUF_CONTENT_TYPE)
            .content(createClassBytes)
            .with(user(new CustomUserDetails(testUserId, userSub))) // Mock authentication
            .with(csrf())) // Include CSRF token
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(PROTOBUF_CONTENT_TYPE)) // Updated assertion
        .andReturn();

    // Parse the binary response to GetClassResponse protobuf message
    byte[] createClassResponseBytes = createClassResult.getResponse().getContentAsByteArray();
    GetClassResponse createClassResponse = GetClassResponse.parseFrom(createClassResponseBytes);

    Integer classId = createClassResponse.getClassroom().getId();
    assertNotNull(classId, "Class ID should not be null");
    assertEquals("Test Class", createClassResponse.getClassroom().getName(), "Class name mismatch");
    assertEquals("OWNER", createClassResponse.getRole(), "User role should be OWNER");

    // Step 4: Prepare CreateAnnouncementRequest protobuf message
    CreateAnnouncementRequest createAnnouncementRequest = CreateAnnouncementRequest.newBuilder()
        .setTitle("Test Announcement")
        .setContent("This is a test announcement")
        .setSentAt("2024-04-27 11:00") // Ensure the timestamp format matches expected format
        .build();

    byte[] createAnnouncementBytes = createAnnouncementRequest.toByteArray();

    // Step 5: Perform POST /api/classes/{class_id}/announcements/create to create an announcement
    MvcResult createAnnouncementResult = mockMvc.perform(post("/api/classes/" + classId + "/announcements/create")
            .contentType(PROTOBUF_CONTENT_TYPE)
            .accept(PROTOBUF_CONTENT_TYPE)
            .content(createAnnouncementBytes)
            .with(user(new CustomUserDetails(testUserId, userSub))) // Mock authentication
            .with(csrf())) // Include CSRF token
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(PROTOBUF_CONTENT_TYPE)) // Updated assertion
        .andReturn();

    // Parse the binary response to CreateAnnouncementResponse protobuf message
    byte[] createAnnouncementResponseBytes = createAnnouncementResult.getResponse().getContentAsByteArray();
    CreateAnnouncementResponse createAnnouncementResponse = CreateAnnouncementResponse.parseFrom(createAnnouncementResponseBytes);

    Integer announcementId = createAnnouncementResponse.getId();
    assertNotNull(announcementId, "Announcement ID should not be null");

    // Step 6: Perform GET /api/classes/{class_id}/announcements/{announcement_id} to verify the announcement
    MvcResult getAnnouncementResult = mockMvc.perform(get("/api/classes/" + classId + "/announcements/" + announcementId)
            .accept(PROTOBUF_CONTENT_TYPE)
            .with(user(new CustomUserDetails(testUserId, userSub)))) // Mock authentication
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(PROTOBUF_CONTENT_TYPE)) // Updated assertion
        .andReturn();

    // Parse the binary response to GetAnnouncementResponse protobuf message
    byte[] getAnnouncementResponseBytes = getAnnouncementResult.getResponse().getContentAsByteArray();
    GetAnnouncementResponse getAnnouncementResponse = GetAnnouncementResponse.parseFrom(getAnnouncementResponseBytes);

    // Step 7: Assert that the announcement data matches
    Announcement announcement = getAnnouncementResponse.getAnnouncement();
    assertNotNull(announcement, "Announcement should not be null");
    assertEquals("Test Announcement", announcement.getTitle(), "Announcement title mismatch");
    assertEquals("This is a test announcement", announcement.getContent(), "Announcement content mismatch");
    assertEquals(classId.intValue(), announcement.getClassId(), "Announcement class ID mismatch");
    assertEquals(testUserId.intValue(), announcement.getAuthorId(), "Announcement author ID mismatch");
    assertEquals("2024-04-27T11:00", announcement.getSentAt(), "Announcement sent_at mismatch");
  }
}
