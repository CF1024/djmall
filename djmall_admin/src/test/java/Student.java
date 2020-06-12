import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Student implements Serializable {
    /**
     * 主键id
     */
    private Integer id;
    /**
     * 学生姓名
     */
    private String studentName;
    /**
     * 学科
     */
    private String subject;
    /**
     * 分数
     */
    private Integer score;
}
