
    <div class="container py-4">
        <div class="row g-4">
            <c:forEach items="${products}" var="product">
            <%-- 12 means full-width, 6 means half-width, 4 means one-third width, 3 means one-forth width   --%>
            <div class="col-sm-12 col-md-6 col-lg-4 col-xl-3">
                <div class="card shadow-sm">
                    <div class="card-header py-2">
                        <h4 class="my-0 text-center">${product.name}</h4>
                    </div>
                    <div class="card-body">
                        <p class="card-text">${product.description}</p>
                        <div class="d-flex justify-content-between align-items-center">
                            <small class="fw-bold"><fmt:formatNumber value="${product.price}" type="currency" /></small>
                            <a href="add-to-cart?prod_id=${product.id}" class="btn btn-secondary">Add to Cart</a>
                        </div>
                    </div>
                </div>
            </div>
            </c:forEach>
        </div>
    </div>
