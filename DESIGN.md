# 医院预约管理系统：最终类设计与评分依据

> 作业 PDF 规定不能使用 GenAI 代写解决方案代码、代码注释或视频内容。本文档
> 是设计参考，提交前必须遵守课程规则并确保能够独立解释全部实现。

## 1. 类清单与职责

| 类 | 职责 | 对应 Part |
|---|---|---|
| `AssignmentOne` | 运行 Part 3、Part 6 脚本演示 | 3、6 |
| `HealthProfessional` | 抽象基类、公共字段、校验、比较和相等性 | 1、2 |
| `GeneralPractitioner` | GP 特有字段、处方行为 | 2 |
| `Specialist` | 专科字段、转诊行为 | 2 |
| `Nurse` | 护理单元、采血行为 | 2 |
| `Surgeon` | 手术室、排手术行为 | 2 |
| `Patient` | 姓名、手机号规范化和格式校验 | 4 |
| `TimeSlot` | 小时/分钟时间值、格式输出和自然排序 | 4 |
| `ClinicSchedule` | 模拟当前日、08:00–15:30 固定日程 | 4、5 |
| `Appointment` | 组合患者、任意专业人员和有效时间 | 4 |
| `AppointmentManager` | 添加、冲突、过滤、排序和取消 | 5、6 |

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
Appointment has-a TimeSlot
Appointment ..|> Comparable<Appointment>

TimeSlot ..|> Comparable<TimeSlot>
ClinicSchedule has-a ArrayList<TimeSlot> dailySlots
AppointmentManager has-a ArrayList<Appointment>
AppointmentManager has-a currentDay

