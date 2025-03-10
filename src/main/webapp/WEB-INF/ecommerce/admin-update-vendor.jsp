
<div class="container py-4">
    <a href="vendors" class="btn btn-primary" role="button">View All Vendors</a>
    <h1>Update Vendor</h1>
    <c:choose>
        <c:when test="${empty vendor}">
            <p class="lead">Vendor not found</p>
        </c:when>
        <c:otherwise>
            <c:if test="${not empty vendorUpdated}">
                <div class="alert <c:choose><c:when test="${vendorUpdated == true}">alert-success</c:when><c:when test="${vendorUpdated == false}">alert-danger</c:when><c:otherwise></c:otherwise></c:choose>" role="alert">
                        ${vendorUpdatedMessage}
                </div>
            </c:if>
            <%-- A Bootstrap row contains a grid of 12 columns --%>
            <form class="row g-3" method="POST" action="update-vendor?id=${id}">
                    <%-- col-md-4 means the column will be 1/3 of the row's width  --%>
                <div class="col-md-4">
                    <label for="vend_id" class="form-label">ID</label>
                    <input disabled type="text" class="form-control <c:choose><c:when test="${vendorIdError == true}">is-invalid</c:when><c:when test="${vendorIdError == false}">is-valid</c:when><c:otherwise></c:otherwise></c:choose>" id="vend_id" name="vend_id" value="${vendor.vend_id}">
                    <div class="<c:choose><c:when test="${vendorIdError == true}">invalid-feedback</c:when><c:when test="${vendorIdError == false}">valid-feedback</c:when><c:otherwise></c:otherwise></c:choose>">
                            ${vendorIdMessage}
                    </div>
                </div>
                    <%-- col-md-8 means the column will be 2/3 of the row's width  --%>
                <div class="col-md-8">
                    <label for="vend_name" class="form-label">Name</label>
                    <input type="text" class="form-control <c:choose><c:when test="${vendorNameError == true}">is-invalid</c:when><c:when test="${vendorNameError == false}">is-valid</c:when><c:otherwise></c:otherwise></c:choose>" id="vend_name" name="vend_name" value="${vendor.vend_name}">
                    <div class="<c:choose><c:when test="${vendorNameError == true}">invalid-feedback</c:when><c:when test="${vendorNameError == false}">valid-feedback</c:when><c:otherwise></c:otherwise></c:choose>">
                            ${vendorNameMessage}
                    </div>
                </div>
                    <%-- col-md-3 means the column will be 1/4 of the row's width  --%>
                <div class="col-md-3">
                    <label for="country" class="form-label">Country Abbreviation</label>
                    <input type="text" maxlength="3" class="form-control <c:choose><c:when test="${countryError == true}">is-invalid</c:when><c:when test="${countryError == false}">is-valid</c:when><c:otherwise></c:otherwise></c:choose>" id="country" name="country" value="${vendor.address.country}">
                    <div class="<c:choose><c:when test="${countryError == true}">invalid-feedback</c:when><c:when test="${countryError == false}">valid-feedback</c:when><c:otherwise></c:otherwise></c:choose>">
                            ${countryMessage}
                    </div>
                </div>
                    <%-- col-md-9 means the column will be 3/4 of the row's width  --%>
                <div class="col-md-9">
                    <label for="address" class="form-label">Street Address</label>
                    <input type="text" class="form-control <c:choose><c:when test="${addressError == true}">is-invalid</c:when><c:when test="${addressError == false}">is-valid</c:when><c:otherwise></c:otherwise></c:choose>" id="address" name="address" value="${vendor.address.address}">
                    <div class="<c:choose><c:when test="${addressError == true}">invalid-feedback</c:when><c:when test="${addressError == false}">valid-feedback</c:when><c:otherwise></c:otherwise></c:choose>">
                            ${addressMessage}
                    </div>
                </div>
                <div class="col-md-4">
                    <label for="city" class="form-label">City</label>
                    <input type="text" class="form-control <c:choose><c:when test="${cityError == true}">is-invalid</c:when><c:when test="${cityError == false}">is-valid</c:when><c:otherwise></c:otherwise></c:choose>" id="city" name="city" value="${vendor.address.city}">
                    <div class="<c:choose><c:when test="${cityError == true}">invalid-feedback</c:when><c:when test="${cityError == false}">valid-feedback</c:when><c:otherwise></c:otherwise></c:choose>">
                            ${cityMessage}
                    </div>
                </div>
                <div class="col-md-4">
                    <label for="state" class="form-label">State Abbreviation</label>
                    <input type="text" maxlength="2" class="form-control <c:choose><c:when test="${stateError == true}">is-invalid</c:when><c:when test="${stateError == false}">is-valid</c:when><c:otherwise></c:otherwise></c:choose>" id="state" name="state" value="${vendor.address.state}">
                    <div class="<c:choose><c:when test="${stateError == true}">invalid-feedback</c:when><c:when test="${stateError == false}">valid-feedback</c:when><c:otherwise></c:otherwise></c:choose>">
                            ${stateMessage}
                    </div>
                </div>
                <div class="col-md-4">
                    <label for="zip" class="form-label">Zip</label>
                    <input type="text" class="form-control <c:choose><c:when test="${zipError == true}">is-invalid</c:when><c:when test="${zipError == false}">is-valid</c:when><c:otherwise></c:otherwise></c:choose>" id="zip" name="zip" value="${vendor.address.zip}">
                    <div class="<c:choose><c:when test="${zipError == true}">invalid-feedback</c:when><c:when test="${zipError == false}">valid-feedback</c:when><c:otherwise></c:otherwise></c:choose>">
                            ${zipMessage}
                    </div>
                </div>
                <input type="hidden" name="id" value="${id}">
                <div class="col-12">
                    <button class="btn btn-secondary" type="submit">Submit form</button>
                </div>
            </form>
        </c:otherwise>
    </c:choose>
</div>

