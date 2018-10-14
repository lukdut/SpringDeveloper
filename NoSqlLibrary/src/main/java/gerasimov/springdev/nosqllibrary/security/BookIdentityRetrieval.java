package gerasimov.springdev.nosqllibrary.security;

import gerasimov.springdev.nosqllibrary.model.Book;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.ObjectIdentityRetrievalStrategy;

public class BookIdentityRetrieval implements ObjectIdentityRetrievalStrategy {
    public static long idFromString(String value) {
        long h = 0;
        if (value.length() > 0) {
            char val[] = value.toCharArray();

            for (int i = 0; i < value.length(); i++) {
                h = 31 * h + val[i];
            }
        }
        return h;
    }

    @Override
    public ObjectIdentity getObjectIdentity(Object domainObject) {
        if (Book.class.equals(domainObject.getClass())) {
            return new ObjectIdentityImpl(Book.class, idFromString(((Book) domainObject).getId()));
        }
        return null;
    }
}
