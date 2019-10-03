/*
 * The MIT License
 *
 * Copyright 2018 mohamed.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package machine;

/**
 *
 * @author mohamed
 */
import com.sun.javacard.apduio.*;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;

public class TestAPDUIO {

    public static void main(String[] args) {

        CadClientInterface cad;
        Socket sock;
        Apdu apdu = new Apdu();
        apdu.command = new byte[]{(byte) 0x00, (byte) 0xa4, (byte) 0x04, (byte) 0x00, (byte) 0x00};

        try {
            sock = new Socket("localhost", 9025);
            InputStream is = sock.getInputStream();
            OutputStream os = sock.getOutputStream();
            cad = CadDevice.getCadClientInstance(CadDevice.PROTOCOL_T1, is, os);

            byte[] ATR = cad.powerUp();
            System.out.println("Answer To Reset:\n");
            System.out.println(Arrays.toString(ATR));

            byte[] input = apdu.getDataIn();
            byte[] output = apdu.getDataOut();
            System.out.println("-----------------");
            System.out.println(input);
            System.out.println("\n");
            System.out.println(output);

            cad.exchangeApdu(apdu);
            System.out.println("-----------------");
            System.out.println(input);
            System.out.println("\n");
            System.out.println(output);

            cad.powerDown();
        } catch (Exception e) {
            System.out.println("Exception Occurred");
        }

    }

}
