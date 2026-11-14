# 医院预约管理系统：类设计与提交指南

> 说明：作业 PDF 规定不得使用 GenAI 代写解决方案代码或注释。本文件和 `src`
> 目录是参考实现，不应未经改写直接作为个人作品提交。

## 1. 类清单与职责表

| 类 | 职责 | 对应 Part | 与其他类的关系 |
|---|---|---|---|
| `AssignmentOne` | 编写 `main`，演示 Part 3 与 Part 6 | Part 3、6 | 创建并依赖所有领域类 |
| `ClinicSchedule` | 保存星期名称、固定时间槽与合法性校验 | Part 1、4、5 | 被专业人员、预约和管理器使用 |
| `HealthProfessional` | 抽象基类，保存共有信息并规定统一行为 | Part 1、2 | 被四个具体类型继承；实现 `Comparable<HealthProfessional>` |
| `GeneralPractitioner` | 保存执照号，执行处方行为 | Part 2 | 继承 `HealthProfessional` |
| `Specialist` | 保存专科名称，执行转诊行为 | Part 2 | 继承 `HealthProfessional` |
| `Nurse` | 保存护理单元，执行采血行为 | Part 2 | 继承 `HealthProfessional` |
| `Surgeon` | 保存手术室，执行排手术行为 | Part 2 | 继承 `HealthProfessional` |
| `Patient` | 保存并校验患者姓名和手机号 | Part 4 | 被 `Appointment` 组合 |
| `Appointment` | 组合患者、任意专业人员和一个时间字符串 | Part 4 | 实现 `Comparable<Appointment>` |
| `AppointmentManager` | 管理预约集合、冲突、过滤、排序与取消 | Part 5、6 | 聚合 `ArrayList<Appointment>` |

## 2. 文字版类图

```text
HealthProfessional
|-- GeneralPractitioner
|-- Specialist
|-- Nurse
`-- Surgeon

HealthProfessional ..|> Comparable<HealthProfessional>
HealthProfessional has-a ArrayList<String> availableDays

Appointment has-a Patient
Appointment has-a HealthProfessional
Appointment has-a String timeSlot
Appointment ..|> Comparable<Appointment>

AppointmentManager has-a ArrayList<Appointment>
AppointmentManager has-a String clinicDay

ClinicSchedule ..> HealthProfessional
ClinicSchedule ..> Appointment
ClinicSchedule ..> AppointmentManager

