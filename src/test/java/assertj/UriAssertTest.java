package assertj;

import org.junit.Test;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

public class UriAssertTest {

    @Test
    public void withUriAssert() {
        URI googleUri = URI.create("https://www.youtube.com/watch?v=ZyD4OngSF9M&index=34&t=0s&list=FLHwwtB8ET-5XvOw-dMaGr6Q");
        assertThat(googleUri).hasScheme("https")
                .hasAuthority("www.youtube.com")
                .hasNoUserInfo()
                .hasHost("www.youtube.com")
                .hasNoPort()
                .hasPath("/watch")
                .hasQuery("v=ZyD4OngSF9M&index=34&t=0s&list=FLHwwtB8ET-5XvOw-dMaGr6Q")
                .hasNoParameter("simple", "false")
                .hasParameter("v", "ZyD4OngSF9M").hasParameter("index").hasParameter("t").hasParameter("list")
                .hasNoFragment();

        URI sshUri = URI.create("ssh://mmwsdemo:pa$$word@uw2-mss-tool:22/getty/app/index.html#fragment");
        assertThat(sshUri).hasScheme("ssh")
                .hasAuthority("mmwsdemo:pa$$word@uw2-mss-tool:22")
                .hasUserInfo("mmwsdemo:pa$$word")
                .hasHost("uw2-mss-tool")
                .hasPort(22)
                .hasPath("/getty/app/index.html")
                .hasNoQuery()
                .hasFragment("fragment");
    }

}
