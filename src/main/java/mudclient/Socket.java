package mudclient;

import java.io.IOException;
import java.util.ArrayList;

import org.teavm.jso.dom.events.MessageEvent;
import org.teavm.interop.Async;
import org.teavm.interop.AsyncCallback;
import org.teavm.jso.dom.events.Event;
import org.teavm.jso.dom.events.EventListener;
import org.teavm.jso.typedarrays.Int8Array;
import org.teavm.jso.websocket.*;

interface SocketCallback {
    void run(int result);
}

public class Socket {
    private String host;
    private int port;
    private WebSocket client;
    private boolean connected = false;
    private int bytesAvailable = 0;
    private ArrayList<Int8Array> buffers = new ArrayList<Int8Array>();
    private Int8Array currentBuffer;
    private int offset = 0;
    private int bytesLeft = 0;
    private SocketCallback onError;
    private SocketCallback onClose;
    private SocketCallback onMessage;

    Socket(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Async
    public native int connect();
    public void connect(AsyncCallback<Integer> callback) {
        this.client = WebSocket.create("ws://" + this.host + ":" + this.port, "binary");
        this.client.setBinaryType("arraybuffer");

        this.client.onClose(new EventListener<CloseEvent>(){
            public void handleEvent(CloseEvent event) {
                if (onClose != null) {
                    onClose.run(-1);
                    onClose = null;
                }
            }
        });

        this.client.onMessage(new EventListener<MessageEvent>(){
            public void handleEvent(MessageEvent event) {
                Int8Array toAdd = Int8Array.create(event.getDataAsArray());
                buffers.add(toAdd);
                bytesAvailable += toAdd.getLength();
                refreshCurrentBuffer();

                if (onMessage != null) {
                    onMessage.run(toAdd.getLength());
                    onMessage = null;
                }
            };
        });

        this.client.onOpen(new EventListener<MessageEvent>(){
            public void handleEvent(MessageEvent event) {
                connected = true;
                callback.complete(1);
            }
        });

        this.client.onError(new EventListener<Event>(){
            public void handleEvent(Event event) {
                if (onError != null) {
                    onError.run(-1);
                }

                callback.error(new IOException("WebSocket error"));
            }
        });
    }

    public void write(byte[] bytes, int offset, int length) {
        Int8Array toSend = Int8Array.create(length);

        for (int i = 0; i < length; i += 1) {
            toSend.set(i, bytes[offset + i]);
        }

        this.client.send("");
    }

    private void refreshCurrentBuffer() {
        if (this.bytesLeft == 0 && this.bytesAvailable > 0) {
            this.currentBuffer = this.buffers.remove(0);
            this.offset = 0;

            if (this.currentBuffer != null && this.currentBuffer.getLength() > 0) {
                this.bytesLeft = this.currentBuffer.getLength();
            } else {
                this.bytesLeft = 0;
            }
        }
    }

    @Async
    private native int waitForBytes();
    private void waitForBytes(AsyncCallback<Integer> callback) {
        //this.onError = (result) -> callback.error(new IOException("WebSocket error"));
        //this.onClose = (result) -> callback.complete(result);
        //this.onMessage = (result) -> { callback.complete(123); };
        //callback.complete(123);
    }

    public int read() {
        if (!this.connected) {
            return -1;
        }

        if (this.bytesLeft > 0) {
            this.bytesLeft--;
            this.bytesAvailable--;
            return this.currentBuffer.get(this.offset++) & 0xff;
        }

        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
        }

        /*
        int bytesRead = waitForBytes() + 1;

        System.out.println("bytes read finished? " + bytesRead);

        if (bytesRead == -1) {
            return -1;
        }*/

        return this.read();
    }

    public int readBytes(byte[] destination, int off, int length) {
        if (!this.connected) {
            return -1;
        }

        if (this.bytesAvailable >= length) {
            while (length > 0) {
                destination[off++] = this.currentBuffer.get(this.offset++);
                this.bytesLeft -= 1;
                this.bytesAvailable -= 1;
                length -= 1;

                if (this.bytesLeft == 0) {
                    this.refreshCurrentBuffer();
                }
            }

            return length;
        }

        /*
        int bytesRead = waitForBytes();

        if (bytesRead == -1) {
            return -1;
        }*/

        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
        }

        return this.readBytes(destination, offset, length);
    }

    public void close() {
        if (!this.connected) {
            return;
        }

        this.client.close();
    }

    public int available() {
        return this.bytesAvailable;
    }

    public void clear() {
        if (this.connected) {
            this.client.close();
        }

        this.currentBuffer = null;
        this.buffers.clear();
        this.bytesLeft = 0;
    }
}
