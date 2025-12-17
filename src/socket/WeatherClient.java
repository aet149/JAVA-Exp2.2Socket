package socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class WeatherClient {

    public static void main(String[] args) {
        try {
            // 1. 连接服务器（本机 + 5000 端口）
            Socket socket = new Socket("localhost", 5000);

            // 2. 输入城市名
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter city name: ");
            String city = scanner.nextLine();

            // 3. 发送城市名到服务器
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(city);

            // 4. 接收服务器返回的天气信息
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );
            String weather = in.readLine();

            // 5. 输出结果
            System.out.println("Weather result: " + weather);

            // 6. 关闭连接
            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
