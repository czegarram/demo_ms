package czegarram.demo.opms.persistence.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import czegarram.demo.opms.util.Constants;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
public class Comment {

    private Long commentId;

    @NotNull
    private String userId;
    @NotNull
    private String storeId;
    @NotNull
    private String purchaseId;

    @NotNull
    @JsonFormat(pattern = Constants.ISO8601_PATTERN)
    @DateTimeFormat(pattern = Constants.ISO8601_PATTERN)
    private Date date;

    @NotNull
    private String commentText;

    @NotNull
    @Min(value=1)
    @Max(value=5)
    private Integer score;

    private boolean active;
}