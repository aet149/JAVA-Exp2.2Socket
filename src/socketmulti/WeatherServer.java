package socketmulti;

import java.io.*;
import java.net.*;

public class WeatherServer {

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            System.out.println("Weather Server started, listening on port 5000...");

            while (true) {
                Socket clientSocket = serverSocket.accept();  // 等待客户端连接
                System.out.println("New client connected: " + clientSocket.getInetAddress());

                // 为每个客户端创建线程处理
                Thread clientThread = new Thread(new ClientHandler(clientSocket));
                clientThread.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

// 处理单个客户端请求的线程类
class ClientHandler implements Runnable {

    private Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
        ) {
            String city = in.readLine(); // 接收客户端发送的城市名
            System.out.println("Received query for city: " + city);

            // 简单硬编码天气数据
            String weather = getWeather(city);
            out.println(weather); // 返回客户端

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try { socket.close(); } catch (IOException e) { e.printStackTrace(); }
        }
    }

    // 硬编码天气数据的方法
    private String getWeather(String city) {
        switch(city.toLowerCase()) {
            case "beijing": return "Beijing: 31C and sunny";
            case "shanghai": return "Shanghai: 28C and cloudy";
            case "guangzhou": return "Guangzhou: 33C and rainy";
            case "shenzhen": return "Shenzhen: 32C and sunny";
            default: return city + ": Weather data not available";
        }
    }
}

