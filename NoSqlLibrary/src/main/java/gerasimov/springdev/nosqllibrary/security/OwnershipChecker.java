package gerasimov.springdev.nosqllibrary.security;

import gerasimov.springdev.nosqllibrary.model.Book;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.Acl;
import org.springframework.security.acls.model.AclService;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class OwnershipChecker {
    private final AclService aclService;

    public OwnershipChecker(AclService aclService) {
        this.aclService = aclService;
    }

    public boolean isOwner(String bookId) {
        try {
            Acl acl = aclService.readAclById(new ObjectIdentityImpl(Book.class, BookIdentityRetrieval.idFromString(bookId)));
            return acl.getOwner().equals(new PrincipalSid(SecurityContextHolder.getContext().getAuthentication()));
        } catch (NotFoundException e) {
            //Everyone is owner for unknown elements
            return true;
        }
    }
}
