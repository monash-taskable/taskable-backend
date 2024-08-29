package com.taskable.backend;

import com.taskable.backend.config.ProtobufConfig;
import com.taskable.backend.controllers.UserController;
import com.taskable.backend.services.UserService;
import com.taskable.protobufs.PersistenceProto.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@Import({UserService.class, ProtobufConfig.class})
@WebMvcTest(UserController.class)
public class UserTest {
    private static final Logger logger = LoggerFactory.getLogger(UserTest.class);

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    public void getUserApiTest() throws Exception{
        User expectedOutput = User.newBuilder()
                .setId(1)
//                .setUsername("admin alice")
                .setEmail("alice@taskable.com")
                .build();
        byte[] expectedBytes = expectedOutput.toByteArray();
        mockMvc.perform(get("/api/get-user")
                        .with(SecurityMockMvcRequestPostProcessors.oauth2Login()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/x-protobuf"))
                .andExpect(content().bytes(expectedBytes));
    }
}