AssignmentOne ..> AppointmentManager
AssignmentOne ..> all concrete health professional types
```

关系重点：

- 四个具体专业人员与基类之间是 is-a 继承关系。
- `Appointment` 与 `Patient`、`HealthProfessional` 之间是 has-a 组合关系。
- `Appointment` 用 `String` 保存受 `ClinicSchedule` 校验的时间值。
- `AppointmentManager` 聚合预约集合。
- 两个类实现 `Comparable`，全量排序和过滤后排序都调用
  `Collections.sort()`。

## 3. 字段、构造器与方法设计

### 3.1 字段表

| 类 | 字段 | 类型 | 可见性 | final | 设计理由 |
|---|---|---|---|---|---|
| `ClinicSchedule` | 星期名称 | `String` | `public static final` | 是 | 提供统一、可读的星期常量 |
| `ClinicSchedule` | 时间槽名称 | `String` | `public static final` | 是 | 避免在多个类中重复输入时间文本 |
| `ClinicSchedule` | `VALID_DAYS` | `ArrayList<String>` | `private static final` | 是 | 保存允许的星期名称供统一校验 |
| `ClinicSchedule` | `DAILY_SLOTS` | `ArrayList<String>` | `private static final` | 是 | 保存固定日程供统一校验 |
| `HealthProfessional` | `id` | `String` | `private final` | 是 | 身份字段，必须是纯数字 |
| `HealthProfessional` | `name` | `String` | `private final` | 是 | 防止外部绕过封装修改姓名 |
| `HealthProfessional` | `availableDays` | `ArrayList<String>` | `private final` | 是 | 保存受控上班日集合 |
| `GeneralPractitioner` | `medicalLicenceNumber` | `String` | `private final` | 是 | 保存该类型特有信息 |
| `Specialist` | `specialty` | `String` | `private final` | 是 | 保存专科信息 |
| `Nurse` | `nursingUnit` | `String` | `private final` | 是 | 保存护理单元信息 |
| `Surgeon` | `operatingTheatre` | `String` | `private final` | 是 | 保存手术室信息 |
| `Patient` | `name` | `String` | `private final` | 是 | 保存并保护患者姓名 |
| `Patient` | `mobile` | `String` | `private final` | 是 | 保存并保护已校验手机号 |
| `Appointment` | `patient` | `Patient` | `private final` | 是 | 预约必须关联一个患者 |
| `Appointment` | `healthProfessional` | `HealthProfessional` | `private final` | 是 | 使用父类型接收任意专业人员 |
| `Appointment` | `timeSlot` | `String` | `private final` | 是 | 保存已校验的固定时间文本 |
| `AppointmentManager` | `appointments` | `ArrayList<Appointment>` | `private final` | 是 | 管理器独占预约集合 |
| `AppointmentManager` | `clinicDay` | `String` | `private final` | 是 | 保存当前诊所日 |

### 3.2 构造器列表

| 类 | 构造器签名 | 可见性 | 理由 |
|---|---|---|---|
| `ClinicSchedule` | `ClinicSchedule()` | `private` | 该类只提供静态常量和方法，不需要实例 |
| `HealthProfessional` | `(String id, String name, ArrayList<String> availableDays)` | `protected` | 子类调用全参构造器并完成共有状态初始化 |
| `HealthProfessional` | `(String id, String name)` | `protected` | 提供周一至周五默认值 |
| `GeneralPractitioner` | 三个参数版本 | `public` | 使用周一、三、五默认上班日 |
| `GeneralPractitioner` | 加入 `ArrayList<String>` 的全参版本 | `public` | 允许自定义上班日 |
| `Specialist` | 三个参数版本 | `public` | 使用周一至周五默认上班日 |
| `Specialist` | 加入 `ArrayList<String>` 的全参版本 | `public` | 允许不同专科医生设置不同日程 |
| `Nurse` | 三个参数版本 | `public` | 使用周一至周五默认上班日 |
| `Nurse` | 加入 `ArrayList<String>` 的全参版本 | `public` | 支持轮班日程 |
| `Surgeon` | 三个参数版本 | `public` | 使用周一、四、五默认手术日 |
| `Surgeon` | 加入 `ArrayList<String>` 的全参版本 | `public` | 允许调整手术日 |
| `Patient` | `(String name, String mobile)` | `public` | 创建时完成姓名和手机号校验 |
| `Appointment` | `(Patient, HealthProfessional, String)` | `public` | 所有关联必须存在，时间必须属于固定日程 |
| `AppointmentManager` | `(String clinicDay)` | `public` | 创建空预约集合并保存当前诊所日 |

### 3.3 主要方法

| 类 | 方法 | 说明 |
|---|---|---|
| `ClinicSchedule` | `getValidDays()`、`getDailySlots()` | 返回新 `ArrayList`，避免外部修改内部数据 |
| `ClinicSchedule` | `isValidDay(String)`、`isScheduledSlot(String)` | 统一验证星期与预约时间 |
| `HealthProfessional` | `getId()`、`getName()` | 公共只读访问 |
| `HealthProfessional` | `getAvailableDays()` | 返回上班日集合副本 |
| `HealthProfessional` | `worksOn(String)` | 判断专业人员是否在指定日期上班 |
| `HealthProfessional` | `getRoleDescription()` | 抽象方法，由具体类型提供角色 |
| `HealthProfessional` | `performDuty()` | 抽象方法，由具体类型提供职责 |
| `HealthProfessional` | `compareTo(HealthProfessional)` | 按 ID 实现自然排序 |
| `HealthProfessional` | `equals(Object)`、`hashCode()` | 同一 ID 被视为同一专业人员；哈希值使用 `Objects.hash(id)` |
| `HealthProfessional` | `toString()` | 输出公共信息 |
| 四个子类 | 特有行为 | 处方、转诊、采血、排手术 |
| 四个子类 | `getRoleDescription()`、`performDuty()`、`toString()` | 使用 `@Override`，并在 `toString()` 中复用 `super.toString()` |
| `Patient` | `getName()`、`getMobile()`、`toString()` | 公共只读访问与完整输出 |
| `Appointment` | 三个 getter、`compareTo()`、`toString()` | 只读关联、按时间自然排序、完整输出 |
| `AppointmentManager` | `addAppointment(...)` | 检查必填值、固定时间、上班日和医生冲突 |
| `AppointmentManager` | 三个取消方法 | 分别支持姓名加时间、手机号加时间、专业人员 ID 加时间 |
| `AppointmentManager` | 五个 display 方法 | 显示全部、按医生过滤、按手机号过滤及过滤后排序 |
| `AppointmentManager` | 私有查询、冲突和打印方法 | 对调用方隐藏实现细节 |
| `AssignmentOne` | `main`、`printHeading` | 执行演示并统一标题格式 |

## 4. 概念落点表

| 白名单概念 | 代码落点 | 作用 |
|---|---|---|
| class / object | 所有领域类与 `main` 中创建的对象 | 用对象表示现实实体 |
| encapsulation | 所有类的 `private` 字段和 `public` getter | 防止外部直接破坏状态 |
| private fields + public getters | `HealthProfessional`、`Patient`、`Appointment`、`AppointmentManager` | 控制读写权限 |
| constructor validation | `HealthProfessional`、`Patient`、`Appointment`、`AppointmentManager` | 在创建时拒绝无效状态 |
| inheritance | 四个具体类型 `extends HealthProfessional` | 共用字段、校验和公共方法 |
| composition | `Appointment` 持有 `Patient` 和 `HealthProfessional` | 表达预约的 has-a 关系 |
| polymorphism | `ArrayList<HealthProfessional>` 的统一遍历 | 父类型变量处理不同子类对象 |
| abstraction | `HealthProfessional` 抽象类和两个抽象方法 | 定义统一契约 |
| abstract class / abstract method | `HealthProfessional` | 阻止创建没有具体角色的对象 |
| interface / implements | `HealthProfessional` 和 `Appointment` 实现 `Comparable` | 使用排序能力契约 |
| method overriding / @Override | 子类行为、`toString()`、`compareTo()` | 让具体类型提供自己的实现 |
| dynamic binding | 循环调用 `getRoleDescription()` 与 `performDuty()` | 运行时根据真实对象类型调用方法 |
| parent-type reference | `Appointment` 的 `HealthProfessional` 字段 | 让一个预约接受所有专业人员类型 |
| `ArrayList<ParentType>` | `main` 中的 `ArrayList<HealthProfessional>` | 同时保存四种具体类型 |
| `toString()` | 基类、子类、患者和预约 | 输出可读对象信息 |
| `super.toString()` | 四个专业人员的 `toString()` | 复用基类公共格式 |
| `equals()` / `hashCode()` | `HealthProfessional` | 按 ID 判断身份并保持契约一致 |
| `Objects.hash(...)` | `HealthProfessional.hashCode()` | 生成与 ID 一致的哈希值 |
| `instanceof` / casting | `HealthProfessional.equals()` | 完成类型检查和安全向下转换 |
| constructor overloading | 基类与四个子类 | 同时支持默认和自定义上班日 |
| `this(...)` | 四个子类构造器 | 在一个构造器中复用另一个构造器 |
| `super(...)` | 四个子类构造器 | 初始化父类共有字段 |
| `static final constants` | `ClinicSchedule` 的星期与时间常量 | 避免重复字符串 |
| `ArrayList` | 上班日、专业人员、预约与过滤结果 | 课程范围内的有序集合 |
| `Comparable<T>` | `HealthProfessional`、`Appointment` | 提供对象自然排序 |
| simple collection sorting | `Collections.sort(...)` | 对全部预约和过滤子集排序 |
| if / else / loop | 校验、冲突、查找、过滤 | 表达业务规则并遍历集合 |
| String 与连接 | ID、姓名、手机号、时间、提示信息 | 满足当前数据模型 |
| defensive copy | `getAvailableDays()`、`getAppointments()` 等 | 防止外部修改内部集合 |
| IllegalArgumentException | 构造器中的非法 ID、手机号、日期、时间 | 在源头拒绝无效对象 |
| helper methods | 输入校验、查询、过滤、打印 | 避免重复代码 |

## 5. Part 3 演示脚本

1. 创建 3 个 `GeneralPractitioner`。
2. 创建 2 个 `Specialist`。
3. 创建 2 个 `Nurse`。
4. 创建 1 个 `Surgeon`。
5. 将全部对象加入 `ArrayList<HealthProfessional>`。
6. 调用 `Collections.sort(professionals)`，按 ID 排序。
7. 用普通 `for` 循环统一打印完整对象、角色和职责。
8. 分别调用处方、转诊、采血与排手术行为。
9. 创建同 ID 的临时 GP 引用，验证 `equals()` 与 `hashCode()`。

每条输出的格式为：

```text
序号. 完整对象信息
   Role: 具体角色
   Duty: 具体职责
