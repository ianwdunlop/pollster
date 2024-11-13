package org.thetravellingbard.polling;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/poll")
public class PollController {
    @Autowired
    private PollRepository pollRepository;

    @Autowired
    private OptionRepository optionRepository;

    @Autowired
    private VoteRepository voteRepository;

    /**
     * Creates a new poll from the request json.
     * A request should take the format:
     * <pre>{"question":"What is your most favouritest food?", "options": [{"text":"Beans"}{ "text":"Chips"}]}</pre>
     * @param poll
     * @return Http 201 Created
     */
    @PostMapping("/savePoll")
    public ResponseEntity<String> savePoll(@RequestBody Poll poll) {
        System.out.println("Poll save called...");

	// a new poll
        Poll pollIn = new Poll(poll.getQuestion());

	// list of Options
        List<Option> options = new ArrayList<>();

        for (Option optionIn : poll.getOptionList()) {
            // new Option
            Option option = new Option(optionIn.getText());
            // connect poll to the option
            option.setPoll(pollIn);
            // add empty list of votes
            List<Vote> votes = new ArrayList<>();
            option.setVoteList(votes);
            // add option to list
            options.add(option);
        }
        
        // add option list to the poll
        pollIn.setOptionList(options);

	// save Owner
        Poll pollOut = pollRepository.save(pollIn);
        System.out.println("Poll out :: " + pollOut);

        System.out.println("Saved!!!");
        return new ResponseEntity<String>("Create new poll", HttpStatus.CREATED);
    }

    @PostMapping(path = "/saveOption", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> saveOption(@RequestParam(name = "id") String id) {
        System.out.println("Option save called...");
	
        Poll pollTemp = pollRepository.getReferenceById(Integer.valueOf(id));

        List<Option> options = new ArrayList<>();

        Option option = new Option("Build application server using NodeJs");
        option.setPoll(pollTemp);
        options.add(option);

        option = new Option("Single Page Application using Angular");
        option.setPoll(pollTemp);
        options.add(option);

	// add Option list to Poll
        pollTemp.setOptionList(options);

	// save Poll
        pollRepository.save(pollTemp);

        System.out.println("Saved!!!");
        return new ResponseEntity<String>("Create new poll", HttpStatus.CREATED);
    }

    /**
     * Vote for an option by sending a post request to this route.
     * A vote needs to include the id of the option as a parameter ie poll/saveVote?id=8
     * 
     * @param id
     * @return
     */
    @PostMapping("/saveVote")
    public ResponseEntity<String> saveVote(@RequestParam(name = "id") String id) {
        System.out.println("Vote save called...");
	
	// fetch option
        Option optionTemp = optionRepository.getReferenceById(Integer.valueOf(id));

	// new Vote
        Vote vote = new Vote();
        vote.setOption(optionTemp);
        optionTemp.getVoteList().add(vote);
        optionRepository.save(optionTemp);

        System.out.println("Saved!!!");
        return new ResponseEntity<String>("Create new vote", HttpStatus.CREATED);
    }
    

    /**
     * Returns json response for a Poll including the options and the number of
     * votes for an option.
     * 
     * @param id
     * @return
     */
    @GetMapping(path = "/getPoll/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getPoll(@PathVariable(name = "id") String id) {
        System.out.println("Poll get called...");

	// fetch Poll
        Poll pollOut = pollRepository.getReferenceById(Integer.valueOf(id));
        System.out.println("\nOwner details :: \n" + pollOut);
        System.out.println("\nList of Options :: \n" + pollOut.getOptionList());

        System.out.println("\nDone!!!");
        return new ResponseEntity<String>(pollOut.toString(), HttpStatus.OK);
    }

    @GetMapping("/getOption/{id}")
    public String getOption(@PathVariable(name = "id") String id) {
        System.out.println("Option get called...");

	// fetch Blog
        Option optionOut = optionRepository.getReferenceById(Integer.valueOf(id));
        System.out.println("\nOption details :: \n" + optionOut);
        System.out.println("\nOwner details :: \n" + optionOut.getPoll());

        System.out.println("\nDone!!!");
        return "Option fetched...";
    }
}