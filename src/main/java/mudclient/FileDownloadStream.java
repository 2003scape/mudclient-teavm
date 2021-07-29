package mudclient;

import java.io.IOException;

import org.teavm.interop.*;
import org.teavm.jso.ajax.XMLHttpRequest;
import org.teavm.jso.typedarrays.Int8Array;
import org.teavm.jso.typedarrays.ArrayBuffer;

public class FileDownloadStream {
    private XMLHttpRequest xhr;
    private Int8Array buffer = null;
    private int position = 0;

    FileDownloadStream(String file) {
        this.xhr = XMLHttpRequest.create();
        this.xhr.setResponseType("arraybuffer");
        this.xhr.open("get", file);
    }

    @Async
    private native int loadResBytes();
    private void loadResBytes(AsyncCallback<Integer> callback) {
        this.xhr.setOnReadyStateChange(() -> {
            if (xhr.getReadyState() != XMLHttpRequest.DONE) {
                return;
            }

            int statusGroup = xhr.getStatus() / 100;

            if (statusGroup != 2 && statusGroup != 3) {
                callback.error(
                    new IOException(
                        "HTTP status: " + xhr.getStatus() + " " +
                            xhr.getStatusText()
                    )
                );
            } else {
                this.buffer = Int8Array.create((ArrayBuffer) xhr.getResponse());
                callback.complete(1);
            }
        });

        this.xhr.send();
    }

    public void readFully(byte[] destination, int offset, int length) throws IOException {
        if (this.buffer == null) {
            this.loadResBytes();
        }

        for (int i = 0; i < length; i++) {
            destination[offset + i] = this.buffer.get(i + this.position);
        }

        this.position += length;
    }

    public void readFully(byte[] destination, int offset) throws IOException {
        this.readFully(destination, offset, destination.length);
    }
}
