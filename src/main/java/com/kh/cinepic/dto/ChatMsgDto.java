package com.kh.cinepic.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMsgDto {
    public enum MessageType {
        ENTER, TALK, CLOSE
    }

    private MessageType type;
    private String roomId;
    private String sender;
    private String senderAlias;
    private String msg;
    private String profile;
}
