package gerasimov.springdev.nosqllibrary.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashMap;
import java.util.Map;

class HardcodedUserDetailsService implements UserDetailsService {
    private static final Logger LOG = LoggerFactory.getLogger(HardcodedUserDetailsService.class);

    private Map<String, UserDetails> passToUserMap = new HashMap<>();

    HardcodedUserDetailsService() {
        passToUserMap.put("admin", new SimpleUserDetails("admin", "password", Roles.ROLE_ADMIN));
        passToUserMap.put("author", new SimpleUserDetails("author", "password", Roles.ROLE_AUTHOR, Roles.ROLE_READER));
        passToUserMap.put("user", new SimpleUserDetails("user", "password", Roles.ROLE_READER));
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        LOG.debug("Obtaining user with name " + s);
        UserDetails userDetails = passToUserMap.get(s);
        if (userDetails == null) {
            throw new UsernameNotFoundException("can not find user " + s);
        }
        return userDetails;
    }
}
