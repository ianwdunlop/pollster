package org.thetravellingbard.polling;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(PollController.class)
public class PollControllerTest {

    @MockBean
    private PollRepository pollRepository;

    @MockBean
    private OptionRepository optionRepository;

    @MockBean
    private VoteRepository voteRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldCreatePoll() throws Exception {
        // Poll poll = new Poll("Is this a good test?");
        String pollJson = "{"
        + "\"question\": \"What is your favourite food?\", "
        + "\"optionList\": [{"
        + "\"text\": \"Salad\""
        + "},"
        + "{"
        + "\"text\": \"Pasta\""
        + "},"
        + "{"
        + "\"text\": \"Chips\""
        + "}]"
        + "}";
        mockMvc.perform(post("/poll/savePoll").contentType(MediaType.APPLICATION_JSON)
        .content(pollJson))
        .andExpect(status().isCreated())
        .andDo(print());
    }
    
}
