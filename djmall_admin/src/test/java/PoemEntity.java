/*
 * 作者：CF
 * 日期：2020-07-06 10:25
 * 项目：djmall
 * 模块：djmall_admin
 * 类名：PoemEntity
 * 版权所有(C), 2020. 所有权利保留
 */

public class PoemEntity {
    /**
     * 古诗
     */
    private String Poem;

    public PoemEntity(String Poem) {
        super();
        this.Poem = Poem;
    }

    @Override
    public String toString() {
        return "古诗:" + Poem;
    }
}
