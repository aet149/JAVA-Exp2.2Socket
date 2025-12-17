package socketmulti;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class WeatherClient {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 5000);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             Scanner sc = new Scanner(System.in)) {

            System.out.print("Enter city name: ");
            String city = sc.nextLine();

            out.println(city); // 发送查询

            String weather = in.readLine(); // 接收服务器返回
            System.out.println("Weather result: " + weather);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

