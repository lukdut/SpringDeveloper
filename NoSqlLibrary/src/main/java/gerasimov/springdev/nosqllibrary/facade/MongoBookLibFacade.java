package gerasimov.springdev.nosqllibrary.facade;

import gerasimov.springdev.nosqllibrary.model.Book;
import gerasimov.springdev.nosqllibrary.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.*;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;
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
    public void deleteBook(String id) {
        bookRepository.deleteById(id);
    }

    @Override
    public void updateBook(Book book) {
        bookRepository.save(book);
    }

    @Override
    public String addBook(Book book) {
        String id = bookRepository.save(book).getId();
        // создать SID-ы для владельца и пользователя
        final Sid owner = new PrincipalSid(SecurityContextHolder.getContext().getAuthentication());
        final Sid admin = new GrantedAuthoritySid("ROLE_ADMIN");
        // создать ObjectIdentity для бизнес сущности
        final ObjectIdentity oid = new ObjectIdentityImpl(Book.class, idFromString(id));
        // создать пустой ACL
        final MutableAcl acl = aclService.createAcl(oid);

        // определить владельца сущности и права пользователей
        acl.setOwner(owner);
        acl.insertAce(acl.getEntries().size(), BasePermission.READ, owner, true);
        acl.insertAce(acl.getEntries().size(), BasePermission.ADMINISTRATION, admin, true);
        // обновить ACL в БД
        aclService.updateAcl(acl);

        check(id);

        return id;
    }

    private long idFromString(String value) {
        long h = 0;
        if (h == 0 && value.length() > 0) {
            char val[] = value.toCharArray();

            for (int i = 0; i < value.length(); i++) {
                h = 31 * h + val[i];
            }
        }
        return h;
    }


    private void check(String id) {
        //Проверка
        // создать ObjectIdentity для бизнес сущности
        final ObjectIdentity oid = new ObjectIdentityImpl(Book.class, idFromString(id));
        // прочитать ACL бизнес сущности
        Acl acl = aclService.readAclById(oid);
        // определить какие права и для кого проверять
        final List<Permission> permissions = Arrays.asList(BasePermission.READ);
        final List<Sid> sids = Arrays.asList((Sid) new PrincipalSid("admin"));
        // выполнить проверку
        if (!acl.isGranted(permissions, sids, false)) {
            throw new RuntimeException("Access denied.");
        }
    }
}
