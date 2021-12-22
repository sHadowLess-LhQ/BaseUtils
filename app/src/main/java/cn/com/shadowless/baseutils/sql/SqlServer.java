package cn.com.shadowless.baseutils.sql;

import android.net.LocalServerSocket;
import android.net.LocalSocket;

import java.io.FileNotFoundException;
import java.io.IOException;

class SqlServer implements ISqlServer {

    private LocalServerSocket serverSocket;
    private IoServer ioServer;

    public SqlServer(LocalServerSocket serverSocket, final IoServer ioServer) {
        this.serverSocket = serverSocket;
        this.ioServer = ioServer;
        new Thread() {
            @Override
            public void run() {
                super.run();
                while (true) {
                    final LocalSocket client;
                    try {
                        client = SqlServer.this.serverSocket.accept();
                        IoServer.THREAD_SERVICE.execute(() -> {
                            try {
                                new ResponseInvoker(SqlServer.this, client);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                        break;
                    }
                }
            }
        }.start();
    }

    @Override
    public boolean write(final String key, String data) {
        int dataLen = data.getBytes().length;
        byte[] buffer = new byte[dataLen + 2];
        System.arraycopy(data.getBytes(), 0, buffer, 1, dataLen);
        buffer[buffer.length - 1] = Sql.END_TAG;
        buffer[0] = Sql.END_TAG;
        return ioServer.write(key, buffer);
    }

    @Override
    public String read(String key) {
        byte[] read = ioServer.read(key);
        if (read == null || read.length == 0 || read[read.length - 1] != Sql.END_TAG || read[0] != Sql.END_TAG) {
            return "";
        }
        byte[] source = new byte[read.length - 2];
        System.arraycopy(read, 1, source, 0, source.length);
        return new String(source);
    }

    /**
     * 保存数据，会覆盖前面的数据
     *
     * @param key
     * @param data
     * @return
     * @throws Exception
     */
    @Override
    public boolean write(String key, byte[] data) {
        byte[] buffer = new byte[data.length + 2];
        System.arraycopy(data, 0, buffer, 1, data.length);
        buffer[buffer.length - 1] = Sql.END_TAG;
        buffer[0] = Sql.END_TAG;
        return ioServer.write(key, buffer);
    }

    /**
     * 读取数据，读取失败或没有值返回空字符“”
     *
     * @param key
     * @return
     * @throws Exception
     */
    @Override
    public byte[] readBytes(String key) {
        byte[] read = ioServer.read(key);
        if (read == null || read.length == 0 || read[read.length - 1] != Sql.END_TAG || read[0] != Sql.END_TAG) {
            return null;
        }
        byte[] source = new byte[read.length - 2];
        System.arraycopy(read, 1, source, 0, source.length);
        return source;
    }

    @Override
    public boolean cut(String key) {
        return ioServer.cut(key);
    }

    @Override
    public boolean remove(String key) {
        return ioServer.remove(key);
    }

    @Override
    public boolean clearRef() {
        ioServer.clearRef();
        return true;
    }

    @Override
    public boolean isClose() {
        return false;
    }
}
