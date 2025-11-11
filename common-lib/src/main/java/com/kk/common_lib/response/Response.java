package com.kk.common_lib.response;

public enum Response {

    SUCCESS("0000", "Thành Công"),
    OBJECT_IS_EXISTS("0001", "Đối tượng đã tồn tại"),
    OBJECT_NOT_FOUND("0002", "Đối tượng không tồn tại"),
    OBJECT_IS_INVALID("0003", "Đối tượng không chính xác"),
    OBJECT_IS_NULL("0004", "Đối tượng bị null"),
    OBJECT_INACTIVE("0005", "Đối tượng không ở trạng thái hoạt động"),
    MISSING_PARAM("0006", "Thiếu dữ liệu đầu vào bắt buộc"),
    DATA_NOT_FOUND("0007", "Không tìm thấy kết quả"),
    DATA_INVALID("0008", "Dữ liệu đầu vào không đúng định dạng"),
    USER_ID_NOT_FOUND("0009", "Không tìm thấy thông tin người thực hiện nghiệp vụ"),
    REQUIRED_MAX_LENGTH("0010", "Vượt quá giới hạn ký tự cho phép"),

    USER_INACTIVE("0010", "Tài khoản ở trạng thái không hoạt động"),
    LOGIN_FAIL("0011", "Tài khoản đăng nhập hoặc mật khẩu không chính xác"),
    INVALID_JWT_SIGNATURE("0012", "Token có chữ ký không hợp lệ"),
    INVALID_JWT_TOKEN("0013", "Token không hợp lệ"),
    EXPIRED_JWT_TOKEN("0014", "Token hết hiệu lực"),
    UNSUPPORTED_JWT_TOKEN("0015", "Token có định dạng không được hỗ trợ"),
    OTP_INVALID_FOR_USER("0016", "OTP không đúng cho user"),
    OTP_INVALID_FOR_TRANS("0017", "OTP không đúng cho giao dịch"),
    OTP_INVALID_FOR_TYPE("0018", "OTP không đúng loại"),
    OTP_INVALID_FOR_PHONE("0019", "OTP không đúng"),

    OTP_EXPIRY("0020", "OTP đã hết hạn"),
    OTP_INACTIVE("0021", "OTP đã bị xác thực trước đó"),
    TOKEN_NOT_FOUND("0028", "Hết phiên làm việc, vui lòng đang nhập lại để tiếp tục."),

    FILE_NOT_FOUND("0040", "Tệp dữ liệu không tồn tại"),
    FILE_TOO_LARGE("0041", "Tệp tải lên vượt quá 2MB"),
    FILE_EXTENSION_INVALID("0041", "Tệp tải lên sai định dạng"),
    FILE_SIGNATURE_INVALID("0042", "File chưa có chữ ký số"),
    IS_EXITS_FILE_REPORT("0030", "Đã có tệp báo cáo không được phép xóa!"),
    EXISTS_SUPPLY_NUMB_DECI("0031", "Số quyết định đã tồn tại!"),
    CODE_DECI_REF_IS_USING("0032", "QĐ đã sử dụng làm QĐ tham chiếu nên không cho phép chỉnh sửa"),
    UNAUTHORIZED("0401", "Không có quyền thực hiện"),
    SUPPLIER_TIME_OUT("9997", "Quá thời gian chờ phản hồi từ nhà cung cấp"),
    SUPPLIER_ERROR("9998", "Lỗi từ nhà cung cấp"),
    SEND_EMAIL_NO_SUCCESS("0052", "Gửi email không thành công"),
    EMAIL_IS_EXISTS("0053", "Email đã tồn tại"),
    USER_EXPIRED("0053", "Mật khẩu đã hết hạn sử dụng.!"),
    NOT_FORMAT_PASS("0055", "Mật khẩu định dạng không đúng.!"),
    ACCEPTED_REQUEST("0056", "Tiếp nhận yêu cầu"),
    CAPTCHA_INVALID("0057", "Captcha không đúng hoặc hết hạn"),
    SYSTEM_ERROR("9999", "Lỗi hệ thống");


    private String responseCode;
    private String responseMessage;

    private Response(String responseCode, String responseMessage) {
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append("\"responseCode\":").append("\"").append(this.responseCode).append("\"").append(",");
        stringBuilder.append("\"responseMessage\":").append("\"").append(this.responseMessage).append("\"");
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}
