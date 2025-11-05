<%-- showProducts.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta charset="UTF-8">
  <title>产品列表</title>
  <style>
    :root{
      --primary:#2d6cdf;        /* 主色 */
      --primary-600:#1f56c0;
      --bg:#f6f8fb;             /* 背景 */
      --card:#fff;              /* 卡片底色 */
      --text:#1f2937;           /* 正文 */
      --muted:#6b7280;          /* 次要文字 */
      --border:#e5e7eb;         /* 边框 */
      --success:#16a34a;        /* 成功 */
      --warn:#d97706;           /* 警告 */
      --danger:#dc2626;         /* 危险 */
    }
    *{box-sizing:border-box}
    body{margin:24px; font-family:system-ui,Segoe UI,Arial; color:var(--text); background:var(--bg);}
    .toolbar{display:flex; align-items:center; justify-content:space-between; gap:12px; margin-bottom:18px}
    h1{font-size:22px; margin:0}
    .btn{
      display:inline-flex; align-items:center; gap:6px;
      padding:8px 14px; border-radius:999px; text-decoration:none; font-weight:600;
      border:1px solid transparent; transition:transform .15s ease, box-shadow .15s ease, background-color .2s ease, border-color .2s ease;
      cursor:pointer; user-select:none;
    }
    .btn-primary{background:var(--primary); color:#fff;}
    .btn-primary:hover{transform:translateY(-1px); box-shadow:0 10px 24px rgba(45,108,223,.25); background:var(--primary-600);}
    .btn-secondary{background:#fff; color:var(--text); border-color:var(--border);}
    .btn-secondary:hover{transform:translateY(-1px); box-shadow:0 8px 20px rgba(2,6,23,.08);}
    .products{display:grid; grid-template-columns:repeat(auto-fill, minmax(260px,1fr)); gap:18px;}
    .product-card{
      background:var(--card); border:1px solid var(--border); border-radius:14px; padding:16px;
      box-shadow:0 1px 2px rgba(2,6,23,.04); transition:transform .15s ease, box-shadow .15s ease;
    }
    .product-card:hover{transform:translateY(-2px); box-shadow:0 12px 28px rgba(2,6,23,.08);}
    .product-name{font-size:18px; font-weight:700; margin:6px 0 8px}
    .thumb{width:100%; height:200px; object-fit:contain; background:#f8fafc; border-radius:10px;} /* object-fit 保证图片缩放不变形 */
    .product-description{color:var(--muted); min-height:38px; margin:10px 0}
    .product-price{color:#e74c3c; font-size:18px; font-weight:700;}
    .original-price{text-decoration:line-through; color:#95a5a6; margin-right:10px; font-weight:400}
    .discount{background:#e74c3c; color:#fff; padding:2px 6px; border-radius:4px; font-size:12px; margin-left:6px}
    .out-of-stock{opacity:.65}
    .meta{display:flex; justify-content:space-between; align-items:center; margin-top:8px}
    .stock-ok{color:var(--success)} .stock-few{color:var(--warn)} .stock-zero{color:var(--danger)}
    .rating{color:#f7b500; font-size:18px}
    .actions{margin-top:10px}
    .btn-outline{background:#fff; border-color:var(--border); color:var(--text)}
    .btn-outline:hover{border-color:#cbd5e1; box-shadow:0 8px 20px rgba(2,6,23,.08); transform:translateY(-1px)}
  </style>
</head>
<body>

<header class="toolbar">
  <a class="btn btn-secondary" href="${pageContext.request.contextPath}/index.jsp">← 返回上一级</a>
  <h1>产品列表</h1>
  <span></span> <!-- 占位使标题居中 -->
</header>

<div class="products">
  <c:forEach var="product" items="${products}">
    <div class="product-card ${product.stock == 0 ? 'out-of-stock' : ''}">
      <div class="product-name">${product.name}</div>

      <c:if test="${not empty product.image}">
        <c:url value="${product.image}" var="imgUrl"/>
        <img class="thumb" src="${imgUrl}" alt="${product.name}">
      </c:if>

      <div class="product-description">
        <c:choose>
          <c:when test="${fn:length(product.description) > 50}">
            ${fn:substring(product.description, 0, 50)}...
          </c:when>
          <c:otherwise>${product.description}</c:otherwise>
        </c:choose>
      </div>

      <div class="product-price">
        <c:choose>
          <c:when test="${product.discount > 0}">
              <span class="original-price">
                <fmt:formatNumber value="${product.price}" type="number" maxFractionDigits="2"/> 元
              </span>
            <span>
                <fmt:formatNumber value="${product.price * (1 - product.discount)}" type="number" maxFractionDigits="2"/> 元
              </span>
            <span class="discount">- <fmt:formatNumber value="${product.discount * 100}" type="number" maxFractionDigits="0"/>%</span>
          </c:when>
          <c:otherwise>
            <span><fmt:formatNumber value="${product.price}" type="number" maxFractionDigits="2"/> 元</span>
          </c:otherwise>
        </c:choose>
      </div>

      <div class="meta">
        <div class="product-stock">
          <c:choose>
            <c:when test="${product.stock > 10}">
              <span class="stock-ok">库存充足</span>
            </c:when>
            <c:when test="${product.stock > 0}">
              <span class="stock-few">仅剩 ${product.stock} 件</span>
            </c:when>
            <c:otherwise>
              <span class="stock-zero">缺货</span>
            </c:otherwise>
          </c:choose>
        </div>
        <div class="rating">
          <c:forEach begin="1" end="${product.rating}">⭐</c:forEach>
          <c:forEach begin="1" end="${5 - product.rating}"><span style="opacity:.25">☆</span></c:forEach>
        </div>
      </div>

      <div class="actions">
        <c:choose>
          <c:when test="${product.stock > 0}">
            <button class="btn btn-outline" type="button">加入购物车</button>
          </c:when>
          <c:otherwise>
            <button class="btn btn-outline" type="button" disabled>缺货</button>
          </c:otherwise>
        </c:choose>
      </div>
    </div>
  </c:forEach>
</div>
</body>
</html>
