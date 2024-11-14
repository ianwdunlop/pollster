package org.thetravellingbard.polling;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
         * Creates a new poll from the request json. A poll can have 2 to 7 options.
         * A request should take the format:
         * 
         * <pre>
         * {"question":"What is your most favouritest food?", "options": [{"text":"Beans"}{ "text":"Chips"}]}
         * </pre>
         * 
         * @param poll
         * @return Http 201 Created
         */
        @CrossOrigin(origins = "*",  methods = {RequestMethod.OPTIONS, RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE}, allowedHeaders = "*")
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
                if (poll.getOptionList().size() < 2 || poll.getOptionList().size() > 7) {
                        return new ResponseEntity<>("A poll must have between 2 and 7 options", HttpStatus.BAD_REQUEST);
                }

                // add option list to the poll
                pollIn.setOptionList(options);

                // save Owner
                Poll pollOut = pollRepository.save(pollIn);
                System.out.println("Poll out :: " + pollOut);

                System.out.println("Saved!!!");
                return new ResponseEntity<String>("Create new poll", HttpStatus.CREATED);
        }

        /**
         * Vote for an option by sending a post request to this route.
         * A vote needs to include the id of the option as a parameter ie
         * poll/saveVote?id=8
         * 
         * @param id
         * @return
         */
        @CrossOrigin(origins = "*",  methods = {RequestMethod.OPTIONS, RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE}, allowedHeaders = "*")
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
        @CrossOrigin(origins = "*",  methods = {RequestMethod.OPTIONS, RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE}, allowedHeaders = "*")
        @GetMapping(path = "/getPoll/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<String> getPoll(@PathVariable(name = "id") String id) {
                System.out.println("Poll get called...");

                // fetch Poll
                Poll pollOut = pollRepository.getReferenceById(Integer.valueOf(id));
                System.out.println("\nOwner details :: \n" + pollOut);
                System.out.println("\nList of Options :: \n" + pollOut.getOptionList());

                System.out.println("\nDone!!!");
                return new ResponseEntity<String>(pollOut.toString(), HttpStatus.OK);
        }

        /**
         * Returns all the polls in a json formatted list. Includes the id and the question
         * 
         * @return 
         */
        @CrossOrigin(origins = "*",  methods = {RequestMethod.OPTIONS, RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE}, allowedHeaders = "*")
        @GetMapping(path = "/getPolls", produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<String> getPolls() {
                List<Poll> polls = pollRepository.findAll();
                String pollsJson = "";
                for (Poll poll: polls) {
                        pollsJson += "{\"id\":" + poll.getId() + ",\"question\": \"" + poll.getQuestion() + "\"},";
                }
                // Remove the trailing comma
                pollsJson = pollsJson.substring(0, pollsJson.length() - 1);
                return new ResponseEntity<String>("[" + pollsJson + "]", HttpStatus.OK);
        }

}