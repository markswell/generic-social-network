package com.markswell.dto;

import com.markswell.domain.Post;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostResponse {

    private String text;
    private LocalDateTime dateTime;

    public static PostResponse fromEntity(Post post) {
        var postResponse = new PostResponse();
        postResponse.setText(post.getText());
        postResponse.setDateTime(post.getDateTime());
        return postResponse;
    }

}
