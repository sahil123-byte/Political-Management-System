package com.pms.political_management_system.service;

import com.pms.political_management_system.dto.request.BoothRequestDTO;
import com.pms.political_management_system.dto.response.BoothResponseDTO;
import com.pms.political_management_system.entity.Booth;
import com.pms.political_management_system.entity.Constituency;
import com.pms.political_management_system.repository.BoothRepository;
import com.pms.political_management_system.repository.ConstituencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BoothService {

    @Autowired
    private BoothRepository boothRepository;

    @Autowired
    private ConstituencyRepository constituencyRepository;

    // Get All Booths
    public List<BoothResponseDTO> getAllBooths() {

        return boothRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Save Booth
    public BoothResponseDTO saveBooth(BoothRequestDTO requestDTO) {

        if (requestDTO.getConstituencyId() == null) {
            throw new RuntimeException("Constituency is required");
        }

        Constituency constituency = constituencyRepository
                .findById(requestDTO.getConstituencyId())
                .orElseThrow(() -> new RuntimeException("Constituency Not Found"));

        Booth booth = new Booth();

        booth.setBoothNumber(requestDTO.getBoothNumber());
        booth.setBoothName(requestDTO.getBoothName());
        booth.setLocation(requestDTO.getLocation());
        booth.setConstituency(constituency);

        Booth saved = boothRepository.save(booth);

        return mapToResponse(saved);
    }

    // Get Booth By ID
    public BoothResponseDTO getBoothById(Long id) {

        Booth booth = boothRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booth Not Found"));

        return mapToResponse(booth);
    }

    // Update Booth
    public BoothResponseDTO updateBooth(Long id, BoothRequestDTO requestDTO) {

        Booth booth = boothRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booth Not Found"));

        if (requestDTO.getConstituencyId() == null) {
            throw new RuntimeException("Constituency is required");
        }

        Constituency constituency = constituencyRepository
                .findById(requestDTO.getConstituencyId())
                .orElseThrow(() -> new RuntimeException("Constituency Not Found"));

        booth.setBoothNumber(requestDTO.getBoothNumber());
        booth.setBoothName(requestDTO.getBoothName());
        booth.setLocation(requestDTO.getLocation());
        booth.setConstituency(constituency);

        Booth updated = boothRepository.save(booth);

        return mapToResponse(updated);
    }

    // Delete Booth
    public String deleteBooth(Long id) {

        Booth booth = boothRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booth Not Found"));

        boothRepository.delete(booth);

        return "Booth Deleted Successfully";
    }

    // Mapping Method (null-safe against a missing/deleted constituency link)
    private BoothResponseDTO mapToResponse(Booth booth) {

        return new BoothResponseDTO(
                booth.getId(),
                booth.getBoothNumber(),
                booth.getBoothName(),
                booth.getLocation(),
                booth.getConstituency() != null ? booth.getConstituency().getId() : null,
                booth.getConstituency() != null ? booth.getConstituency().getConstituencyName() : null
        );
    }
}
