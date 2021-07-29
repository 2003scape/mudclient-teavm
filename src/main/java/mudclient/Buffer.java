package mudclient;
import java.math.BigInteger;

public class Buffer {

    public byte buffer[];
    public int offset;

    public Buffer(byte buff[]) {
        buffer = buff;
        offset = 0;
    }

    public void putByte(int i) {
        buffer[offset++] = (byte) i;
    }

    public void putInt(int i) {
        buffer[offset++] = (byte) (i >> 24);
        buffer[offset++] = (byte) (i >> 16);
        buffer[offset++] = (byte) (i >> 8);
        buffer[offset++] = (byte) i;
    }

    public void putString(String s) {
        //s.getBytes(0, s.length(), buffer, offset);
        System.arraycopy(s.getBytes(), 0, buffer, offset, s.length());
        offset += s.length();
        buffer[offset++] = 10; // null terminate
    }

    public void putBytes(byte src[], int srcPos, int len) {
        //for (int k = srcPos; k < srcPos + len; k++)
        //    buffer[offset++] = src[k];
        System.arraycopy(src, srcPos, buffer, offset, len);
        offset += len;
    }

    public int getUnsignedByte() {
        return buffer[offset++] & 0xff;
    }

    public int getUnsignedShort() {
        offset += 2;
        return ((buffer[offset - 2] & 0xff) << 8) + (buffer[offset - 1] & 0xff);
    }

    public int getUnsignedInt() {
        offset += 4;
        return ((buffer[offset - 4] & 0xff) << 24) + ((buffer[offset - 3] & 0xff) << 16) + ((buffer[offset - 2] & 0xff) << 8) + (buffer[offset - 1] & 0xff);
    }

    public void getBytes(byte dest[], int destPos, int len) {
        //for (int k = destPos; k < destPos + len; k++)
        //    dest[k] = buffer[offset++];
        System.arraycopy(buffer, offset, dest, destPos, len);
        offset += len;
    }

    public void encrypt(BigInteger exponent, BigInteger modulus) {
        int i = offset;
        offset = 0;
        byte buf[] = new byte[i];
        getBytes(buf, 0, i);
        BigInteger biginteger2 = new BigInteger(buf);
        BigInteger biginteger3 = biginteger2.modPow(exponent, modulus);
        byte abyte1[] = biginteger3.toByteArray();
        offset = 0;
        putByte(abyte1.length);
        putBytes(abyte1, 0, abyte1.length);
    }

}
