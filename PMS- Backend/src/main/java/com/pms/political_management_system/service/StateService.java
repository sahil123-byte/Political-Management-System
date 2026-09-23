package com.pms.political_management_system.service;

import com.pms.political_management_system.dto.request.StateRequestDTO;
import com.pms.political_management_system.dto.response.StateResponseDTO;
import com.pms.political_management_system.entity.State;
import com.pms.political_management_system.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StateService {

    @Autowired
    private StateRepository stateRepository;

    public List<StateResponseDTO> getAllStates() {

        return stateRepository.findAll()
                .stream()
                .map(state -> new StateResponseDTO(
                        state.getId(),
                        state.getStateName()
                ))
                .collect(Collectors.toList());
    }

    public StateResponseDTO saveState(StateRequestDTO requestDTO) {

        State state = new State();
        state.setStateName(requestDTO.getStateName());

        State savedState = stateRepository.save(state);

        return new StateResponseDTO(
                savedState.getId(),
                savedState.getStateName()
        );
    }
}