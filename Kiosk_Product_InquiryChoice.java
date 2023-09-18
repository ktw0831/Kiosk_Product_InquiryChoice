package a_010_java_after2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Kiosk_Product_InquiryChoice {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("주문 방법 입력 (단품상품 - 1, 세트상품 - 2, 추가주문상품 - 3, 전체 - 4, 종료 - 9): ");
        int pom = input.nextInt();

        if (pom == 9) {
            System.out.println("프로그램을 종료합니다.");
            Kiosk_MainMenu.main(args);
            return;
        }

        String url = "jdbc:oracle:thin:@localhost:1521:xe";
        String id = "system";
        String pw = "1234";

        try (Connection connection = DriverManager.getConnection(url, id, pw)) {
            System.out.println("===================출력============================");
            System.out.println("  NO    상품코드     단가     주문 방법      상품명");
            System.out.println("=================================================");

            String sql;
            if (pom == 4) {
                sql = "select * from tbl_product_master";
            } else {
                sql = "select * from tbl_product_master where pdt_order_method = ?";
            }
            PreparedStatement selectStatement = connection.prepareStatement(sql);
            if (pom != 4) {
                selectStatement.setInt(1, pom);
            }
            ResultSet rs = selectStatement.executeQuery();

            int n = 1;

            while (rs.next()) {

                int pdt_id = rs.getInt("pdt_id");
                String pdt_id_name = rs.getString("pdt_id_name");
                int pdt_unit_price = rs.getInt("pdt_unit_price");
                int order_method = rs.getInt("pdt_order_method");

                String order;
                if (order_method == 1) {
                    order = "1 단품";
                } else if (order_method == 2) {
                    order = "2 세트";
                } else if (order_method == 3) {
                    order = "3 추가";
                } else {
                    order = "알 수 없음";
                }

                System.out.printf(" %3d     %5d     %5d      %s       %2s%n", n, pdt_id, pdt_unit_price, order, pdt_id_name);
                n++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("=================================================");
    }
}