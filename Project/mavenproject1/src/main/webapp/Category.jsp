<%@page import="java.sql.Blob"%>
<%@page import="za.ac.bakery.model.Item"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title> ${catergorytitle}</title>
        <link rel="website icon" type="png" href="img/logo.png">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
        <link rel="stylesheet" href="style.css">

    </head>
    <body>
        <section class="products">
            <h1 class="title">Our <span>${catergorytitle}</span></h1>

            <div class="box-container">

                <%
                    List<Item> items = (List<Item>) session.getAttribute("items");
                    for (Item item : items) {
                        Blob imageBlob = item.getPic();
                        if (imageBlob != null) {
                            byte[] imageData = imageBlob.getBytes(1, (int) imageBlob.length());
                            String base64Image = java.util.Base64.getEncoder().encodeToString(imageData);
                            String imgSrc = "data:image/png;base64, " + base64Image;
                %>
                <!-- Move the form outside of the loop -->

                <div class="box">
                    <div class="icons">
                        <a href="/mavenproject1/AddToCart?action=GET&quantity=1&itemId=<%=item.getItem_id()%>" class="fas fa-shopping-cart" name="itemId"></a>
                        <a href="/mavenproject1/AdminController.do?action=POST&act=viewItem&itemid=<%=item.getItem_id()%>" class="fas fa-eye"></a>

                    </div>
                    <div class="img">
                        <img decoding="async" src="<%=imgSrc%>" alt="Category 1">
                    </div>
                    <div class="content">
                        <h3><%=item.getItem_title()%></h3>
                        <div class="price">R<%= item.getItem_price()%></div>
                        <div class="stars">
                            <i class="fas fa-star"></i>
                            <i class="fas fa-star"></i>
                            <i class="fas fa-star"></i>
                            <i class="fas fa-star"></i>
                            <i class="far fa-star"></i>
                        </div>
                    </div>
                </div>

                </form>
                <%
                        }
                    }
                %>
            </div>
        </section>

</body>
</html>
