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
@RequestMapping("/owner")
public class OwnerController {
    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private BlogRepository blogRepository;

    @PostMapping("/saveOwner")
    public String saveOwner(@RequestBody Owner owner) {
        System.out.println("Owner save called...");

	// a new poll
        Owner pollIn = new Owner(owner.getName(), owner.getEmail());

	// list of Options
        List<Blog> options = new ArrayList<>();
        for (Blog optionIn : owner.getBlogList()) {
            // new Option
            Blog option = new Blog(optionIn.getTitle(), optionIn.getCategory(), optionIn.getContent());
            // set owner to this option
            option.setOwner(pollIn);
            // add option to list
            options.add(option);
        }
        
        // add option list to the poll
        pollIn.setBlogList(options);

	// save Owner
        Owner pollOut = ownerRepository.save(pollIn);
        System.out.println("Poll out :: " + pollOut);

        System.out.println("Saved!!!");
        return "Poll saved!!!";
    }

    @PostMapping("/saveBlog")
    public String saveBlog(@RequestParam(name = "id") String id) {
        System.out.println("Blog save called...");
	
	// fetch Ower
        Owner ownerTemp = ownerRepository.getReferenceById(Integer.valueOf(id));

	// list of Blog
        List<Blog> blogs = new ArrayList<>();

	// new Blog
        Blog blog = new Blog("Build application server using NodeJs", "nodeJs",
                "We will build REStful api using nodeJs.");
	// set owner to blog
        blog.setOwner(ownerTemp);
        // add Blog to list
        blogs.add(blog);

        blog = new Blog("Single Page Application using Angular", "Angular",
                "We can build robust application using Angular framework.");
	// set owner to blog
        blog.setOwner(ownerTemp);
        blogs.add(blog);

	// add Blog list to Owner
        ownerTemp.setBlogList(blogs);

	// save Owner
        ownerRepository.save(ownerTemp);

        System.out.println("Saved!!!");
        return "Blog saved!!!";
    }

    @GetMapping("/getOwner/{id}")
    public ResponseEntity<String> getOwner(@PathVariable(name = "id") String id) {
        System.out.println("Owner get called...");

	// fetch Owner
        Owner ownerOut = ownerRepository.getReferenceById(Integer.valueOf(id));
        System.out.println("\nOwner details :: \n" + ownerOut);
        System.out.println("\nList of Blogs :: \n" + ownerOut.getBlogList());

        System.out.println("\nDone!!!");
        return new ResponseEntity<String>(ownerOut.toString(), HttpStatus.OK);
    }

    @GetMapping("/getBlog/{id}")
    public String getBlog(@PathVariable(name = "id") String id) {
        System.out.println("Blog get called...");

	// fetch Blog
        Blog blogOut = blogRepository.getReferenceById(Integer.valueOf(id));
        System.out.println("\nBlog details :: \n" + blogOut);
        System.out.println("\nOwner details :: \n" + blogOut.getOwner());

        System.out.println("\nDone!!!");
        return "Blog fetched...";
    }
}