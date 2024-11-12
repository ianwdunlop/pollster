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
        Poll pollIn = new Poll(poll.getQuestion(), poll.getEmail());

	// list of Options
        List<Option> options = new ArrayList<>();
        for (Option optionIn : poll.getOptionList()) {
            // new Option
            Option option = new Option(optionIn.getTitle(), optionIn.getCategory(), optionIn.getContent());
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
        List<Option> blogs = new ArrayList<>();

	// new Blog
        Option blog = new Option("Build application server using NodeJs", "nodeJs",
                "We will build REStful api using nodeJs.");
	// set owner to blog
        blog.setPoll(ownerTemp);
        // add Blog to list
        blogs.add(blog);

        blog = new Option("Single Page Application using Angular", "Angular",
                "We can build robust application using Angular framework.");
	// set owner to blog
        blog.setPoll(ownerTemp);
        blogs.add(blog);

	// add Blog list to Owner
        ownerTemp.setOptionList(blogs);

	// save Owner
        pollRepository.save(ownerTemp);

        System.out.println("Saved!!!");
        return "Blog saved!!!";
    }

    @GetMapping("/getPoll/{id}")
    public ResponseEntity<String> getOwner(@PathVariable(name = "id") String id) {
        System.out.println("Owner get called...");

	// fetch Owner
        Poll ownerOut = pollRepository.getReferenceById(Integer.valueOf(id));
        System.out.println("\nOwner details :: \n" + ownerOut);
        System.out.println("\nList of Blogs :: \n" + ownerOut.getOptionList());

        System.out.println("\nDone!!!");
        return new ResponseEntity<String>(ownerOut.toString(), HttpStatus.OK);
    }

    @GetMapping("/getOption/{id}")
    public String getBlog(@PathVariable(name = "id") String id) {
        System.out.println("Blog get called...");

	// fetch Blog
        Option blogOut = optionRepository.getReferenceById(Integer.valueOf(id));
        System.out.println("\nBlog details :: \n" + blogOut);
        System.out.println("\nOwner details :: \n" + blogOut.getPoll());

        System.out.println("\nDone!!!");
        return "Blog fetched...";
    }
}