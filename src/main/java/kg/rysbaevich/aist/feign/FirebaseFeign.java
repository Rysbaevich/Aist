package kg.rysbaevich.aist.feign;

import kg.rysbaevich.aist.model.request.customer.FirebaseLoginRequest;
import kg.rysbaevich.aist.model.request.customer.FirebaseRefreshRequest;
import kg.rysbaevich.aist.model.response.customer.FirebaseLoginResponse;
import kg.rysbaevich.aist.model.response.customer.FirebaseRefreshResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "Firebase", url = "${firebase.base.url}")
public interface FirebaseFeign {

    @PostMapping("/v1/accounts:signInWithPassword?key=AIzaSyBjeXEwf0ttOywD2nLALqqdFkWfo8mg9go")
    FirebaseLoginResponse login(@RequestBody FirebaseLoginRequest request);

    @PostMapping(value = "/v1/token?key=AIzaSyBjeXEwf0ttOywD2nLALqqdFkWfo8mg9go", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    FirebaseRefreshResponse refresh(@RequestBody FirebaseRefreshRequest refreshToken);
}
