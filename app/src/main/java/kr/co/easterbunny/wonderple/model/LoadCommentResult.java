package kr.co.easterbunny.wonderple.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by scona on 2017-03-20.
 */

public class LoadCommentResult {

    @SerializedName("result") private String result;
    @SerializedName("message") private String message;
    @SerializedName("comments") private List<Comment> comments = new ArrayList<>();

    public List<Comment> getComments() {
        return comments;
    }
    public String getResult() {
        return result;
    }
    public String getMessage() {
        return message;
    }

    public class Comment {
        @SerializedName("user") private String user;
        @SerializedName("name") private String name;
        @SerializedName("profile_image") private String profileImg;
        @SerializedName("detail") private String detail;
        @SerializedName("cid") private String cid;
        @SerializedName("date") private String date;

        public String getUser() {
            return user;
        }
        public String getName() {
            return name;
        }
        public String getProfileImg() {
            return profileImg;
        }
        public String getDetail() {
            return detail;
        }
        public String getCid() {
            return cid;
        }
        public String getDate() {
            return date;
        }
    }

}
