# PROG2004 Assessment 1 视频口播稿

目标时长：4 分 55 秒  
建议画面：PPT 与 VS Code 交替显示  
建议录制：1440p 或 1080p，编辑器字号至少 18pt

> 学术诚信提醒：作业 PDF 规定不能使用 GenAI 生成视频讲解内容。本稿仅作为
> 结构和表达参考。录制前请用自己的语言改写，并确保你能独立解释每一行代码。

## 录制前准备

1. 打开 `AssignmentOne.java`、`HealthProfessional.java`、
   `GeneralPractitioner.java`、`ClinicSchedule.java`、`TimeSlot.java`、
   `Appointment.java` 和
   `AppointmentManager.java`。
2. 先运行一次 `AssignmentOne`，确认控制台没有异常。
3. 录制时先展示 PPT，讲到代码时切到编辑器，指出文件和方法。
4. 保持语速自然。如果超过 5 分钟，优先缩短第 7、8 页。

## 0:00–0:15｜封面

画面：PPT 第 1 页。

口播：

> 大家好，我是 mak1noo。这份作业使用 Java 建立医院预约系统的类骨架。接下来我会指出封装、抽象、继承和多态在代码中的具体位置，并说明这些设计选择的原因。

## 0:15–0:45｜系统结构

画面：PPT 第 2 页。

口播：

> 系统以一个抽象类 `HealthProfessional` 作为共同父类型，下面有 GP、专科医生、护士和外科医生四种类型。`Appointment` 组合患者、专业人员和固定时间，`AppointmentManager` 用 `ArrayList` 管理预约。因为预约字段使用父类型，所以同一个预约可以关联任意一种专业人员。

## 0:45–1:25｜封装

画面：PPT 第 3 页，随后切到 `HealthProfessional.java` 的私有字段和
`getAvailableDays()` 方法。

口播：

> 第一个支柱是封装。`HealthProfessional` 的 ID、姓名和上班日都是私有 final 字段，外部代码只能通过 getter 读取。构造器会验证数字 ID、非空姓名和合法日期，所以对象一旦创建成功，就处于有效状态。`getAvailableDays()` 返回一个新的 `ArrayList`，调用方拿到的是副本，不能修改内部集合。我把校验放在对象内部，而不是依赖 `main` 方法每次都记得检查。

## 1:25–2:00｜抽象

画面：PPT 第 4 页，随后切到 `HealthProfessional.java` 的抽象类声明和两个
抽象方法。

口播：

> 第二个支柱是抽象。`HealthProfessional` 是 abstract class，不能直接创建。它声明了 `getRoleDescription()` 和 `performDuty()` 两个抽象方法，强制每个具体类型说明自己的角色和职责。共享字段和公共逻辑留在基类，差异行为交给子类。这样，系统里不会出现一个没有具体职业的普通健康人员。

## 2:00–2:35｜继承

画面：PPT 第 5 页，随后切到 `GeneralPractitioner.java`。

口播：

> 第三个支柱是继承。四个具体类型都使用 `extends HealthProfessional`，复用 ID、姓名、上班日、输入校验和公共方法。子类构造器通过 `super(...)` 初始化父类字段，再设置自己的执照号、专科、护理单元或手术室。子类的 `toString()` 也会先调用 `super.toString()`，再追加特有信息，所以公共格式只有一份。

## 2:35–3:20｜多态

画面：PPT 第 6 页，随后切到 `AssignmentOne.java` 的父类型集合和多态循环。

口播：

> 第四个支柱是多态。这里用 `ArrayList<HealthProfessional>` 同时保存四种具体对象，循环变量也声明为父类型。调用 `getRoleDescription()` 和 `performDuty()` 时，Java 通过动态绑定执行真实子类的方法。新增一种专业人员时，这段遍历逻辑不需要修改。我还使用 `Collections.sort()` 按 ID 排序，体现了 `Comparable` 的一致设计。

## 3:20–4:00｜预约模型与管理器

画面：PPT 第 7 页，随后切到 `Appointment.java` 和 `AppointmentManager.java`。

口播：

> `Appointment` 持有 `Patient`、父类型的 `HealthProfessional` 和 `TimeSlot`。`TimeSlot` 使用小时和分钟保存时间，并按一天中的分钟数排序。`ClinicSchedule` 明确模拟当前日，并把 16:00 当关门时间，所以最后一个预约起点是 15:30。`AppointmentManager` 负责检查当天、重复预约、过滤、排序和取消。

## 4:00–4:35｜运行与错误案例

画面：PPT 第 8 页，随后切到运行中的 `AssignmentOne` 控制台。

口播：

> 运行演示会创建跨四种专业人员的预约，显示全部、按医生和手机号过滤，并按时间排序。手机号只匹配一条预约时直接取消，匹配多条时返回歧义警告，再用手机号加时间精确取消。错误案例还包括同一医生重复预约、取消不存在预约、医生当天不上班以及 16:00 不是可预约时间。

## 4:35–4:55｜四支柱总结

画面：PPT 第 9 页。

口播：

> 最后总结。封装保护对象状态，抽象定义统一契约，继承复用公共代码，多态让调用方统一处理不同子类。这四个支柱在同一套预约系统中一起工作。我的讲解到这里，谢谢。

## 录制后自查

- 视频不超过 5 分钟。
- 四个支柱都按名称、概念、代码位置和设计理由讲解。
- 至少实际展示一次运行结果。
- 能独立解释 `super`、`@Override`、`Comparable`、防冲突判断和集合副本。
- 上传到 SCU OneDrive，并确认分享权限。
