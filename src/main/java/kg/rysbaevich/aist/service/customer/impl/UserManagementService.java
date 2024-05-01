package kg.rysbaevich.aist.service.customer.impl;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import kg.rysbaevich.aist.enums.SubscriptionName;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserManagementService {

    private final FirebaseAuth firebaseAuth;

    public void setUserClaims(String uid, Set<SubscriptionName> requestedPermissions) throws FirebaseAuthException {
        List<String> permissions = requestedPermissions
                .stream()
                .map(Enum::toString)
                .toList();

        Map<String, Object> claims = Map.of("subscription_claims", permissions);

        firebaseAuth.setCustomUserClaims(uid, claims);
    }
}
