# PROG2004 Assessment 1 最终交接说明

最后更新：2026-11-15

## 当前状态

项目目录：

```text
C:\Users\1\Desktop\学习\OOP\A1-mak1noo
```

GitHub 私有仓库：

```text
https://github.com/mak1noo/A1-Guo-Fan
```

最终源码以唯一的 `src` 目录为准。之前出现的 `src - 副本` 已不存在，不应再按
双目录同步流程操作。

## 最终功能

- 抽象健康专业人员基类。
- GP、Specialist、Nurse、Surgeon 四个具体类型。
- 父类型 `ArrayList`、普通循环和动态绑定。
- 患者手机号规范化与格式校验。
- `TimeSlot` 使用小时和分钟建模并按分钟数排序。
- 模拟当前日为 `MONDAY`。
- 08:00 到 16:00 的日程，最后可预约槽为 15:30。
- 16:00 被明确视为关门时间。
- 同一专业人员同时间冲突检查。
- 按专业人员 ID 和患者手机号过滤。
- 全部和过滤子集按时间排序。
- 手机号单参数取消。
- 多条手机号预约时返回歧义警告。
- 手机号加时间的精确取消重载。
- 专业人员 ID 加时间取消。
- 患者姓名加时间取消。
- 正常和错误案例完整演示。

## 最终验证

```powershell
New-Item -ItemType Directory -Force -Path .\build | Out-Null
javac -Xlint:all -Werror -d .\build (Get-ChildItem -LiteralPath .\src -Filter *.java | ForEach-Object { $_.FullName })
java -cp .\build AssignmentOne
```

契约测试：

```powershell
javac -Xlint:all -Werror -d .\build (Get-ChildItem -LiteralPath .\src -Filter *.java | ForEach-Object { $_.FullName }) .\test\OopContractCheck.java
java -cp .\build OopContractCheck
```

Javadoc：

```powershell
javadoc -Xdoclint:all -quiet -d .\build\docs (Get-ChildItem -LiteralPath .\src -Filter *.java | ForEach-Object { $_.FullName })
```

已验证：

- `javac -Xlint:all -Werror` 通过。
- 契约测试输出 `CONTRACT CHECK PASSED`。
- `javadoc -Xdoclint:all` 无警告。
- 主程序正常和错误路径通过。
- 最终剩余预约数为 3。
- 白名单扫描通过。
- 当前 `src` 没有 `.class` 文件。

## 视频材料

```text
presentation/
|-- output/PROG2004-A1-OOP-Presentation-v4.pptx
`-- VIDEO_SCRIPT.md
```

PPT 共 9 页，含逐页 speaker notes。`VIDEO_SCRIPT.md` 是约 4 分 55 秒的参考
口播稿。当前 PPT 已同步 `TimeSlot`、15:30 最后预约槽和手机号取消歧义规则。

## 提交 ZIP

提交前重新生成：

```text
C:\Users\1\Desktop\学习\OOP\A1-mak1noo.zip
```

ZIP 只应包含：

- `src` 中的 11 个 Java 源文件
- `README.md`
- `DESIGN.md`
- `HANDOFF.md`
- `CHANGELOG.md`

不能包含：

- `A1-YinghaoLiu/`
- `.git/`
- `.vscode/`
- `build/`
- `.codex-finalizer/`
- `.class`
- 测试用构建文件

## 提交前剩余事项

1. 确认 MySCU 用户名与 `mak1noo` 的关系，必要时同步目录、ZIP 和仓库名。
2. 提交 WakaTime 报告。
3. 录制不超过 5 分钟的视频并上传到 SCU OneDrive。
4. 按实际使用情况填写 GenAI declaration。
5. 如需评阅，邀请 tutor 和 unit assessor 加入私有仓库。

## 学术诚信

作业 PDF 规定不能使用 GenAI 代写解决方案代码、代码注释或视频内容。提交前应
确保所有内容符合课程规则、能够独立解释每一行代码，并真实保留 GitHub 与
WakaTime 记录。
