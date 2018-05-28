package com.nick.model.type

/**
 * Created by KangShuai on 2018/5/22.
 */
class access_flags(var flag: Int) {

    companion object {
        val ACC_PUBLIC = 0x1 // public：全部可见 public：全部可见 public：全部可见
        val ACC_PRIVATE = 0x2 // *private：仅对定义类可见 private：仅对定义类可见 private：仅对定义类可见
        val ACC_PROTECTED = 0x4 //   *protected ：对软件包和子类可见    protected ：对软件包和子类可见    protected ：对软件包和子类可见
        val ACC_STATIC = 0x8 //*static：未通过外部 this 引用进行构建    static：对定义类全局可见    static：不采用 this 参数
        val ACC_FINAL = 0x10 //final ：不可子类化    final ：构建后不可变    final ：不可覆盖
        val ACC_SYNCHRONIZED = 0x20 //        synchronized：调用此方法时自动获得关联锁定。注意：这一项仅在同时设置 ACC_NATIVE 的情况下才有效。
        val ACC_VOLATILE = 0x40 //volatile：有助于确保线程安全的特殊访问规则
        val ACC_BRIDGE = 0x40 //  桥接方法，由编译器自动添加为类型安全桥
        val ACC_TRANSIENT = 0x80 // transient：不会通过默认序列化保存
        val ACC_VARARGS = 0x80 //   最后一个参数应被编译器解译为“rest”参数
        val ACC_NATIVE = 0x100 //   native：在本机代码中实现
        val ACC_INTERFACE = 0x200 //    interface：可多倍实现的抽象类
        val ACC_ABSTRACT = 0x400 //abstract ：不可直接实例化        abstract ：不通过此类实现
        val ACC_STRICT = 0x800 //    strictfp：严格的浮点运算规则
        val ACC_SYNTHETIC = 0x1000 //不在源代码中直接定义    不在源代码中直接定义    不在源代码中直接定义
        val ACC_ANNOTATION = 0x2000 //声明为注释类
        val ACC_ENUM = 0x4000 //声明为枚举类型    声明为枚举值
        val ACC_UNUSED = 0x8000 //（未使用）
        val ACC_CONSTRUCTOR = 0x10000 //构造函数方法（类或实例初始化器）
        val ACC_DECLARED_SYNCHRONIZED = 0x20000          //  声明了 synchronized。
    }

    override fun toString(): String {

        var result = ""
        if ((flag and ACC_PUBLIC) == ACC_PUBLIC) result += ("ACC_PUBLIC") + " | "
        if ((flag and ACC_PRIVATE) == ACC_PRIVATE) result += ("ACC_PRIVATE") + " | "
        if ((flag and ACC_PROTECTED) == ACC_PROTECTED) result += ("ACC_PROTECTED") + " | "
        if ((flag and ACC_STATIC) == ACC_STATIC) result += ("ACC_STATIC") + " | "
        if ((flag and ACC_FINAL) == ACC_FINAL) result += ("ACC_FINAL") + " | "
        if ((flag and ACC_SYNCHRONIZED) == ACC_SYNCHRONIZED) result += ("ACC_SYNCHRONIZED") + " | "
        if ((flag and ACC_VOLATILE) == ACC_VOLATILE) result += ("ACC_VOLATILE") + " | "
        if ((flag and ACC_BRIDGE) == ACC_BRIDGE) result += ("ACC_BRIDGE") + " | "
        if ((flag and ACC_TRANSIENT) == ACC_TRANSIENT) result += ("ACC_TRANSIENT") + " | "
        if ((flag and ACC_VARARGS) == ACC_VARARGS) result += ("ACC_VARARGS") + " | "
        if ((flag and ACC_NATIVE) == ACC_NATIVE) result += ("ACC_NATIVE") + " | "
        if ((flag and ACC_INTERFACE) == ACC_INTERFACE) result += ("ACC_INTERFACE") + " | "
        if ((flag and ACC_ABSTRACT) == ACC_ABSTRACT) result += ("ACC_ABSTRACT") + " | "
        if ((flag and ACC_STRICT) == ACC_STRICT) result += ("ACC_STRICT") + " | "
        if ((flag and ACC_SYNTHETIC) == ACC_SYNTHETIC) result += ("ACC_SYNTHETIC") + " | "
        if ((flag and ACC_ANNOTATION) == ACC_ANNOTATION) result += ("ACC_ANNOTATION") + " | "
        if ((flag and ACC_ENUM) == ACC_ENUM) result += ("ACC_ENUM") + " | "
        if ((flag and ACC_UNUSED) == ACC_UNUSED) result += ("ACC_UNUSED") + " | "
        if ((flag and ACC_CONSTRUCTOR) == ACC_CONSTRUCTOR) result += ("ACC_CONSTRUCTOR") + " | "
        if ((flag and ACC_DECLARED_SYNCHRONIZED) == ACC_DECLARED_SYNCHRONIZED) result += ("ACC_DECLARED_SYNCHRONIZED") + " | "
        return result
    }
}

