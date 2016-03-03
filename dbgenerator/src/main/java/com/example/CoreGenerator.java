package com.example;


import java.io.IOException;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class CoreGenerator {
    public static void main(String[] args) throws Exception {
        // 正如你所见的，你创建了一个用于添加2ji实体（Entity）的模式（Schema）对象。
        // 两个参数分别代表：数据库版本号与自动生成代码的包路径。
        Schema schema = new Schema(1, "com.shopex.phone.phone.db");
        addNote(schema);
        // 最后我们将使用 DAOGenerator 类的 generateAll() 方法自动生成代码，此处你需要根据自己的情况更改输出目录
        // （既之前创建的 java-gen)。
        // 其实，输出目录的路径可以在 build.gradle 中设置，有兴趣的朋友可以自行搜索，这里就不再详解。
        new DaoGenerator().generateAll(schema,"D:\\andstudiowork\\Phone\\app\\src\\main\\java-gen");
    }

    public static void addNote(Schema schema){
        //// 一个实体（类）就关联到数据库中的一张表，此处表名为「Note」（既类名）
        Entity note=schema.addEntity("ContactUser");
        // 你也可以重新给表命名
        note.setTableName("ContactUser");
        note.addLongProperty("id").primaryKey().autoincrement();
        note.addStringProperty("name");
        note.addStringProperty("phone").notNull();
    }

}
