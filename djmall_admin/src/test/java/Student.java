/*
 * 作者：CF
 * 日期：2020-07-06 10:25
 * 项目：djmall
 * 模块：djmall_admin
 * 类名：Student
 * 版权所有(C), 2020. 所有权利保留
 */

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
