package cn.com.shadowless.baseutils.sql;

import android.net.LocalSocket;
import android.text.TextUtils;
import android.util.Base64;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * The type Response invoker.
 *
 * @author sHadowLess
 */
public class ResponseInvoker {
    /**
     * The Input stream.
     */
    private InputStream inputStream;
    /**
     * The Output stream.
     */
    private OutputStream outputStream;
    /**
     * The Client.
     */
    private LocalSocket client;
    /**
     * The Server.
     */
    private ISqlServer server;

    /**
     * Instantiates a new Response invoker.
     *
     * @param server the server
     * @param client the client
     * @throws FileNotFoundException the file not found exception
     */
    public ResponseInvoker(ISqlServer server, LocalSocket client) throws FileNotFoundException {
        this.server = server;
        this.client = client;
        invoke();
    }

    /**
     * Invoke.
     */
    private void invoke() {
        try {
            inputStream = client.getInputStream();
            outputStream = client.getOutputStream();
            recv();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                client.shutdownInput();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                client.shutdownOutput();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * Recv.
     */
    private void recv() {
        byte[] buffer = new byte[1024];
        int read = 0;
        String value = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while (true) {
            try {
                read = inputStream.read(buffer);
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
            if (read > 0) {
                bos.write(buffer, 0, read);
                if (buffer[read - 1] == '\0') {
                    value = new String(bos.toByteArray()).trim();
                    bos = new ByteArrayOutputStream();
                    response(value);
                }
            }
        }
    }

    /**
     * Response.
     *
     * @param value the value
     */
    private void response(final String value) {
        IoServer.THREAD_SERVICE.execute(() -> {
            try {
                JSONObject object = new JSONObject(value);
                String k = object.optString("key");
                String v = object.optString("value");
                boolean isBase64 = object.optBoolean("isBase64", false);
                String op = object.optString("op");
                if (!TextUtils.isEmpty(k) && !TextUtils.isEmpty("op")) {
                    operation(op, k, v, isBase64);
                } else {
                    answerError();
                }
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    answerError();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    /**
     * Operation.
     *
     * @param op       the op
     * @param key      the key
     * @param value    the value
     * @param isBase64 the is base 64
     * @throws Exception the exception
     */
    private void operation(String op, String key, String value, boolean isBase64) throws Exception {
        if ("set".equals(op)) {
            set(key, value, isBase64);
        } else if ("get".equals(op)) {
            get(key);
        } else if ("cut".equals(op)) {
            set(key, null, isBase64);
        } else if ("remove".equals(op)) {
            remove(key);
        } else if ("clearRef".equals(op)) {
            clearRef();
        } else {
            try {
                answerError();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Answer error.
     *
     * @throws IOException the io exception
     */
    private void answerError() throws IOException {
        outputStream.write("".getBytes());
        outputStream.write('\0');
    }

    /**
     * Set.
     *
     * @param key      the key
     * @param value    the value
     * @param isBase64 the is base 64
     * @throws Exception the exception
     */
    private void set(String key, String value, boolean isBase64) throws Exception {
        if (value == null) {
            if (server.cut(key)) {
                try {
                    outputStream.write("ok".getBytes());
                    outputStream.write('\0');
                } catch (IOException e) {
                    e.printStackTrace();
                    close();
                }
                return;
            }
        } else if (isBase64) {
            byte[] data = Base64.decode(value, Base64.NO_CLOSE | Base64.NO_PADDING | Base64.NO_WRAP);
            if (server.write(key, data)) {
                try {
                    outputStream.write("ok".getBytes());
                    outputStream.write('\0');
                } catch (IOException e) {
                    e.printStackTrace();
                    close();
                }
                return;
            }
        } else if (server.write(key, value)) {
            try {
                outputStream.write("ok".getBytes());
                outputStream.write('\0');
            } catch (IOException e) {
                e.printStackTrace();
                close();
            }
            return;
        }
        try {
            answerError();
        } catch (IOException e) {
            e.printStackTrace();
            close();
        }

    }

    /**
     * Remove.
     *
     * @param key the key
     * @throws Exception the exception
     */
    private void remove(String key) throws Exception {

        if (server.remove(key)) {
            try {
                outputStream.write("ok".getBytes());
                outputStream.write('\0');
                return;
            } catch (IOException e) {
                e.printStackTrace();
                close();
            }
        }
        try {
            answerError();
        } catch (IOException e) {
            e.printStackTrace();
            close();
        }
    }

    /**
     * Clear ref.
     *
     * @throws Exception the exception
     */
    private void clearRef() throws Exception {

        if (server.clearRef()) {
            try {
                outputStream.write("ok".getBytes());
                outputStream.write('\0');
                return;
            } catch (IOException e) {
                e.printStackTrace();
                close();
            }
        }
        try {
            answerError();
        } catch (IOException e) {
            e.printStackTrace();
            close();
        }
    }

    /**
     * Close.
     */
    private void close() {

    }

    /**
     * Get.
     *
     * @param key the key
     * @throws Exception the exception
     */
    private void get(String key) throws Exception {
        byte[] value = server.read(key).getBytes();

        try {
            outputStream.write(value);
            outputStream.write('\0');
        } catch (IOException e) {
            e.printStackTrace();
            close();
        }
    }
}
