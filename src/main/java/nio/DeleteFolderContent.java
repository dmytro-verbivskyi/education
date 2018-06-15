package nio;

import org.apache.tomcat.util.http.fileupload.FileUtils;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.util.Objects.requireNonNull;

public class DeleteFolderContent {

    public void deleteFolder(Path path) {
        requireNonNull(path, "Path cannot be null");

        if (!path.toFile().exists()) {
            throw new IllegalArgumentException("Only valid file can be a target");
        }
        if (Files.isDirectory(path)) {
            throw new IllegalArgumentException("Directory cannot be a valid target");
        }
        try {
            FileUtils.deleteDirectory(path.getParent().toFile());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
