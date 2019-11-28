package cn.stylefeng.guns.modular.shuheng.state;

public enum NewsModelEnum {


    NEWS("news", "新闻"),
    VIDEO("video", "视频"),
    DOWNLOAD("download", "下载");

    String code;
    String message;

    NewsModelEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static String getMessage(String status) {
        if (status == null) {
            return "";
        } else {
            for (NewsModelEnum s : NewsModelEnum.values()) {
                if (s.getCode().equals(status)) {
                    return s.getMessage();
                }
            }
            return "";
        }
    }

}
