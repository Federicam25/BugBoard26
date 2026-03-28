package com.bugboard26.controller;

import com.bugboard26.model.Issue;
import com.bugboard26.model.IssueHistory;
import com.bugboard26.service.IssueService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("/api/issues")
public class IssueController {

    private final IssueService service;

    public IssueController(IssueService service){
        this.service = service;
    }

    @GetMapping
    public List<Issue> getIssues(

            @RequestParam(required = false) String status,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String priority,
            @RequestParam(required = false) String orderBy

    ) {

        return service.getFilteredIssues(status, type, priority, orderBy);

    }

    @GetMapping("/stats")
    public Map<String,Object> getStats(){
        return service.getBugStats();
    }

    @PostMapping
    public void createIssue(@RequestBody Issue issue,
                            HttpServletRequest request){
        String username = (String) request.getAttribute("username");
        issue.setCreatedBy(username);
        service.createIssue(issue);
    }

    @PutMapping("/{id}")
    public void updateIssue(
            @PathVariable int id,
            @RequestBody Issue issue,
            HttpServletRequest request
    ){

        String username = (String) request.getAttribute("username");
        String role = (String) request.getAttribute("role");

        service.updateIssue(id, issue, username, role);

    }

    @GetMapping("/{id}/history")
    public List<IssueHistory> getHistory(@PathVariable int id){
        Issue issue = service.getIssueById(id);
        if(!"BUG".equals(issue.getType())){
            throw new RuntimeException("History disponibile solo per BUG");
        }
        return service.getHistory(id);

    }

    @GetMapping("/{id}")
    public Issue getIssue(@PathVariable int id){
        return service.getIssueById(id);
    }

}