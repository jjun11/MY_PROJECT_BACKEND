package com.projectBackend.project.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class MusicCommentDTO {

    private Long musiccommentId;
    private Long musicId;

    private String userEmail;
    private String userNickname;
    private String content;
}
