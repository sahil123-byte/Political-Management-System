package com.pms.political_management_system.dto.response;

public class BoothResponseDTO {

    private Long id;
    private String boothNumber;
    private String boothName;
    private String location;

    private Long constituencyId;
    private String constituencyName;

    public BoothResponseDTO() {
    }

    public BoothResponseDTO(Long id,
                            String boothNumber,
                            String boothName,
                            String location,
                            Long constituencyId,
                            String constituencyName) {
        this.id = id;
        this.boothNumber = boothNumber;
        this.boothName = boothName;
        this.location = location;
        this.constituencyId = constituencyId;
        this.constituencyName = constituencyName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBoothNumber() {
        return boothNumber;
    }

    public void setBoothNumber(String boothNumber) {
        this.boothNumber = boothNumber;
    }

    public String getBoothName() {
        return boothName;
    }

    public void setBoothName(String boothName) {
        this.boothName = boothName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Long getConstituencyId() {
        return constituencyId;
    }

    public void setConstituencyId(Long constituencyId) {
        this.constituencyId = constituencyId;
    }

    public String getConstituencyName() {
        return constituencyName;
    }

    public void setConstituencyName(String constituencyName) {
        this.constituencyName = constituencyName;
    }
}