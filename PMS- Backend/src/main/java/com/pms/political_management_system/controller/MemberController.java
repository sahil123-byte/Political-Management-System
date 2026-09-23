package com.pms.political_management_system.controller;

import com.pms.political_management_system.dto.request.MemberRequestDTO;
import com.pms.political_management_system.dto.response.MemberResponseDTO;
import com.pms.political_management_system.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public List<MemberResponseDTO> getAllMembers() {
        return memberService.getAllMembers();
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @PostMapping
    public MemberResponseDTO saveMember(
            @Valid @RequestBody MemberRequestDTO requestDTO) {

        return memberService.saveMember(requestDTO);
    }
}