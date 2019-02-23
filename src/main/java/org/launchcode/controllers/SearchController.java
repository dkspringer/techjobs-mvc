package org.launchcode.controllers;

import org.launchcode.models.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping("search")
public class SearchController {

    @RequestMapping(value = "")
    public String search(Model model) {
        model.addAttribute("columns", ListController.columnChoices);
        return "search";
    }

    // TODO #1 - Create handler to process search request and display results
    @RequestMapping(value = "results")
    public String search(Model model, @RequestParam String searchType, @RequestParam String searchTerm) {
        System.out.println("Column: " + searchType);
        System.out.println("Value: " + searchTerm);

        ArrayList<HashMap<String, String>> jobs;

        if (searchType.equals("all"))
            jobs = JobData.findByValue(searchTerm);
        else
            jobs = JobData.findByColumnAndValue(searchType, searchTerm);

        model.addAttribute("columns", ListController.columnChoices);
        model.addAttribute("jobs", jobs);

        printJobs(jobs);

        return "search";
    }

    // Print a list of jobs
    private static void printJobs(ArrayList<HashMap<String, String>> someJobs) {

        StringBuilder sb = new StringBuilder();

        if (someJobs.isEmpty()) {
            System.out.println("\n***** No Jobs Found *****");
            return;
        }

        for (HashMap<String, String> job : someJobs) {
            sb.append("\n*****\n");
            for (String column : job.keySet())
                sb.append(column).append(": ").append(job.get(column)).append("\n");
            sb.append("*****\n");

            System.out.println(sb.toString());
        }
    }

}
