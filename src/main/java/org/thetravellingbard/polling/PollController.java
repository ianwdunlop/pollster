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
            // set owner to this option
            option.setPoll(pollIn);
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
    public String saveOption(@RequestParam(name = "id") String id) {
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
        return "Option saved!!!";
    }

    @PostMapping("/saveVote")
    public String saveVote(@RequestParam(name = "id") String id) {
        System.out.println("Vote save called...");
	
	// fetch option
        Option optionTemp = optionRepository.getReferenceById(Integer.valueOf(id));

	// new Vote
        Vote vote = new Vote();
        vote.setOption(optionTemp);
        optionTemp.getVoteList().add(vote);
        optionRepository.save(optionTemp);

        System.out.println("Saved!!!");
        return "Vote saved!!!";
    }

//     @GetMapping("/getVotesForPoll/{id}")
//     public ResponseEntity<String> getVotesForPoll(@PathVariable(name = "id") String id) {
//         System.out.println("Poll get called...");

// 	// fetch Owner
//         Poll pollOut = pollRepository.getReferenceById(Integer.valueOf(id));
//         for (Option option: pollOut.getOptionList()) {
//                 option.
//         }
//         System.out.println("\nOwner details :: \n" + pollOut);
//         System.out.println("\nList of Options :: \n" + pollOut.getOptionList());

//         System.out.println("\nDone!!!");
//         return new ResponseEntity<String>(pollOut.toString(), HttpStatus.OK);
//     }

    

    @GetMapping(path = "/getPoll/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getPoll(@PathVariable(name = "id") String id) {
        System.out.println("Poll get called...");

	// fetch Owner
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