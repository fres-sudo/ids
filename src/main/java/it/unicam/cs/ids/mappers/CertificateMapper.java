package it.unicam.cs.ids.mappers;

import it.unicam.cs.ids.dtos.CertificateDTO;
import it.unicam.cs.ids.entities.Certificate;

public class CertificateMapper implements DTOMapper<CertificateDTO, Certificate> {

    @Override
    public Certificate fromDTO(CertificateDTO dto) {
        if (dto == null) {
            return null;
        }
        Certificate certificate = new Certificate();
        certificate.setId(dto.getId());
        certificate.setName(dto.getName());
        return certificate;
    }

    @Override
    public CertificateDTO toDTO(Certificate entity) {
        if (entity == null) {
            return null;
        }
        CertificateDTO dto = new CertificateDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        return dto;
    }
}