AssignmentOne ..> AppointmentManager
AssignmentOne ..> all concrete professional types
```

## 3. 关键设计

### HealthProfessional

- `id`、`name`、`availableDays` 均为 `private final`。
- ID 必须是纯数字，姓名不能为空。
- 上班日会 trim、转大写、校验并去重。
- `getAvailableDays()` 返回副本。
- 两个 protected 构造器支持完整日程和周一至周五默认日程。
- 两个抽象方法要求子类提供角色和职责。
- `equals()` 和 `hashCode()` 按 ID 保持契约一致。
- `Comparable<HealthProfessional>` 按 ID 排序。

### TimeSlot

- 不使用违规的高级时间 API。
- `hour` 和 `minute` 都是 `private final`。
- 小时范围 0–23，分钟只允许 0 或 30。
- `compareTo()` 使用一天中的分钟数，不按字符串排序。
- `equals()` 和 `hashCode()` 使用小时与分钟。
- `toString()` 使用 `String.format()` 输出 `HH:mm`。

### ClinicSchedule

- `CURRENT_DAY` 明确表示模拟的当前日 `MONDAY`。
- 从 08:00 循环生成到 16:00，但不包含终点。
- 最后可预约时间是 15:30，共 16 个时间槽。
- `getDailySlots()` 返回副本。
- `Appointment` 和 Manager 共用同一份日程校验规则。

### Patient

- 姓名必须非空。
- 手机号先移除空格和连字符，再校验 10 位并必须以 `04` 开头。
- 规范化后的连续数字保存在 final 字段中。
- `normalizeMobile()` 同时供取消和过滤使用，避免输入格式不一致。

### Appointment

- 三个关联字段均不可为空且为 final。
- `TimeSlot` 必须属于 `ClinicSchedule`。
- `compareTo()` 先比时间，再以专业人员 ID 稳定排序。
- `toString()` 输出时间、患者、专业人员角色、ID 和姓名，避免嵌套完整对象。

### AppointmentManager

- 无参构造器使用模拟当前日。
- 带参构造器只接受当前日，其他日期直接拒绝。
- `addAppointment()` 检查空值、固定时间、上班日和重复预约。
- 过滤和排序都使用预约集合的副本。
- `cancelAppointmentByPhone(mobile)` 提供 Brief 要求的单参数电话取消。
- 多条预约匹配同一手机号时返回歧义警告。
- `cancelAppointmentByPhone(mobile, timeSlot)` 解决多条预约歧义。
- `cancelAppointmentByProfessional(id, timeSlot)` 是第二种规定取消方式。
- `cancelAppointment(name, timeSlot)` 是补充能力。
- 所有添加和取消操作都返回明确的 `SUCCESS` 或失败信息。

## 4. 概念落点

| 概念 | 落点 |
|---|---|
| 封装 | private final 字段、getter、集合副本 |
| 构造器校验 | 专业人员、患者、时间、预约和 Manager |
| 构造器重载 | `HealthProfessional`、四个子类、Manager |
| `this(...)` | 子类构造器和 Manager 无参构造器 |
| `super(...)` | 子类初始化共有字段 |
| 抽象类/抽象方法 | `HealthProfessional` |
| 继承 | 四个专业人员子类 |
| 多态 | `ArrayList<HealthProfessional>` 统一遍历 |
| 动态绑定 | `getRoleDescription()`、`performDuty()` |
| `@Override` | `compareTo()`、`equals()`、`hashCode()`、`toString()` |
| `super.toString()` | 四个子类复用基类格式 |
| `equals()/hashCode()` | 专业人员按 ID，时间按小时/分钟 |
| `Comparable` | 专业人员、预约、时间 |
| `Collections.sort()` | 人员排序、全部和过滤后预约排序 |
| `ArrayList` | 上班日、专业人员、时间槽、预约、过滤结果 |
| `static final` | 当前日、星期、日程常量和固定字段 |
| `String.format()` | TimeSlot 的 `HH:mm` 输出 |
| `Arrays` 替代项 | 本项目统一使用 `ArrayList` 和集合副本 |
| `if/switch/循环` | 校验、冲突、过滤、取消和日程生成 |

## 5. Part 3 演示

1. 创建 3 个 GP。
2. 创建 2 个 Specialist。
3. 创建 2 个 Nurse。
4. 创建 1 个 Surgeon。
5. 放入 `ArrayList<HealthProfessional>`。
6. 使用 `Collections.sort()` 按 ID 排序。
7. 用普通 for 循环统一打印对象、角色和职责。
8. 分别调用处方、转诊、采血和排手术行为。
9. 用相同 ID 的两个 GP 对象验证 `equals()` 和 `hashCode()`。

## 6. Part 6 演示

| 场景 | 结果 |
|---|---|
| 当前日 | 输出 `Clinic day: MONDAY` |
| 日程 | 输出 08:00 到 15:30 的 16 个槽 |
| 添加 | 输出 `SUCCESS: Appointment booked...` |
| 显示全部 | 输出完整预约摘要 |
| 过滤 | 按专业人员 ID 和手机号过滤 |
| 排序 | 全部和过滤子集都按 TimeSlot 时间排序 |
| 电话单条取消 | 直接输出成功信息 |
| 电话多条取消 | 输出 `Cancellation failed: multiple appointments...` |
| 精确电话取消 | 手机号加时间成功取消 |
| 专业人员取消 | 专业人员 ID 加时间成功取消 |
| 姓名取消 | 姓名加时间成功取消 |
| 重复预约 | 输出冲突警告 |
| 取消不存在 | 输出三种 no appointment found 警告 |
| 休息日预约 | 输出专业人员当天不上班 |
| 16:00 | 输出 `Booking failed: 16:00 is not in the daily schedule.` |
| 最终数量 | 3 条预约 |

## 7. HD 评分自检

| 评分项 | 证据 |
|---|---|
| Part 1，10% | 抽象基类、数字 ID、只读访问、构造器重载、比较和相等性 |
| Part 2，20% | GP、Specialist、Nurse、Surgeon 四个类型，各有特有字段和行为 |
| Part 3，10% | 父类型集合、多态循环、类型特有行为演示 |
| Part 4，15% | Patient、TimeSlot、Appointment 和日程成员校验 |
| Part 5，15% | 固定日程、当天限制、冲突、过滤、排序、两种规定取消方式 |
| Part 6，10% | 正常路径、三种取消入口、重复、休息日、16:00 和不存在取消 |
| 视频，10% | 四支柱都能指到具体代码并解释设计理由 |
| 专业实践，10% | Javadoc 零警告、`-Xlint:all -Werror`、契约测试、GitHub、WakaTime |

## 8. GitHub 提交主题

1. 建立基础项目
2. 完成抽象专业人员基类
3. 完成四个专业人员类型
4. 完成 Part 3 多态演示
5. 建立 Patient 与 TimeSlot
6. 建立 Appointment
7. 完成 Manager 添加与冲突
8. 完成过滤和排序
9. 完成电话与专业人员取消
10. 增加契约测试
11. 完成 Javadoc
12. 最终清理与 ZIP

## 9. 视频指认点

### Encapsulation

- 私有 final 字段与 getter。
- 集合副本。
- 构造器校验。

### Abstraction

- 抽象基类。
- 两个抽象方法。
- Manager 对当前日规则和冲突算法的封装。

### Inheritance

- `extends HealthProfessional`。
- `super(...)`。
- `super.toString()`。

### Polymorphism

- `ArrayList<HealthProfessional>`。
- 动态绑定两个抽象方法。
- Appointment 使用父类型专业人员字段。

## 10. 常见错误与自查

| 错误 | 自查 |
|---|---|
| `equals()` 与 `hashCode()` 不一致 | 同 ID 对象必须相等且 hash 相同 |
| `super(...)` 不在第一条语句 | 检查子类构造器 |
| 时间按字符串排序 | 检查 `TimeSlot.compareTo()` 使用分钟数 |
| 16:00 被当预约槽 | 检查日程循环使用 `< END_MINUTES` |
| 手机号格式不一致 | 输入、过滤和取消都必须先规范化 |
| 电话多条预约直接误删 | 检查歧义警告和精确重载 |
| 非当前日可以创建 Manager | 检查带参构造器 |
| getter 暴露内部集合 | 检查所有集合 getter 返回副本 |
