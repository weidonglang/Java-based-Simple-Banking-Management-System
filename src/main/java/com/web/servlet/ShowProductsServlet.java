package com.web.servlet;
import com.web.entity.Product;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@WebServlet("/showProducts")
public class ShowProductsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Product> list = new ArrayList<>();
        // —— 按题目给的测试数据 ——
        list.add(new Product(1, "小米手机", "小米手机 小米15系列", 4099, 0.1, 10, 4, "/imgs/xiaomi.png"));
        list.add(new Product(2, "格力空调", "格力空调 1.5匹 冷暖两用", 2999, 0.2, 5, 3, "/imgs/gree.png")); // ← 这里改名
        list.add(new Product(3, "华为手机", "华为手机 鸿蒙智能手机", 3999, 0.1, 0, 5, "/imgs/huawei.png"));

        req.setAttribute("products", list);
        req.getRequestDispatcher("/showProducts.jsp").forward(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doGet(req, resp);
    }
}
