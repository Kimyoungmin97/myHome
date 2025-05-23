package com.ssafy.home.user.dto;

import java.util.Base64;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private int userId;
    private @NonNull String username;
    private @NonNull String password;
    private String name;
    private @NonNull String email;
    private String aptSeq;
    private String role;
    private byte[] profileImage;
    private String refreshToken;

    public User(int userId, String username, String email, String password, String role) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public String getProfileImg() {
        if (profileImage != null) {
            return "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(profileImage);
        } else {
            return "/img/profile.png";
        }
    }

}
