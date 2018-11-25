package gerasimov.springdev.nosqllibrary.facade;

import gerasimov.springdev.nosqllibrary.actuator.annotation.BookAddition;
import gerasimov.springdev.nosqllibrary.actuator.annotation.BookDeletion;
import gerasimov.springdev.nosqllibrary.model.Book;
import gerasimov.springdev.nosqllibrary.repository.BookRepository;
import gerasimov.springdev.nosqllibrary.security.BookIdentityRetrieval;
import gerasimov.springdev.nosqllibrary.security.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Sid;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

public class MongoBookLibFacade implements BookLibFacade {
    final BookRepository bookRepository;

    //Да простят меня боги_вайринга_через_конструктор
    @Autowired
    private MutableAclService aclService;

    public MongoBookLibFacade(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> allBooks() {
        return bookRepository.findAll();
    }

    @Override
    //Only author can delete book
    //А если например захотеть еще дать разрешение всем у кого есть разрешение DELETE, то придется писать тоже кастомный
    //метод в бине, тк "hasPermission" не заюзать ибо нужно еще ID сконвертить в нужный формат?
    @PreAuthorize("@ownershipChecker.isOwner(#id)")
    @BookDeletion
    public void deleteBook(String id) {
        bookRepository.deleteById(id);
        aclService.deleteAcl(new ObjectIdentityImpl(Book.class, BookIdentityRetrieval.idFromString(id)), true);
    }

    @Override
    @PreAuthorize("hasPermission(#book, 'WRITE')")
    public void updateBook(Book book) {
        bookRepository.save(book);
    }

    @Override
    @BookAddition
    public String addBook(Book book) {
        String id = bookRepository.save(book).getId();
        // создать SID-ы для владельца и пользователя
        final Sid owner = new PrincipalSid(SecurityContextHolder.getContext().getAuthentication());
        final Sid admin = new GrantedAuthoritySid(Roles.ROLE_ADMIN);
        // создать ObjectIdentity для бизнес сущности
        final ObjectIdentity oid = new ObjectIdentityImpl(Book.class, BookIdentityRetrieval.idFromString(id));
        // создать пустой ACL
        final MutableAcl acl = aclService.createAcl(oid);

        // определить владельца сущности и права пользователей
        acl.setOwner(owner);
        acl.insertAce(acl.getEntries().size(), BasePermission.READ, owner, true);
        acl.insertAce(acl.getEntries().size(), BasePermission.WRITE, owner, true);
        acl.insertAce(acl.getEntries().size(), BasePermission.ADMINISTRATION, admin, true);
        acl.insertAce(acl.getEntries().size(), BasePermission.READ, admin, true);
        acl.insertAce(acl.getEntries().size(), BasePermission.DELETE, admin, true);
        // обновить ACL в БД
        aclService.updateAcl(acl);
        return id;
    }
}
