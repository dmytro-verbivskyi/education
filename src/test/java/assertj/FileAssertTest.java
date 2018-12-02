package assertj;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import static org.assertj.core.api.Assertions.assertThat;

public class FileAssertTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void withDirectory() throws IOException {
        File folder = temporaryFolder.newFolder("folder");

        assertThat(folder).isDirectory().exists().hasName("folder");
    }

    @Test
    public void withFile() throws IOException {
        File file = temporaryFolder.newFile("file.with.music.mp3");

        assertThat(file).hasName("file.with.music.mp3").hasExtension("mp3")
                .canRead().canWrite()
                .hasContent("")
                .hasSameContentAs(temporaryFolder.newFile(), Charset.defaultCharset());
    }

}
