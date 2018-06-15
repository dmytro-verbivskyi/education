package nio;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class DeleteFolderContentTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private DeleteFolderContent service = new DeleteFolderContent();

    @Test
    public void pathCannotBeNull() {
        expectedException.expectMessage("Path cannot be null");
        expectedException.expect(NullPointerException.class);

        service.deleteFolder(null);
    }

    @Test
    public void targetMustBeAFile() {
        expectedException.expectMessage("Only valid file can be a target");
        expectedException.expect(IllegalArgumentException.class);

        service.deleteFolder(Paths.get("notExisting.file"));
    }

    @Test
    public void targetCannotBeFolder() throws IOException {
        expectedException.expectMessage("Directory cannot be a valid target");
        expectedException.expect(IllegalArgumentException.class);

        Path dir = createTempDirectory("deleteThisDir");
        service.deleteFolder(dir);
    }

    @Test
    public void targetFileMustBeDeletedWithOtherFilesAndDirectoryItself() throws IOException {
        Path dir = createTempDirectory("deleteThisDir");
        Path path1 = createTempFile(dir, "target1", "txt");
        Path path2 = createTempFile(dir, "target2", "txt");

        assertThat(path1.toFile()).as("At this point file1 must exist").exists();
        assertThat(path2.toFile()).as("At this point file2 must exist").exists();
        service.deleteFolder(path1);
        assertThat(path1.toFile()).as("Now file1 must be deleted").doesNotExist();
        assertThat(path2.toFile()).as("file2 must be also deleted").doesNotExist();
        assertThat(dir.toFile()).as("Directory must be also deleted").doesNotExist();
    }

    @Test
    public void ifDeletionOfFileIsNotPossibleAnExceptionShouldBeThrown() throws IOException {
        Path dir = createTempDirectory("deleteThisDir");
        Path path = createTempFile(dir, "target1", "txt");

        expectedException.expectMessage("Hello");
//        expectedException.expectMessage("Unable to delete directory " + dir.toString());
        expectedException.expect(IllegalArgumentException.class);
//        expectedException.expect(UncheckedIOException.class);

        try (BufferedReader letsReadThisFileRightNow = Files.newBufferedReader(path)) {
            letsReadThisFileRightNow.read();
            service.deleteFolder(path);
        }
    }

    private Path createTempDirectory(String pathStr) throws IOException {
        Path path = Files.createTempDirectory(pathStr);
        path.toFile().deleteOnExit();
        return path;
    }

    private Path createTempFile(Path dir, String prefix, String suffix) throws IOException {
        Path path = Files.createTempFile(dir, prefix, suffix);
        path.toFile().deleteOnExit();
        return path;
    }

}
