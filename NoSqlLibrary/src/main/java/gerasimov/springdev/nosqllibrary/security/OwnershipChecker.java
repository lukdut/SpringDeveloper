package gerasimov.springdev.nosqllibrary.security;

import gerasimov.springdev.nosqllibrary.model.Book;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class OwnershipChecker {
    private final AclService aclService;

    public OwnershipChecker(AclService aclService) {
        this.aclService = aclService;
    }

    public boolean isOwner(String bookId) {
        final ObjectIdentity oid = new ObjectIdentityImpl(Book.class, BookIdentityRetrieval.idFromString(bookId));
        // прочитать ACL бизнес сущности
        Acl acl = aclService.readAclById(oid);
        // определить какие права и для кого проверять
        final List<Permission> permissions = Collections.singletonList(BasePermission.WRITE);
        final List<Sid> sids = Collections.singletonList(new PrincipalSid(SecurityContextHolder.getContext().getAuthentication()));

        //TODO
        return acl.isGranted(permissions, sids, true);
    }
}
