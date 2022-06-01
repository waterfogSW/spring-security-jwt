package com.example.test.jwt;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.util.Assert;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

public class JwtAuthentication {
    public final String token;
    public final String username;

    public JwtAuthentication(String token, String username) {
        Assert.isTrue(isNotEmpty(token), "Token must be provided");
        Assert.isTrue(isNotEmpty(username), "Token must be provided");

        this.token = token;
        this.username = username;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("token", token)
                .append("username", username)
                .toString();
    }
}
