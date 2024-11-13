package org.thetravellingbard.polling;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

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

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreatePoll() throws Exception {
        Poll poll = new Poll("Is this a good test?");
        Option firstOption = new Option("Yes");
        Option secondOption = new Option("No");
        List<Option> options = new ArrayList<Option>();
        options.add(firstOption);
        options.add(secondOption);
        poll.setOptionList(options);
        mockMvc.perform(post("/poll/savePoll").contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(poll)))
        .andExpect(status().isCreated())
        .andDo(print());
    }

    @Test
    void shouldCreateVote() throws Exception {
        Option option = new Option("Yes");
        List<Vote> votes= new ArrayList<>();
        option.setVoteList(votes);
        when(optionRepository.getReferenceById(1)).thenReturn(option);
        mockMvc.perform(post("/poll/saveVote?id=1"))
        .andExpect(status().isCreated())
        .andDo(print());
    }

    @Test
    void pollShouldHaveMoreThanOneOption() throws Exception {
        Poll poll = new Poll("Is this a good test?");
        Option firstOption = new Option("Yes");
        List<Option> options = new ArrayList<Option>();
        options.add(firstOption);
        poll.setOptionList(options);
        mockMvc.perform(post("/poll/savePoll").contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(poll)))
        .andExpect(status().isBadRequest())
        .andDo(print());
    }
    
}
