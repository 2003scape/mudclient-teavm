package mudclient;
import java.io.IOException;

public class ClientStream extends Packet {
    private boolean closing;
    private byte buffer[];
    private boolean closed;
    private Socket socket;

    public ClientStream(Socket socket, GameShell applet) {
        this.socket = socket;
        closing = false;
        //closed = true;
        //closed = false;
    }

    public void closeStream() {
        super.closeStream();
        closing = true;
        closed = true;
        buffer = null;
    }

    public int readStream() {
        if (closing) {
            return 0;
        }

        return this.socket.read();
    }

    public int availableStream() {
        if (closing) {
            return 0;
        }

        return this.socket.available();
    }

    public void readStreamBytes(int len, int off, byte buff[]) {
        if (closing) {
            return;
        }

        this.socket.readBytes(buff, off, len);
        //throw new IOException("EOF");
    }

    public void writeStreamBytes(byte buff[], int off, int len) {
        if (closing) {
            return;
        }

        if (buffer == null) {
            buffer = new byte[5000];
        }

        this.socket.write(buff, off, len);
    }
}
