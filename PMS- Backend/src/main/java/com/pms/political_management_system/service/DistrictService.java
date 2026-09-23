package com.pms.political_management_system.service;

import com.pms.political_management_system.dto.request.DistrictRequestDTO;
import com.pms.political_management_system.dto.response.DistrictResponseDTO;
import com.pms.political_management_system.entity.District;
import com.pms.political_management_system.entity.State;
import com.pms.political_management_system.repository.DistrictRepository;
import com.pms.political_management_system.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DistrictService {

    @Autowired
    private DistrictRepository districtRepository;

    @Autowired
    private StateRepository stateRepository;

    // Get All Districts
    public List<DistrictResponseDTO> getAllDistricts() {

        return districtRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Save District
    public DistrictResponseDTO saveDistrict(DistrictRequestDTO requestDTO) {

        if (requestDTO.getStateId() == null) {
            throw new RuntimeException("State is required");
        }

        State state = stateRepository.findById(requestDTO.getStateId())
                .orElseThrow(() -> new RuntimeException("State Not Found"));

        District district = new District();

        district.setDistrictName(requestDTO.getDistrictName());
        district.setState(state);

        District savedDistrict = districtRepository.save(district);

        return mapToResponse(savedDistrict);
    }

    // Mapping Method (null-safe against a missing/deleted state link)
    private DistrictResponseDTO mapToResponse(District district) {

        return new DistrictResponseDTO(
                district.getId(),
                district.getDistrictName(),
                district.getState() != null ? district.getState().getId() : null,
                district.getState() != null ? district.getState().getStateName() : null
        );
    }
}
