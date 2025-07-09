package it.unicam.cs.ids.dtos.requests;

import it.unicam.cs.ids.dtos.DTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 * CreateCertificateRequest is used to create a new certificate for a product.
 */
@Data @EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class CreateCertificateRequest extends DTO {
    private long id;
    private String name;
    private String description;
    private long issuerId;
    private MultipartFile certificateFile;
    private Date issueDate;
    private Date expirationDate;
}
