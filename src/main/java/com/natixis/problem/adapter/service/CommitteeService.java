package com.natixis.problem.adapter.service;

import com.natixis.problem.adapter.dtos.CommitteeDTO;
import com.natixis.problem.adapter.dtos.ProblemDTO;
import com.natixis.problem.adapter.exceptions.types.CommitteSavedWithRestrictionsException;
import com.natixis.problem.domain.Committee;
import com.natixis.problem.domain.Problem;
import com.natixis.problem.domain.enums.StatusCommittee;
import com.natixis.problem.domain.enums.StatusProblem;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.natixis.problem.adapter.utils.CustomMapper.fromXtoY;

@Service
@AllArgsConstructor
public class CommitteeService{

    private final HttpAdapter httpAdapter;


    public CommitteeDTO createCommittee(CommitteeDTO committeeDto) {

        /**
         * Once a committee has started, it is in progress.
         */
        if (committeeDto.getStatus() == null){
            committeeDto.setStatus(StatusCommittee.IN_PROGRESS);
        }
        /**
         * If you try to create a Committee that already has a Problem without
         * Comment, save the Problem and the Committee and then send a message
         * informing that both the Committee and the Problem were registered
         * successfully, but the Problem was not attached to the Committee because
         * it did not have at least One comment.
         */
        if (committeeDto.getId() == null) {
            if (committeeDto.getProblems() != null) {
                List<String> problemsJustSaved = new ArrayList<>();
                List<String> problemsInserted = new ArrayList<>();

                // just saved problems
                committeeDto.getProblems().stream().filter(p -> p.getComments() == null)
                        .forEach(p -> {
                            p.setStatus(StatusProblem.OPEN);
                            Problem savedProblem = httpAdapter.createProblem(fromXtoY(p, Problem.class));
                            problemsJustSaved.add(savedProblem.getName());
                        });

                // check if respective problem has any comments
                List<Problem> savedAndInsertToCommittee = committeeDto.getProblems().stream().filter(p -> p.getComments() != null)
                        .map(pm -> {
                            pm.setStatus(StatusProblem.OPEN);
                            return httpAdapter.createProblem(fromXtoY(pm, Problem.class));
                        })
                        .collect(Collectors.toList());

                if (savedAndInsertToCommittee.size() > 0){
                    savedAndInsertToCommittee.stream().forEach(ps -> problemsInserted.add(ps.getName()));

                    // Parse the saved Problem list and updated Committee problem list
                    committeeDto.setProblems(savedAndInsertToCommittee.stream().map(sp -> fromXtoY(sp, ProblemDTO.class)).collect(Collectors.toList()));
                }else{
                    committeeDto.setProblems(null);
                }

                // Save Committee
                CommitteeDTO savedCommitteeDTO = fromXtoY(httpAdapter.createCommittee(fromXtoY(committeeDto, Committee.class)), CommitteeDTO.class);

                StringBuffer message = new StringBuffer();
                // Create the exception response
                if (savedCommitteeDTO.getId() != null){
                    // Problems inserted
                    if (problemsInserted.size() > 0){
                        message.append("- Committee saved and attached the following Problems:\n");
                        problemsInserted.stream().forEach(m -> message.append(m + ".\n"));
                    }
                    // Problems just saved
                    if (problemsJustSaved.size() > 0){
                        message.append("- Following Problems were saved in the database, but not attached to Committe:");
                        problemsJustSaved.stream().forEach(m -> message.append("\n" + m));
                    }
                    throw new CommitteSavedWithRestrictionsException(message.toString());
                }
            }
        }


        return fromXtoY(httpAdapter.createCommittee(fromXtoY(committeeDto, Committee.class)),CommitteeDTO.class);
    }


    public List<CommitteeDTO> findAllCommittees() {
        return httpAdapter.listAllCommittees()
                .stream()
                .map(domain -> fromXtoY(domain, CommitteeDTO.class))
                .collect(Collectors.toList());
    }

    public CommitteeDTO getCommitteeById(Integer id) {
        return fromXtoY(httpAdapter.getCommitteeById(id), CommitteeDTO.class);
    }

    public CommitteeDTO addProblem(Integer committeeId, Integer problemId){
        return fromXtoY(httpAdapter.addProblemToCommittee(committeeId,problemId), CommitteeDTO.class);
    }

    public CommitteeDTO closeCommitte(Integer id) {
        return fromXtoY(httpAdapter.closeCommitteeById(id), CommitteeDTO.class);
    }
}
