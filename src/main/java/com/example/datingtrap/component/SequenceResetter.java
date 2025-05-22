package com.example.datingtrap.component;

import jakarta.annotation.PostConstruct;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class SequenceResetter {
    private final JdbcTemplate jdbcTemplate;

    public SequenceResetter(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    public void resetUserSequence() {
        // Giả sử max id hiện tại bạn lấy được là 42
        Long maxId = jdbcTemplate.queryForObject("SELECT MAX(id) FROM users", Long.class);
        if (maxId == null) {
            maxId = 0L;
        }
        // reset sequence để lần nextval trả về maxId + 1
        jdbcTemplate.execute("SELECT setval('public.users_id_seq', " + maxId + ", true)");
    }
}
