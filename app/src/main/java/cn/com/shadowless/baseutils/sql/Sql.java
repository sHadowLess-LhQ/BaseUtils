package cn.com.shadowless.baseutils.sql;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.LocalServerSocket;
import android.net.LocalSocket;
import android.net.LocalSocketAddress;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;


/**
 * The type Sql.
 *
 * @author sHadowLess
 */
public class Sql {

    /**
     * The Db name.
     */
    private String dbName = null;
    /**
     * The Context.
     */
    private Context context = null;
    /**
     * The Server socket.
     */
    private LocalServerSocket serverSocket = null;
    /**
     * The Server.
     */
    private LocalSocket server = null;
    /**
     * The Addr.
     */
    private String address = null;
    /**
     * The Is create.
     */
    private boolean isCreate;
    /**
     * The Sql server.
     */
    private IoServer ioServer = null;
    /**
     * The Bamboo server.
     */
    private ISqlServer sqlServer = null;
    /**
     * The constant END_TAG.
     */
    public static final byte END_TAG = -0x10;
    /**
     * The constant mInstance.
     */
    @SuppressLint("StaticFieldLeak")
    public static volatile Sql mInstance = null;

    /**
     * Gets bamboo server.
     *
     * @return the bamboo server
     */
    public ISqlServer getSqlServer() {
        if (!isCreate || sqlServer.isClose()) {
            try {
                init(context, dbName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sqlServer;
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public synchronized static Sql getInstance() {
        if (null == mInstance) {
            synchronized (Sql.class) {
                if (null == mInstance) {
                    mInstance = new Sql();
                }
            }
        }
        return mInstance;
    }

    /**
     * Sets file.
     *
     * @param file the file
     * @throws Exception the exception
     */
    private void setFile(File file) throws Exception {
        if (file == null) {
            throw new NullPointerException("setFile is null");
        }

        if (!file.exists() || !file.isFile()) {
            if (!file.getParentFile().exists() && !file.getParentFile().mkdirs()) {
                throw new Exception("create file dir fail:" + file);
            }
            if (!file.createNewFile()) {
                throw new Exception("create file fail:" + file);
            }
        }
        /**
         * The Read file name.
         */
        String readFileName = file.getAbsolutePath();

        byte[] hash = MessageDigest.getInstance("MD5").digest(readFileName.getBytes());
        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) {
                hex.append("0");
            }
            hex.append(Integer.toHexString(b & 0xFF));
        }
        this.address = hex.toString();
    }

    /**
     * Init.
     *
     * @param context the context
     * @param dbName  the db name
     * @throws Exception the exception
     */
    public void init(Context context, String dbName) {
        this.context = context;
        this.dbName = dbName;
        File db = new File(context.getExternalFilesDir(null).getAbsolutePath() + File.separator + dbName);
        if (isCreate && !sqlServer.isClose()) {
            return;
        }
        try {
            setFile(db);
            serverSocket = new LocalServerSocket(address);
            ioServer = new IoServer(db);
            sqlServer = new SqlServer(serverSocket, ioServer);
            isCreate = true;
        } catch (Exception e) {
            System.out.println("connect");
            server = new LocalSocket();
            try {
                server.connect(new LocalSocketAddress(address));
                sqlServer = new SqlClient(server);
                isCreate = true;
            } catch (Exception e1) {
                isCreate = false;
                e1.printStackTrace();
            }
        }
    }

    /**
     * Close.
     *
     * @throws IOException the io exception
     */
    public void close() throws IOException {
        if (serverSocket != null) {
            serverSocket.close();
        }

        if (server != null) {
            server.close();
        }

        if (ioServer != null) {
            ioServer.destroy();
        }
    }
}