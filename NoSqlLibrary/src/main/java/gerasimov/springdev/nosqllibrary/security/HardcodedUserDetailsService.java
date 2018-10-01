package gerasimov.springdev.nosqllibrary.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashMap;
import java.util.Map;

class HardcodedUserDetailsService implements UserDetailsService {
    private Map<String, UserDetails> passToUserMap = new HashMap<>();

    HardcodedUserDetailsService() {
        passToUserMap.put("admin", new SimpleUserDetails("admin", "password"));
        passToUserMap.put("user", new SimpleUserDetails("user", "1"));
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        System.out.println("Obtaining user with name " + s);
        return passToUserMap.get(s);
    }
}
