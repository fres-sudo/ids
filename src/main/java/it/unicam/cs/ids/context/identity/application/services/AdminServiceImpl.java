package it.unicam.cs.ids.context.identity.application.services;

import it.unicam.cs.ids.context.catalog.domain.model.ApprovalStatus;
import it.unicam.cs.ids.context.certification.application.mappers.CertifierMapper;
import it.unicam.cs.ids.context.certification.domain.repositories.CertifierRequestRepository;
import it.unicam.cs.ids.context.certification.infrastructure.web.dtos.requests.CertifierRequest;
import it.unicam.cs.ids.context.company.application.mappers.CompanyMapper;
import it.unicam.cs.ids.context.company.domain.models.Company;
import it.unicam.cs.ids.context.company.domain.repositories.CompanyRepository;
import it.unicam.cs.ids.context.company.infrastructure.web.dtos.CompanyDTO;
import it.unicam.cs.ids.context.company.infrastructure.web.dtos.requests.EditCompanyRequest;
import it.unicam.cs.ids.context.identity.application.mappers.UserMapper;
import it.unicam.cs.ids.context.identity.domain.model.PlatformRoles;
import it.unicam.cs.ids.context.identity.domain.model.User;
import it.unicam.cs.ids.context.identity.domain.repositories.UserRepository;
import it.unicam.cs.ids.context.identity.infrastructure.web.dtos.UserDTO;
import it.unicam.cs.ids.context.identity.infrastructure.web.dtos.requests.EditUserRequest;
import it.unicam.cs.ids.shared.application.BaseRequest;
import it.unicam.cs.ids.shared.application.DTO;
import it.unicam.cs.ids.shared.application.Finder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AdminServiceImpl implements AdminService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    private final CertifierRequestRepository certifierRequestRepository;
    private final CertifierMapper certifierMapper;


    @Override
    @SuppressWarnings("unchecked")
    public <R extends DTO, T> R editEntity(Class<T> entityType, Long id, BaseRequest request) {
        if (entityType == User.class && request instanceof EditUserRequest) {
            return (R) editUser(id, (EditUserRequest) request);
        }
        else if (entityType == Company.class && request instanceof EditCompanyRequest) {
            return (R) editCompany(id, (EditCompanyRequest) request);
        }
        throw new UnsupportedOperationException("Unsupported entity type or request format");
    }

    @Override
    public void deleteEntity(Class<?> entityType, Long id) {
        if (entityType == User.class) deleteUser(id);
        else if (entityType == Company.class) deleteCompany(id);
        else throw new UnsupportedOperationException("Unsupported entity type");
    }

    ////////////////////////// Specific implementation methods
    private UserDTO editUser(Long userId, EditUserRequest request) {
        User user = Finder.findByIdOrThrow(
                userRepository,
                userId,
                "User not found"
        );
        User updatedUser = userMapper.updateUserFromRequest(user, request);
        return userMapper.toDto(userRepository.save(updatedUser));
    }

    private void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    private CompanyDTO editCompany(Long companyId, EditCompanyRequest request) {
        Company company = Finder.findByIdOrThrow(
                companyRepository,
                companyId,
                "Company not found"
        );
        Company updatedCompany = companyMapper.updateCompanyFromRequest(company, request);
        return companyMapper.toDto(companyRepository.save(updatedCompany));
    }

    private void deleteCompany(Long companyId) {
        companyRepository.deleteById(companyId);
    }

    @Override
    public void treatCertificationRequest(Long requestId, boolean verdict) {
        CertifierRequest certifierRequest = Finder.findByIdOrThrow(
                certifierRequestRepository,
                requestId,
                "Certification request not found"
        );

        if (!certifierRequest.getStatus().equals(ApprovalStatus.PENDING))
            throw new IllegalArgumentException("Certification request is not pending");

        if (verdict) {
            certifierRequest.setStatus(ApprovalStatus.APPROVED);
            User user = Finder.findByIdOrThrow(
                    userRepository,
                    certifierRequest.getUserId(),
                    "User not found for certification request"
            );
            user.setRole(PlatformRoles.CERTIFIER);
            userRepository.save(user);
        } else {
            certifierRequest.setStatus(ApprovalStatus.REJECTED);
        }
        certifierRequestRepository.save(certifierRequest);
    }
}
