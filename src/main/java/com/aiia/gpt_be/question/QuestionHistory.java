package com.aiia.gpt_be.question;


import com.aiia.gpt_be.api.BaseEntity;
import com.aiia.gpt_be.history.dto.HistoryInfo;
import com.aiia.gpt_be.history.dto.HistoryMetaInfo;
import com.aiia.gpt_be.question.dto.QuestionReplyToUser;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuestionHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String question;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String answer;

    private LocalDateTime talkedTime;

    @Builder
    private QuestionHistory(Long id, String question, String answer, LocalDateTime talkedTime) {
        this.id = id;
        this.question = question;
        this.answer = answer;
        this.talkedTime = talkedTime;
    }

    public static QuestionHistory of(String question, String answer, LocalDateTime talkedTime) {
        return QuestionHistory.builder()
                .question(question)
                .answer(answer)
                .talkedTime(talkedTime)
                .build();
    }

    public QuestionReplyToUser toReply(){
        return QuestionReplyToUser.of(question, answer);
    }

    public HistoryMetaInfo toHistoryMetaInfo(){
        return HistoryMetaInfo.of(
                id,
                question,
                talkedTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
    }

    public HistoryInfo toHistoryInfo() {
        return HistoryInfo.of(
                question,
                answer,
                talkedTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
        );
    }
}
