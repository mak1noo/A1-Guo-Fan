# PROG2004 Assessment 1 - Hospital Appointment System

## 项目结构

```text
A1-mak1noo/
|-- .gitignore
|-- HANDOFF.md
|-- src/
|   |-- AssignmentOne.java
|   |-- Appointment.java
|   |-- AppointmentManager.java
|   |-- ClinicSchedule.java
|   |-- GeneralPractitioner.java
|   |-- HealthProfessional.java
|   |-- Nurse.java
|   |-- Patient.java
|   |-- Specialist.java
|   |-- Surgeon.java
|   `-- TimeSlot.java
|-- test/
|   `-- OopContractCheck.java
|-- DESIGN.md
`-- README.md
```

## 编译与运行

在 `A1-mak1noo` 目录执行：

```powershell
New-Item -ItemType Directory -Force -Path .\build | Out-Null
javac -Xlint:all -Werror -d .\build (Get-ChildItem -LiteralPath .\src -Filter *.java | ForEach-Object { $_.FullName })
java -cp .\build AssignmentOne
```

运行本地契约检查：

```powershell
javac -Xlint:all -Werror -d .\build (Get-ChildItem -LiteralPath .\src -Filter *.java | ForEach-Object { $_.FullName }) .\test\OopContractCheck.java
java -cp .\build OopContractCheck
```

## 当前日与时间槽

- `ClinicSchedule.CURRENT_DAY` 明确表示本次脚本模拟的当天，值为 `MONDAY`。
- `AppointmentManager` 默认使用当前日，并且拒绝其他日期。
- 诊所开放时间为 08:00 到 16:00。
- 每 30 分钟一个槽位，最后一个可预约起点是 15:30。
- 16:00 作为关门时间，不作为预约起点。
- `TimeSlot` 使用小时和分钟保存时间，并按一天中的分钟数排序。

## 患者手机号

手机号允许输入空格和连字符，系统会先去除这些字符，再验证 10 位澳洲手机号
格式。最终保存的号码是连续数字，例如：

```text
" 0412 345-678 " -> "0412345678"
```

## 电话取消规则

- `cancelAppointmentByPhone(mobile)` 仅使用手机号取消。
- 手机号只对应一条预约时，直接取消。
- 手机号对应多条预约时，返回警告并要求提供时间。
- `cancelAppointmentByPhone(mobile, timeSlot)` 用于解决多条预约歧义。

## Javadoc

公共类、公共方法、受保护方法和公共常量均提供简短英文 Javadoc。最终验证使用：

```powershell
javadoc -Xdoclint:all -quiet -d .\build\docs (Get-ChildItem -LiteralPath .\src -Filter *.java | ForEach-Object { $_.FullName })
```

## 私有仓库

```text
https://github.com/mak1noo/A1-mak1noo
```

## 学术诚信

作业 PDF 规定不得使用 GenAI 代写解决方案代码或注释。本目录是参考实现和设计
说明，不应未经改写直接作为个人作品提交。提交前应遵守课程规定，自行完成最终
代码、注释、视频及 GenAI declaration。
