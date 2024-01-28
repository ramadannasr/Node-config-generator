package pl.helwan.fci.storage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import pl.helwan.fci.file.Storage;
import pl.helwan.fci.model.Destination;


@Builder
@AllArgsConstructor
public class LocalfileDestination implements Destination {

    private String path;

    @Override
    public void store(String content, String label) {
        Storage.getInstance().storeFile(path, content, label);
    }
}
