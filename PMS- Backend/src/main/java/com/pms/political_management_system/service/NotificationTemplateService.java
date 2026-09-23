package com.pms.political_management_system.service;

import com.pms.political_management_system.dto.request.NotificationTemplateRequestDTO;
import com.pms.political_management_system.dto.response.NotificationTemplateResponseDTO;
import com.pms.political_management_system.entity.NotificationTemplate;
import com.pms.political_management_system.repository.NotificationTemplateRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class NotificationTemplateService {

    private static final Pattern PLACEHOLDER = Pattern.compile("\\{\\{(\\w+)}}");

    private final NotificationTemplateRepository templateRepository;

    public NotificationTemplateService(NotificationTemplateRepository templateRepository) {
        this.templateRepository = templateRepository;
    }

    public List<NotificationTemplateResponseDTO> getAllTemplates() {

        return templateRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public NotificationTemplateResponseDTO getTemplateById(Long id) {

        NotificationTemplate template = templateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Template Not Found"));

        return mapToResponse(template);
    }

    public NotificationTemplateResponseDTO saveTemplate(NotificationTemplateRequestDTO requestDTO) {

        if (templateRepository.existsByName(requestDTO.getName())) {
            throw new RuntimeException("A template with this name already exists");
        }

        NotificationTemplate template = new NotificationTemplate();

        template.setName(requestDTO.getName());
        template.setType(requestDTO.getType());
        template.setChannel(requestDTO.getChannel());
        template.setContent(requestDTO.getContent());
        template.setVariables(requestDTO.getVariables());
        template.setIsActive(requestDTO.getIsActive() != null ? requestDTO.getIsActive() : true);
        template.setCreatedAt(LocalDateTime.now());

        NotificationTemplate saved = templateRepository.save(template);

        return mapToResponse(saved);
    }

    public NotificationTemplateResponseDTO updateTemplate(Long id, NotificationTemplateRequestDTO requestDTO) {

        NotificationTemplate template = templateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Template Not Found"));

        if (!template.getName().equals(requestDTO.getName())
                && templateRepository.existsByName(requestDTO.getName())) {
            throw new RuntimeException("A template with this name already exists");
        }

        template.setName(requestDTO.getName());
        template.setType(requestDTO.getType());
        template.setChannel(requestDTO.getChannel());
        template.setContent(requestDTO.getContent());
        template.setVariables(requestDTO.getVariables());

        if (requestDTO.getIsActive() != null) {
            template.setIsActive(requestDTO.getIsActive());
        }

        template.setUpdatedAt(LocalDateTime.now());

        NotificationTemplate updated = templateRepository.save(template);

        return mapToResponse(updated);
    }

    public void deleteTemplate(Long id) {

        NotificationTemplate template = templateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Template Not Found"));

        templateRepository.delete(template);
    }

    // Fills {{placeholder}} tokens in a template's content from the given
    // variable map. Any placeholder with no matching key is left as-is
    // rather than throwing, so a missing variable doesn't break the send.
    public String render(String content, java.util.Map<String, String> variables) {

        if (content == null || variables == null || variables.isEmpty()) {
            return content;
        }

        Matcher matcher = PLACEHOLDER.matcher(content);
        StringBuilder result = new StringBuilder();

        while (matcher.find()) {
            String key = matcher.group(1);
            String value = variables.getOrDefault(key, matcher.group(0));
            matcher.appendReplacement(result, Matcher.quoteReplacement(value));
        }

        matcher.appendTail(result);

        return result.toString();
    }

    private NotificationTemplateResponseDTO mapToResponse(NotificationTemplate t) {

        return new NotificationTemplateResponseDTO(
                t.getId(),
                t.getName(),
                t.getType(),
                t.getChannel(),
                t.getContent(),
                t.getVariables(),
                t.getIsActive(),
                t.getCreatedAt(),
                t.getUpdatedAt()
        );
    }
}
