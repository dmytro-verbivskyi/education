package assertj;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;

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
        File file = temporaryFolder.newFile("file.with.mp3");

        assertThat(file).hasName("file.with.mp3").hasExtension("mp3")
                .canRead().canWrite()
                .hasContent("")
                .hasSameContentAs(temporaryFolder.newFile(), Charset.defaultCharset());
    }

    @Test
    public void withPath() throws IOException {
        File file = temporaryFolder.newFile("hello.mp3");

        Path path = Paths.get(file.toURI());
        Path tempFolderPath = Paths.get(temporaryFolder.getRoot().toURI());
        assertThat(path).isAbsolute()
                .isCanonical()
                .isRegularFile()
                .isNormalized()
                .hasParent(tempFolderPath)
                .exists();
    }

}
