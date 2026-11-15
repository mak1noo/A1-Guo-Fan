# 修改记录

## 2026-11-15

- 将 GitHub 仓库地址切换为 `mak1noo/A1-Guo-Fan`。
- 新增 `TimeSlot` 值对象，使用小时和分钟保存预约时间。
- 将时间排序改为按一天中的分钟数比较，不再依赖字符串顺序。
- 将每日日程明确为 08:00–15:30，共 16 个可预约槽。
- 明确 16:00 是关门时间，不能作为预约起点。
- 增加模拟当前日 `MONDAY`，并拒绝其他日期的 AppointmentManager。
- 在 Appointment 构造器中验证时间是否属于固定日程。
- 手机号输入允许空格和连字符，保存前统一规范化。
- 新增 `cancelAppointmentByPhone(String mobile)`。
- 手机号匹配多条预约时返回歧义警告。
- 保留手机号加时间的精确取消重载。
- 缩短 Appointment 输出，避免嵌套打印完整专业人员对象。
- 为公共类、方法、参数、返回值和常量补全英文 Javadoc。
- 增加 `OopContractCheck` 本地契约测试。
- 增加 Java 17 `--release 17 -Xlint:all -Werror` 验证。
- 更新 README、DESIGN、HANDOFF、视频口播稿和 PPT。
- 生成不包含 `.class`、测试或无关同事项目的提交 ZIP。

## 验证结果

- Java 17 编译通过，零警告。
- 契约测试输出 `CONTRACT CHECK PASSED`。
- `javadoc -Xdoclint:all` 零警告。
- 主程序正常和错误案例运行通过。
- 最终剩余预约数为 3。
- Module 1–3 白名单扫描通过。
