<main>
    <jsp:include page="/WEB-INF/edit-profile-header.jspf"></jsp:include>

    <section class="pt-0">
        <div class="container">
            <div class="row">

                <jsp:include page="/WEB-INF/left-sidebar.jspf"></jsp:include>

                <!-- Main content START -->
                <div class="col-xl-9">
                    <!-- Title and select START -->
                    <div class="card border bg-transparent rounded-3 mb-0">
                        <!-- Card header -->
                        <div class="card-header bg-transparent border-bottom">
                            <h3 class="card-header-title mb-0">Delete Account</h3>
                        </div>
                        <!-- Card body -->
                        <div class="card-body">
                            <h6>If you delete your account, you will lose your all data.</h6>
                            <form method="POST" action="${appURL}/delete-account">
                                <!-- Email id -->
                                <div class="col-md-6 my-4">
                                    <label class="form-label" for="email">Enter your email to confirm account deletion</label>
                                    <input class="form-control <c:if test="${not empty results.emailError}">is-invalid</c:if>" type="text" id="email" name="email" value="${email}">
                                    <c:if test="${not empty results.emailError }"><div class="invalid-feedback">${results.emailError}</div></c:if>
                                </div>

                                <button type="submit" class="btn btn-danger mb-0">Delete my account</button>
                            </form>
                        </div>
                    </div>
                    <!-- Title and select END -->
                </div>
                <!-- Main content END -->
            </div><!-- Row END -->
        </div>
    </section>
</main>