```

## 6. Part 6 演示脚本

| 情形 | 操作与预期输出 |
|---|---|
| 建预约 | 每次成功输出 `SUCCESS: Appointment booked...` |
| 显示全部 | 输出当前诊所日和全部预约 |
| 按医生过滤 | 只输出指定专业人员 ID 的预约 |
| 按患者过滤 | 只输出指定手机号的预约 |
| 全部排序 | 使用 `Collections.sort()` 从 09:00 往后显示 |
| 过滤后排序 | 对医生或患者子集再次调用 `Collections.sort()` |
| 取消（手机号） | 用患者手机号和预约时间输出 `SUCCESS: Appointment cancelled...` |
| 取消（专业人员 ID） | 用专业人员 ID 和预约时间输出 `SUCCESS: Appointment cancelled...` |
| 取消（患者姓名，补充能力） | 用患者姓名和预约时间输出 `SUCCESS: Appointment cancelled...` |
| 再次显示 | 被取消记录消失，编号重新连续，最终剩余 5 条预约 |
| 冲突预约 | 输出 `Booking failed: ... already has an appointment...` |
| 取消不存在 | 输出 `Cancellation failed: no appointment found...` |
| 休息日预约 | 输出 `Booking failed: ... does not work on Monday.` |
| 非法时间 | 输出 `Booking failed: 17:00 is not in the daily schedule.` |

八种必需情形全部覆盖，并额外展示三种取消搜索键、16:00 边界预约、
手机号过滤、子集排序、休息日错误和非法时间错误。

## 7. HD 自检清单

PDF 评分表有 8 个评分行：Part 1–6、视频和专业实践。

| 评分项 | 检查点 |
|---|---|
| Part 1，10% | 抽象基类保存并校验 ID、姓名、上班日；包含构造器重载和完整 `toString()` |
| Part 2，20% | 四个具体类型均有特有字段、行为和完整输出；基类不可直接创建 |
| Part 3，10% | 使用父类型 `ArrayList` 统一遍历；动态绑定展示多态 |
| Part 4，15% | 患者独立建模；预约接受任意专业人员；关联字段不可变 |
| Part 5，15% | 固定日程、上班日检查、冲突检查、过滤、排序、取消与警告消息全部实现 |
| Part 6，10% | 正常与错误案例完整，取消前后输出清楚 |
| 视频，10% | 四支柱各有至少两个可指认位置和设计理由 |
| 专业实践，10% | 真实 GitHub 进度、WakaTime 报告、英文 Javadoc、清晰命名与一致格式 |

## 8. GitHub 提交主题建议

1. 初始化项目和 `AssignmentOne`
2. 建立固定诊所日程常量与校验
3. 完成健康专业人员抽象基类
4. 补充构造器重载和只读访问
5. 完成 GP 特化
6. 完成其余三个专业人员类型
7. 完成父类型集合、多态与排序
8. 完成 Patient 和 Appointment
9. 完成添加、上班日与冲突检查
10. 完成过滤、排序和三种取消方式
11. 完成 Part 6 正常与错误演示
12. 整理 Javadoc、格式和最终自查

必须按真实开发过程提交。

## 9. 视频四支柱指认点

### Encapsulation

- `HealthProfessional` 的私有 final 字段与公共 getter。
- `getAvailableDays()` 和 `getAppointments()` 返回集合副本。
- `Patient` 在构造器中校验手机号。

### Inheritance

- 四个具体类 `extends HealthProfessional`。
- 子类构造器通过 `super(...)` 初始化共有字段。
- 子类 `toString()` 复用 `super.toString()`。

### Polymorphism

- `ArrayList<HealthProfessional>` 同时保存四种对象。
- 同一个循环调用 `getRoleDescription()` 和 `performDuty()`。
- `Appointment` 使用父类型字段接收任意专业人员。

### Abstraction

- `HealthProfessional` 是不能直接实例化的抽象类。
- 两个抽象方法建立所有子类必须实现的统一契约。
- `AppointmentManager` 用私有 helper 方法隐藏内部查找算法。

## 10. 新手易错点

| 易错点 | 错在哪 | 自查方法 |
|---|---|---|
| `equals()` 与 `hashCode()` 不一致 | 相等规则使用 ID，哈希却使用姓名 | 两个同 ID 对象必须同时满足 `equals()` 为 true、哈希值相同 |
| `super(...)` 位置错误 | 未放在子类构造器第一条语句 | 检查每个子类构造器开头 |
| 重复写公共 `toString()` 内容 | 子类重复实现 ID、姓名和日期格式 | 先调用 `super.toString()` |
| 时间用字符串却未校验 | 任意文本都可能进入预约 | 所有时间必须经过 `ClinicSchedule.isScheduledSlot()` |
| getter 直接返回内部集合 | 外部可清空或添加非法元素 | 返回新的 `ArrayList` |
| 冲突检查使用对象引用比较 | 不同对象即使同 ID 也可能被当成不同医生 | 使用重写后的 `equals()` |
| 只在 `main` 中检查输入 | 其他调用路径仍可创建非法对象 | 把校验放在构造器或管理器入口 |
| 排序逻辑写在多个 display 方法中 | 规则重复且容易不一致 | 统一依赖 `Appointment.compareTo()` 和 `Collections.sort()` |

## 时间槽边界

当前日程按作业字面包含 `08:00` 到 `16:00`。如果老师说明 `16:00` 只是闭馆
时间，只需从 `ClinicSchedule.createDailySlots()` 中删除 `SLOT_16_00`。其他
类会自动使用更新后的日程。
