# PROG2004 Assessment 1 - Hospital Appointment System

## 项目结构

```text
A1-mak1noo/
|-- .gitignore
|-- HANDOFF.md
|-- src/
|   |-- AssignmentOne.java
|   |-- ClinicSchedule.java
|   |-- HealthProfessional.java
|   |-- GeneralPractitioner.java
|   |-- Specialist.java
|   |-- Nurse.java
|   |-- Surgeon.java
|   |-- Patient.java
|   |-- Appointment.java
|   `-- AppointmentManager.java
|-- DESIGN.md
`-- README.md
```

## 编译与运行

在 `A1-mak1noo` 目录执行：

```powershell
New-Item -ItemType Directory -Force -Path .\build | Out-Null
javac -Xlint:all -d .\build (Get-ChildItem -LiteralPath .\src -Filter *.java | ForEach-Object { $_.FullName })
java -cp .\build AssignmentOne
```

## 时间槽设置

`ClinicSchedule` 使用 `static final String` 保存星期名称与时间槽，并用私有
`ArrayList` 作为校验来源。当前按作业字面要求包含 `08:00` 到 `16:00`。

如果老师明确说明 `16:00` 只是关门时间，只需从 `createDailySlots()` 中删除
`SLOT_16_00`，其他类不需要修改。

## 私有仓库

```text
https://github.com/mak1noo/A1-mak1noo
```

## 学术诚信

作业 PDF 规定不得使用 GenAI 代写解决方案代码或注释。本目录是参考实现和设计
说明，不应未经改写直接作为个人作品提交。提交前应遵守课程规定，自行完成最终
代码、注释、视频及 GenAI declaration。
