package com.bugboard26.service;

import com.bugboard26.model.Issue;
import com.bugboard26.model.IssueHistory;
import com.bugboard26.repository.IssueHistoryRepository;
import com.bugboard26.repository.IssueRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class IssueService {

    private final IssueRepository repository;
    private final IssueHistoryRepository historyRepository;

    public IssueService(IssueRepository repository, IssueHistoryRepository historyRepository){
        this.repository = repository;
        this.historyRepository = historyRepository;
    }

    public List<Issue> getAllIssues(){
        return repository.findAll();
    }

    public void createIssue(Issue issue){
        int issueRetId = repository.save(issue);
        historyRepository.saveHistory(issueRetId,"Status","", "todo",issue.getCreatedBy());
    }

    public List<Issue> getFilteredIssues(
            String status,
            String type,
            String priority,
            String orderBy
    ) {

        return repository.findFiltered(status, type, priority, orderBy);

    }

    public Map<String, Object> getBugStats(){

        Map<String, Object> stats = new HashMap<>();

        stats.put("openIssues", repository.countOpenIssues());
        stats.put("byUser", repository.countByUser());
        stats.put("avgResolution", repository.avgResolution());
        stats.put("avgResolutionByUser", repository.avgResolutionByUser());

        return stats;
    }

    public Issue getIssueById(int issueId){
        return repository.findById(issueId);
    }

    public List<IssueHistory> getHistory(int issueId){
        return historyRepository.findByIssueId(issueId);
    }

    public void updateIssue(
            int id,
            Issue updated,
            String username,
            String role
    ){

        Issue current = repository.findById(id);
        current.setId(id);

        if(!username.equals(current.getAssignedTo()) && !"ADMIN".equalsIgnoreCase(role) )
            throw new RuntimeException("Unauthorized");

        if(!Objects.equals(current.getStatus(), updated.getStatus())){

            historyRepository.saveHistory(
                    id,
                    "status",
                    current.getStatus(),
                    updated.getStatus(),
                    username
            );
            current.setStatus(updated.getStatus());
        }

        if(!Objects.equals(current.getType(), updated.getType())){

            historyRepository.saveHistory(
                    id,
                    "type",
                    current.getType(),
                    updated.getType(),
                    username
            );
            current.setType(updated.getType());
        }

        if(!updated.getCommento().isEmpty()){

            historyRepository.saveHistory(
                    id,
                    "ultimo commento",
                    current.getCommento(),
                    updated.getCommento(),
                    username
            );
            current.setCommento(updated.getCommento());
        }

        if(!Objects.equals(current.getPriority(), updated.getPriority())){

            historyRepository.saveHistory(
                    id,
                    "priority",
                    current.getPriority(),
                    updated.getPriority(),
                    username
            );
            current.setPriority(updated.getPriority());

        }

        // ASSEGNAZIONE (solo admin)
        if("ADMIN".equalsIgnoreCase(role) && updated.getAssignedTo() != null &&
                !Objects.equals(current.getAssignedTo(), updated.getAssignedTo())){

            historyRepository.saveHistory(
                    id,
                    "assigned_to",
                    current.getAssignedTo(),
                    updated.getAssignedTo(),
                    username
            );

            current.setAssignedTo(updated.getAssignedTo());
        }

        repository.update(current);

    }
}