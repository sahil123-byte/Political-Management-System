package com.pms.political_management_system.service;

import com.pms.political_management_system.dto.request.MemberRequestDTO;
import com.pms.political_management_system.dto.response.MemberResponseDTO;
import com.pms.political_management_system.entity.Booth;
import com.pms.political_management_system.entity.Member;
import com.pms.political_management_system.repository.BoothRepository;
import com.pms.political_management_system.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BoothRepository boothRepository;

    @Autowired
    private CurrentUserService currentUserService;

    public List<MemberResponseDTO> getAllMembers() {

        // MEMBER role: "Self" access - only see their own linked Member record.
        if (currentUserService.isMember()) {

            Long memberId = currentUserService.getCurrentMemberId();

            if (memberId == null) {
                return List.of();
            }

            return memberRepository.findById(memberId)
                    .map(member -> List.of(mapToResponse(member)))
                    .orElse(List.of());
        }

        return memberRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public MemberResponseDTO saveMember(MemberRequestDTO requestDTO) {

        if (requestDTO.getBoothId() == null) {
            throw new RuntimeException("Booth is required");
        }

        Booth booth = boothRepository.findById(requestDTO.getBoothId())
                .orElseThrow(() -> new RuntimeException("Booth not found"));

        Member member = new Member();

        member.setName(requestDTO.getName());
        member.setMobile(requestDTO.getMobile());
        member.setEmail(requestDTO.getEmail());
        member.setAddress(requestDTO.getAddress());
        member.setDesignation(requestDTO.getDesignation());
        member.setJoiningDate(requestDTO.getJoiningDate());
        member.setBooth(booth);
        member.setImageUrl(requestDTO.getImageUrl());

        Member savedMember = memberRepository.save(member);

        return mapToResponse(savedMember);
    }

    // Mapping Method (null-safe against a missing/deleted booth link)
    private MemberResponseDTO mapToResponse(Member member) {

        return new MemberResponseDTO(
                member.getId(),
                member.getName(),
                member.getMobile(),
                member.getEmail(),
                member.getAddress(),
                member.getDesignation(),
                member.getJoiningDate(),
                member.getBooth() != null ? member.getBooth().getId() : null,
                member.getBooth() != null ? member.getBooth().getBoothName() : null,
                member.getImageUrl()
        );
    }
}
