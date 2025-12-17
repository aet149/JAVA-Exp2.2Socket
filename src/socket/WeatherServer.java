package socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class WeatherServer {

    public static void main(String[] args) {
        try {
            // 1. 创建 ServerSocket，监听 5000 端口
            ServerSocket serverSocket = new ServerSocket(5000);
            System.out.println("Weather Server started, listening on port 5000...");

            // 2. 循环处理多个客户端（顺序）
            while (true) {
                // 等待客户端连接（阻塞）
                Socket socket = serverSocket.accept();
                System.out.println("Client connected.");

                // 3. 接收客户端发送的城市名
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream())
                );
                String city = in.readLine();
                System.out.println("Received city: " + city);

                // 4. 查询天气（这里用硬编码）
                String weather = queryWeather(city);

                // 5. 返回天气信息
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                out.println(weather);

                // 6. 关闭本次连接
                socket.close();
                System.out.println("Response sent, connection closed.\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 模拟天气查询
    private static String queryWeather(String city) {
        if (city == null) {
            return "Invalid city.";
        }
        switch (city) {
            case "beijing":
                return "Beijing: -1C and sunny";
            case "shanghai":
                return "Shanghai: 8C and cloudy";
            case "taiyuan":
                return "Taiyuan: 0C and clear";
            case "kunming":
            	return "Kunming: 8C and clear";
            case "haikou":
            	return "Haikou: 21C and clear";
            default:
                return city + ": weather data not found";
        }
    }
}
