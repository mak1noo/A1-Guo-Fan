# PROG2004 Assessment 1 交接说明

最后更新：2026-11-14

## 当前状态

本项目已完成 PROG2004 Assessment 1 的 Java 医院预约管理系统类骨架，并创建了
独立私有 GitHub 仓库：

```text
本地目录：C:\Users\1\Desktop\学习\OOP\A1-mak1noo
GitHub：https://github.com/mak1noo/A1-mak1noo
可见性：Private
```

目录名使用已验证的 GitHub 登录账号 `mak1noo`。如果学校 MySCU 用户名不同，提交前
只需把目录、ZIP 文件名和 GitHub 仓库名同步改成 `A1-对应的MySCU用户名`。

本项目不是完整应用，不包含 UI、登录、数据库、文件保存、菜单或用户输入。
全部演示都位于 `AssignmentOne.main()`。

## 本地源码来源

按项目所有者说明，真实代码工作副本是：

```text
C:\Users\1\Desktop\学习\OOP\A1-mak1noo\src - 副本
```

根目录 `src` 同时用于本地 WakaTime 工作流。为避免破坏现有 WakaTime 配置，
没有重命名或移动这两个目录。上传前已从 `src - 副本` 直接编译和运行，并逐文件
校验它与仓库中 `src` 的 10 个 Java 文件 SHA-256 完全一致。

以后修改真实代码时，应先更新 `src - 副本`，再同步到仓库的 `src`，最后分别从
真实副本和仓库源码重新编译、运行并提交。

## 源码清单

源码目录：`C:\Users\1\Desktop\学习\OOP\A1-mak1noo\src`

| 文件 | 职责 |
|---|---|
| `AssignmentOne.java` | Part 3 与 Part 6 的主演示 |
| `ClinicSchedule.java` | 星期、固定时间槽和合法性校验 |
| `HealthProfessional.java` | 抽象基类、封装、比较、相等性 |
| `GeneralPractitioner.java` | GP 子类和处方行为 |
| `Specialist.java` | 专科医生子类和转诊行为 |
| `Nurse.java` | 护士子类和采血行为 |
| `Surgeon.java` | 外科医生子类和排手术行为 |
| `Patient.java` | 患者姓名和手机号校验 |
| `Appointment.java` | 患者、专业人员和时间的组合 |
| `AppointmentManager.java` | 预约添加、冲突、过滤、排序和取消 |

## 收尾时完成的修改

- Part 6 增加 16:00 边界预约，证明固定日程包含 16:00。
- Part 6 分别成功演示按患者手机号、专业人员 ID 和患者姓名取消预约。
- Part 6 增加 17:00 非法时间错误路径。
- `Patient` 先去除手机号两端空白再校验，保持输入校验顺序一致。
- 已确认最终源码来自真实工作副本 `src - 副本`，并同步到仓库 `src`。
- `.gitignore` 排除 `build/`、临时构建目录、`.class`、`.zip`、`.vscode/`
  和 `src - 副本/`，避免把同一份源码重复提交。
- README、DESIGN 和 HANDOFF 已同步当前目录、演示路径和私有仓库信息。

## 白名单约束

- 只使用 Module 1-3 中允许的 Java 概念。
- 时间使用 `String` 常量配合 `ClinicSchedule` 校验。
- 预约排序使用 `Comparable` 和 `Collections.sort()`。
- 不使用 `enum`、独立 `Comparator`、`Stream`、`lambda`、`Optional`、
  `record`、`Map`、文件 IO、`Scanner`、`java.time`、GUI 或第三方库。
- 代码注释只使用简短英文 Javadoc 或必要的英文内联注释。

## 验证命令

在项目目录执行：

```powershell
New-Item -ItemType Directory -Force -Path .\build | Out-Null
javac -Xlint:all -d .\build (Get-ChildItem -LiteralPath .\src -Filter *.java | ForEach-Object { $_.FullName })
java -cp .\build AssignmentOne
```

最终验收通过标准：

- `javac -Xlint:all` 没有错误或警告。
- Part 3 通过父类型 `ArrayList<HealthProfessional>` 统一展示 8 名专业人员。
- Part 6 演示成功、冲突、休息日、非法时间和三种取消搜索键。
- 最终剩余预约数为 5。
- 白名单关键词扫描没有命中禁用概念。

## 提交前剩余事项

1. 如果 MySCU 用户名不是 `mak1noo`，同步修改目录名、ZIP 文件名和 GitHub 仓库名。
2. 提交 WakaTime 报告，文件名为 `A1-username-WakaTime.[png/jpg/pdf]`。
3. 录制不超过 5 分钟的四支柱 OOP 视频并上传到 SCU OneDrive。
4. 根据实际使用情况在 Blackboard 评论中填写 GenAI declaration。
5. 私有仓库如果要求评阅，邀请 tutor 和 unit assessor 为 collaborator。

## 重要限制

- 本仓库创建于 2026-11-14，无法补造评估周期内并不存在的历史提交。
  不要为了看起来“进度丰富”而伪造提交时间或虚假的增量开发记录。
- 作业 PDF 规定不能使用 GenAI 代写解决方案代码、代码注释或视频内容。
  只有在确认代码符合课程规则、能够独立解释每一行且按实际情况完成声明后，
  才能作为个人作品提交。
- 不要把本项目推送到 `mak1noo/Dunbar-Veterinary-Clinic`。
