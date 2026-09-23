package com.pms.political_management_system.service;

import com.pms.political_management_system.dto.request.VoterRequestDTO;
import com.pms.political_management_system.dto.response.VoterResponseDTO;
import com.pms.political_management_system.entity.Booth;
import com.pms.political_management_system.entity.Voter;
import com.pms.political_management_system.repository.BoothRepository;
import com.pms.political_management_system.repository.VoterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VoterService {

    @Autowired
    private VoterRepository voterRepository;

    @Autowired
    private BoothRepository boothRepository;

    // Get All
    public List<VoterResponseDTO> getAllVoters() {

        return voterRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Save
    public VoterResponseDTO saveVoter(VoterRequestDTO requestDTO) {

        if (requestDTO.getBoothId() == null) {
            throw new RuntimeException("Booth is required");
        }

        Booth booth = boothRepository.findById(requestDTO.getBoothId())
                .orElseThrow(() -> new RuntimeException("Booth Not Found"));

        Voter voter = new Voter();

        voter.setVoterId(requestDTO.getVoterId());
        voter.setName(requestDTO.getName());
        voter.setFatherName(requestDTO.getFatherName());
        voter.setGender(requestDTO.getGender());
        voter.setAge(requestDTO.getAge());
        voter.setMobile(requestDTO.getMobile());
        voter.setAddress(requestDTO.getAddress());
        voter.setBooth(booth);
        voter.setImageUrl(requestDTO.getImageUrl());

        Voter saved = voterRepository.save(voter);

        return mapToResponse(saved);
    }

    // Get By Id
    public VoterResponseDTO getVoterById(Long id) {

        Voter voter = voterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Voter Not Found"));

        return mapToResponse(voter);
    }

    // Update
    public VoterResponseDTO updateVoter(Long id,
                                        VoterRequestDTO requestDTO) {

        Voter voter = voterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Voter Not Found"));

        if (requestDTO.getBoothId() == null) {
            throw new RuntimeException("Booth is required");
        }

        Booth booth = boothRepository.findById(requestDTO.getBoothId())
                .orElseThrow(() -> new RuntimeException("Booth Not Found"));

        voter.setVoterId(requestDTO.getVoterId());
        voter.setName(requestDTO.getName());
        voter.setFatherName(requestDTO.getFatherName());
        voter.setGender(requestDTO.getGender());
        voter.setAge(requestDTO.getAge());
        voter.setMobile(requestDTO.getMobile());
        voter.setAddress(requestDTO.getAddress());
        voter.setBooth(booth);
        voter.setImageUrl(requestDTO.getImageUrl());

        Voter updated = voterRepository.save(voter);

        return mapToResponse(updated);
    }

    // Delete
    public String deleteVoter(Long id) {

        Voter voter = voterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Voter Not Found"));

        voterRepository.delete(voter);

        return "Voter Deleted Successfully";
    }

    // Mapping Method (null-safe against a missing/deleted booth link)
    private VoterResponseDTO mapToResponse(Voter v) {

        return new VoterResponseDTO(
                v.getId(),
                v.getVoterId(),
                v.getName(),
                v.getFatherName(),
                v.getGender(),
                v.getAge(),
                v.getMobile(),
                v.getAddress(),

                v.getBooth() != null ? v.getBooth().getId() : null,
                v.getBooth() != null ? v.getBooth().getBoothName() : null,
                v.getImageUrl()
        );
    }
}
