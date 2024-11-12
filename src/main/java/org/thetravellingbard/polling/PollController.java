package org.thetravellingbard.polling;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @PostMapping("/savePoll")
    public String savePoll(@RequestBody Poll poll) {
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
        return "Poll saved!!!";
    }

    @PostMapping("/saveOption")
    public String saveOption(@RequestParam(name = "id") String id) {
        System.out.println("Blog save called...");
	
	// fetch Ower
        Poll ownerTemp = pollRepository.getReferenceById(Integer.valueOf(id));

	// list of Blog
        List<Option> options = new ArrayList<>();

	// new Blog
        Option option = new Option("Build application server using NodeJs");
	// set owner to blog
        option.setPoll(ownerTemp);
        // add Blog to list
        options.add(option);

        option = new Option("Single Page Application using Angular");
	// set owner to blog
        option.setPoll(ownerTemp);
        options.add(option);

	// add Blog list to Owner
        ownerTemp.setOptionList(options);

	// save Owner
        pollRepository.save(ownerTemp);

        System.out.println("Saved!!!");
        return "Option saved!!!";
    }

    @GetMapping("/getPoll/{id}")
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