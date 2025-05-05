<div class="container">
    <div class="row py-4">
        <div class="col-md-6">
            <h2>Send a text message</h2>
            <form action="${appURL}/sms" method="POST">
                <div class="form-group">
                    <label for="phone">Phone Number</label>
                    <input type="text" class="form-control mb-2" id="phone" name="phone" value="${phone}">
                </div>
                <div class="form-group">
                    <label for="message">Message</label>
                    <textarea class="form-control mb-2" rows="3" id="message" name="message">${message}</textarea>
                </div>
                <input type="submit" value="Send" class="btn btn-primary">
            </form>
        </div>
    </div>
</div>